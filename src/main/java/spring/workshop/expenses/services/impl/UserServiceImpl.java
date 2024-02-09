package spring.workshop.expenses.services.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import spring.workshop.expenses.entities.User;
import spring.workshop.expenses.repositories.UserRepository;
import spring.workshop.expenses.services.UserService;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User addUser(String name) {
        if (name == null || name.trim().isEmpty())
            throw new IllegalArgumentException("User with empty name cannot be created.");

        Optional<User> user = userRepository.findByName(name);
        if (user.isPresent())
            throw new IllegalArgumentException("User with name = " + name + " already exists.");

        User newUser = new User(name);
        userRepository.save(newUser);
        LOG.info("User with name = " + name + " created successfully.");
        return newUser;
    }

    @Override
    public User updateUser(User user) {
        Optional<User> oldUser = userRepository.findById(user.getId());
        if (oldUser.isPresent()) {
            User updatedUser = oldUser.get();
            updatedUser.setName(user.getName());
            LOG.info("User with id = " + user.getId() + " updated succesfully.");
            return userRepository.save(updatedUser);
        } else {
            throw new IllegalArgumentException("User with id = " + user.getId() + " not found.");
        }
    }

    @Override
    public Boolean deleteUser(String name) {
        Optional<User> user = userRepository.findByName(name);
        if (user.isPresent()) {
            userRepository.deleteByName(name);
            LOG.info("User with name = " + name + " deleted successfully.");
            return true;
        } else {
            LOG.info("User with name = " + name + " not found.");
            return false;
        }
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent())
            throw new IllegalArgumentException("User with id = " + id + " not found.");

        return user.get();
    }

    @Override
    public User getUserByName(String name) {
        Optional<User> user = userRepository.findByName(name);
        if (!user.isPresent())
            throw new IllegalArgumentException("User with name = " + name + " not found.");

        return user.get();
    }

}
