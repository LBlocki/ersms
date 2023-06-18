package pl.ersms.core.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.ersms.core.domain.MenuItem;
import pl.ersms.core.infrastructure.mssql.MenuItemRepository;
import pl.ersms.core.security.SecurityService;
import pl.ersms.core.service.validator.ClientValidator;
import pl.ersms.core.web.converter.MenuItemConverter;
import pl.ersms.core.web.dto.MenuItemDTO;
import pl.ersms.core.web.request.ChangeMenuItemStateByClientRequest;
import pl.ersms.core.web.request.FetchClientMenuItemsRequest;

import java.util.Collection;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClientService {

    private final MenuItemRepository menuItemRepository;
    private final ClientValidator clientValidator;
    private final SecurityService securityService;

    public Collection<MenuItemDTO> fetchAvailableMenuItemsFromRestaurants() {
        clientValidator.validateFetchAllAvailableMenuItemsByRestaurants();
        log.debug("Fetching all available menu items from restaurants for user {}", securityService.getUsername());
        return menuItemRepository.findAllByPendingUserIdNull().stream()
                .map(menuItem -> MenuItemConverter.convertMenuItemToDTO(menuItem, MenuItem.State.AVAILABLE))
                .collect(Collectors.toSet());
    }

    public MenuItemDTO changeMenuItemStateByClient(final ChangeMenuItemStateByClientRequest request) {
        clientValidator.validateChangeMenuItemStateByClientRequest(request);
        log.debug("Changing menu item state for user {} with request {}", securityService.getUsername(), request);
        var menuItem = menuItemRepository.findById(request.menuItemId()).orElseThrow();
        switch (request.change()) {
            case RESERVE -> {
                menuItem.setPendingUserId(securityService.getUsername());
                return MenuItemConverter.convertMenuItemToDTO(menuItemRepository.save(menuItem), MenuItem.State.PENDING);
            }
            case CANCEL -> {
                menuItem.setPendingUserId(null);
                return MenuItemConverter.convertMenuItemToDTO(menuItemRepository.save(menuItem), MenuItem.State.AVAILABLE);
            }
            default -> throw new IllegalArgumentException("Unknown change type");
        }
    }

    public Collection<MenuItemDTO> fetchClientMenuItemsByState(final FetchClientMenuItemsRequest request) {
        clientValidator.validateFetchClientMenuItemsByState(request);
        var username = securityService.getUsername();
        log.debug("Fetching client menu items by state for user {} with request {}", username, request);
        switch (request.state()) {
            case PENDING -> {
                return menuItemRepository.findAllByPendingUserIdAndAcceptedUserIdIsNull(username).stream()
                        .map(menuItem -> MenuItemConverter.convertMenuItemToDTO(menuItem, MenuItem.State.PENDING))
                        .collect(Collectors.toSet());
            }
            case RESERVED -> {
                return menuItemRepository.findAllByAcceptedUserIdAndCollectedDateIsNull(username).stream()
                        .map(menuItem -> MenuItemConverter.convertMenuItemToDTO(menuItem, MenuItem.State.RESERVED))
                        .collect(Collectors.toSet());
            }
            case FINISHED -> {
                return menuItemRepository.findAllByAcceptedUserIdAndCollectedDateIsNotNull(username).stream()
                        .map(menuItem -> MenuItemConverter.convertMenuItemToDTO(menuItem, MenuItem.State.FINISHED))
                        .collect(Collectors.toSet());
            }
            default -> throw new IllegalArgumentException("Unknown state type");
        }
    }
}
