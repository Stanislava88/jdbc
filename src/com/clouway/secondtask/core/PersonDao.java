package com.clouway.secondtask.core;

import java.util.List;

/**
 * Created by clouway on 16-2-24.
 */
public interface PersonDao {

    void create(Person person);

    void update(Person person);

    List<Person> getPeople();

    List<Person> getAllPeopleNameWhichStartWithSameCharacters(String phrase);

    void deletePeople(String chooseTableOrContent);
}
