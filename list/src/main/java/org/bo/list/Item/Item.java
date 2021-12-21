package org.bo.list.Item;

public class Item {
    private String idItem;
    private String name;
    private String description;
    private double price;

    public Item(String idItem, String name, String description, double price){
        this.idItem = idItem;
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public String getIdItem() {
        return idItem;
    }

    public void setIdItem(String idItem) {
        this.idItem = idItem;
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
}
