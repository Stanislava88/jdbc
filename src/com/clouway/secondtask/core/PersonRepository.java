package com.clouway.secondtask.core;

import java.util.List;

/**
 * Created by clouway on 16-2-24.
 */
public interface PersonRepository {

    void create(Person person);

    void update(Person person);

    List<Person> findAll();

    List<Person> getAllPeopleNameWhichStartWithSameCharacters(String phrase);

    void delete(String chooseTableOrContent);
}
