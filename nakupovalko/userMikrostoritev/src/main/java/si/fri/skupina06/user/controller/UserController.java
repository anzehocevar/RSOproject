package si.fri.skupina06.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import si.fri.skupina06.user.entity.User;
import si.fri.skupina06.user.service.UserService;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private ProfileController profileController;

    @PostMapping
    public User addUser(@RequestBody User user) {
        return userService.addUser(user);
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    // Get user with GET request
    @GetMapping("/{id}")
    public User getUserById(@PathVariable int id) {
        return userService.getUserById(id);
    }

    // Update user with PUT request
    @PutMapping("/{id}")
    public User updateUser(@PathVariable int id, @RequestBody User updatedUser) {
        return userService.updateUser(id, updatedUser);
    }

    // Delete user with DELETE request
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
        return "User with ID " + id + " has been deleted successfully.";
    }

    @GetMapping("/active/id")
    public int getLoggedInUserId() {
        int id = profileController.getLoggedInUser();
        return userService.getUserById(id).getId();
    }

    @GetMapping("/active")
    public Dictionary<String, String> getLoggedInUser() {
        int id = profileController.getLoggedInUser();
        User loggedInUser = userService.getUserById(id);

        Dictionary<String, String> user = new Hashtable<>();
        user.put("name", loggedInUser.getName());
        user.put("email", loggedInUser.getEmail());
        user.put("surname", loggedInUser.getSurname());
        user.put("username", loggedInUser.getUsername());

        return user;
    }

}