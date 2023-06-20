package pl.ersms.core.infrastructure.queue;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface QueueGateway {

    void sendMessageAboutReservationBeingApproved(String menuItemName, String restaurantName, String userId) throws JsonProcessingException;
}
