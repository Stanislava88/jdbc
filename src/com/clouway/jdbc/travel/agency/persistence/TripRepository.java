package com.clouway.jdbc.travel.agency.persistence;

import java.sql.Date;
import java.util.List;

/**
 * @author Krasimir Raikov(raikov.krasimir@gmail.com)
 */
public interface TripRepository {
    void register(Trip trip);

    Trip getById(int id);

    void update(Trip trip);

    List<Trip> findAll();

    List<String> citiesByPopularity();

    List<Client> peopleTripsOverlapBetween(Date startDate, Date endDate, String city);
}
