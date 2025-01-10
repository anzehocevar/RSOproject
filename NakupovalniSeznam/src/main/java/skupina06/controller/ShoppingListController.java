package skupina06.controller;

import skupina06.model.ShoppingList;
import skupina06.service.ShoppingListService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@RestController
@RequestMapping("/api/shopping-lists")
@Tag(name = "Shopping List API", description = "Manage shopping lists and items")
public class ShoppingListController {

    private final ShoppingListService shoppingListService;

    public ShoppingListController(ShoppingListService shoppingListService) {
        this.shoppingListService = shoppingListService;
    }

    @PostMapping
    @Operation(summary = "Create a new shopping list", description = "Creates a shopping list with the provided details.")
    public ResponseEntity<ShoppingList> createShoppingList(@RequestBody ShoppingList shoppingList) {
        ShoppingList created = shoppingListService.createShoppingList(shoppingList);
        return ResponseEntity.ok(created);
    }

    @GetMapping
    @Operation(summary = "Get all shopping lists", description = "Retrieves all shopping lists.")
    public ResponseEntity<List<ShoppingList>> getAllShoppingLists() {
        return ResponseEntity.ok(shoppingListService.getAllShoppingLists());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a shopping list by ID", description = "Retrieves a specific shopping list by its ID.")
    public ResponseEntity<ShoppingList> getShoppingListById(@PathVariable Long id) {
        ShoppingList shoppingList = shoppingListService.getShoppingListById(id);
        return ResponseEntity.ok(shoppingList);
    }

    @GetMapping("/{shoppingListId}/users")
    @Operation(
            summary = "Get all users for a shopping list",
            description = "Retrieves a list of all user IDs associated with a specific shopping list."
    )
    public ResponseEntity<List<Long>> getUsersForShoppingList(@PathVariable Long shoppingListId) {
        List<Long> userIds = shoppingListService.getUsersForShoppingList(shoppingListId);
        return ResponseEntity.ok(userIds);
    }

    @GetMapping("/user/{userId}")
    @Operation(
            summary = "Get all shopping lists for a user",
            description = "Retrieves all shopping lists that a specific user has access to."
    )
    public ResponseEntity<List<ShoppingList>> getShoppingListsForUser(@PathVariable Long userId) {
        List<ShoppingList> shoppingLists = shoppingListService.getShoppingListsForUser(userId);
        return ResponseEntity.ok(shoppingLists);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a shopping list", description = "Deletes a shopping list by ID.")
    public ResponseEntity<Void> deleteShoppingList(@PathVariable Long id) {
        shoppingListService.deleteShoppingList(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{shoppingListId}/add-item/{itemId}")
    @Operation(summary = "Add item to shopping list", description = "Adds an item to a specific shopping list.")
    public ResponseEntity<ShoppingList> addItemToShoppingList(
            @PathVariable Long shoppingListId,
            @PathVariable Long itemId) {
        ShoppingList updated = shoppingListService.addItemToShoppingList(shoppingListId, itemId);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{shoppingListId}/remove-item/{itemId}")
    @Operation(summary = "Remove item from shopping list", description = "Removes an item from a specific shopping list.")
    public ResponseEntity<ShoppingList> removeItemFromShoppingList(
            @PathVariable Long shoppingListId,
            @PathVariable Long itemId) {
        ShoppingList updated = shoppingListService.removeItemFromShoppingList(shoppingListId, itemId);
        return ResponseEntity.ok(updated);
    }

    @PostMapping("/{shoppingListId}/mark-bought/{itemId}")
    @Operation(summary = "Mark item as bought", description = "Marks a specific item in the shopping list as bought.")
    public ResponseEntity<ShoppingList> markItemAsBought(
            @PathVariable Long shoppingListId,
            @PathVariable Long itemId) {
        ShoppingList updated = shoppingListService.markItemAsBought(shoppingListId, itemId);
        return ResponseEntity.ok(updated);
    }

    @PostMapping("/{shoppingListId}/add-user/{userId}")
    @Operation(
            summary = "Add a user to a shopping list",
            description = "Associates a user with the specified ID to a shopping list with the given shopping list ID. "
                    + "This allows the user to access the shopping list."
    )
    public ResponseEntity<ShoppingList> addUserToShoppingList(
            @PathVariable Long shoppingListId,
            @PathVariable Long userId) {
        ShoppingList updated = shoppingListService.addUserToShoppingList(shoppingListId, userId);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{shoppingListId}/remove-user/{userId}")
    @Operation(
            summary = "Remove a user from a shopping list",
            description = "Removes the association of a user with the specified ID from a shopping list "
                    + "with the given shopping list ID. This revokes the user's access to the shopping list."
    )
    public ResponseEntity<ShoppingList> removeUserFromShoppingList(
            @PathVariable Long shoppingListId,
            @PathVariable Long userId) {
        ShoppingList updated = shoppingListService.removeUserFromShoppingList(shoppingListId, userId);
        return ResponseEntity.ok(updated);
    }
}
