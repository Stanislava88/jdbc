package com.clouway.jdbc.travel.agency.persistence;

import com.clouway.jdbc.travel.agency.Client;
import com.clouway.jdbc.travel.agency.ExecutionException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Krasimir Raikov(raikov.krasimir@gmail.com)
 */
public class PersistentClientRepository {

    private Connection connection;

    public PersistentClientRepository(Connection connection) {
        this.connection = connection;
    }

    public void register(Client client) {
        String insertSql = "INSERT INTO people VALUES(?, ?, ?, ?);";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(insertSql);
            preparedStatement.setString(1, client.name);
            preparedStatement.setString(2, client.egn);
            preparedStatement.setInt(3, client.age);
            preparedStatement.setString(4, client.email);
            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException e) {
            throw new ExecutionException("Could not register client");
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

    public Client getByEgn(String egn) {
        String selectUser = "SELECT * FROM people WHERE egn=?;";
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(selectUser);
            preparedStatement.setString(1, egn);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            String name = resultSet.getString("name");
            String egnReturned = resultSet.getString("egn");
            int age = resultSet.getInt("age");
            String email = resultSet.getString("email");
            resultSet.close();
            preparedStatement.close();
            return new Client(name, egnReturned, age, email);
        } catch (SQLException e) {
            throw new ExecutionException("Could not find client with such egn: " + egn);
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

    public void update(Client client) {
        String updatePeople = "UPDATE people SET name=?, egn=?, age=?, email=? WHERE egn=?;";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(updatePeople);
            preparedStatement.setString(1, client.name);
            preparedStatement.setString(2, client.egn);
            preparedStatement.setInt(3, client.age);
            preparedStatement.setString(4, client.email);
            preparedStatement.setString(5, client.egn);
            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException e) {
            throw new ExecutionException("Could not update client with egn " + client.egn);
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

    public List<Client> getClientsList() {
        return getClientsWith("");
    }

    public List<Client> getClientsWith(String nameBeginning) {
        String clientsLike = "SELECT * FROM people WHERE name LIKE '" + nameBeginning + "%';";
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(clientsLike);
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
        } catch (SQLException e) {
            throw new ExecutionException("Could not find clients whose names begin with: " + nameBeginning);
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
