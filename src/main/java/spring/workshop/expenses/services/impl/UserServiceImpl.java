package spring.workshop.expenses.services.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import spring.workshop.expenses.entities.User;
import spring.workshop.expenses.exceptions.ResourceNotFoundException;
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
    public User addUser(String username, String pass, Long roleId) {
        if (username == null || username.trim().isEmpty())
            throw new IllegalArgumentException("User with empty username cannot be created.");

        if (pass == null || pass.trim().isEmpty())
            throw new IllegalArgumentException("User with empty password cannot be created.");

        if (roleId == null)
            throw new IllegalArgumentException("User with empty roleID cannot be created.");

        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent())
            throw new IllegalArgumentException("User with username = " + username + " already exists.");

        User newUser = new User(username, pass, roleId);
        // create an employee

        // or create a superior

        userRepository.save(newUser);
        LOG.info("User with name = " + username + " created successfully.");
        return newUser;
    }

    @Override
    public User updateUser(User user) {
        Optional<User> oldUser = userRepository.findById(user.getId());
        if (oldUser.isPresent()) {
            User updatedUser = oldUser.get();
            updatedUser.setUsername(user.getUsername());
            updatedUser.setPassword(user.getPassword());
            updatedUser.setRole(user.getRole());
            LOG.info("User with id = " + user.getId() + " updated succesfully.");
            return userRepository.save(updatedUser);

            // TODO Role update check
        } else {
            throw new IllegalArgumentException("User with id = " + user.getId() + " not found.");
        }
    }

    @Override
    public void deleteUser(Long id) {
        User userToDelete= userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User with user ID = " + id + " not found."));
        userRepository.delete(userToDelete);
        LOG.info("User with user ID = {} deleted successfully.", userToDelete.getId());
            // TODO userId auf null setzen für Emplyoee / Superior
        }
        
        /**
         *  ).orElseGet(() -> {
            LOG.warn("User with user ID = {} not found.", id);
            throw new IllegalArgumentException();
        });
        */
 

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent())
            throw new ResourceNotFoundException("User with id = " + id + " not found.");

        return user.get();
    }
}
