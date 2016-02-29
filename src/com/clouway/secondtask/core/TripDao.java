package com.clouway.secondtask.core;

import java.util.Date;
import java.util.List;

/**
 * Created by clouway on 16-2-24.
 */
public interface TripDao {

    void create(Trip trip);

    void update(Trip trip);

    List<Trip> getTrips();

    List<String> getMostVisitedCities();

    void deleteTrip(String chooseTableOrContent);

    List<Person> findAllPeopleInTheSameCityAtTheSameTime(Date arrival, Date departure);
}
