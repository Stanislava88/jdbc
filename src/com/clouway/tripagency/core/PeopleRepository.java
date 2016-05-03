package com.clouway.tripagency.core;

import java.util.List;

/**
 * The implementation of this interface will be used to save and retrieval people information
 *
 * @author Stanislava Kaukova(sisiivanovva@gmail.com)
 */
public interface PeopleRepository {
  /**
   * @param person registered person
   */
  void register(Person person);

  /**
   * Will return person
   *
   * @param egn egn of the person
   * @return person
   */
  Person findByEgn(String egn);

  /**
   * @param egn    egn of the person
   * @param person person
   */
  void updateByEgn(String egn, Person person);

  /**
   * Will return list of all people
   *
   * @return list of people
   */
  List<Person> findAll();

  /**
   * Will return list of people meeting certain requirements
   *
   * @param pattern matched values
   * @return list of people
   */
  List<Person> findMatching(String pattern);
}
