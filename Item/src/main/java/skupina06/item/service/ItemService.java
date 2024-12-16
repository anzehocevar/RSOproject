package skupina06.item.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import skupina06.item.model.Item;
import skupina06.item.repository.ItemRepository;

import java.util.List;
//import java.util.Optional;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    // Add a new item
    public Item addItem(Item item) {
        return itemRepository.save(item);
    }

    // Retrieve all items
    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    // Retrieve a specific item by ID
    public Item getItemById(int id) {
        return itemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item not found with ID: " + id));
    }

    // Update an existing item
    public Item updateItem(int id, Item updatedItem) {
        return itemRepository.findById(id).map(item -> {
            item.setName(updatedItem.getName());
            item.setDescription(updatedItem.getDescription());
            item.setPrice(updatedItem.getPrice());
//            item.setAssignedUsers(updatedItem.getAssignedUsers());
//            item.setPurchased(updatedItem.isPurchased());
            return itemRepository.save(item);
        }).orElseThrow(() -> new RuntimeException("Item not found with ID: " + id));
    }

    // Delete an item
    public void deleteItem(int id) {
        if (!itemRepository.existsById(id)) {
            throw new RuntimeException("Item not found with ID: " + id);
        }
        itemRepository.deleteById(id);
    }
}
