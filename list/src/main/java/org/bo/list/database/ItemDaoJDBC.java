package org.bo.list.database;

import org.bo.list.Item.ItemDTO;

import java.sql.SQLException;
import java.util.List;

public class ItemDaoJDBC implements ItemDao {
    @Override
    public List<ItemDTO> select() throws SQLException {
        return null;
    }

    @Override
    public int insert(ItemDTO itemDTO) throws SQLException {
        return 0;
    }

    @Override
    public int update(ItemDTO itemDTO) throws SQLException {
        return 0;
    }

    @Override
    public int delete(ItemDTO itemDTO) throws SQLException {
        return 0;
    }

    @Override
    public ItemDTO select(int idItem) throws SQLException {
        return null;
    }
}
