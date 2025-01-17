package skupina06.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import skupina06.model.ShoppingList;


@Repository
public interface ShoppingListRepository extends JpaRepository<ShoppingList, Long> {
}
