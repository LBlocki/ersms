package pl.ersms.core.service.validator;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.ersms.core.infrastructure.mssql.MenuItemRepository;
import pl.ersms.core.security.SecurityService;
import pl.ersms.core.web.request.ChangeMenuItemStateByClientRequest;
import pl.ersms.core.web.request.FetchClientMenuItemsRequest;

import java.util.Collections;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class ClientValidator {

    private final SecurityService securityService;
    private final MenuItemRepository menuItemRepository;
    private final Validator validator;

    public void validateChangeMenuItemStateByClientRequest(final ChangeMenuItemStateByClientRequest request) {
        if (!securityService.isAdmin() && !securityService.isClient()) {
            throw new ConstraintViolationException("Only admins and clients can change menu item client state", Collections.emptySet());
        }
        validateNotNullRequest(request);
        var result = validator.validate(request);
        if (!result.isEmpty()) {
            throw new ConstraintViolationException(result);
        }
        var menuItem = menuItemRepository.findById(request.menuItemId());
        if (menuItem.isEmpty()) {
            throw new ConstraintViolationException("Menu item with id " + request.menuItemId() + " does not exist", Collections.emptySet());
        }
        switch (request.change()) {
            case RESERVE -> {
                if (Objects.nonNull(menuItem.get().getPendingUserId())) {
                    throw new ConstraintViolationException("Menu item with id " + request.menuItemId() + " is already reserved or in pending state", Collections.emptySet());
                }
            }
            case CANCEL -> {
                if (Objects.isNull(menuItem.get().getPendingUserId())) {
                    throw new ConstraintViolationException("Menu item with id " + request.menuItemId() + " is not reserved", Collections.emptySet());
                }
                if (!menuItem.get().getPendingUserId().equals(securityService.getUsername())) {
                    throw new ConstraintViolationException("Menu item with id " + request.menuItemId() + " is reserved by another user", Collections.emptySet());
                }
            }
        }
    }

    public void validateFetchAllAvailableMenuItemsByRestaurants() {
        if (!securityService.isAdmin() && !securityService.isClient()) {
            throw new ConstraintViolationException("Only admins and clients can fetch all available menu items", Collections.emptySet());
        }
    }

    public void validateFetchClientMenuItemsByState(final FetchClientMenuItemsRequest request) {
        if (!securityService.isAdmin() && !securityService.isClient()) {
            throw new ConstraintViolationException("Only admins and clients can fetch all client menu items by state", Collections.emptySet());
        }
        validateNotNullRequest(request);
    }

    private void validateNotNullRequest(final Object request) {
        if (Objects.isNull(request)) {
            throw new ConstraintViolationException("Request cannot be null", Collections.emptySet());
        }
    }
}
