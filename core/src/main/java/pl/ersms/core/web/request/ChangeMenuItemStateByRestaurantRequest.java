package pl.ersms.core.web.request;

import jakarta.validation.constraints.NotNull;

public record ChangeMenuItemStateByRestaurantRequest(
        @NotNull(message = "Menu item ID cannot be null")
        Long menuItemId,
        @NotNull(message = "Change cannot be null, must be one of: APPROVE, DENY, COMPLETE")
        Change change) {

    public enum Change {
        APPROVE,
        DENY,
        COMPLETE
    }
}
