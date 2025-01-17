package skupina06.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import skupina06.model.ShoppingList;
import skupina06.repository.ShoppingListRepository;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;

import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

@Service
public class ShoppingListService {

    private final ShoppingListRepository shoppingListRepository;
    private final RestTemplate restTemplate;

    @Value("${item.service.url}")
    private String itemServiceUrl;

    public ShoppingListService(ShoppingListRepository shoppingListRepository, RestTemplate restTemplate) {
        this.shoppingListRepository = shoppingListRepository;
        this.restTemplate = restTemplate;
    }

    public ShoppingList createShoppingList(ShoppingList shoppingList) {
        // Save the parent shopping list first
        ShoppingList savedShoppingList = shoppingListRepository.save(shoppingList);

        // Ensure the collections are not null
        if (savedShoppingList.getItemIds() == null) {
            savedShoppingList.setItemIds(new ArrayList<>());
        }
        if (savedShoppingList.getBoughtItems() == null) {
            savedShoppingList.setBoughtItems(new ArrayList<>());
        }
        if (savedShoppingList.getUserIds() == null) {
            savedShoppingList.setUserIds(new ArrayList<>());
        }

        // Manually update userIds after saving the parent
        List<Long> validUserIds = new ArrayList<>(shoppingList.getUserIds());
        savedShoppingList.setUserIds(validUserIds);

        // Save the updated shopping list with userIds
        return shoppingListRepository.save(savedShoppingList);
    }


//    public ShoppingList createShoppingList(ShoppingList shoppingList) {
//        shoppingList.setName(shoppingList.getName());
//        if (shoppingList.getItemIds() == null) {
//            shoppingList.setItemIds(new ArrayList<>());
//        }
//        if (shoppingList.getBoughtItems() == null) {
//            shoppingList.setBoughtItems(new ArrayList<>());
//        }
//        if (shoppingList.getUserIds() == null) {
//            shoppingList.setUserIds(new ArrayList<>());
//        }
//        return shoppingListRepository.save(shoppingList);
//    }


    // Retrieve all shopping lists
    public List<ShoppingList> getAllShoppingLists() {
        return shoppingListRepository.findAll();
    }

    // Delete a shopping list by ID
    public void deleteShoppingList(Long id) {
        shoppingListRepository.deleteById(id);
    }


    @Retry(name = "itemServiceRetry", fallbackMethod = "addItemFallback")
    @CircuitBreaker(name = "itemServiceCircuitBreaker", fallbackMethod = "addItemFallback")
    public ShoppingList addItemToShoppingList(Long shoppingListId, Long itemId) {
        ShoppingList shoppingList = shoppingListRepository.findById(shoppingListId)
                .orElseThrow(() -> new IllegalArgumentException("Shopping List not found"));

        // Validate item exists in the item service
//        String url = itemServiceUrl + "/" + itemId;
//        Boolean itemExists = restTemplate.getForObject(url, Boolean.class);
//        if (itemExists == null || !itemExists) {
//            throw new IllegalArgumentException("Item with ID " + itemId + " does not exist");
//        }

        // Add item to the shopping list
        shoppingList.getItemIds().add(itemId);
        return shoppingListRepository.save(shoppingList);
    }

    // Fallback method
    private ShoppingList addItemFallback(Long shoppingListId, Long itemId, Throwable throwable) {
        System.err.println("Fallback triggered for addItemToShoppingList: " + throwable.getMessage());
        ShoppingList shoppingList = shoppingListRepository.findById(shoppingListId)
                .orElseThrow(() -> new IllegalArgumentException("Shopping List not found"));

        // Provide a degraded experience or log the issue
        shoppingList.getItemIds().add(-1L); // Add a placeholder item to indicate failure
        return shoppingListRepository.save(shoppingList);
    }

//    // Add an item to a shopping list
//    public ShoppingList addItemToShoppingList(Long shoppingListId, Long itemId) {
//        ShoppingList shoppingList = shoppingListRepository.findById(shoppingListId)
//                .orElseThrow(() -> new IllegalArgumentException("Shopping List not found"));
//
//        // Optional: Validate item exists in the Item service
////        validateItemExists(itemId);
//
//        shoppingList.getItemIds().add(itemId);
//        return shoppingListRepository.save(shoppingList);
//    }

