package com.clouway.secondtask.core;

import java.util.Date;
import java.util.List;

/**
 * Created by clouway on 16-2-24.
 */
public interface PersonRepository {

    void create(Person person);

    void update(Person person);

    List<Person> findAll();

    List<Person> findByPartialName(String phrase);

    List<Person> findAllPeopleInTheSameCityAtTheSameTime(String city, Date arrival, Date departure);

    void delete();
}
