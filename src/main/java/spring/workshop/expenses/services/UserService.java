package spring.workshop.expenses.services;

import java.util.List;

import spring.workshop.expenses.entities.User;

public interface UserService {

    User addUser(User user);

    void deleteUser(Long id);

    User updateUser(User user);

    List<User> getAllUsers();

    User getUserById(Long id);

    User getUserByUsername(String username);

}