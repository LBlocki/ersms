package pl.ersms.core.web.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CreateNewRestaurantRequest(
        @NotBlank(message = "Name cannot be blank")
        @Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters")
        String name,
        @NotBlank(message = "Address city cannot be blank")
        @Size(min = 3, max = 50, message = "Address city must be between 3 and 50 characters")
        String addressCity,
        @NotBlank(message = "Address street cannot be blank")
        @Size(min = 3, max = 50, message = "Address street must be between 3 and 50 characters")
        String addressStreet,
        @NotBlank(message = "Address building number cannot be blank")
        @Size(min = 1, max = 50, message = "Address building number must be between 1 and 50 characters")
        String addressBuildingNumber,
        @Size(min = 0, max = 50, message = "Address flat number must be between 0 and 50 characters")
        String addressFlatNumber,
        @Size(min = 0, max = 50, message = "Phone number must be between 0 and 50 characters")
        String phoneNumber
) {
}
