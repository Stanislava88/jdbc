package com.clouway.tripagency.adapter.jdbc;

import com.clouway.tripagency.core.City;
import com.clouway.tripagency.core.CityRepository;
import com.clouway.tripagency.core.Provider;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Stanislava Kaukova(sisiivanovva@gmail.com)
 */
public class PersistentCityRepository implements CityRepository {
  private Provider<Connection> provider;

  public PersistentCityRepository(Provider<Connection> provider) {
    this.provider = provider;
  }

  @Override
  public List<City> findMostVisited() {
    List<City> mostVisitedCities = new ArrayList<>();
    try (PreparedStatement preparedStatement = provider.get().prepareStatement("SELECT trip.city,count(trip.city) FROM trip GROUP BY trip.city ORDER BY COUNT(trip.city)")) {

      ResultSet resultSet = preparedStatement.executeQuery();

      while (resultSet.next()) {
        String name = resultSet.getString("city");

        mostVisitedCities.add(new City(name));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return mostVisitedCities;
  }
}
