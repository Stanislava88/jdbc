package com.clouway.jdbc.info.users.persistence;

import com.clouway.jdbc.info.users.ExecutionException;

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
            preparedStatement.setInt(1, user.id);
            preparedStatement.setString(2, user.name);
            preparedStatement.execute();

            preparedStatement.close();
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
    public User getById(int id) {
        String selectById = "SELECT * FROM users WHERE id=?;";
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(selectById);
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            int userId = resultSet.getInt("id");
            String name = resultSet.getString("name");

            preparedStatement.close();
            resultSet.close();
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
    public List<User> getList() {
        String usersQuery = "SELECT * FROM users";
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(usersQuery);
            List<User> userList = new ArrayList<>();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                userList.add(new User(id, name));
            }
            resultSet.close();
            statement.close();
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
