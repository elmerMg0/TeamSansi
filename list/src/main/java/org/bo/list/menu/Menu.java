package org.bo.list.menu;

import org.bo.list.Item.ItemDTO;

import java.util.HashSet;
import java.util.Set;

public class Menu {
    private Set<ItemDTO> avaibleItems;
    private Set<ItemDTO> orderDishes;

    public Menu(){
        avaibleItems = new HashSet<>();
        orderDishes = new HashSet<>();
    }

    public void addItem(ItemDTO item){
        avaibleItems.add(item);
    }
    public void addOrder(ItemDTO item){
        orderDishes.add(item);
    }
}

