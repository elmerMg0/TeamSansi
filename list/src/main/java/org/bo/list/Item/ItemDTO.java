package org.bo.list.Item;

import java.util.Objects;

public class ItemDTO {
    protected int idItem;
    protected String name;
    protected String description;
    protected double price;
    protected boolean isDish;
    protected String pathImage;

    public ItemDTO(int idItem, String name, String description, double price, String pathImage) {
        this(name, description, price, pathImage);
        this.idItem = idItem;
    }

    public ItemDTO(String name, String description, double price, String pathImage) {
        this.idItem = 0;
        this.name = name;
        this.description = description;
        this.price = price;
        this.pathImage = pathImage;
    }

    public int getIdItem() {
        return idItem;
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

    public String getPathImage() {
        return pathImage;
    }

    public void setPathImage(String pathImage) {
        this.pathImage = pathImage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemDTO itemDTO = (ItemDTO) o;
        return idItem == itemDTO.idItem;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idItem);
    }

    @Override
    public String toString() {
        return "ItemDTO{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                '}';
    }
}
