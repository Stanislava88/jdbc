package com.clouway.tripagency.core;

import java.util.List;

/**
 * The implementation of this interface will be used to save and retrieve cities
 *
 * @author Stanislava Kaukova(sisiivanovva@gmail.com)
 */
public interface CityRepository {
  /**
   * Will return list of the most popular cities
   *
   * @return list of the cities
   */
  List<City> findMostVisited();
}
