package com.clouway.tripagency.core;

/**
 * The implementation of this interface will be used to save and retrieve trips
 *
 * @author Stanislava Kaukova(sisiivanovva@gmail.com)
 */
public interface TripRepository {
  /**
   * @param trip registered trip
   */
  void register(Trip trip);

  /**
   * Will return trip for the person
   *
   * @param id egn of the person
   * @return trip for the person
   */

  Trip findById(int id);

  /**
   * @param id   id at the trip
   * @param trip trip
   */
  void updateById(int id, Trip trip);
}
