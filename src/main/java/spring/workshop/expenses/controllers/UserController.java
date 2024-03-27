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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
  @Operation(summary = "Add a user", description = "Adds a new user to the database")
  public ResponseEntity<Person> addUser(@RequestBody User user,
      @RequestParam @Parameter(description = "Name of the person") String name,
      @RequestParam(name = "superior_id", required = false) @Parameter(description = "ID of the superior") Long superiorId) {
    Person newUser = createUserUc.createUser(user, name, superiorId);

    return ResponseEntity
        .created(
            ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(newUser.getId())
                .toUri())
        .body(newUser);
  }

  // TODO
  @DeleteMapping(path = "/{id}")
  @Operation(summary = "Delete a user", description = "Deletes a new user from the database")
  public ResponseEntity<Void> deleteUser(
      @PathVariable @Parameter(description = "ID of the user to be deleted") Long id) {
    userService.deleteUser(id);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @GetMapping
  @Operation(summary = "Get all users", description = "Fetches all users from the database")
  public ResponseEntity<List<UserDTO>> getAllUsers() {
    return ResponseEntity.status(HttpStatus.OK)
        .body(userMapper.toDto(userService.getAllUsers()));
  }

  @GetMapping(path = "/{id}")
  @Operation(summary = "Get a user", description = "Fetche the user with the given id from the database")
  public ResponseEntity<UserDTO> getUserById(@PathVariable @Parameter(description = "ID of the user") Long id) {
    return ResponseEntity.status(HttpStatus.OK)
        .body(userMapper.toDto(userService.getUserById(id)));
  }

}