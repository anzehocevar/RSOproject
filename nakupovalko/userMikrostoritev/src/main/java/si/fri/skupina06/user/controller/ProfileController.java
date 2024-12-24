package si.fri.skupina06.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Controller
public class ProfileController {

    private static final Logger logger = LogManager.getLogger(ProfileController.class);

    private int loggedInUser = 2;


    @GetMapping("/profile")
    public String profile() {
        return "profile";
    }


    public int getLoggedInUser() {
        logger.log(Level.INFO, "Getting logged in user");
        return loggedInUser;
    }

}
