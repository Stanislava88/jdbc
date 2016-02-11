package com.clouway.task2.domain;

import java.util.List;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public interface PersonRepository {
    void register(Person person);
    void updatePerson(Person peerson);
    List<Person> allPeopleInRepository();
}
