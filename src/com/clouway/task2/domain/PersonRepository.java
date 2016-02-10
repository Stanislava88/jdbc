package com.clouway.task2.domain;

import java.util.List;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public interface PersonRepository {
    void register(int egn, String name, int age, String email);
    void updatePerson(int egn, String newName, int newAge, String newEmail);
    List<Person> allPeopleInRepository();
}
