package spring.workshop.expenses.unit.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import spring.workshop.expenses.entities.User;
import spring.workshop.expenses.repositories.UserRepository;
import spring.workshop.expenses.services.UserService;
import spring.workshop.expenses.services.impl.UserServiceImpl;

// This class contains unit tests for the UserController class

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class UserServiceTest {

    private UserService sut;

    @Mock
    UserRepository userRepositoryMock;

    @BeforeEach
    public void setup() {
        // CategoryRepository categoryRepositoryMock =
        // Mockito.mock(CategoryRepository.class);
        sut = new UserServiceImpl(userRepositoryMock);
    }

    @Test
    public void testAddUser() {
        // Arrange
        String username = "usrname";
        String pass = "pass";
        Long roleID = 2L;
        // Given
        User user = new User(username, pass, roleID);
        when(userRepositoryMock.save(user)).thenReturn(new User(username, pass, roleID));
        // When
        User response = sut.addUser(username, pass, roleID);
        // Then
        assertEquals(user.getUsername(), response.getUsername());
        assertEquals(user.getPassword(), response.getPassword());
        assertEquals(user.getRole(), response.getRole());
    }

    @Test
    public void testUpdateUser() {
        // Arrange
        Long id = 1L;
        User updatedUser = new User(id, "usrname", "passX", 4L);
        // Given
        when(userRepositoryMock.findById(1L)).thenReturn(Optional.of(new User(1L, "usern", "passZ", 4L)));
        when(userRepositoryMock.save(any())).thenReturn(updatedUser);
        // When
        User response = sut.updateUser(updatedUser);
        // Then
        assertEquals(id, response.getId());
        assertEquals("usrname", response.getUsername());
        assertEquals("passX", response.getPassword());
        assertEquals(4L, response.getRole());
    }

    @Test
    public void testGetAllUsers() {
        // Given
        when(userRepositoryMock.findAll()).thenReturn(List.of(new User(), new User(), new User()));
        // When
        List<User> response = sut.getAllUsers();
        // Then
        assertEquals(3, response.size());
    }

    @Test
    public void testGetUserById() {
        // Arrange
        Long id = 1L;
        // Given
        when(userRepositoryMock.findById(id)).thenReturn(Optional.of(new User(1L, "user", "password", 1L)));
        // When
        User response = sut.getUserById(id);
        // Then
        assertEquals(id, response.getId());
    }
}
