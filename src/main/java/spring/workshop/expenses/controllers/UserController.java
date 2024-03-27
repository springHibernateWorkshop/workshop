package spring.workshop.expenses.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import spring.workshop.expenses.dto.UserDTO;
import spring.workshop.expenses.entities.Person;
import spring.workshop.expenses.entities.User;
import spring.workshop.expenses.mapper.UserMapper;
import spring.workshop.expenses.services.UserService;
import spring.workshop.expenses.useCases.CreateUserUc;

@RestController
@RequestMapping(path = "/users")
public class UserController {

  @Autowired
  private UserService userService;

  @Autowired
  private UserMapper userMapper;

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

  // TODO
  @DeleteMapping(path = "/{id}")
  public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
    userService.deleteUser(id);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @GetMapping
  public ResponseEntity<List<UserDTO>> getAllUsers() {
    return ResponseEntity.status(HttpStatus.OK)
        .body(userMapper.toDto(userService.getAllUsers()));
  }

  @GetMapping(path = "/{id}")
  public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
    return ResponseEntity.status(HttpStatus.OK)
        .body(userMapper.toDto(userService.getUserById(id)));
  }

}