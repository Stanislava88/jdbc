package com.clouway.thirdtask;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by clouway on 16-2-25.
 */
public class BackupData {

    private Connection connection;

    public BackupData(Connection connection) {
        this.connection = connection;

    }

    public void register(Customer customer) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("INSERT INTO customer (customer_id, customer_name, phone_number, email) values (?,?,?,?)");
            preparedStatement.setInt(1, customer.id);
            preparedStatement.setString(2, customer.name);
            preparedStatement.setString(3, customer.phoneNumber);
            preparedStatement.setString(4, customer.email);
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

    public void update(Customer customer) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement("UPDATE customer SET customer_name=?, phone_number=?, email=? WHERE customer_id=?");
            preparedStatement.setString(1, customer.name);
            preparedStatement.setString(2, customer.phoneNumber);
            preparedStatement.setString(3, customer.email);
            preparedStatement.setInt(4, customer.id);
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

    public void insertThousandCustomers() {
        int id = 12027;
        String name = "John Doe";
        String email = "johndoe@abv.bg";
        PreparedStatement preparedStatement = null;
        String characters = "1234567890";
        try {
            for (int i = 0; i < 1000; i++) {
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


    public List<Customer> findAll(){
        List<Customer> customers = new ArrayList<Customer>();
        ResultSet resultSet = null;
        Statement stmt = null;
        try {
            stmt = connection.createStatement();
            resultSet = stmt.executeQuery("SELECT * FROM customer");
            while (resultSet.next()) {
                int id=resultSet.getInt("customer_id");
                String name = resultSet.getString("customer_name");
                String phone = resultSet.getString("phone_number");
                String email = resultSet.getString("email");
                customers.add(new Customer(id,name,phone,email));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return customers;
    }

    public List<Customer> findAllBackUp(){
        List<Customer> customers = new ArrayList<Customer>();
        ResultSet resultSet = null;
        Statement stmt = null;
        try {
            stmt = connection.createStatement();
            resultSet = stmt.executeQuery("SELECT * FROM customer_backup");
            while (resultSet.next()) {
                int id=resultSet.getInt("customer_id");
                String name = resultSet.getString("customer_name");
                String phone = resultSet.getString("phone_number");
                String email = resultSet.getString("email");
                customers.add(new Customer(id,name,phone,email));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return customers;
    }


    public void deleteCustomers(){
        Statement statement = null;
        try{
            statement=connection.createStatement();
            statement.executeUpdate("DELETE FROM customer");
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void deleteCustomersBackup(){
        Statement statement = null;
        try{
            statement=connection.createStatement();
            statement.executeUpdate("DELETE FROM customer_backup");
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            try {
                if (statement != null) {
                    statement.close();
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
