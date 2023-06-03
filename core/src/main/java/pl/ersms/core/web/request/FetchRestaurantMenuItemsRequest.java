package pl.ersms.core.web.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;
import pl.ersms.core.web.dto.MenuItemDTO;

@JsonIgnoreProperties(ignoreUnknown = true)
public record FetchRestaurantMenuItemsRequest(
        @NotNull(message = "State cannot be null")
        MenuItemDTO.StateDTO state) {
}
