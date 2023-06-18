package pl.ersms.core.web.dto;


import lombok.NonNull;

public record RestaurantDTO(
        @NonNull
        Long id,
        @NonNull
        String name,
        @NonNull
        String addressCity,
        @NonNull
        String addressStreet,
        @NonNull
        String addressBuildingNumber,
        String addressFlatNumber,
        String phoneNumber,
        @NonNull
        Boolean isApproved
) {
}
