package com.clouway.secondtask.core;

import java.util.List;

/**
 * Created by clouway on 16-2-24.
 */
public interface TripRepository {

    void create(Trip trip);

    void update(Trip trip);

    List<Trip> getAll();

    List<String> getMostVisitedCities();

    void deleteAll();

}
