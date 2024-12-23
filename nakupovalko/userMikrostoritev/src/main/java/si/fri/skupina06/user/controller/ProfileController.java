package si.fri.skupina06.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProfileController {

    private int loggedInUser = 2;


    @GetMapping("/profile")
    public String profile() {
        return "profile";
    }


    public int getLoggedInUser() {
        return loggedInUser;
    }

}
