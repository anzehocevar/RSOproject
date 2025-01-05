package skupina06.service;

import skupina06.model.ShoppingList;
import skupina06.repository.ShoppingListRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.util.List;

@Service
public class ShoppingListService {

    private final ShoppingListRepository shoppingListRepository;


    public ShoppingListService(ShoppingListRepository shoppingListRepository) {
        this.shoppingListRepository = shoppingListRepository;
    }

    public ShoppingList markItemAsBought(Long shoppingListId, Long itemId) {
        ShoppingList shoppingList = shoppingListRepository.findById(shoppingListId)
                .orElseThrow(() -> new IllegalArgumentException("Shopping List not found"));

        if (!shoppingList.getItemIds().contains(itemId)) {
            throw new IllegalArgumentException("Item not in shopping list");
        }

        shoppingList.getBoughtItems().add(itemId);
        return shoppingListRepository.save(shoppingList);
    }


    public ShoppingList createShoppingList(ShoppingList shoppingList) {
        return shoppingListRepository.save(shoppingList);
    }

    public List<ShoppingList> getAllShoppingLists() {
        return shoppingListRepository.findAll();
    }

    public void deleteShoppingList(Long id) {
        shoppingListRepository.deleteById(id);
    }

    public ShoppingList addItemToShoppingList(Long shoppingListId, Long itemId) {
        ShoppingList shoppingList = shoppingListRepository.findById(shoppingListId)
                .orElseThrow(() -> new IllegalArgumentException("Shopping List not found"));
        shoppingList.getItemIds().add(itemId);
        return shoppingListRepository.save(shoppingList);
    }

    public ShoppingList removeItemFromShoppingList(Long shoppingListId, Long itemId) {
        ShoppingList shoppingList = shoppingListRepository.findById(shoppingListId)
                .orElseThrow(() -> new IllegalArgumentException("Shopping List not found"));
        shoppingList.getItemIds().remove(itemId);
        return shoppingListRepository.save(shoppingList);
    }
}
