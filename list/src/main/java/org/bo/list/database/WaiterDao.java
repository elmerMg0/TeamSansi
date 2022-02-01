package org.bo.list.database;

import org.bo.list.waiter.WaiterDTO;

import java.sql.SQLException;
import java.util.List;

public interface WaiterDao {

    public List<WaiterDTO> select() throws SQLException;

    public List<WaiterDTO> select(int isDish) throws SQLException;

    public int insert(WaiterDTO itemDTO) throws SQLException;

    public int update(WaiterDTO itemDTO) throws SQLException;

    public int delete(WaiterDTO itemDTO) throws SQLException;

}
