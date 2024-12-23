package si.fri.skupina06.user.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import io.swagger.v3.core.util.Json;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

    @Operation(
            summary = "Dodaj uporabnika",
            description = "Doda objekt Uporabnik med vse uporabnike.",
            responses = {
                    @ApiResponse(
                            description = "User added",
                            responseCode = "200",
                            content = @Content(
                                    schema = @Schema(implementation = User.class)
                            )
                    ),
                    @ApiResponse(
                            description = "User not added. Check if all fields are provided.",
                            responseCode= "500"
                    )
            }
    )
    @PostMapping
    public User addUser(@RequestBody JsonNode user) throws JsonProcessingException {
        return userService.addUser(user.toString());
    }

    @Operation(
            summary = "Pridobi uporabnike",
            description = "Pridobi vse obstoječe uporabnike.",
            responses = {
                    @ApiResponse(
                            description = "Users found",
                            responseCode = "200",
                            content = @Content(
                                    schema = @Schema(implementation = User.class)
                            )
                    )
            }
    )
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @Operation(
            summary = "Pridobi uporabnika na podlagi ID",
            description = "Pridobi objekt uporabnika na podlagi njegove ID številke",
            responses = {
                    @ApiResponse(
                            description = "User found",
                            responseCode = "200",
                            content = @Content(
                                    schema = @Schema(implementation = User.class)
                            )
                    ),
                    @ApiResponse(
                            description = "User not found",
                            responseCode = "404"
                    )
            }
    )
    @GetMapping("/{id}")
    public User getUserById(@PathVariable int id) {
        return userService.getUserById(id);
    }


    @Operation(
            summary = "Posodobi uporabnika na podlagi ID",
            description = "Posodobi objekt uporabnika na podlagi njegove ID številke.",
            responses = {
                    @ApiResponse(
                            description = "User updated",
                            responseCode = "200",
                            content = @Content(
                                    schema = @Schema(implementation = User.class)
                            )
                    ),
                    @ApiResponse(
                            description = "User not found",
                            responseCode = "404"
                    ),
                    @ApiResponse(
                            description = "User not updated. Check if all fields are provided.",
                            responseCode= "500"
                    )
            }
    )
    @PutMapping("/{id}")
    public User updateUser(@PathVariable int id, @RequestBody JsonNode updatedUser) throws JsonProcessingException {
        return userService.updateUser(id, updatedUser.toString());
    }

    @Operation(
            summary = "Izbriši uporabnika na podlagi ID",
            description = "Izbriši objekt uporabnika na podlagi njegove ID številke.",
            responses = {
                    @ApiResponse(
                            description = "User deleted",
                            responseCode = "200",
                            content = @Content(
                                    schema = @Schema(implementation = User.class)
                            )
                    ),
                    @ApiResponse(
                            description = "User not found",
                            responseCode = "404"
                    )
            }
    )
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
        return "User with ID " + id + " has been deleted successfully.";
    }

    @Operation(
            summary = "Pridobi ID prijavljenega uporabnika",
            description = "Pridobi ID številko uporabnika, ki je trenutno prijavljen v aplikacijo.",
            responses = {
                    @ApiResponse(
                            description = "User found",
                            responseCode = "200",
                            content = @Content(
                                    schema = @Schema(implementation = User.class)
                            )
                    ),
                    @ApiResponse(
                            description = "User not found",
                            responseCode = "404"
                    )
            }
    )
    @GetMapping("/active/id")
    public int getLoggedInUserId() {
        int id = profileController.getLoggedInUser();
        return userService.getUserById(id).getId();
    }

    @Operation(
            summary = "Pridobi prijavljenega uporabnika",
            description = "Pridobi objekt uporabnika, ki je trenutno prijavljen v aplikacijo. Vrne ime, priimek, email in uporabniško ime uporabnika.",
            responses = {
                    @ApiResponse(
                            description = "User found",
                            responseCode = "200",
                            content = @Content(
                                    schema = @Schema(implementation = User.class)
                            )
                    ),
                    @ApiResponse(
                            description = "User not found",
                            responseCode = "404"
                    )
            }
    )
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