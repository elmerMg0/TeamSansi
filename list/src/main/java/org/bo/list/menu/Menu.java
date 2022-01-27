package org.bo.list.menu;

import org.bo.list.Item.ItemDTO;
import org.bo.list.database.ConnectionDatabase;
import org.bo.list.database.ItemDao;
import org.bo.list.database.ItemDaoJDBC;

import java.sql.SQLException;
import java.util.*;

public class Menu {

    private ItemDao connection;
    private Set<ItemDTO> avaibleItems;
    private Map<ItemDTO, Integer> orderDishes;

    public Menu() throws SQLException {
        this.connection = new ItemDaoJDBC(ConnectionDatabase.getConnection());
        this.avaibleItems = new HashSet<>();
        this.orderDishes = new HashMap<>();
        updateData();
    }

    private void updateData() throws SQLException {
        List<ItemDTO> items = connection.select();
        avaibleItems.addAll(items);
    }

    public void addItem(ItemDTO item) throws SQLException {
        if (avaibleItems.add(item)) {
            connection.insert(item);
        }
    }

    public void update(ItemDTO itemDTO) {
        avaibleItems.forEach(item -> {
            if (item.getIdItem() == itemDTO.getIdItem()) {
                item = itemDTO;
                try {
                    connection.update(itemDTO);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void addOrder(ItemDTO item, int value) {
        orderDishes.put(item, value);
    }

    public void setOrderDishes(Map<ItemDTO, Integer> orderDishes) {
        this.orderDishes = orderDishes;
    }

    public Map<ItemDTO, Integer> getOrderDishes() {
        return orderDishes;
    }

    public List<ItemDTO> select() {
        return new ArrayList<>(avaibleItems);
    }

    public void delete(ItemDTO item) throws SQLException {
        connection.delete(item);
        avaibleItems.remove(item);
    }
}

