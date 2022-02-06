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

    public void addWaiter(WaiterDTO waiterDTO) throws SQLException {
        waiterDao.insert(waiterDTO);
    }

    public void updateWaiter(WaiterDTO waiterDTO) throws SQLException {
        waiterDao.update(waiterDTO);
    }

    public void deleteWaiter(WaiterDTO waiterDTO) throws SQLException {
        waiterDao.delete(waiterDTO);
    }
}
