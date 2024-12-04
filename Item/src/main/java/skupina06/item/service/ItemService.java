package skupina06.item.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import skupina06.item.model.Item;
import skupina06.item.repository.ItemRepository;

import java.util.List;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    public Item addItem(Item item){
        return itemRepository.save(item);
    }

}
