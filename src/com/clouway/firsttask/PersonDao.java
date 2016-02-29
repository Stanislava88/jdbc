package com.clouway.firsttask;

import java.sql.*;

/**
 * Created by clouway on 16-2-24.
 */
public class PersonDao {
    private String DB_URL;
    private String user;
    private String password;
    private Connection connection;
    private Statement stmt;
    private Display display;
    private ResultSet resultSet;

    public PersonDao(String DB_URL, String user, String password, Display display) {
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

    //preimenuvan metoda na findAll i da vrystha list sys vsi4ki obekti v tablicata
    public void select() {
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

    //podavame obekta person
    public void update(){
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

    //podavame nqkakyv paramter po koito da triem zapis
    public void delete(){
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

    //podavame obekt ot Person
    public void insert(){
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

    //podavam nqkakyv string(imeto na kolonata) parameter
    public void alter(){
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

    //find by name i podavame za prameter godini
    public void where(){
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

    //podavame wildcard kato parameter
    public void like(){
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
