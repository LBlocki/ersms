package pl.ersms.core.infrastructure.queue;

import lombok.NonNull;

public record ReservationApprovedMessage(@NonNull String menuItemName, @NonNull String restaurantName,
                                         @NonNull String userId) {
}
