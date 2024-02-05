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
        when(userServiceMock.addUser(any(), any(), any())).thenReturn(new User(1L, "usr", "pass", 2L));
        // When
        ResponseEntity<User> response = sut.addUser("usr", "pass", 2L);
        // Then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("usr", response.getBody().getUsername());
        assertEquals("pass", response.getBody().getPassword());
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
        User updatedUser = new User(id, "usr", "pass", 2L);
        // Given
        when(userServiceMock.getUserById(id)).thenReturn(new User(id, "user", "passXYZ", 2L));
        when(userServiceMock.updateUser(updatedUser)).thenReturn(updatedUser);
        // When
        ResponseEntity<User> response = sut.updateUser(updatedUser);
        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(id, response.getBody().getId());
        assertEquals("usr", response.getBody().getUsername());
        assertEquals("pass", response.getBody().getPassword());
        assertEquals(2L, response.getBody().getRole());
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
        when(userServiceMock.getUserById(id)).thenReturn(new User(1L, "username", "pass", 3L));
        // When
        ResponseEntity<User> response = sut.getUserById(id);
        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(id, response.getBody().getId());
        assertEquals("username", response.getBody().getUsername());
        assertEquals("pass", response.getBody().getPassword());
        assertEquals(3L, response.getBody().getRole());
    }

    @Test
    public void testGetUserByUsername() {
        // Arrange
        String name = "Test";
        // Given
        when(userServiceMock.getUserByUsername(name)).thenReturn(new User(1L, "username", "pass", 3L));
        // When
        ResponseEntity<User> response = sut.getUserByUsername(name);
        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("username", response.getBody().getUsername());
        assertEquals("pass", response.getBody().getPassword());
        assertEquals(3L, response.getBody().getRole());
    }

}