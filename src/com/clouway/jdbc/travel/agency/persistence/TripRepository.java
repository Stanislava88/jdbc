package com.clouway.jdbc.travel.agency.persistence;

import java.util.List;

/**
 * @author Krasimir Raikov(raikov.krasimir@gmail.com)
 */
public interface TripRepository {
    void book(Trip trip);

    Trip getById(int id);

    void update(Trip trip);

    List<Trip> getList();

    List<String> citiesByPopularity();
}
