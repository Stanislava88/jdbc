package com.clouway.task2.domain;

import java.util.Date;
import java.util.List;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public interface TripRepository {
    void register(TripRequest tripRequest);
    List<Person> getPeopleInCityOnDate(String city, Date from, Date to);
    List<String> mostVisitedCities();
    void deleteRepositoryContent();
    void deleteRepository();
}
