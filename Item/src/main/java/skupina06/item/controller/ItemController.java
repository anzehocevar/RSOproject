package skupina06.item.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import skupina06.item.model.Item;
import skupina06.item.service.ItemService;

import java.util.List;

@RestController
@RequestMapping("/items")
public class ItemController {

    @Autowired
    private ItemService itemService;

    // Create a new item
    @PostMapping
    public Item addItem(@RequestBody Item item) {
        return itemService.addItem(item);
    }

    // Retrieve all items
    @GetMapping
    public List<Item> getAllItems() {
        return itemService.getAllItems();
    }

    // Retrieve a specific item by ID
    @GetMapping("/{id}")
    public Item getItemById(@PathVariable int id) {
        return itemService.getItemById(id);
    }

    // Update an item
    @PutMapping("/{id}")
    public Item updateItem(@PathVariable int id, @RequestBody Item updatedItem) {
        return itemService.updateItem(id, updatedItem);
    }

    // Delete an item
    @DeleteMapping("/{id}")
    public String deleteItem(@PathVariable int id) {
        itemService.deleteItem(id);
        return "Item with ID " + id + " has been deleted successfully.";
    }

    // Retrieve items by a list of IDs
    @GetMapping("/batch")
    public List<Item> getItemsByIds(@RequestParam List<Integer> ids) {
        return itemService.getItemsByIds(ids);
    }

}
