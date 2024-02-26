package spring.workshop.expenses.services.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import spring.workshop.expenses.entities.User;
import spring.workshop.expenses.exceptions.ResourceNotFoundException;
import spring.workshop.expenses.repositories.UserRepository;
import spring.workshop.expenses.security.Role;
import spring.workshop.expenses.services.RoleService;
import spring.workshop.expenses.services.UserService;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleService roleService;

    public UserServiceImpl() {

    }

    @Override
    public User addUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Fetch the role information from the database based on role.id
        Role role = roleService.findById(user.getRole().getId());

        user.setRole(role);
        User createdUser = userRepository.save(user);

        LOG.info("User with name = " + user.getUsername() + " created successfully.");
        return createdUser;
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

            // TODO Role update check, can role be updated?
        } else {
            throw new IllegalArgumentException("User with id = " + user.getId() + " not found.");
        }
    }

    @Override
    public void deleteUser(Long id) {
        User userToDelete = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with user ID = " + id + " not found."));
        userRepository.delete(userToDelete);
        LOG.info("User with user ID = {} deleted successfully.", userToDelete.getId());
    }

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

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User with username = " + username + " not found."));
    }
}
