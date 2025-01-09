package skupina06.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import skupina06.model.ShoppingList;
import skupina06.repository.ShoppingListRepository;

import java.util.List;

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

    // Create a new shopping list
    public ShoppingList createShoppingList(ShoppingList shoppingList) {
        return shoppingListRepository.save(shoppingList);
    }

    // Retrieve all shopping lists
    public List<ShoppingList> getAllShoppingLists() {
        return shoppingListRepository.findAll();
    }

    // Delete a shopping list by ID
    public void deleteShoppingList(Long id) {
        shoppingListRepository.deleteById(id);
    }

    // Add an item to a shopping list
    public ShoppingList addItemToShoppingList(Long shoppingListId, Long itemId) {
        ShoppingList shoppingList = shoppingListRepository.findById(shoppingListId)
                .orElseThrow(() -> new IllegalArgumentException("Shopping List not found"));

        // Optional: Validate item exists in the Item service
        validateItemExists(itemId);

        shoppingList.getItemIds().add(itemId);
        return shoppingListRepository.save(shoppingList);
    }

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
}
