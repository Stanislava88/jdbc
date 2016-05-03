package com.clouway.tripagency.adapter.jdbc;

import com.clouway.tripagency.core.Provider;
import com.clouway.tripagency.core.Trip;
import com.clouway.tripagency.core.TripRepository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Stanislava Kaukova(sisiivanovva@gmail.com)
 */
public class PersistentTripRepository implements TripRepository {
  private Provider<Connection> provider;

  public PersistentTripRepository(Provider<Connection> provider) {
    this.provider = provider;
  }

  @Override
  public void register(Trip trip) {
    try (PreparedStatement preparedStatement = provider.get().prepareStatement("INSERT INTO trip(id,egn,dateArrived,dateDeparture,city) VALUES (?,?,?,?,?)")) {

      preparedStatement.setInt(1, trip.id);
      preparedStatement.setString(2, trip.egn);
      preparedStatement.setLong(3, new Date(trip.dateArrived).getTime());
      preparedStatement.setLong(4, new Date(trip.dateDeparture).getTime());
      preparedStatement.setString(5, trip.city);

      preparedStatement.executeUpdate();
    } catch (SQLException e) {
    }
  }

  @Override
  public Trip findById(int id) {
    try (PreparedStatement preparedStatement = provider.get().prepareStatement("SELECT * FROM trip WHERE id=?")) {
      preparedStatement.setInt(1, id);

      ResultSet resultSet = preparedStatement.executeQuery();

      while (resultSet.next()) {

        String egn = resultSet.getString("egn");
        long dateArrived = resultSet.getLong("dateArrived");
        long dateDeparture = resultSet.getLong("dateDeparture");
        String city = resultSet.getString("city");

        return new Trip(id, egn, dateArrived, dateDeparture, city);
      }
    } catch (SQLException e) {
    }
    return null;
  }

  @Override
  public void updateById(int id, Trip trip) {
    try (PreparedStatement preparedStatement = provider.get().prepareStatement("UPDATE trip SET egn=?,datearrived= ?, datedeparture=?,city=? WHERE id=? ")) {

      preparedStatement.setString(1, trip.egn);
      preparedStatement.setLong(2, trip.dateArrived);
      preparedStatement.setLong(3, trip.dateDeparture);
      preparedStatement.setString(4, trip.city);
      preparedStatement.setInt(5, id);

      preparedStatement.executeUpdate();
    } catch (SQLException e) {
    }
  }
}