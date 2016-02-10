package com.clouway.task2.adapter;

import com.clouway.task2.domain.Person;

import javax.sql.DataSource;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class PersistentPersonRepository {

    private final DataSource dataSource;

    public PersistentPersonRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void register(int egn, String name, int age, String email){
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement statement=connection.prepareStatement("insert into people(EGN,age,name,email) values(?,?,?,?)");
            statement.setInt(1,egn);
            statement.setInt(2,age);
            statement.setString(3,name);
            statement.setString(4,email);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updatePerson(int egn, String newName, int newAge, String newEmail){
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement statement=connection.prepareStatement("update people set age=?, name=?,email=? where egn=?");
            statement.setInt(1,newAge);
            statement.setString(2,newName);
            statement.setString(3,newEmail);
            statement.setInt(4,egn);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public List<Person> allPeopleInRepository(){
        List<Person> users = new LinkedList<>();
        Connection connection = null;
        Statement statement=null;
        try {
            connection = dataSource.getConnection();
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select EGN, age, name, email from people");
            while (rs.next()) {
                int egn=rs.getInt("EGN");
                int age=rs.getInt("age");
                String email = rs.getString("email");
                String name = rs.getString("name");
                users.add(new Person(egn,name,age,email));
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return users;
    }
}

