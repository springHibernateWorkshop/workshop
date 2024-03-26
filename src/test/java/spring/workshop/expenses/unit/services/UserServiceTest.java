package spring.workshop.expenses.unit.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import spring.workshop.expenses.entities.User;
import spring.workshop.expenses.repositories.AbstractRepositoryHelper;
import spring.workshop.expenses.repositories.UserRepository;
import spring.workshop.expenses.security.Role;
import spring.workshop.expenses.services.RoleService;
import spring.workshop.expenses.services.UserService;
import spring.workshop.expenses.services.impl.UserServiceImpl;

// This class contains unit tests for the UserController class

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class UserServiceTest {

        @Mock
        PasswordEncoder passwordEncoderMock;

        @Mock
        RoleService roleServiceMock;

        @Mock
        UserRepository userRepositoryMock;

        @Mock
        AbstractRepositoryHelper<User> abstractRepositoryHelperMock;

        @InjectMocks
        private UserService sut = new UserServiceImpl();

        @Test
        public void testAddEmployee() {
                // Arrange
                String username = "username";
                String pass = "pass";
                Role role = new Role(1L, "ROLE_EMPLOYEE");
                // Given
                User user = new User(username, pass, role);
                when(passwordEncoderMock.encode(any(String.class))).thenReturn(user.getPassword());
                when(roleServiceMock.findById(any(Long.class))).thenReturn(role);
                when(abstractRepositoryHelperMock.saveAndRefresh(any(User.class)))
                                .thenReturn(new User(username, pass, role));
                // When
                User response = sut.createUser(user);
                // Then
                assertEquals(user.getUsername(), response.getUsername());
                assertEquals(user.getPassword(), response.getPassword());
                assertEquals(user.getRole(), response.getRole());
        }

        @Test
        public void testAddSuperior() {
                // Arrange
                String username = "usrname";
                String pass = "pass";
                Role role = new Role(2L, "SUPERIOR");
                // Given
                User user = new User(username, pass, role);
                when(passwordEncoderMock.encode(any(String.class))).thenReturn(user.getPassword());
                when(roleServiceMock.findById(any(Long.class))).thenReturn(role);
                when(abstractRepositoryHelperMock.saveAndRefresh(any(User.class)))
                                .thenReturn(new User(username, pass, role));
                // When
                User response = sut.createUser(user);
                // Then
                assertEquals(user.getUsername(), response.getUsername());
                assertEquals(user.getPassword(), response.getPassword());
                assertEquals(user.getRole(), response.getRole());
        }

        // TODO negative Role != EMPLOYEE or SUPERIOR

        @Test
        public void testUpdateUser() {
                // Arrange
                Long id = 1L;
                User updatedUser = new User(id, "usrname", "passX", new Role("ROLE_EMPLOYEE"));
                // Given
                when(userRepositoryMock.findById(1L))
                                .thenReturn(Optional.of(new User(1L, "usern", "passZ", new Role("ROLE_EMPLOYEE"))));
                when(abstractRepositoryHelperMock.saveAndRefresh(any(User.class))).thenReturn(updatedUser);
                // When
                User response = sut.updateUser(updatedUser);
                // Then
                assertEquals(id, response.getId());
                assertEquals("usrname", response.getUsername());
                assertEquals("passX", response.getPassword());
                assertEquals("ROLE_EMPLOYEE", response.getRole().getAuthority());
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
                when(userRepositoryMock.findById(id))
                                .thenReturn(Optional.of(new User(1L, "user", "password", new Role("EMPLOYEE"))));
                // When
                User response = sut.getUserById(id);
                // Then
                assertEquals(id, response.getId());
        }
}
