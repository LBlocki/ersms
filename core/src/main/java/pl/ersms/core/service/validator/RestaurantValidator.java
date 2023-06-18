package pl.ersms.core.service.validator;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import pl.ersms.core.domain.MenuItem;
import pl.ersms.core.infrastructure.mssql.RestaurantRepository;
import pl.ersms.core.security.SecurityService;
import pl.ersms.core.web.request.ChangeMenuItemStateByRestaurantRequest;
import pl.ersms.core.web.request.CreateNewMenuItemRequest;
import pl.ersms.core.web.request.CreateNewRestaurantRequest;
import pl.ersms.core.web.request.FetchRestaurantMenuItemsRequest;

import java.util.Collections;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class RestaurantValidator {

    private final RestaurantRepository restaurantRepository;
    private final SecurityService securityService;
    private final Validator validator;

    public void validateCreateNewMenuItemRequest(final CreateNewMenuItemRequest request) {
        if (!securityService.isAdmin() && !securityService.isRestaurantOwner()) {
            throw new AccessDeniedException("Only restaurant owners and admins can add menu items.");
        }
        validateNotNullRequest(request);
        var result = validator.validate(request);
        if (!result.isEmpty()) {
            throw new ConstraintViolationException(result);
        }
        var restaurant = restaurantRepository.findByUserId(securityService.getUsername());
        if (restaurant.isEmpty()) {
            throw new ConstraintViolationException("User with username " + securityService.getUsername() +
                    " does not have a restaurant" +
                    ". Only restaurant owners can add menu items.", Collections.emptySet());
        }
        if (!restaurant.get().getIsApproved()) {
            throw new ConstraintViolationException("Restaurant with id " + restaurant.get().getRestaurantId() +
                    " is not approved. Only approved restaurants can add menu items.", Collections.emptySet());
        }
    }

    public void validateFetchRestaurantMenuItemsRequest(final FetchRestaurantMenuItemsRequest request) {
        if (!securityService.isAdmin() && !securityService.isRestaurantOwner()) {
            throw new AccessDeniedException("Only restaurant owners and admins can fetch restaurant menu items.");
        }
        validateNotNullRequest(request);
        var result = validator.validate(request);
        if (!result.isEmpty()) {
            throw new ConstraintViolationException(result);
        }
        var restaurant = restaurantRepository.findByUserId(securityService.getUsername());
        if (restaurant.isEmpty()) {
            throw new ConstraintViolationException("User with username " + securityService.getUsername() +
                    " does not have a restaurant" +
                    ". Only restaurant owners can fetch menu items.", Collections.emptySet());
        }
    }

    public void validateFetchOwnRestaurant() {
        if (!securityService.isRestaurantOwner()) {
            throw new AccessDeniedException("Only restaurant owners can fetch their restaurant.");
        }
        var restaurant = restaurantRepository.findByUserId(securityService.getUsername());
        if (restaurant.isEmpty()) {
            throw new ConstraintViolationException("User with username " + securityService.getUsername() +
                    " does not have a restaurant yet", Collections.emptySet());
        }
    }

    public void validateCreateNewRestaurantRequest(final CreateNewRestaurantRequest request) {
        if (!securityService.isAdmin() && !securityService.isRestaurantOwner()) {
            throw new AccessDeniedException("Only restaurant owners and admins can create restaurants.");
        }
        validateNotNullRequest(request);
        var result = validator.validate(request);
        if (!result.isEmpty()) {
            throw new ConstraintViolationException(result);
        }
        var restaurant = restaurantRepository.findByUserId(securityService.getUsername());
        if (restaurant.isPresent()) {
            throw new ConstraintViolationException("User with username " + securityService.getUsername() +
                    " already has a restaurant" +
                    ". Only one restaurant per user is allowed.", Collections.emptySet());
        }
        if (restaurantRepository.existsByName(request.name())) {
            throw new ConstraintViolationException("Restaurant with name " + request.name() +
                    " already exists", Collections.emptySet());
        }
    }

    private void validateNotNullRequest(final Object request) {
        if (Objects.isNull(request)) {
            throw new ConstraintViolationException("Request cannot be null", Collections.emptySet());
        }
    }

    public void validateDeleteRestaurantRequest() {
        if (!securityService.isAdmin() && !securityService.isRestaurantOwner()) {
            throw new AccessDeniedException("Only restaurant owners and admins can delete restaurants.");
        }
        var restaurant = restaurantRepository.findByUserId(securityService.getUsername());
        if (restaurant.isEmpty()) {
            throw new ConstraintViolationException("User with username " + securityService.getUsername() +
                    " does not have a restaurant to delete", Collections.emptySet());
        }
    }

    public void validateDeleteMenuItemRequest(final Long menuItemId) {
        if (!securityService.isAdmin() && !securityService.isRestaurantOwner()) {
            throw new AccessDeniedException("Only restaurant owners and admins can delete menu items.");
        }
        validateNotNullRequest(menuItemId);
        var restaurant = restaurantRepository.findByUserId(securityService.getUsername());
        if (restaurant.isEmpty()) {
            throw new ConstraintViolationException("User with username " + securityService.getUsername() +
                    " does not have a restaurant to delete menu item from", Collections.emptySet());
        }
        if (restaurant.get().getMenuItems().stream().map(MenuItem::getId).noneMatch(id -> id.equals(menuItemId))) {
            throw new ConstraintViolationException("User with username " + securityService.getUsername() +
                    " does not have a menu item with id " + menuItemId + " to delete", Collections.emptySet());
        }
        if (!restaurant.get().getIsApproved()) {
            throw new ConstraintViolationException("Restaurant with id " + restaurant.get().getRestaurantId() +
                    " is not approved. Only approved restaurants can add menu items.", Collections.emptySet());
        }
    }

    public void validateChangeMenuItemStateByRestaurantRequest(final ChangeMenuItemStateByRestaurantRequest request) {
        if (!securityService.isAdmin() && !securityService.isRestaurantOwner()) {
            throw new AccessDeniedException("Only restaurant owners and admins can restaurant menu item states.");
        }
        validateNotNullRequest(request);
        var result = validator.validate(request);
        if (!result.isEmpty()) {
            throw new ConstraintViolationException(result);
        }
        var restaurant = restaurantRepository.findByUserId(securityService.getUsername());
        if (restaurant.isEmpty()) {
            throw new ConstraintViolationException("User with username " + securityService.getUsername() +
                    " does not have a restaurant" +
                    ". Only restaurant owners can change menu item states.", Collections.emptySet());
        }
        if (!restaurant.get().getIsApproved()) {
            throw new ConstraintViolationException("Restaurant with id " + restaurant.get().getRestaurantId() +
                    " is not approved. Only approved restaurants can change menu item states.", Collections.emptySet());
        }
        if (restaurant.get().getMenuItems().stream().map(MenuItem::getId).noneMatch(id -> id.equals(request.menuItemId()))) {
            throw new ConstraintViolationException("User with username " + securityService.getUsername() +
                    " does not have a menu item with id " + request.menuItemId() + " to change state", Collections.emptySet());
        }
        var menuItem = restaurant.get().getMenuItems().stream()
                .filter(item -> item.getId().equals(request.menuItemId()))
                .findFirst()
                .orElseThrow();
        switch (request.change()) {
            case APPROVE, DENY -> {
                if (Objects.isNull(menuItem.getPendingUserId())) {
                    throw new ConstraintViolationException("Menu item with id " + request.menuItemId() +
                            " does not have pending status defined", Collections.emptySet());
                }
                if (Objects.nonNull(menuItem.getAcceptedUserId())) {
                    throw new ConstraintViolationException("Menu item with id " + request.menuItemId() +
                            " is already approved", Collections.emptySet());
                }
            }
            case COMPLETE -> {
                if (Objects.isNull(menuItem.getAcceptedUserId())) {
                    throw new ConstraintViolationException("Menu item with id " + request.menuItemId() +
                            " is not approved", Collections.emptySet());
                }
                if (Objects.nonNull(menuItem.getCollectedDate())) {
                    throw new ConstraintViolationException("Menu item with id " + request.menuItemId() +
                            " is already completed", Collections.emptySet());
                }
            }
        }
    }
}
