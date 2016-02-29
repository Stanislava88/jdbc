package com.clouway.firsttask;

import java.sql.*;

/**
 * Created by clouway on 16-2-24.
 */
public class Queries {
    private String DB_URL;
    private String user;
    private String password;
    private Connection connection;
    private Statement stmt;
    private Display display;
    private ResultSet resultSet;

    public Queries(String DB_URL, String user, String password, Display display) {
        this.DB_URL = DB_URL;
        this.user = user;
        this.password = password;
        this.display=display;
    }

    public void connectToDatabase() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(DB_URL, user, password);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void executeSelectMethod() {
        try {
            stmt = connection.createStatement();
            resultSet = stmt.executeQuery("SELECT * FROM peopleinfo");
            while (resultSet.next()) {
                String firstName = resultSet.getString("FirstName");
                String lastName = resultSet.getString("LastName");
                String egn = resultSet.getString("EGN");
                int age = resultSet.getInt("age");
                display.setFirstName(firstName);
                display.setLastName(lastName);
                display.setEgn(egn);
                display.setAge(age);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void executeUpdateMethod(){
        try {
            stmt = connection.createStatement();
            stmt.executeUpdate("UPDATE peopleinfo SET FirstName=\"David\", LastName=\"Bekcham\" WHERE EGN=\"1231231234\" ");
            ResultSet resultSet=stmt.executeQuery("SELECT * FROM peopleinfo");
            while (resultSet.next()) {
                String firstName = resultSet.getString("FirstName");
                String lastName = resultSet.getString("LastName");
                String egn = resultSet.getString("EGN");
                int age = resultSet.getInt("age");
                display.setFirstName(firstName);
                display.setLastName(lastName);
                display.setEgn(egn);
                display.setAge(age);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void executeDeleteMethod(){
        try {
            stmt=connection.createStatement();
            stmt.executeUpdate("DELETE FROM peopleinfo WHERE FirstName='Ivan'");
            ResultSet resultSet=stmt.executeQuery("SELECT * FROM peopleinfo");
            while (resultSet.next()) {
                String firstName = resultSet.getString("FirstName");
                String lastName = resultSet.getString("LastName");
                String egn = resultSet.getString("EGN");
                int age = resultSet.getInt("age");
                display.setFirstName(firstName);
                display.setLastName(lastName);
                display.setEgn(egn);
                display.setAge(age);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void executeInsertMethod(){
        try {
            stmt=connection.createStatement();
            stmt.executeUpdate("INSERT INTO peopleinfo(FirstName, LastName, EGN, age) VALUES ('Ivan', 'Ivanov', '0987654321', 40)");
            ResultSet resultSet=stmt.executeQuery("SELECT * FROM peopleinfo");
            while (resultSet.next()) {
                String firstName = resultSet.getString("FirstName");
                String lastName = resultSet.getString("LastName");
                String egn = resultSet.getString("EGN");
                int age = resultSet.getInt("age");
                display.setFirstName(firstName);
                display.setLastName(lastName);
                display.setEgn(egn);
                display.setAge(age);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void executeAlterMethod(){
        try {
            stmt=connection.createStatement();
            stmt.executeUpdate("ALTER TABLE peopleinfo ADD gender VARCHAR(6)");
            ResultSet resultSet=stmt.executeQuery("SELECT * FROM peopleinfo");
            while (resultSet.next()) {
                String firstName = resultSet.getString("FirstName");
                String lastName = resultSet.getString("LastName");
                String egn = resultSet.getString("EGN");
                int age = resultSet.getInt("age");
                display.setFirstName(firstName);
                display.setLastName(lastName);
                display.setEgn(egn);
                display.setAge(age);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void executeWhereMethod(){
        try {
            stmt = connection.createStatement();
            resultSet = stmt.executeQuery("SELECT * FROM peopleinfo WHERE age<25");
            while (resultSet.next()) {
                String firstName = resultSet.getString("FirstName");
                String lastName = resultSet.getString("LastName");
                String egn = resultSet.getString("EGN");
                int age = resultSet.getInt("age");
                display.setFirstName(firstName);
                display.setLastName(lastName);
                display.setEgn(egn);
                display.setAge(age);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void executeLikeMethod(){
        try{
            stmt=connection.createStatement();
            ResultSet resultSet=stmt.executeQuery("SELECT * FROM peopleinfo WHERE FirstName LIKE 'Dav%'");
            while(resultSet.next()){
                String firstName = resultSet.getString("FirstName");
                String lastName = resultSet.getString("LastName");
                String egn = resultSet.getString("EGN");
                int age = resultSet.getInt("age");
                display.setFirstName(firstName);
                display.setLastName(lastName);
                display.setEgn(egn);
                display.setAge(age);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void closeAllConnections(){
        try {
            resultSet.close();
            stmt.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
