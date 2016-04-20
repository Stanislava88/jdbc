package com.clouway.tripagency.core;

import java.sql.SQLException;
import java.util.List;

/**
 * @author Stanislava Kaukova(sisiivanovva@gmail.com)
 */
public interface PeopleRepository {
  void register(People people) throws SQLException;

  People findByEgn(String egn) throws SQLException;

  void updateEmailByEgn(String email, String egn) throws SQLException;

  List<People> findAll() throws SQLException;

  List<People> findNamesLikeSymbol(String symbol) throws SQLException;
}
