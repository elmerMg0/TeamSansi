package org.bo.list.database;

import org.bo.list.Item.Dish;
import org.bo.list.Item.Drink;
import org.bo.list.Item.ItemDTO;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class ItemDaoJDBC implements ItemDao {

    private Connection connection;

    private static final String SQL_SELECT = "SELECT * FROM item;";
    private static final String SQL_DELETE = "DELETE FROM item WHERE id_item = ?";

    private static final String SQL_INSERT = "INSERT INTO item(name, description, price, is_dish) VALUES (?, ?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE item SET name = ?, description = ?, price = ?,is_dish = ? WHERE id_item = ?";

    public ItemDaoJDBC(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<ItemDTO> select() throws SQLException {
        Connection conn = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<ItemDTO> items = new LinkedList<>();

        try {
            conn = this.connection != null ? this.connection : ConnectionDatabase.getConnection();
            statement = conn.createStatement();
            resultSet = statement.executeQuery(SQL_SELECT);
            while (resultSet.next()) {
                System.out.println("hola");
                int idItem = resultSet.getInt("id_item");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                double price = resultSet.getDouble("price");
                boolean isDish = resultSet.getBoolean("is_dish");

                ItemDTO itemDTO;
                if (isDish) {
                    itemDTO = new Dish(idItem, name, description, price);
                } else {
                    itemDTO = new Drink(idItem, name, description, price);
                }
                items.add(itemDTO);
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
        return items;
    }

    @Override
    public int insert(ItemDTO itemDTO) throws SQLException {
        Connection conn = null;
        PreparedStatement statement = null;
        int result = 0;
        try {
            conn = this.connection != null ? this.connection : ConnectionDatabase.getConnection();
            statement = conn.prepareStatement(SQL_INSERT);
            statement.setString(1, itemDTO.getName());
            statement.setString(2, itemDTO.getDescription());
            statement.setDouble(3, itemDTO.getPrice());
            statement.setBoolean(4, itemDTO.isDish());
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
    public int update(ItemDTO itemDTO) throws SQLException {
        Connection conn = null;
        PreparedStatement statement = null;
        int result = 0;
        try {
            conn = this.connection != null ? this.connection : ConnectionDatabase.getConnection();
            statement = conn.prepareCall(SQL_UPDATE);
            statement.setString(1, itemDTO.getName());
            statement.setString(2, itemDTO.getDescription());
            statement.setDouble(3, itemDTO.getPrice());
            statement.setBoolean(4, itemDTO.isDish());
            statement.setInt(5, itemDTO.getIdItem());
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
    public int delete(ItemDTO itemDTO) throws SQLException {
        Connection conn = null;
        PreparedStatement statement = null;
        int result = 0;

        try {
            conn = this.connection != null ? this.connection : ConnectionDatabase.getConnection();
            statement = conn.prepareStatement(SQL_DELETE);
            statement.setInt(1, itemDTO.getIdItem());
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

    private void closeAll(Connection conn, ResultSet resultSet, PreparedStatement statement) {
        try {
            if (resultSet != null) {
                ConnectionDatabase.close(resultSet);
            }
            if (statement != null) {
                ConnectionDatabase.close(statement);
            }
            if (this.connection == null) {
                ConnectionDatabase.close(conn);
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }
    }

}