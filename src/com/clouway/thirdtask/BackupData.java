package com.clouway.thirdtask;

import java.sql.*;
import java.util.Random;

/**
 * Created by clouway on 16-2-25.
 */
public class BackupData {
    private String DB_URL;
    private String user;
    private String password;
    private Connection connection;

    public BackupData(String DB_URL, String user, String password) {
        this.DB_URL = DB_URL;
        this.user = user;
        this.password = password;
    }

    public void connectToDatabase() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(DB_URL, user, password);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void update(Customer customer) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("UPDATE customer SET customer_name=?, phone_number=?, email=? WHERE customer_id=?");
            preparedStatement.setString(1, customer.getName());
            preparedStatement.setString(2, customer.getPhoneNumber());
            preparedStatement.setString(3, customer.getEmail());
            preparedStatement.setInt(4, customer.getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void insert() {
        int id = 12027;
        String name = "John Doe";
        String email = "johndoe@abv.bg";
        PreparedStatement preparedStatement = null;
        String characters = "1234567890";
        try {
            for (int i = 0; i < 10000; i++) {
                char[] text = new char[characters.length()];
                for (int g = 0; g < 10; g++) {
                    text[g] = characters.charAt(new Random().nextInt(characters.length()));
                }
                String phone = new String(text);
                preparedStatement = connection.prepareStatement("INSERT INTO customer (customer_id, customer_name, phone_number, email) values (?,?,?,?)");
                preparedStatement.setInt(1, id);
                preparedStatement.setString(2, name);
                preparedStatement.setString(3, phone);
                preparedStatement.setString(4, email);
                preparedStatement.execute();
                id++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
