package com.clouway.jdbc.info.users.persistence;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * @author Krasimir Raikov(raikov.krasimir@gmail.com)
 */
public class PersistentUserRepository {
    private Connection connection;

    public PersistentUserRepository(Connection connection) {
        this.connection = connection;
    }

    public void register(User user) {
        String sqlStatement = "INSERT INTO users VALUES(?, ?);";
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(sqlStatement);
            preparedStatement.setInt(1, user.id);
            preparedStatement.setString(2, user.name);
            preparedStatement.execute();

            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public User getById(int id) throws SQLException {
        String selectById = "SELECT * FROM users WHERE id=?;";
        PreparedStatement preparedStatement = connection.prepareStatement(selectById);
        preparedStatement.setInt(1, id);
        preparedStatement.execute();
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            int userId = resultSet.getInt("id");
            String name = resultSet.getString("name");

            preparedStatement.close();
            resultSet.close();
            return new User(userId, name);
        } else {
            resultSet.close();
            preparedStatement.close();
            throw new NoSuchElementException("No users with such id.");
        }
    }

    public List<User> getList() throws SQLException {
        String usersQuery = "SELECT * FROM users";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(usersQuery);
        List<User> userList = new ArrayList<>();
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            userList.add(new User(id, name));
        }
        resultSet.close();
        statement.close();
        return userList;
    }
}
