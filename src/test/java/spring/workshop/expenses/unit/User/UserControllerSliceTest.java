package spring.workshop.expenses.unit.User;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
import spring.workshop.expenses.dto.UserDTO;
import spring.workshop.expenses.entities.Person;
import spring.workshop.expenses.entities.Superior;
import spring.workshop.expenses.entities.User;
import spring.workshop.expenses.mapper.UserMapper;
import spring.workshop.expenses.security.Role;
import spring.workshop.expenses.services.EmployeeService;
import spring.workshop.expenses.services.SuperiorService;
import spring.workshop.expenses.services.UserService;
import spring.workshop.expenses.useCases.impl.CreateUserUcImpl;

// This class contains unit tests for the UserController

@WebMvcTest(UserController.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class UserControllerSliceTest {

    @Autowired
    private UserController sut;

    @MockBean
    private UserService userServiceMock;

    @MockBean
    private UserMapper userMapperMock;

    @MockBean
    private CreateUserUcImpl createUserUcMock;

    @MockBean
    EmployeeService employeeServiceMock;

    @MockBean
    SuperiorService superiorServiceMock;

    @BeforeEach
    public void setUp() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

    }

    @Test
    public void testAddUser() {
        // Given
        User user = new User(1L, "usr", "pass", new Role("ROLE_EMPLOYEE"));
        when(createUserUcMock.createUser(any(), any(), any())).thenReturn(new Superior(1L, "Alicja", user));
        // When
        ResponseEntity<Person> response = sut.addUser(
                new User("usr", "pass", new Role("ROLE_EMPLOYEE")), "Alicja",
                300L);
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
    public void testGetAllUsers() {
        // Given
        when(userServiceMock.getAllUsers()).thenReturn(List.of(new User(), new User(), new User()));
        when(userMapperMock.toDto(anyList())).thenReturn(List.of(new UserDTO(), new UserDTO(), new UserDTO()));
        // When
        ResponseEntity<List<UserDTO>> response = sut.getAllUsers();
        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(3, response.getBody().size());
    }

    @Test
    public void testGetUserById() {
        // Given
        Long id = 1L;
        User user = new User(1L, "username", "pass", new Role("ROLE_SUPERIOR"));
        UserDTO userDto = new UserDTO();
        userDto.setId(id);
        userDto.setRole(new Role("ROLE_SUPERIOR"));
        when(userServiceMock.getUserById(id)).thenReturn(user);
        when(userMapperMock.toDto(user)).thenReturn(userDto);
        // When
        ResponseEntity<UserDTO> response = sut.getUserById(id);
        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(id, response.getBody().getId());
        assertEquals("ROLE_SUPERIOR", response.getBody().getRole().getAuthority());

    }

}