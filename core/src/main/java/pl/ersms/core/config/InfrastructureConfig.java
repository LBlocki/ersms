package pl.ersms.core.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsTemplate;
import pl.ersms.core.infrastructure.queue.AzureServiceBus;
import pl.ersms.core.infrastructure.queue.MockQueueGateway;
import pl.ersms.core.infrastructure.queue.QueueGateway;

@Slf4j
@Configuration
@RequiredArgsConstructor
@EnableJms
public class InfrastructureConfig {

    private final JmsTemplate jmsTemplate;

    @Bean
    @ConditionalOnProperty(name = "mock.queue", havingValue = "true")
    public QueueGateway mockQueueGateway() {
        log.info("[MOCK] Using mock queue gateway");
        return new MockQueueGateway();
    }

    @Bean
    @ConditionalOnProperty(name = "mock.queue", havingValue = "false", matchIfMissing = true)
    public QueueGateway queueGateway() {
        log.info("Using real queue gateway");
        return new AzureServiceBus(jmsTemplate);
    }
}
