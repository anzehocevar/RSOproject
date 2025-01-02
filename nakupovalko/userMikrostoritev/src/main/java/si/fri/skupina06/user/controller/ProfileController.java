package si.fri.skupina06.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.client.RestTemplate;
import si.fri.skupina06.user.service.UserService;

import java.util.Map;

@Controller
public class ProfileController {

    private static final Logger logger = LogManager.getLogger(ProfileController.class);

    @Autowired
    private UserService userService;


    @GetMapping("/profile")
    public String profile() {
        return "profile";
    }


    public int getLoggedInUser() {
        logger.log(Level.INFO, "Getting logged in user");

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<Map> response = restTemplate.getForEntity("http://localhost:8081/api/logged-in-user", Map.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            Map<String, Object> responseBody = response.getBody();

            if (responseBody != null && responseBody.containsKey("email")) {
                String loggedInUserEmail = (String) responseBody.get("email");
                return userService.getIdByEmail(loggedInUserEmail);
            } else {
                logger.warn("Expected key 'email' not found in the response.");
                return -1;
            }
        } else {
            logger.warn("Failed to get logged in user, response status: {}", response.getStatusCode());
            return -1;
        }
    }

}
