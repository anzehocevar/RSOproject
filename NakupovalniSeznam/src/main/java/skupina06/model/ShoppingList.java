package skupina06.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "shopping_lists")
public class ShoppingList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ElementCollection
    @CollectionTable(name = "shopping_list_items", joinColumns = @JoinColumn(name = "shopping_list_id"))
    @Column(name = "item_id")
    private List<Long> itemIds = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "bought_items", joinColumns = @JoinColumn(name = "shopping_list_id"))
    @Column(name = "item_id")
    private List<Long> boughtItems = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "shopping_list_users", joinColumns = @JoinColumn(name = "shopping_list_id"))
    @Column(name = "user_id")
    private List<Long> userIds = new ArrayList<>(); // List of user IDs with access

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Long> getItemIds() {
        return itemIds;
    }

    public void setItemIds(List<Long> itemIds) {
        this.itemIds = itemIds;
    }

    public List<Long> getBoughtItems() {
        return boughtItems;
    }

    public void setBoughtItems(List<Long> boughtItems) {
        this.boughtItems = boughtItems;
    }

    public List<Long> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<Long> userIds) {
        this.userIds = userIds;
    }


}