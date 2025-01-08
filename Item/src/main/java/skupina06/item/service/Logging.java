package skupina06.item.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Logging {
    private static final Logger logger = LoggerFactory.getLogger(Logger.class);

    //
    public void doSomething() {
        logger.info("Processing request {}", System.currentTimeMillis());
        logger.error("An error occurred while processing request");
    }
}
