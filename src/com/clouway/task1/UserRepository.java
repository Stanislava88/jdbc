package com.clouway.task1;

import java.util.List;

/**
 * @author Slavi Dichkov (slavidichkof@gmail.com)
 */
public interface UserRepository {
    int register(User user);

    List<User> getUsers();

    User delete(Integer userId);

    List<User> findUsers(String partOfName);

    void updateUser(Integer id, String newName, String newEmail);
}
