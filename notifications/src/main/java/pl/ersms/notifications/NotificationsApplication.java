package pl.ersms.notifications;

import com.azure.messaging.servicebus.*;
import com.azure.messaging.servicebus.ServiceBusProcessorClient;
import com.azure.messaging.servicebus.ServiceBusReceivedMessage;
import com.azure.messaging.servicebus.ServiceBusProcessorClientBuilder;
import com.azure.communication.sms.*;
import com.azure.communication.sms.models.*;
import com.azure.identity.ClientSecretCredentialBuilder;
import com.azure.identity.DefaultAzureCredentialBuilder;
import com.azure.identity.ManagedIdentityCredentialBuilder;
import com.azure.identity.UsernamePasswordCredentialBuilder;
import com.azure.spring.autoconfigure.aad.UserPrincipal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class NotificationsApplication {

    private static final String CONNECTION_STRING = "<ServiceBusConnectionString>";
    private static final String QUEUE_NAME = "<QueueName>";
    private static final String COMMUNICATION_CONNECTION_STRING = "<CommunicationServicesConnectionString>";
    private static final String ADB2C_CLIENT_ID = "<ADB2CClientId>";
    private static final String ADB2C_CLIENT_SECRET = "<ADB2CClientSecret>";
    private static final String ADB2C_TENANT_ID = "<ADB2CTenantId>";

    public static void main(String[] args) {
        SpringApplication.run(NotificationsApplication.class, args);

        // Start listening to the Service Bus queue
        receiveMessagesFromQueue();
    }

    private static void receiveMessagesFromQueue() {
        // Create an instance of the Service Bus ProcessorClient
        ServiceBusProcessorClient processorClient = new ServiceBusProcessorClientBuilder()
                .connectionString(CONNECTION_STRING)
                .processor()
                .queueName(QUEUE_NAME)
                .processMessage(NotificationsApplication::processMessage)
                .processError(context -> {
                    System.err.println("Error occurred. Error details: " + context.getException());
                })
                .buildProcessorClient();

        // Start processing messages and wait for a stop signal
        processorClient.start();
        System.out.println("Listening to messages from the Service Bus queue...");

        // Wait for a stop signal (e.g., via interruption or termination signal)
        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Stop processing messages
        processorClient.stop();
    }

    private static void processMessage(ServiceBusReceivedMessage message) {
        String messageId = message.getMessageId();
        String messageBody = message.getBody().toString();

        // Read user data from Azure AD B2C based on the received message (e.g., extract user ID)

        // Use Azure AD B2C SDK or REST API to fetch user data
        // Example:
        // UserPrincipal user = getUserDataFromADB2C(userId);

        // Send a notification using Azure Communication Services based on the user data
        sendNotification(messageId, messageBody, user);
    }

    private static void sendNotification(String messageId, String messageBody, UserPrincipal user) {
        // Create an instance of the SMS client using the Communication Services connection string
        SmsClient smsClient = new SmsClientBuilder()
                .connectionString(COMMUNICATION_CONNECTION_STRING)
                .buildClient();

        // Compose the notification message
        SmsSendResult sendResult = smsClient.sendMessage(
                new PhoneNumber(user.getPhoneNumber()),
                new PhoneNumber("<SenderPhoneNumber>"),
                messageBody);

        // Handle the send result (e.g., check if the message was successfully sent)
        if (sendResult.isSuccessful()) {
            System.out.println("Notification sent successfully. Message ID: " + messageId);
        } else {
            System.out.println("Failed to send notification. Message ID: " + messageId +
                    "Error details: " + sendResult.getErrorMessage());
        }
    }

    private static UserPrincipal getUserDataFromADB2C(String userId) {
        // Use Azure AD B2C SDK or REST API to fetch user data based on the user ID
        // Example:
        // Use the ADB2C_CLIENT_ID, ADB2C_CLIENT_SECRET, and ADB2C_TENANT_ID to authenticate and authorize the request
        // UserPrincipal user = ...

        // Return the user data
        // return user;
        return null;
    }
}
