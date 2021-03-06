package org.bo.list.database;

import org.bo.list.Item.ItemDTO;

import java.sql.SQLException;
import java.util.List;

public interface ItemDao {

    public List<ItemDTO> select() throws SQLException;

    public List<ItemDTO> select(int isDish) throws SQLException;

    public int insert(ItemDTO itemDTO) throws SQLException;

    public int update(ItemDTO itemDTO) throws SQLException;

    public int delete(ItemDTO itemDTO) throws SQLException;

}
