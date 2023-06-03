package pl.ersms.core.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.ersms.core.domain.MenuItem;
import pl.ersms.core.domain.Restaurant;
import pl.ersms.core.infrastructure.mssql.MenuItemRepository;
import pl.ersms.core.infrastructure.mssql.RestaurantRepository;
import pl.ersms.core.infrastructure.queue.QueueGateway;
import pl.ersms.core.security.SecurityService;
import pl.ersms.core.service.validator.RestaurantValidator;
import pl.ersms.core.web.converter.MenuItemConverter;
import pl.ersms.core.web.converter.RestaurantConverter;
import pl.ersms.core.web.dto.MenuItemDTO;
import pl.ersms.core.web.dto.RestaurantDTO;
import pl.ersms.core.web.request.ChangeMenuItemStateByRestaurantRequest;
import pl.ersms.core.web.request.CreateNewMenuItemRequest;
import pl.ersms.core.web.request.CreateNewRestaurantRequest;
import pl.ersms.core.web.request.FetchRestaurantMenuItemsRequest;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Collection;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantValidator restaurantValidator;
    private final RestaurantRepository restaurantRepository;
    private final SecurityService securityService;
    private final MenuItemRepository menuItemRepository;
    private final QueueGateway queueGateway;

    @Transactional
    public MenuItemDTO createNewMenuItem(CreateNewMenuItemRequest request) {
        restaurantValidator.validateCreateNewMenuItemRequest(request);
        log.debug("Creating new menu item for user {} with request {}", securityService.getUsername(), request);
        var menuItem = menuItemRepository.save(buildMenuItemFromCreateNewMenuItemRequest(request));
        return MenuItemConverter.convertMenuItemToDTO(menuItem, MenuItem.State.AVAILABLE);
    }

    @Transactional
    public RestaurantDTO createNewRestaurant(final CreateNewRestaurantRequest request) {
        restaurantValidator.validateCreateNewRestaurantRequest(request);
        log.debug("Creating new restaurant for user {} with request {}", securityService.getUsername(), request);
        var restaurant = restaurantRepository.save(buildRestaurantFromCreateNewRestaurantRequest(request));
        return RestaurantConverter.convertRestaurantToDTO(restaurant);
    }

    @Transactional
    public Collection<MenuItemDTO> retrieveMenuItemsFromRestaurantWithState(final FetchRestaurantMenuItemsRequest request) {
        restaurantValidator.validateFetchRestaurantMenuItemsRequest(request);
        log.debug("Retrieving menu items from restaurant with for user {} and state {}", securityService.getUsername(), request.state());
        var restaurantId = restaurantRepository.findByUserId(securityService.getUsername()).orElseThrow().getRestaurantId();
        return switch (request.state()) {
            case AVAILABLE ->
                    menuItemRepository.findByRestaurant_RestaurantIdAndPendingUserIdNull(restaurantId).stream()
                            .map(menuItem -> MenuItemConverter.convertMenuItemToDTO(menuItem, MenuItem.State.AVAILABLE))
                            .collect(Collectors.toSet());
            case PENDING ->
                    menuItemRepository.findByRestaurant_RestaurantIdAndPendingUserIdNotNullAndAcceptedUserIdNull(restaurantId).stream()
                            .map(menuItem -> MenuItemConverter.convertMenuItemToDTO(menuItem, MenuItem.State.PENDING))
                            .collect(Collectors.toSet());
            case RESERVED ->
                    menuItemRepository.findByRestaurant_RestaurantIdAndAcceptedUserIdNotNullAndCollectedDateNull(restaurantId).stream()
                            .map(menuItem -> MenuItemConverter.convertMenuItemToDTO(menuItem, MenuItem.State.RESERVED))
                            .collect(Collectors.toSet());
            case FINISHED ->
                    menuItemRepository.findByRestaurant_RestaurantIdAndAcceptedUserIdNotNullAndCollectedDateNotNull(restaurantId).stream()
                            .map(menuItem -> MenuItemConverter.convertMenuItemToDTO(menuItem, MenuItem.State.FINISHED))
                            .collect(Collectors.toSet());
        };
    }

    @Transactional
    public MenuItemDTO changeMenuItemStateByRestaurant(final ChangeMenuItemStateByRestaurantRequest request) {
        restaurantValidator.validateChangeMenuItemStateByRestaurantRequest(request);
        log.debug("Switching menu item state for user {} and request {}", securityService.getUsername(), request);
        var menuItem = menuItemRepository.findById(request.menuItemId()).orElseThrow();
        switch (request.change()) {
            case APPROVE -> {
                menuItem.setAcceptedUserId(securityService.getUsername());
                menuItem.setAcceptedDate(Timestamp.valueOf(LocalDateTime.now(ZoneOffset.UTC)));
                var newMenuItem = MenuItemConverter.convertMenuItemToDTO(menuItemRepository.save(menuItem), MenuItem.State.RESERVED);
                queueGateway.sendMessageAboutReservationBeingApproved(menuItem.getName(),
                        menuItem.getRestaurant().getName(), securityService.getUsername());
                return newMenuItem;
            }
            case DENY -> {
                menuItem.setPendingUserId(null);
                return MenuItemConverter.convertMenuItemToDTO(menuItemRepository.save(menuItem), MenuItem.State.PENDING);
            }
            case COMPLETE -> {
                menuItem.setCollectedDate(Timestamp.valueOf(LocalDateTime.now(ZoneOffset.UTC)));
                return MenuItemConverter.convertMenuItemToDTO(menuItemRepository.save(menuItem), MenuItem.State.FINISHED);
            }
            default -> throw new IllegalArgumentException("Unknown change type");
        }
    }

    @Transactional
    public void deleteRestaurant() {
        restaurantValidator.validateDeleteRestaurantRequest();
        log.debug("Deleting restaurant for user {}", securityService.getUsername());
        var restaurantId = restaurantRepository.findByUserId(securityService.getUsername()).orElseThrow().getRestaurantId();
        restaurantRepository.deleteByRestaurantId(restaurantId);
    }

    @Transactional
    public void deleteMenuItem(final Long menuItemId) {
        restaurantValidator.validateDeleteMenuItemRequest(menuItemId);
        log.debug("Deleting menu item with id {} for user {}", menuItemId, securityService.getUsername());
        menuItemRepository.deleteById(menuItemId);
    }

    private Restaurant buildRestaurantFromCreateNewRestaurantRequest(@NonNull final CreateNewRestaurantRequest request) {
        var restaurant = new Restaurant();
        restaurant.setName(request.name());
        restaurant.setAddressCity(request.addressCity());
        restaurant.setAddressStreet(request.addressStreet());
        restaurant.setAddressBuildingNumber(request.addressBuildingNumber());
        restaurant.setAddressFlatNumber(request.addressFlatNumber());
        restaurant.setPhoneNumber(request.phoneNumber());
        restaurant.setIsApproved(false);
        restaurant.setUserId(securityService.getUsername());
        return restaurant;
    }

    private MenuItem buildMenuItemFromCreateNewMenuItemRequest(@NonNull final CreateNewMenuItemRequest request) {
        var restaurant = restaurantRepository.findByUserId(securityService.getUsername()).orElseThrow();
        var menuItem = new MenuItem();
        menuItem.setName(request.name());
        menuItem.setPrice(request.price());
        menuItem.setDescription(request.description());
        menuItem.setRestaurant(restaurant);
        return menuItem;
    }
}
