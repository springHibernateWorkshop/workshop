package spring.workshop.expenses.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

  @PostMapping(path = "/add")
  public ResponseEntity<User> addUser(@RequestParam String name) {
    User newUser = userService.addUser(name);

    return ResponseEntity.status(HttpStatus.CREATED)
        .body(newUser);
  }

  @DeleteMapping(path = "/delete")
  public ResponseEntity<Boolean> deleteUser(@RequestParam String name) {
    return ResponseEntity.status(HttpStatus.OK)
        .body(userService.deleteUser(name));
  }

  @PutMapping(path = "/update")
  public ResponseEntity<User> updateUser(@RequestBody User user) {
    User updatedUser = userService.updateUser(user);

    return ResponseEntity.status(HttpStatus.OK)
        .body(updatedUser);
  }

  @GetMapping(path = "/get_all")
  public ResponseEntity<List<User>> getAllUsers() {
    return ResponseEntity.status(HttpStatus.OK)
        .body(userService.getAllUsers());
  }

  @GetMapping(path = "/get_by_id")
  public ResponseEntity<User> getUserById(@RequestParam Long id) {
    return ResponseEntity.status(HttpStatus.OK)
        .body(userService.getUserById(id));
  }

  @GetMapping(path = "/get_by_name")
  public ResponseEntity<User> getUserByName(@RequestParam String name) {
    return ResponseEntity.status(HttpStatus.OK)
        .body(userService.getUserByUsername(name));
  }

}