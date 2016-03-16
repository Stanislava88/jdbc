package com.clouway.firsttask;

import java.util.List;

/**
 * Created by clouway on 16-3-16.
 */
public interface PersonRepository {

    void register(Person person);

    List<Person> findAll();

    void update(Person person);

    void delete(String egn);

    void alter(String columname, String columnType);

    Person find(String egn);

    List<Person> like(String columName, String wildcard);

    void deleteAll();

}
