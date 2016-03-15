package com.clouway.jdbc.info.users.persistence;

import com.clouway.jdbc.ExecutionException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Krasimir Raikov(raikov.krasimir@gmail.com)
 */
public class PersistentUserRepository implements UserRepository {
    private Connection connection;

    public PersistentUserRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void register(User user) {
        String sqlStatement = "INSERT INTO users VALUES(?, ?);";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setLong(1, user.id);
            preparedStatement.setString(2, user.name);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new ExecutionException("Could not register user: " + user.id);
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public User findById(long id) {
        String selectById = "SELECT * FROM users WHERE id=?;";
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(selectById);
            preparedStatement.setLong(1, id);
            preparedStatement.execute();
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            long userId = resultSet.getLong("id");
            String name = resultSet.getString("name");
            return new User(userId, name);

        } catch (SQLException e) {
            throw new ExecutionException("Could not find address with that id.");
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public List<User> findAll() {
        String usersQuery = "SELECT * FROM users";
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(usersQuery);
            List<User> userList = new ArrayList<User>();
            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                userList.add(new User(id, name));
            }
            return userList;
        } catch (SQLException e) {
            throw new ExecutionException("Could not get the list of addresses");
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
