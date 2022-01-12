package org.bo.list.Item;

public class Drink extends ItemDTO {
    public Drink(int idItem, String name, String description, double price, String pathImage) {
        super(idItem, name, description, price, pathImage);
        super.isDish = false;
    }

    public Drink(String name, String description, double price, String pathImage) {
        super(name, description, price, pathImage);
        super.isDish = false;
    }
}
