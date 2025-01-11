package si.fri.skupina06.user.service;

import io.github.resilience4j.retry.RetryRegistry;
import jakarta.annotation.PostConstruct;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RetryRegistryEventListener {
    @Autowired
    private RetryRegistry registry;

    private static final Logger logger = LogManager.getLogger(RetryRegistryEventListener.class);


    @PostConstruct
    public void postConstruct() {
        //registry.retry(<resilience retry instance name>)
        registry.retry("userServiceTestRetry").getEventPublisher()
                .onRetry(ev -> logger.info("#### RetryRegistryEventListener message: {}", ev));
    }
}
