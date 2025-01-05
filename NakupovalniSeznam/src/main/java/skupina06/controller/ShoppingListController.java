package skupina06.controller;


import skupina06.model.ShoppingList;
import skupina06.service.ShoppingListService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shopping-lists")
public class ShoppingListController {

    private final ShoppingListService shoppingListService;

    public ShoppingListController(ShoppingListService shoppingListService) {
        this.shoppingListService = shoppingListService;
    }

    @PostMapping
    public ResponseEntity<ShoppingList> createShoppingList(@RequestBody ShoppingList shoppingList) {
        ShoppingList created = shoppingListService.createShoppingList(shoppingList);
        return ResponseEntity.ok(created);
    }

    @GetMapping
    public ResponseEntity<List<ShoppingList>> getAllShoppingLists() {
        return ResponseEntity.ok(shoppingListService.getAllShoppingLists());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShoppingList(@PathVariable Long id) {
        shoppingListService.deleteShoppingList(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{shoppingListId}/add-item/{itemId}")
    public ResponseEntity<ShoppingList> addItemToShoppingList(
            @PathVariable Long shoppingListId,
            @PathVariable Long itemId) {
        ShoppingList updated = shoppingListService.addItemToShoppingList(shoppingListId, itemId);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{shoppingListId}/remove-item/{itemId}")
    public ResponseEntity<ShoppingList> removeItemFromShoppingList(
            @PathVariable Long shoppingListId,
            @PathVariable Long itemId) {
        ShoppingList updated = shoppingListService.removeItemFromShoppingList(shoppingListId, itemId);
        return ResponseEntity.ok(updated);
    }

    @PostMapping("/{shoppingListId}/mark-bought/{itemId}")
    public ResponseEntity<ShoppingList> markItemAsBought(
            @PathVariable Long shoppingListId,
            @PathVariable Long itemId) {
        ShoppingList updated = shoppingListService.markItemAsBought(shoppingListId, itemId);
        return ResponseEntity.ok(updated);
    }

}
