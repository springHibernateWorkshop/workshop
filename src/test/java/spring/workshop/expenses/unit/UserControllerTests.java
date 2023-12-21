package spring.workshop.expenses.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import jakarta.transaction.Transactional;
import spring.workshop.expenses.entities.User;
import spring.workshop.expenses.repos.UserRepository;
import spring.workshop.expenses.rest.UserController;

@SpringBootTest
@Transactional
@Rollback
class UserControllerTests {

    @Autowired
    private UserController userController;

    @Autowired
    private UserRepository userRepository;
    
    @Test
    public void testAddNewUser() {
    // Calling the method to create an entity    
    userController.addNewUser("Test");

    // Verify that the entity was created and returned
    User userCreated = userRepository.findByName("Test");
    assertNotNull(userCreated, "Entity should not be null");
    }

    @Test
    public void testDeleteUser() {
    // Create an entity to be deleted later
    User user = new User("Test");
    userRepository.save(user);

    // Verify that the entity was created and returned
    assertNotNull(user, "Entity should not be null");    

    // Calling the method to delete the entity
    userController.deleteUser("Test");

    // Verify that the entity was deleted
    User userDeleted = userRepository.findByName("Test");
    assertNull(userDeleted, "Entity should be null");
    }

    @Test
    public void testGetAllUsers() {
    // Create an entity
    User user = new User("Test");
    userRepository.save(user);

    // Verify that the entity was created and returned
    assertNotNull(user, "Entity should not be null");    

    // Calling the method to find all entities and verify that the created entity is returned
    List<User> users = userController.getAllUsers();
    assertEquals(1, users.size(), "Size should be 1");
    }

}