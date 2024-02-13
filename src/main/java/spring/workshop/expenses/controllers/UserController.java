package spring.workshop.expenses.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import spring.workshop.expenses.entities.User;
import spring.workshop.expenses.services.UserService;

@RestController
@RequestMapping(path = "/users")
public class UserController {

  private UserService userService;
  private static final Logger LOG = LoggerFactory.getLogger(CategoryController.class);

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping()
  public ResponseEntity<User> addUser(@RequestBody User user) {
    User newUser = userService.addUser(user.getUsername(), user.getPassword(), user.getRole());

    return ResponseEntity.status(HttpStatus.CREATED)
        .body(newUser);
  }

  @DeleteMapping
  public ResponseEntity<Boolean> deleteUser(@PathVariable Long id) {
    return new ResponseEntity<>(userService.deleteUser(id), HttpStatus.OK);
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