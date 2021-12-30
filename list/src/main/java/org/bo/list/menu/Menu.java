package org.bo.list.menu;

import org.bo.list.Item.ItemDTO;
import org.bo.list.database.ConnectionDatabase;
import org.bo.list.database.ItemDao;
import org.bo.list.database.ItemDaoJDBC;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Menu {

    private ItemDao itemDao;
    private Set<ItemDTO> avaibleItems;
    private Set<ItemDTO> orderDishes;

    public Menu() throws SQLException {
        this.itemDao = new ItemDaoJDBC(ConnectionDatabase.getConnection());
        this.avaibleItems = new HashSet<>();
        this.orderDishes = new HashSet<>();
        updateData();
    }

    private void updateData() throws SQLException {
        List<ItemDTO> items = itemDao.select();
        avaibleItems.addAll(items);
        System.out.println(avaibleItems.size());
    }

    public void addItem(ItemDTO item) throws SQLException {
        if (avaibleItems.add(item)) {
            itemDao.insert(item);
        }
    }

    public void update(ItemDTO itemDTO) {
        avaibleItems.forEach(item -> {
            if (item.getIdItem() == itemDTO.getIdItem()) {
                item = itemDTO;
                try {
                    itemDao.update(itemDTO);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void addOrder(ItemDTO item) {
        orderDishes.add(item);
    }

    public List<ItemDTO> select() {
        return new ArrayList<>(avaibleItems);
    }
}

