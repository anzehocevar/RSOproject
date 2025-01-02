package si.fri.skupina06.user.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import si.fri.skupina06.user.entity.User;
import si.fri.skupina06.user.repository.UserRepo;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;
import java.util.Random;

@Service
public class UserService {

    private final Random random = new Random();
    private static final Logger logger = LogManager.getLogger(UserService.class);

    @Autowired //Autowired
    private UserRepo userRepository;


    private final ObjectMapper objectMapper = new ObjectMapper();


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

    public List<User> getAllUsers() {
        logger.info("Getting all users");
        return userRepository.findAll();
    }


    public int retryCount = 0;

    //test @retry
    public List<User> getAllUsersTest() {
        retryCount++;
        logger.info("Attempt #{}: Executing getAllUsersTest()", retryCount);

        int randomBool = random.nextInt(2);

        if(randomBool == 0) {
            logger.info("Attempt #{}: Executing getAllUsersTest()", retryCount);
            throw new RuntimeException("Testing retry");
        }
        logger.info("Success on attempt #{}: Getting all users", retryCount);
        retryCount = 0;

        logger.info("Getting all users");
        return userRepository.findAll();
    }

    public int getIdByEmail(String email) {
        User u = userRepository.findUserByEmail(email);
        if(u == null) {
            logger.error("User not found with email {}", email);
            throw new RuntimeException("User not found with email: " + email);
        }
        logger.info("Found user with username {}", email);
        return u.getId();
    }

    public User getUserByUsername(String username) {
        User u = userRepository.findUserByUsername(username);
        if(u == null) {
            logger.error("User not found with username {}", username);
            throw new RuntimeException("User not found with username: " + username);
        }
        logger.info("Found user with username {}", username);
        return u;
    }


    public User getUserById(int id) {
        return userRepository.findById(id)
                        .orElseThrow(() -> {
                            logger.error("Failed to get user with id {}", id);
                            return new RuntimeException("User with ID not found: " + id);
                        });
    }


    public void deleteUser(int id) {
        if (!userRepository.existsById(id)) {
            logger.error("Failed to delete user with id {}", id);
            throw new RuntimeException("User not found with ID: " + id);
        }
        logger.info("Deleting user with id {}", id);
        userRepository.deleteById(id);
    }


    public String getAvatarUrlById(int id) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            String url = "https://api.dicebear.com/9.x/pixel-art/svg?seed=".concat(user.getUsername());
            logger.info("Found avatar at url {}", url);
            return url;
        }
        return null;
    }
}