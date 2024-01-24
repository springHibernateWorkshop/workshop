package spring.workshop.expenses.unit.User;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import spring.workshop.expenses.entities.User;
import spring.workshop.expenses.rest.UserController;
import spring.workshop.expenses.services.UserService;

// This class contains unit tests for the UserController

@WebMvcTest(UserController.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class UserControllerSliceTests {

    private UserController sut;

    @MockBean
    private UserService userServiceMock;

    @BeforeEach
    public void setUp() throws Exception {
        sut = new UserController(userServiceMock);
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
    }

    @Test
    public void testAddUser() {
        // Given
        when(userServiceMock.addUser(any())).thenReturn(new User(1L, "Test"));
        // When
        ResponseEntity<User> response = sut.addUser("Test");
        // Then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Test", response.getBody().getName());
    }

    @Test
    public void testDeleteUserPositive() {
        // Arrange
        String name = "Test";
        // Given
        when(userServiceMock.deleteUser(name)).thenReturn(Boolean.TRUE);
        // When
        ResponseEntity<Boolean> response = sut.deleteUser(name);
        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(true, response.getBody());
    }

    @Test
    public void testDeleteCategoryNegative() {
        // Arrange
        String name = "Test";
        // Given
        when(userServiceMock.deleteUser(name)).thenReturn(Boolean.FALSE);
        // When
        ResponseEntity<Boolean> response = sut.deleteUser(name);
        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(false, response.getBody());
    }

    @Test
    public void testUpdateUser() {
        // Arrange
        Long id = 1L;
        User updatedUser = new User(id, "Test2");
        // Given
        when(userServiceMock.getUserById(id)).thenReturn(new User(id, "Test1"));
        when(userServiceMock.updateUser(updatedUser)).thenReturn(updatedUser);
        // When
        ResponseEntity<User> response = sut.updateUser(updatedUser);
        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(id, response.getBody().getId());
        assertEquals("Test2", response.getBody().getName());
    }

    @Test
    public void testGetAllUsers() {
        // Given
        when(userServiceMock.getAllUsers()).thenReturn(List.of(new User(), new User(), new User()));
        // When
        ResponseEntity<List<User>> response = sut.getAllUsers();
        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(3, response.getBody().size());
    }

    @Test
    public void testGetUserById() {
        // Arrange
        Long id = 1L;
        // Given
        when(userServiceMock.getUserById(id)).thenReturn(new User(1L, "Test"));
        // When
        ResponseEntity<User> response = sut.getUserById(id);
        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(id, response.getBody().getId());
    }

    @Test
    public void testGetUserByName() {
        // Arrange
        String name = "Test";
        // Given
        when(userServiceMock.getUserByName(name)).thenReturn(new User(1L, "Test"));
        // When
        ResponseEntity<User> response = sut.getUserByName(name);
        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(name, response.getBody().getName());
    }

}