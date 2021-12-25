package org.bo.list.database;

import org.bo.list.Item.Dish;
import org.bo.list.Item.Drink;
import org.bo.list.Item.ItemDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class ItemDaoJDBC implements ItemDao {

    private Connection connection;

    private static final String SQL_SELECT = "SELECT * FROM item";
    private static final String SQL_DELETE = "DELETE FROM item WHERE id_item = ?;";

    public ItemDaoJDBC(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<ItemDTO> select() throws SQLException {
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<ItemDTO> items = new LinkedList<>();

        try {
            conn = this.connection != null ? this.connection : ConnectionDatabase.getConnection();
            statement = conn.prepareStatement(SQL_SELECT);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
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
            closeAll(conn, resultSet, statement);
        }
        return items;
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
            ConnectionDatabase.close(resultSet);
            ConnectionDatabase.close(statement);
            if (this.connection == null) {
                ConnectionDatabase.close(conn);
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }
    }
}
