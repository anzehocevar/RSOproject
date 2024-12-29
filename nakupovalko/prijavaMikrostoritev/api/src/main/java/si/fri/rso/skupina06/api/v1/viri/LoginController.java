package si.fri.rso.skupina06.api.v1.viri;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    private static final Logger logger = LogManager.getLogger(LoginController.class);

    @GetMapping("/nakupovalko-login")
    public String login() {
        logger.info("Getting login screen.");
        return "login";
    }

    @GetMapping("/home")
    @PreAuthorize("hasRole('admin')")
    public String adminEndpoint() {
        logger.info("Getting admin endpoint.");
        return "admin";
    }
}