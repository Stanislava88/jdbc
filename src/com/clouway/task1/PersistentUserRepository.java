package com.clouway.task1;

import com.clouway.task1.User;
import com.clouway.task1.UserRepository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public class PersistentUserRepository implements UserRepository {
    private final DataSource dataSource;

    public PersistentUserRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public int register(User user) {
        int id=0;
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("insert into users(email, name) values(?,?)",Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.email);
            statement.setString(2, user.name);
            statement.executeUpdate();
            ResultSet rs=statement.getGeneratedKeys();
            if(rs.next()){
                id=rs.getInt(1);
            }
            statement.close();
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
            if (connection!=null){
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return id;
    }

    @Override
    public List<User> getUsers() {
        List<User> users = new LinkedList<>();
        Connection connection = null;
        Statement statement=null;
        try {
            connection = dataSource.getConnection();
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select email, name from users");
            while (rs.next()) {
                String email = rs.getString("email");
                String name = rs.getString("name");
                users.add(new User(email, name));
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
            if (connection!=null){
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return users;
    }

    @Override
    public User delete(Integer userId) {
        Connection connection = null;
        Statement statement=null;
        User user=null;
        try {
            connection = dataSource.getConnection();
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select email, name from users where id="+userId);
            while (rs.next()){
                String email = rs.getString("email");
                String name = rs.getString("name");
                user=new User(email, name);
            }
            rs.close();
            statement.executeUpdate("delete from users where id="+userId);


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
            if (connection!=null){
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return user;
    }

    @Override
    public List<User> findUsers(String partOfName) {
        Connection connection = null;
        Statement statement=null;
       List<User> users=new ArrayList<>();
        try {
            connection = dataSource.getConnection();
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select email, name from users where name like '%"+partOfName+"%'");
            while (rs.next()){
                String email = rs.getString("email");
                String name = rs.getString("name");
                users.add(new User(email, name));
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
            if (connection!=null){
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return users;
    }

    @Override
    public void updateUser(Integer id, String newName, String newEmail) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.prepareStatement("update users set email=?, name=?  where id=?");
            statement.setString(1, newEmail);
            statement.setString(2, newName);
            statement.setInt(3,id);

            statement.execute();

            statement.close();
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
            if (connection!=null){
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
