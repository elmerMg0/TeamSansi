package org.bo.list.database;

import org.bo.list.waiter.WaiterDTO;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class WaiterDaoJDBC implements WaiterDao {

    private Connection connection;

    private static final String SQL_SELECT = "SELECT * FROM waiter;";
    private static final String SQL_SELECT_PARAMETER = "SELECT * FROM waiter where ci = ?;";
    private static final String SQL_DELETE = "DELETE FROM waiter WHERE ci = ?";

    private static final String SQL_INSERT = "INSERT INTO waiter(ci, name, birth_date, phone, init_date, path) " +
            "VALUES (?, ?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE waiter SET name = ?, birth_date = ?, phone = ?, init_date = ?, " +
            "path = ? WHERE ci = ?";

    public WaiterDaoJDBC(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<WaiterDTO> select() throws SQLException {
        Connection conn = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<WaiterDTO> users = new LinkedList<>();

        try {
            conn = this.connection != null ? this.connection : ConnectionDatabase.getConnection();
            statement = conn.createStatement();
            resultSet = statement.executeQuery(SQL_SELECT);
            while (resultSet.next()) {
                int ci = resultSet.getInt("ci");
                String name = resultSet.getString("name");
                String birthDate = resultSet.getString("birth_date");
                int phone = resultSet.getInt("phone");
                String initDate = resultSet.getString("init_date");
                String pathImage = resultSet.getString("path");

                WaiterDTO user = new WaiterDTO(ci, name, birthDate, phone, initDate, pathImage);
                users.add(user);
            }
        } finally {
            try {
                ConnectionDatabase.close(statement);
                if (this.connection == null) {
                    ConnectionDatabase.close(conn);
                }
            } catch (SQLException ex) {
                ex.printStackTrace(System.out);
            }
        }
        return users;
    }

    @Override
    public List<WaiterDTO> select(int ci_parameter) throws SQLException {
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<WaiterDTO> users = new LinkedList<>();

        try {
            conn = this.connection != null ? this.connection : ConnectionDatabase.getConnection();
            statement = conn.prepareStatement(SQL_SELECT_PARAMETER);
            statement.setInt(1, ci_parameter);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int ci = resultSet.getInt("ci");
                String name = resultSet.getString("name");
                String birthDate = resultSet.getString("birth_date");
                int phone = resultSet.getInt("phone");
                String initDate = resultSet.getString("init_date");
                String pathImage = resultSet.getString("path");

                WaiterDTO user = new WaiterDTO(ci, name, birthDate, phone, initDate, pathImage);
                users.add(user);
            }
        } finally {
            try {
                ConnectionDatabase.close(statement);
                if (this.connection == null) {
                    ConnectionDatabase.close(conn);
                }
            } catch (SQLException ex) {
                ex.printStackTrace(System.out);
            }
        }
        return users;
    }

    @Override
    public int insert(WaiterDTO waiterDTO) throws SQLException {
        Connection conn = null;
        PreparedStatement statement = null;
        int result = 0;
        try {
            conn = this.connection != null ? this.connection : ConnectionDatabase.getConnection();
            statement = conn.prepareStatement(SQL_INSERT);
            statement.setInt(1, waiterDTO.getCi());
            statement.setString(2, waiterDTO.getName());
            statement.setString(3, waiterDTO.getBirthDate());
            statement.setInt(4, waiterDTO.getPhone());
            statement.setString(5, waiterDTO.getInitDate());
            statement.setString(6, waiterDTO.getPath());
            result = statement.executeUpdate();
        } finally {
            try {
                ConnectionDatabase.close(statement);
                if (this.connection == null) {
                    ConnectionDatabase.close(conn);
                }
            } catch (SQLException ex) {
                ex.printStackTrace(System.out);
            }
        }
        return result;
    }

    @Override
    public int update(WaiterDTO waiterDTO) throws SQLException {
        Connection conn = null;
        PreparedStatement statement = null;
        int result = 0;
        try {
            conn = this.connection != null ? this.connection : ConnectionDatabase.getConnection();
            statement = conn.prepareStatement(SQL_UPDATE);
            statement.setString(1, waiterDTO.getName());
            statement.setString(2, waiterDTO.getBirthDate());
            statement.setInt(3, waiterDTO.getPhone());
            statement.setString(4, waiterDTO.getInitDate());
            statement.setString(5, waiterDTO.getPath());
            statement.setInt(6, waiterDTO.getCi());
            result = statement.executeUpdate();
        } finally {
            try {
                ConnectionDatabase.close(statement);
                if (this.connection == null) {
                    ConnectionDatabase.close(conn);
                }
            } catch (SQLException ex) {
                ex.printStackTrace(System.out);
            }
        }
        return result;
    }

    @Override
    public int delete(WaiterDTO waiterDTO) throws SQLException {
        Connection conn = null;
        PreparedStatement statement = null;
        int result = 0;

        try {
            conn = this.connection != null ? this.connection : ConnectionDatabase.getConnection();
            statement = conn.prepareStatement(SQL_DELETE);
            statement.setInt(1, waiterDTO.getCi());
            result = statement.executeUpdate();
        } finally {
            try {
                ConnectionDatabase.close(statement);
                if (this.connection == null) {
                    ConnectionDatabase.close(conn);
                }
            } catch (SQLException ex) {
                ex.printStackTrace(System.out);
            }
        }
        return result;
    }
}
