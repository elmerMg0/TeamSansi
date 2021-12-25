package org.bo.list.Item;

public class Dish extends ItemDTO {
    public Dish(int idItem, String name, String description, double price) {
        super(idItem, name, description, price);
        super.isDish = true;
    }
}
