package com.clouway.jdbc.travel.agency.persistence;

import java.sql.Date;
import java.util.List;

/**
 * @author Krasimir Raikov(raikov.krasimir@gmail.com)
 */
public interface ClientTrips {
    List<Client> peopleTripsOverlapBetween(Date startDate, Date endDate, String city);
}