    // Remove an item from a shopping list
    public ShoppingList removeItemFromShoppingList(Long shoppingListId, Long itemId) {
        ShoppingList shoppingList = shoppingListRepository.findById(shoppingListId)
                .orElseThrow(() -> new IllegalArgumentException("Shopping List not found"));

        shoppingList.getItemIds().remove(itemId);
        return shoppingListRepository.save(shoppingList);
    }

    // Mark an item as bought in a shopping list
    public ShoppingList markItemAsBought(Long shoppingListId, Long itemId) {
        ShoppingList shoppingList = shoppingListRepository.findById(shoppingListId)
                .orElseThrow(() -> new IllegalArgumentException("Shopping List not found"));

        if (!shoppingList.getItemIds().contains(itemId)) {
            throw new IllegalArgumentException("Item not in shopping list");
        }

        shoppingList.getBoughtItems().add(itemId);
        return shoppingListRepository.save(shoppingList);
    }

    // Validate an item exists in the Item service
    private void validateItemExists(Long itemId) {
        String url = itemServiceUrl + "/" + itemId;
        Boolean itemExists = restTemplate.getForObject(url, Boolean.class);
        if (itemExists == null || !itemExists) {
            throw new IllegalArgumentException("Item with ID " + itemId + " does not exist");
        }
    }

    public ShoppingList addUserToShoppingList(Long shoppingListId, Long userId) {
        ShoppingList shoppingList = shoppingListRepository.findById(shoppingListId)
                .orElseThrow(() -> new IllegalArgumentException("ShoppingList not found"));

        if (!shoppingList.getUserIds().contains(userId)) {
            shoppingList.getUserIds().add(userId);
            shoppingListRepository.save(shoppingList);
        }
        return shoppingList;
    }

    public ShoppingList removeUserFromShoppingList(Long shoppingListId, Long userId) {
        ShoppingList shoppingList = shoppingListRepository.findById(shoppingListId)
                .orElseThrow(() -> new IllegalArgumentException("ShoppingList not found"));

        shoppingList.getUserIds().remove(userId);
        shoppingListRepository.save(shoppingList);
        return shoppingList;
    }

    // Get a shopping list by ID
    public ShoppingList getShoppingListById(Long shoppingListId) {
        return shoppingListRepository.findById(shoppingListId)
                .orElseThrow(() -> new IllegalArgumentException("Shopping List with ID " + shoppingListId + " not found"));
    }

    // Get all users for a shopping list
    public List<Long> getUsersForShoppingList(Long shoppingListId) {
        ShoppingList shoppingList = shoppingListRepository.findById(shoppingListId)
                .orElseThrow(() -> new IllegalArgumentException("Shopping List with ID " + shoppingListId + " not found"));

        // Return the list of user IDs associated with this shopping list
        return shoppingList.getUserIds();
    }

    // Get all shopping lists for a user
    public List<ShoppingList> getShoppingListsForUser(Long userId) {
        // Filter shopping lists where the user ID exists in the userIds field
        return shoppingListRepository.findAll().stream()
                .filter(shoppingList -> shoppingList.getUserIds().contains(userId))
                .collect(Collectors.toList());
    }

    public ShoppingList updateShoppingList(Long id, ShoppingList updatedShoppingList) {
        ShoppingList existing = shoppingListRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Shopping list not found with ID: " + id));

        // Update fields
        if (updatedShoppingList.getName() != null) {
            existing.setName(updatedShoppingList.getName());
        }
        if (updatedShoppingList.getItemIds() != null) {
            existing.setItemIds(updatedShoppingList.getItemIds());
        }
        if (updatedShoppingList.getUserIds() != null) {
            existing.setUserIds(updatedShoppingList.getUserIds());
        }

        if(updatedShoppingList.getBoughtItems() != null) {
            existing.setBoughtItems(updatedShoppingList.getBoughtItems());
        }

        // Save updated shopping list
        return shoppingListRepository.save(existing);
    }


    public ShoppingList updateItemsInShoppingList(Long shoppingListId, List<Long> itemIds) {
        ShoppingList shoppingList = getShoppingListById(shoppingListId);
        shoppingList.setItemIds(itemIds);
        return shoppingListRepository.save(shoppingList);
    }

}
