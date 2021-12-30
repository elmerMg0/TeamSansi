package org.bo.list.Item;

import java.util.Objects;

public class ItemDTO {
    protected int idItem;
    protected String name;
    protected String description;
    protected double price;
    protected boolean isDish;

    public ItemDTO(int idItem, String name, String description, double price) {
        this.idItem = idItem;
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public ItemDTO(String name, String description, double price) {
        this.idItem = 0;
        this.name = name;
        this.description = description;
        this.price = price;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemDTO itemDTO = (ItemDTO) o;
        return Objects.equals(name, itemDTO.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
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
