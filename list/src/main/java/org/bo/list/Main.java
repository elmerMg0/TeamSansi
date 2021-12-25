package org.bo.list;

import org.bo.list.Item.ItemDTO;
import org.bo.list.database.ConnectionDatabase;
import org.bo.list.database.ItemDao;
import org.bo.list.database.ItemDaoJDBC;

import java.sql.SQLException;
import java.util.List;

public class Main {

    public static void main(String[] args) throws SQLException {
        ItemDao item = new ItemDaoJDBC(ConnectionDatabase.getConnection());
        List<ItemDTO> items = item.select();
        System.out.println(items);
    }

}
