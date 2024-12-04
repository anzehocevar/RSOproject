package skupina06.item.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import skupina06.item.model.Item;

public interface ItemRepository extends JpaRepository<Item, Integer> {
}
