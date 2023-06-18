package pl.ersms.core.web.converter;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import pl.ersms.core.domain.Restaurant;
import pl.ersms.core.web.dto.RestaurantDTO;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RestaurantConverter {

    public static RestaurantDTO convertRestaurantToDTO(@NonNull final Restaurant restaurant) {
        return new RestaurantDTO(
                restaurant.getRestaurantId(),
                restaurant.getName(),
                restaurant.getAddressCity(),
                restaurant.getAddressStreet(),
                restaurant.getAddressBuildingNumber(),
                restaurant.getAddressFlatNumber(),
                restaurant.getPhoneNumber(),
                restaurant.getIsApproved()
        );
    }
}
