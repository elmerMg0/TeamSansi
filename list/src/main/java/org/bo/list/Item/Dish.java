package org.bo.list.Item;

public class Dish extends ItemDTO {

    public Dish(int idItem, String name, String description, double price, String pathImage) {
        super(idItem, name, description, price, pathImage);
        super.isDish = true;
    }

    public Dish(String name, String description, double price, String pathImage) {
        super(name, description, price, pathImage);
        super.isDish = true;
    }
}
