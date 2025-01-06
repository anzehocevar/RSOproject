package skupina06.item.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import skupina06.item.model.Item;
import skupina06.item.service.ItemService;

import java.util.List;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/items")
@Tag(name = "Item Controller", description = "REST API for managing items")
public class ItemController {

    @Autowired
    private ItemService itemService;

    // Create a new item
    @Operation(summary = "Create a new item", description = "Adds a new item to the inventory.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Item created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input provided")
    })
    @PostMapping
    public Item addItem(
            @Parameter(description = "Item object to be created", required = true)
            @RequestBody Item item) {
        return itemService.addItem(item);
    }

    // Retrieve all items
    @Operation(summary = "Retrieve all items", description = "Fetches all items in the inventory.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully retrieved items")
    })
    @GetMapping
    public List<Item> getAllItems() {
        return itemService.getAllItems();
    }

    // Retrieve a specific item by ID
    @Operation(summary = "Retrieve an item by ID", description = "Fetches the item with the specified ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Item retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Item not found")
    })
    @GetMapping("/{id}")
    public Item getItemById(
            @Parameter(description = "ID of the item to retrieve", required = true)
            @PathVariable int id) {
        return itemService.getItemById(id);
    }

    // Update an item
    @Operation(summary = "Update an item", description = "Updates the details of an existing item.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Item updated successfully"),
            @ApiResponse(responseCode = "404", description = "Item not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input provided")
    })
    @PutMapping("/{id}")
    public Item updateItem(
            @Parameter(description = "ID of the item to update", required = true)
            @PathVariable int id,
            @Parameter(description = "Updated item details", required = true)
            @RequestBody Item updatedItem) {
        return itemService.updateItem(id, updatedItem);
    }

    // Delete an item
    @Operation(summary = "Delete an item", description = "Deletes an item with the specified ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Item deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Item not found")
    })
    @DeleteMapping("/{id}")
    public String deleteItem(
            @Parameter(description = "ID of the item to delete", required = true)
            @PathVariable int id) {
        itemService.deleteItem(id);
        return "Item with ID " + id + " has been deleted successfully.";
    }

    // Retrieve items by a list of IDs
    @Operation(summary = "Retrieve items by IDs", description = "Fetches multiple items based on a list of IDs.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Items retrieved successfully")
    })
    @GetMapping("/batch")
    public List<Item> getItemsByIds(
            @Parameter(description = "List of item IDs to retrieve", required = true)
            @RequestParam List<Integer> ids) {
        return itemService.getItemsByIds(ids);
    }

}
