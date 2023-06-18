package pl.ersms.core.web.request;

import jakarta.validation.constraints.NotNull;

public record ChangeMenuItemStateByClientRequest(
        @NotNull(message = "Menu item ID cannot be null")
        Long menuItemId,
        @NotNull(message = "Change cannot be null, must be one of: RESERVE, CANCEL")
        Change change
) {

    public enum Change {
        RESERVE,
        CANCEL
    }
}
