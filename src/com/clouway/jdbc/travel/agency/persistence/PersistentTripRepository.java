package com.clouway.jdbc.travel.agency.persistence;

import com.clouway.jdbc.travel.agency.Trip;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * @author Krasimir Raikov(raikov.krasimir@gmail.com)
 */
public class PersistentTripRepository {
    private Connection connection;

    public PersistentTripRepository(Connection connection) {
        this.connection = connection;
    }

    public void book(Trip tripName) throws SQLException {
        String insertTrip = "INSERT INTO trip VALUES(?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(insertTrip);
        preparedStatement.setInt(1, tripName.id);
        preparedStatement.setString(2, tripName.egn);
        preparedStatement.setDate(3, tripName.arrival);
        preparedStatement.setDate(4, tripName.departure);
        preparedStatement.setString(5, tripName.destination);
        preparedStatement.execute();
        preparedStatement.close();
    }

    public Trip getById(int id) throws SQLException {
        String selectTrip = "SELECT * FROM trip WHERE id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(selectTrip);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            int tripId = resultSet.getInt("id");
            String egn = resultSet.getString("egn");
            Date arrival = resultSet.getDate("arrival");
            Date departure = resultSet.getDate("departure");
            String destination = resultSet.getString("city");
            resultSet.close();
            preparedStatement.close();
            return new Trip(tripId, egn, arrival, departure, destination);
        } else {
            resultSet.close();
            preparedStatement.close();
            throw new NoSuchElementException("There is no trip with this id.");
        }
    }

    public void update(Trip trip) throws SQLException {
        String updateTrip = "UPDATE trip SET egn=?, arrival=?, departure=?, city=? WHERE id=?";
        PreparedStatement preparedStatement = connection.prepareStatement(updateTrip);
        preparedStatement.setString(1, trip.egn);
        preparedStatement.setDate(2, trip.arrival);
        preparedStatement.setDate(3, trip.departure);
        preparedStatement.setString(4, trip.destination);
        preparedStatement.setInt(5, trip.id);
        preparedStatement.execute();
        preparedStatement.close();
    }

    public List<Trip> getList() throws SQLException {
        List<Trip> tripsList = new ArrayList<>();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM trip;");
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String egn = resultSet.getString("egn");
            Date arrival = resultSet.getDate("arrival");
            Date departure = resultSet.getDate("departure");
            String destination = resultSet.getString("city");
            tripsList.add(new Trip(id, egn, arrival, departure, destination));
        }
        resultSet.close();
        statement.close();
        return tripsList;
    }

    public List<String> citiesByPopularity() throws SQLException {
        String selectCities = "SELECT city FROM trip GROUP BY city ORDER BY count(egn) DESC, city ASC ;";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(selectCities);
        List<String> cities = new ArrayList<>();
        while (resultSet.next()) {
            String city = resultSet.getString("city");
            cities.add(city);
        }
        resultSet.close();
        statement.close();
        return cities;
    }
}
