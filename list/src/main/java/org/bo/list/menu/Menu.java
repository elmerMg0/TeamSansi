package org.bo.list.menu;

import org.bo.list.Item.Item;

import java.util.HashSet;
import java.util.Set;

public class Menu {
    private Set<Item> avaibleItems;
    private Set<Item> orderDishes;

    public Menu(){
        avaibleItems = new HashSet<>();
        orderDishes = new HashSet<>();
    }

    public void addItem(Item item){
        avaibleItems.add(item);
    }
    public void addOrder(Item item){
        orderDishes.add(item);
    }
}

