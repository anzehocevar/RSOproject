package si.fri.rso.skupina06.api.v1.viri;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/nakupovalko-login")
    public String login() {
        return "login";
    }

    @GetMapping("/home")
    @PreAuthorize("hasRole('admin')")
    public String adminEndpoint() {
        return "admin";
    }
}