package si.fri.skupina06.user.service.health;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;
import si.fri.skupina06.user.controller.ProfileController;

@Component
public class ProfileHealthChecker implements HealthIndicator {

    @Autowired
    private ProfileController profileController;
    @Override
    public Health health() {

        if (!checkProfileControllerHealth()) {
            return Health.down().withDetail("ProfileController", "Service Unavailable").build();
        }

        return Health.up().withDetail("ProfileController", "All dependencies are healthy").build();
    }

    private boolean checkProfileControllerHealth() {
        try {
            profileController.getLoggedInUser();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
