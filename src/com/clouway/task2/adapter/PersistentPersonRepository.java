package com.clouway.task2.adapter;

import com.clouway.task2.domain.Person;
import com.clouway.task2.domain.PersonRepository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class PersistentPersonRepository implements PersonRepository{

    private final DataSource dataSource;

    public PersistentPersonRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void register(Person person) {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement statement=connection.prepareStatement("insert into people(EGN,age,name,email) values(?,?,?,?)");
            statement.setInt(1,person.egn);
            statement.setInt(2,person.age);
            statement.setString(3,person.name);
            statement.setString(4,person.email);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Person person){
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            PreparedStatement statement=connection.prepareStatement("update people set age=?, name=?,email=? where egn=?");
            statement.setInt(1,person.age);
            statement.setString(2,person.name);
            statement.setString(3,person.email);
            statement.setInt(4,person.egn);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
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

