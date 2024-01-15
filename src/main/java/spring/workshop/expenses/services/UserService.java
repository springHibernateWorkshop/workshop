package spring.workshop.expenses.services;

import java.util.List;
import spring.workshop.expenses.entities.User;

public interface UserService {

    User addUser(String name);

    Boolean deleteUser(String name);

    User updateUser(User user);

    List<User> getAllUsers();

    User getUserById(Integer id);

    User getUserByName(String name);

}