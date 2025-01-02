package si.fri.rso.skupina06.prijava.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;
import si.fri.rso.skupina06.prijava.controller.LoginController;

@Slf4j
@Component
public class LoginHealthChecker implements HealthIndicator {

    @Autowired
    private LoginController loginController;

    private static final Logger logger = LogManager.getLogger(LoginHealthChecker.class);

    @Override
    public Health health() {


        if (!loginControllerLoginHealth()) {
            return Health.down().withDetail("loginController", "Get login endpoint service unavailable").build();
        }

        return Health.up().withDetail("loginController", "All dependencies are healthy").build();
    }

    private boolean loginControllerLoginHealth() {
        try {
            return loginController.login().equals("login");

        } catch (Exception e) {
            return false;
        }
    }


}


