package spring.workshop.expenses.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import spring.workshop.expenses.entities.Person;
import spring.workshop.expenses.entities.User;
import spring.workshop.expenses.services.UserService;
import spring.workshop.expenses.useCases.CreateUserUc;

@RestController
@RequestMapping(path = "/users")
@EnableMethodSecurity(prePostEnabled = true)
public class UserController {

  private static final Logger LOG = LoggerFactory.getLogger(CategoryController.class);

  @Autowired
  private UserService userService;

  @Autowired
  private CreateUserUc createUserUc;

  @PostMapping()
  public ResponseEntity<Person> addUser(@RequestBody User user, @RequestParam String name,
      @RequestParam(name = "superior_id", required = false) Long superiorId) {
    Person newUser = createUserUc.createUser(user, name, superiorId);

    return ResponseEntity
        .created(
            ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(newUser.getId())
                .toUri())
        .body(newUser);
  }

  @DeleteMapping(path = "/{id}")
  public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
    userService.deleteUser(id);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @PutMapping
  public ResponseEntity<User> updateUser(@RequestBody User user) {
    User updatedUser = userService.updateUser(user);

    return ResponseEntity.status(HttpStatus.OK)
        .body(updatedUser);
  }

  @GetMapping
  public ResponseEntity<List<User>> getAllUsers() {
    return ResponseEntity.status(HttpStatus.OK)
        .body(userService.getAllUsers());
  }

  @GetMapping(path = "/{id}")
  public ResponseEntity<User> getUserById(@PathVariable Long id) {
    return ResponseEntity.status(HttpStatus.OK)
        .body(userService.getUserById(id));
  }

}