package com.clouway.tripagency.core;

import java.util.List;

/**
 * @author Stanislava Kaukova(sisiivanovva@gmail.com)
 */
public interface TripsPeopleRepository {
  /**
   * Will return list of people list who are detected in a name
   *
   * @param startDate starting date interval
   * @param endDate   ending date interval
   * @param city      name
   * @return list of people
   */

  List<Person> overlapPeopleInCity(long startDate, long endDate, String city);
}
