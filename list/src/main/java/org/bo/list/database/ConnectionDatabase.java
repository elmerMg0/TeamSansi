package org.bo.list.database;

import java.sql.*;

public class ConnectionDatabase {

    private static final String jdbc_url = "jdbc:mysql://localhost:3306/crud?useSSL=false&useTimezone=true&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    private static final String jdbc_user = "root";
    private static final String jdbc_password = "tupassword";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(jdbc_url, jdbc_user, jdbc_password);
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

}
