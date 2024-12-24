package si.fri.skupina06.user.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import si.fri.skupina06.user.entity.User;
import si.fri.skupina06.user.repository.UserRepo;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;

@Service
public class UserService {

    private static final Logger logger = LogManager.getLogger(UserService.class);

    @Autowired //Autowired
    private UserRepo userRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @CircuitBreaker(name = "userService", fallbackMethod = "fallbackUpdateUser")
    public User updateUser(int id, String updatedUser) throws JsonProcessingException {
        JsonNode jsonNode = objectMapper.readTree(updatedUser);

        return userRepository.findById(id).map(user -> {
            user.setName(jsonNode.get("name").asText());
            user.setSurname(jsonNode.get("surname").asText());
            user.setUsername(jsonNode.get("username").asText());
            user.setEmail(jsonNode.get("email").asText());

            logger.info("Updating user with id {}", id);
            return userRepository.save(user);
        }).orElseThrow(() -> {
            logger.error("Failed to update user with id {}", id);
            return new RuntimeException("User not found with ID: " + id);
        });
    }

    @CircuitBreaker(name = "userService", fallbackMethod = "fallbackAddUser")
    public User addUser(String user) throws JsonProcessingException {
        JsonNode jsonNode = objectMapper.readTree(user);

        User newUser = new User();

        newUser.setName(jsonNode.get("name").asText());
        newUser.setSurname(jsonNode.get("surname").asText());
        newUser.setUsername(jsonNode.get("username").asText());
        newUser.setEmail(jsonNode.get("email").asText());
        logger.info("Creating user {}", newUser.getEmail());
        return userRepository.save(newUser);
    }

    @Retry(name = "userServiceRetry", fallbackMethod = "fallbackGetAllUsers")
    public List<User> getAllUsers() {
        logger.info("Getting all users");
        return userRepository.findAll();
    }

    @CircuitBreaker(name = "userService", fallbackMethod = "fallbackGetUserByUsername")
    public User getUserByUsername(String username) {
        User u = userRepository.findUserByUsername(username);
        if(u == null) {
            logger.error("User not found with username {}", username);
            throw new RuntimeException("User not found with username: " + username);
        }
        logger.info("Found user with username {}", username);
        return u;
    }

    @CircuitBreaker(name = "userService", fallbackMethod = "fallbackGetUserById")
    public User getUserById(int id) {
        return userRepository.findById(id)
                        .orElseThrow(() -> {
                            logger.error("Failed to get user with id {}", id);
                            return new RuntimeException("User with ID not found: " + id);
                        });
    }

    @CircuitBreaker(name = "userService", fallbackMethod = "fallbackDeleteUser")
    public void deleteUser(int id) {
        if (!userRepository.existsById(id)) {
            logger.error("Failed to delete user with id {}", id);
            throw new RuntimeException("User not found with ID: " + id);
        }
        logger.info("Deleting user with id {}", id);
        userRepository.deleteById(id);
    }

    @CircuitBreaker(name = "userService", fallbackMethod = "fallbackGetAvatarUrlById")
    public String getAvatarUrlById(int id) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            String url = "https://api.dicebear.com/9.x/pixel-art/svg?seed=".concat(user.getUsername());
            logger.info("Found avatar at url {}", url);
            return url;
        }
        return null;
    }

    //FALLBACK METHODS
    public User fallbackUpdateUser(int id, String updatedUser, Throwable t) {
        logger.error("Circuit breaker activated for updateUser: {}", t.getMessage());
        return new User();
    }

    public User fallbackAddUser(String user, Throwable t) {
        logger.error("Circuit breaker activated for addUser: {}", t.getMessage());
        return new User();
    }

    public List<User> fallbackGetAllUsers(Throwable t) {
        logger.error("Circuit breaker activated for getAllUsers: {}", t.getMessage());
        return List.of();
    }

    public User fallbackGetUserByUsername(String username, Throwable t) {
        logger.error("Circuit breaker activated for getUserByUsername: {}", t.getMessage());
        return new User();
    }

    public User fallbackGetUserById(int id, Throwable t) {
        logger.error("Circuit breaker activated for getUserById: {}", t.getMessage());
        return new User();
    }

    public void fallbackDeleteUser(int id, Throwable t) {
        logger.error("Circuit breaker activated for deleteUser: {}", t.getMessage());
    }

    public String fallbackGetAvatarUrlById(int id, Throwable t) {
        logger.error("Circuit breaker activated for getAvatarUrlById: {}", t.getMessage());
        return null;
    }
}