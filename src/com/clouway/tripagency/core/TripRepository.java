package com.clouway.tripagency.core;

import java.sql.SQLException;

/**
 * @author Stanislava Kaukova(sisiivanovva@gmail.com)
 */
public interface TripRepository {
  void register(Trip trip) throws SQLException;

  Trip findByEgn(String egn);
}
