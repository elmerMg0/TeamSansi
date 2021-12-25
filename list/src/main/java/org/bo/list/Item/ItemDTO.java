package org.bo.list.Item;

public class ItemDTO {
    protected int idItem;
    protected String name;
    protected String description;
    protected double price;
    protected boolean isDish;

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

    public boolean isDish() {
        return isDish;
    }

    public void setDish(boolean dish) {
        isDish = dish;
    }

    @Override
    public String toString() {
        return "ItemDTO{" +
                "idItem=" + idItem +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", isDish=" + isDish +
                '}';
    }
}
