package org.bo.list;

import org.bo.list.Item.Dish;
import org.bo.list.Item.ItemDTO;
import org.bo.list.database.ConnectionDatabase;
import org.bo.list.database.ItemDao;
import org.bo.list.database.ItemDaoJDBC;

import java.sql.SQLException;
import java.util.List;

public class Main {

    public static void main(String[] args) throws SQLException {
        ItemDao item = new ItemDaoJDBC(ConnectionDatabase.getConnection());
        ItemDTO itemDTO = new Dish(0,"CharqueKan","Papa,huevo,matambre",95.0);
        List<ItemDTO> items = item.select();
        System.out.println(items);
        int numFilasAfectadas = item.update(itemDTO,5);
        List<ItemDTO> items2 = item.select();
        System.out.println(items2);
    }

}
