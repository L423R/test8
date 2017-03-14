package ru.maxon.usermanager.service;

import ru.maxon.usermanager.model.User;

import java.util.List;

/**
 * Created by Maxon on 14.03.2017.
 */
public interface UserService {
    void addUser(User user);
    void updateUser(User user);
    void removeUser(int id);
    User getUserById(int id);
    List<User> listUsers();
}
