package pl.ersms.core.infrastructure.queue;

public interface QueueGateway {

    void sendMessageAboutReservationBeingApproved(String menuItemName, String restaurantName, String userId);
}
