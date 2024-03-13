package spring.workshop.expenses.services.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import spring.workshop.expenses.entities.User;
import spring.workshop.expenses.exceptions.ResourceNotFoundException;
import spring.workshop.expenses.repositories.AbstractRepositoryHelper;
import spring.workshop.expenses.repositories.UserRepository;
import spring.workshop.expenses.services.UserService;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);

    private UserRepository userRepository;

    @Autowired
    private AbstractRepositoryHelper<User> abstractRepositoryHelper;

    @Autowired
    private void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
        abstractRepositoryHelper.setRepository(userRepository);

    }

    @Override
    public User addUser(User user) {

        User createdUser = abstractRepositoryHelper.saveAndRefresh(user);

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
            return abstractRepositoryHelper.saveAndRefresh(updatedUser);

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
}
