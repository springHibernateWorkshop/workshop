package spring.workshop.expenses.services;

import java.util.List;

import spring.workshop.expenses.entities.User;

public interface UserService {

    User addUser(String username, String pass, Long roleId);

    Boolean deleteUser(Long id);

    User updateUser(User user);

    List<User> getAllUsers();

    User getUserById(Long id);
}