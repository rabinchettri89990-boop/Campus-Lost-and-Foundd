package models;

import java.io.Serializable;

public class Item implements Serializable {

    private static final long serialVersionUID = 1L;

    private String itemName;
    private String category;
    private String type;
    private String location;
    private String date;
    private String description;
    private String status;

    public Item(
            String itemName,
            String category,
            String type,
            String location,
            String date,
            String description,
            String status) {

        this.itemName = itemName;
        this.category = category;
        this.type = type;
        this.location = location;
        this.date = date;
        this.description = description;
        this.status = status;
    }

    public String getItemName() {
        return itemName;
    }

    public String getCategory() {
        return category;
    }

    public String getType() {
        return type;
    }

    public String getLocation() {
        return location;
    }

    public String getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}