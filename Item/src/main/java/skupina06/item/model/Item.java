package skupina06.item.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String description;
    private double price;

//    @ElementCollection
//    private List<String> assignedUsers;

//    private boolean purchased;

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

//    public List<String> getAssignedUsers() {
//        return assignedUsers;
//    }

//    public void setAssignedUsers(List<String> assignedUsers) {
//        this.assignedUsers = assignedUsers;
//    }

//    public boolean isPurchased() {
//        return purchased;
//    }

//    public void setPurchased(boolean purchased) {
//        this.purchased = purchased;
//    }
}
