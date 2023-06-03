package pl.ersms.core.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.ersms.core.infrastructure.queue.MockQueueGateway;
import pl.ersms.core.infrastructure.queue.QueueGateway;

@Slf4j
@Configuration
public class InfrastructureConfig {

    @Bean
    @ConditionalOnProperty(name = "mock.queue", havingValue = "true")
    public QueueGateway mockQueueGateway() {
        log.info("[MOCK] Using mock queue gateway");
        return new MockQueueGateway();
    }
}
