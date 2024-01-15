package spring.workshop.expenses.unit.User;

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
import spring.workshop.expenses.repos.UserRepository;
import spring.workshop.expenses.services.UserService;
import spring.workshop.expenses.services.UserServiceImpl;

// This class contains unit tests for the UserController class

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class UserServiceTests {

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
        String name = "Test";
        // Given
        User user = new User(name);
        when(userRepositoryMock.save(user)).thenReturn(new User(1L, "Test"));
        // When
        User response = sut.addUser("Test");
        // Then
        assertEquals(user.getName(), response.getName());
    }

    @Test
    public void testDeleteUserPositive() {
        // Arrange
        String name = "Test";
        // Given
        when(userRepositoryMock.findByName(name)).thenReturn(Optional.of(new User(1L, "Test")));
        // When
        Boolean response = sut.deleteUser(name);
        // Then
        assertEquals(Boolean.TRUE, response);
    }

    @Test
    public void testDeleteCategoryNegative() {
        // Arrange
        String name = "Test";
        // Given
        when(userRepositoryMock.findByName(name)).thenReturn(Optional.empty());
        // When
        Boolean response = sut.deleteUser(name);
        // Then
        assertEquals(Boolean.FALSE, response);
    }

    @Test
    public void testUpdateUser() {
        // Arrange
        Long id = 1L;
        User updatedUser = new User(id, "Test2");
        // Given
        when(userRepositoryMock.findById(1L)).thenReturn(Optional.of(new User(1L, "Test1")));
        when(userRepositoryMock.save(any())).thenReturn(updatedUser);
        // When
        User response = sut.updateUser(updatedUser);
        // Then
        assertEquals(id, response.getId());
        assertEquals("Test2", response.getName());
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
        when(userRepositoryMock.findById(id)).thenReturn(Optional.of(new User(1L, "Test")));
        // When
        User response = sut.getUserById(id);
        // Then
        assertEquals(id, response.getId());
    }

    @Test
    public void testGetUserByName() {
        // Arrange
        String name = "Test";
        // Given
        when(userRepositoryMock.findByName(name)).thenReturn(Optional.of(new User(1L, "Test")));
        // When
        User response = sut.getUserByName(name);
        // Then
        assertEquals(name, response.getName());
    }

}
