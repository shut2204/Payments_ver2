package com.my.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Test {
    public static void main(String[] args) throws SQLException {
        Connection connection = DBManager.getInstance().getConnection();
        PreparedStatement statement = connection.prepareStatement("INSERT INTO DBforProject.Customers (phone_number, first_name, last_name, login, password_customer, role)" +
                " VALUES ('456', 'gcjg', 'fhj', 'jhcgjc', 'zgxfhcj', 'user')");
        int i = statement.executeUpdate();

        System.out.println(i);
    }
}
