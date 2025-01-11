package si.fri.skupina06.user.service.health;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;
import si.fri.skupina06.user.entity.User;
import si.fri.skupina06.user.service.UserService;

import java.util.List;


@Component
public class UserHealthChecker implements HealthIndicator {
    @Autowired
    private UserService userService;

    @Override
    public Health health() {
        if (!testGetUser()) {
            return Health.down().withDetail("UserService", "Get Service Unavailable").build();
        }

        if(!testAddUser()) {
            return Health.down().withDetail("UserService", "Add Service Unavailable").build();
        }

        if(!testUpdateUser()) {
            return Health.down().withDetail("UserService", "Update Service Unavailable").build();
        }

        if(!testDeleteUser()) {
            return Health.down().withDetail("UserService", "Delete Service Unavailable").build();
        }

        return Health.up().withDetail("UserController", "All dependencies are healthy").build();
    }

    private boolean testGetUser() {
        try {
            userService.getAllUsers();
            userService.getUserById(1);
        } catch (Exception e) {
            return false;
        }

        if(userService.getAvatarUrlById(1) == null) {
            return false;
        }

        // this user shouldn't exist and should throw an error
        try {
            userService.getUserById(-1);
            return false;
        } catch (Exception e) {
            return true;
        }
    }

    private boolean testAddUser() {
        String user = " {\n" +
                "  \"name\": \"Janze\",\n" +
                "  \"surname\": \"Novak\",\n" +
                "  \"email\": \"jnovak@gmail.com\",\n" +
                "  \"username\": \"jankon\"\n" +
                "}\n";

        String incorrectUser = " {\n" +
                "  \"name\": \"Janze\",\n" +
                "  \"surname\": \"Novak\",\n" +
                "}\n";

        // add incorrect user
        try {
            userService.addUser(incorrectUser);
            Thread.sleep(10000);
            return false;
        } catch (Exception e) {
        }

        /**
         * If the further dependencies failed previously, we must first delete user that was previously inserted
         * else adding the user will fail due to emails and usernames needing to be unique.
         */
        try{
            int id = userService.getUserByUsername("jankon").getId();
            userService.deleteUser(id);
        } catch (Exception e){
            //no user exists
        }


        // add correct user
        try {
            userService.addUser(user);

        } catch (Exception e) {
            return false;
        }
        return true;
    }

    private boolean testUpdateUser() {

        String updatedUser = " {\n" +
                "  \"name\": \"Janzej\",\n" +
                "  \"surname\": \"Novaki\",\n" +
                "  \"email\": \"jnovak@gmail.com\",\n" +
                "  \"username\": \"jankon\"\n" +
                "}\n";

        int id = userService.getUserByUsername("jankon").getId();

        try {
            userService.updateUser(id, updatedUser);
            User u = userService.getUserById(id);
            System.out.println(u.getName());
            if(!u.getName().equals("Janzej")) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }

        try {
            userService.updateUser(-1, updatedUser);
            return false;
        } catch (Exception e) {}

        return true;
    }

    public boolean testDeleteUser() {
        int id = userService.getUserByUsername("jankon").getId();
        try {
            userService.deleteUser(-1);
            return false;
        } catch (Exception e) {}

        try {
            userService.deleteUser(id);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}



