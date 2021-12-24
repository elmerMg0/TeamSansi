package org.bo.list.Item;

public abstract class ItemDTO {
    private int idItem;
    private String name;
    private String description;
    private double price;

    public ItemDTO(int idItem, String name, String description, double price){
        this.idItem = idItem;
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public int getIdItem() {
        return idItem;
    }

    public void setIdItem(int idItem) {
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
