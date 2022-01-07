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

        ItemDao itemDao = new ItemDaoJDBC(ConnectionDatabase.getConnection());
//        ItemDTO pique = new Dish("Planchita","Carne, platano, huevo",95.0);
//        itemDao.insert(pique);
        System.out.println(itemDao.select(0));
//        System.out.println(itemDao.select());
    }

}
