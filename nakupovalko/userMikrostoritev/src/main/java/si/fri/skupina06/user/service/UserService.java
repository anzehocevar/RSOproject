package si.fri.skupina06.user.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import si.fri.skupina06.user.entity.User;
import si.fri.skupina06.user.repository.UserRepo;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;

@Service
public class UserService {

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

            return userRepository.save(user);
        }).orElseThrow(() -> new RuntimeException("User not found with ID: " + id));
    }


    public User addUser(String user) throws JsonProcessingException {
        JsonNode jsonNode = objectMapper.readTree(user);

        User newUser = new User();

        newUser.setName(jsonNode.get("name").asText());
        newUser.setSurname(jsonNode.get("surname").asText());
        newUser.setUsername(jsonNode.get("username").asText());
        newUser.setEmail(jsonNode.get("email").asText());

        return userRepository.save(newUser);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    
    public User getUserById(int id) {
        return userRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("User not found with ID: " + id));
    }


    public void deleteUser(int id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User not found with ID: " + id);
        }
        userRepository.deleteById(id);
    }
}