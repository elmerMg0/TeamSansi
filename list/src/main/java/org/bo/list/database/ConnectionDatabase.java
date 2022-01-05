package org.bo.list.database;

import java.sql.*;

public class ConnectionDatabase {

    private static final String jdbc_url = "jdbc:sqlite:list/crud.db";
    private static final String jdbc_user = "root";
    private static final String jdbc_password = "tupassword";

    public static Connection getConnection() throws SQLException {
        System.out.println("Database connected");
        System.out.println(DriverManager.getConnection(jdbc_url));

        return DriverManager.getConnection(jdbc_url);
    }

    public static void close(ResultSet resultSet) throws SQLException {
        resultSet.close();
    }

    public static void close(PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.close();
    }

    public static void close(Connection conn) throws SQLException {
        conn.close();
    }

    public static void close(Statement statement) throws SQLException {
        statement.close();
    }

}
