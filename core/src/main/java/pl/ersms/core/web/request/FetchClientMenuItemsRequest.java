package pl.ersms.core.web.request;

import jakarta.validation.constraints.NotNull;

public record FetchClientMenuItemsRequest(
        @NotNull(message = "State cannot be null")
        StateDTO state
) {

    public enum StateDTO {
        PENDING,
        RESERVED,
        FINISHED
    }
}
