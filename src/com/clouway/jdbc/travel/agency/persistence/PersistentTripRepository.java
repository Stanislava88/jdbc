package com.clouway.jdbc.travel.agency.persistence;

import com.clouway.jdbc.ExecutionException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Krasimir Raikov(raikov.krasimir@gmail.com)
 */
public class PersistentTripRepository implements TripRepository {
    private Connection connection;

    public PersistentTripRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void book(Trip trip) {
        String insertTrip = "INSERT INTO trip VALUES(?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(insertTrip);
            preparedStatement.setInt(1, trip.id);
            preparedStatement.setString(2, trip.egn);
            preparedStatement.setDate(3, trip.arrival);
            preparedStatement.setDate(4, trip.departure);
            preparedStatement.setString(5, trip.destination);
            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException e) {
            throw new ExecutionException("Could not book a trip: " + trip.id);
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
    public Trip getById(int id) {
        String selectTrip = "SELECT * FROM trip WHERE id=?";
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = connection.prepareStatement(selectTrip);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
            resultSet.next();
            int tripId = resultSet.getInt("id");
            String egn = resultSet.getString("egn");
            Date arrival = resultSet.getDate("arrival");
            Date departure = resultSet.getDate("departure");
            String destination = resultSet.getString("city");
            resultSet.close();
            preparedStatement.close();
            return new Trip(tripId, egn, arrival, departure, destination);
        } catch (SQLException e) {
            throw new ExecutionException("Could not find trip with ID: " + id);
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
    public void update(Trip trip) {
        String updateTrip = "UPDATE trip SET egn=?, arrival=?, departure=?, city=? WHERE id=?";
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(updateTrip);
            preparedStatement.setString(1, trip.egn);
            preparedStatement.setDate(2, trip.arrival);
            preparedStatement.setDate(3, trip.departure);
            preparedStatement.setString(4, trip.destination);
            preparedStatement.setInt(5, trip.id);
            preparedStatement.execute();
            preparedStatement.close();
        } catch (SQLException e) {
            throw new ExecutionException("Could not update trip [ID:" + trip.id + "]");
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
    public List<Trip> getList() {
        List<Trip> tripsList = new ArrayList<>();
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM trip;");
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
        } catch (SQLException e) {
            throw new ExecutionException("Could not gather the Trips list");
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

    @Override
    public List<String> citiesByPopularity() {
        String selectCities = "SELECT city FROM trip GROUP BY city ORDER BY count(egn) DESC, city ASC ;";
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(selectCities);
            List<String> cities = new ArrayList<>();
            while (resultSet.next()) {
                String city = resultSet.getString("city");
                cities.add(city);
            }
            resultSet.close();
            statement.close();
            return cities;
        } catch (SQLException e) {
            throw new ExecutionException("Could not load the cities by trip popularity");
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
