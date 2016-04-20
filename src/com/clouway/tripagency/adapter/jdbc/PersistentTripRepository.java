package com.clouway.tripagency.adapter.jdbc;

import com.clouway.tripagency.core.Trip;
import com.clouway.tripagency.core.TripRepository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author Stanislava Kaukova(sisiivanovva@gmail.com)
 */
public class PersistentTripRepository implements TripRepository {
  private Connection connection;

  public PersistentTripRepository(Connection connection) {
    this.connection = connection;
  }

  @Override
  public void register(Trip trip) throws SQLException {
    try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO TRIP VALUES (?,?,?,?)")) {
      preparedStatement.setDate(1, new Date(trip.dateArrived));
      preparedStatement.setDate(2, new Date(trip.dateDeparture));
      preparedStatement.setString(3, "city");

      preparedStatement.executeUpdate();
    }
  }

  @Override
  public Trip findByEgn(String egn) {
    return null;
  }
}
