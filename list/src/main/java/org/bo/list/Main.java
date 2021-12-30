package org.bo.list;

import org.bo.list.Item.Dish;
import org.bo.list.Item.ItemDTO;
import org.bo.list.database.ConnectionDatabase;
import org.bo.list.database.ItemDao;
import org.bo.list.database.ItemDaoJDBC;
import org.bo.list.menu.Menu;

import java.sql.SQLException;
import java.util.List;

public class Main {

    public static void main(String[] args) throws SQLException {

        Menu menu = new Menu();

        /*ItemDao item = new ItemDaoJDBC(ConnectionDatabase.getConnection());
//        item.insert(itemDTO);
        List<ItemDTO> items = item.select();
        ItemDTO itemDTO = items.get(1);
        System.out.println(items);
        itemDTO.setName("Charque kan");
        int numFilasAfectadas = item.update(itemDTO);
        List<ItemDTO> items2 = item.select();
        System.out.println(items2);*/
    }

}
