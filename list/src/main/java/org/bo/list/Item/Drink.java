package org.bo.list.Item;

public class Drink extends ItemDTO {
    public Drink(int idItem, String name, String description, double price) {
        super(idItem, name, description, price);
        super.isDish = false;
    }
}
