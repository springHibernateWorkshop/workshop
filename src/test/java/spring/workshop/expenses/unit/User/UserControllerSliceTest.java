package spring.workshop.expenses.unit.User;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import spring.workshop.expenses.controllers.UserController;
import spring.workshop.expenses.entities.Person;
import spring.workshop.expenses.entities.Superior;
import spring.workshop.expenses.entities.User;
import spring.workshop.expenses.services.EmployeeService;
import spring.workshop.expenses.services.SuperiorService;
import spring.workshop.expenses.services.UserService;
import spring.workshop.expenses.useCases.impl.CreateUserUcImpl;

// This class contains unit tests for the UserController

@WebMvcTest(UserController.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@ExtendWith(MockitoExtension.class)
public class UserControllerSliceTest {

    @Autowired
    private UserController sut;

    @MockBean
    private UserService userServiceMock;

    @MockBean
    private CreateUserUcImpl createUserUcMock;

    @MockBean
    EmployeeService employeeServiceMock;

    @MockBean
    SuperiorService superiorServiceMock;

    @BeforeEach
    public void setUp() throws Exception {
        // sut = new UserController(userServiceMock, createUserUcMock);
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
    }

    @Test
    public void testAddUser() {
        // Given
        User user = new User(1L, "usr", "pass", "SUPERIOR");
        // when(userServiceMock.addUser(any())).thenReturn(user);
        when(createUserUcMock.createUser(any(), any(), any())).thenReturn(new Superior(1L, "Alicja", user));
        // When
        ResponseEntity<Person> response = sut.addUser(new User("usr", "pass", "SUPERIOR"), "Alicja", 300L);
        // Then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("usr", response.getBody().getUser().getUsername());
        assertEquals("pass", response.getBody().getUser().getPassword());
    }

    @Test
    public void testDeleteUserPositive() {
        // Given
        Long userId = 1L;
        // When
        ResponseEntity<Void> response = sut.deleteUser(userId);
        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testDeleteCategoryNegative() {
        // Given
        Long userId = 1L;
        // When
        ResponseEntity<Void> response = sut.deleteUser(userId);
        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testUpdateUser() {
        // Given
        Long id = 1L;
        User updatedUser = new User(id, "usr", "pass", "EMPLOYEE");

        when(userServiceMock.getUserById(id)).thenReturn(new User(id, "user", "passXYZ", "EMPLOYEE"));
        when(userServiceMock.updateUser(updatedUser)).thenReturn(updatedUser);
        // When
        ResponseEntity<User> response = sut.updateUser(updatedUser);
        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(id, response.getBody().getId());
        assertEquals("usr", response.getBody().getUsername());
        assertEquals("pass", response.getBody().getPassword());
        assertEquals("EMPLOYEE", response.getBody().getRole());
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
        // Given
        Long id = 1L;

        when(userServiceMock.getUserById(id)).thenReturn(new User(1L, "username", "pass", "SUPERIOR"));
        // When
        ResponseEntity<User> response = sut.getUserById(id);
        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(id, response.getBody().getId());
        assertEquals("username", response.getBody().getUsername());
        assertEquals("pass", response.getBody().getPassword());
        assertEquals("SUPERIOR", response.getBody().getRole());
    }

}