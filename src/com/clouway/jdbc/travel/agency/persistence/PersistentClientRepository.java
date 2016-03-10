package com.clouway.jdbc.travel.agency.persistence;

import com.clouway.jdbc.travel.agency.Client;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * @author Krasimir Raikov(raikov.krasimir@gmail.com)
 */
public class PersistentClientRepository {

    private Connection connection;

    public PersistentClientRepository(Connection connection) {
        this.connection = connection;
    }

    public void register(Client client) throws SQLException {
        String insertSql = "INSERT INTO people VALUES(?, ?, ?, ?);";
        PreparedStatement preparedStatement = connection.prepareStatement(insertSql);
        preparedStatement.setString(1, client.name);
        preparedStatement.setString(2, client.egn);
        preparedStatement.setInt(3, client.age);
        preparedStatement.setString(4, client.email);
        preparedStatement.execute();
        preparedStatement.close();
    }

    public Client getByEgn(String egn) throws SQLException {
        String selectUser = "SELECT * FROM people WHERE egn=?;";
        PreparedStatement preparedStatement = connection.prepareStatement(selectUser);
        preparedStatement.setString(1, egn);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            String name = resultSet.getString("name");
            String egnReturned = resultSet.getString("egn");
            int age = resultSet.getInt("age");
            String email = resultSet.getString("email");
            resultSet.close();
            preparedStatement.close();
            return new Client(name, egnReturned, age, email);
        } else {
            resultSet.close();
            preparedStatement.close();
            throw new NoSuchElementException("There is no client with such egn.");
        }
    }

    public void update(Client client) throws SQLException {
        String updatePeople = "UPDATE people SET name=?, egn=?, age=?, email=? WHERE egn=?;";
        PreparedStatement preparedStatement = connection.prepareStatement(updatePeople);
        preparedStatement.setString(1, client.name);
        preparedStatement.setString(2, client.egn);
        preparedStatement.setInt(3, client.age);
        preparedStatement.setString(4, client.email);
        preparedStatement.setString(5, client.egn);
        preparedStatement.execute();
        preparedStatement.close();
    }

    public List<Client> getClientsList() throws SQLException {
        return getClientsWith("");
    }

    public List<Client> getClientsWith(String nameBeginning) throws SQLException {
        String clientsLike = "SELECT * FROM people WHERE name LIKE '" + nameBeginning + "%';";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(clientsLike);
        List<Client> clientList = new ArrayList<>();
        while (resultSet.next()) {
            String name = resultSet.getString("name");
            String egn = resultSet.getString("egn");
            int age = resultSet.getInt("age");
            String email = resultSet.getString("email");
            clientList.add(new Client(name, egn, age, email));
        }
        resultSet.close();
        statement.close();
        return clientList;
    }
}
