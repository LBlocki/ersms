package pl.ersms.core.infrastructure.queue;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MockQueueGateway implements QueueGateway {
    @Override
    public void sendMessageAboutReservationBeingApproved(@NonNull final String menuItemName,
                                                         @NonNull final String restaurantName,
                                                         @NonNull final String userId) {
        log.info("[MOCK Queue] Sending message about menuItem {} from restaurant {} being approved to user {}",
                menuItemName, restaurantName, userId);
    }
}
