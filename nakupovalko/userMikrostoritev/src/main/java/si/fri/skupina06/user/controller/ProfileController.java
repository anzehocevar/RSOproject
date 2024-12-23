package si.fri.skupina06.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import si.fri.skupina06.user.entity.User;
import si.fri.skupina06.user.controller.UserController;
import si.fri.skupina06.user.service.UserService;

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
