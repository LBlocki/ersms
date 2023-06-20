package pl.ersms.core.infrastructure.queue;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;

@RequiredArgsConstructor
@Slf4j
public class AzureServiceBus implements QueueGateway {

    private final JmsTemplate jmsTemplate;

    @Override
    public void sendMessageAboutReservationBeingApproved(String menuItemName, String restaurantName, String userId) throws JsonProcessingException {
        log.info("[Azure Service Bus] Sending message about reservation being approved for user {}", userId);
        jmsTemplate.convertAndSend("reservation-approved",
                "SecondBite here. Your reservation for item " +
                        menuItemName + " from restaurant " + restaurantName + "has been approved.");
    }
}
