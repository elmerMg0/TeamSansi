package org.bo.list.Item;

import java.util.ArrayList;

public class Menu {
    private ArrayList<Item> avaibleItems;
    private ArrayList<Item> orderDishes;

    public Menu(){
        avaibleItems = new ArrayList<>();
        orderDishes = new ArrayList<>();
    }

    public void addItem(Item item){
        avaibleItems.add(item);
    }
    public void addOrder(Item item){
        orderDishes.add(Item);
    }
}

