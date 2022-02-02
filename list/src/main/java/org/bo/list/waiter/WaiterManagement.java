package org.bo.list.waiter;

import org.bo.list.database.ConnectionDatabase;
import org.bo.list.database.WaiterDao;
import org.bo.list.database.WaiterDaoJDBC;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class WaiterManagement {

    private Connection connection;
    private WaiterDao waiterDao;

    public WaiterManagement() throws SQLException {
        this.connection = ConnectionDatabase.getConnection();
        this.waiterDao = new WaiterDaoJDBC(connection);
    }

    public List<WaiterDTO> selectWaiters() throws SQLException {
        return waiterDao.select();
    }
}
