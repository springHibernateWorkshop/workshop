package spring.workshop.expenses.rest;

import java.util.List;

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

import spring.workshop.expenses.entities.Employee;
import spring.workshop.expenses.services.EmployeeService;

@RestController
@RequestMapping(path = "/employees")
public class EmployeeController {

  private EmployeeService employeeService;

  @Autowired
  public EmployeeController(EmployeeService employeeService) {
    this.employeeService = employeeService;
  }

  @PostMapping
  public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee) {
    Employee newEmployee = employeeService.addEmployee(employee);

    return ResponseEntity.status(HttpStatus.CREATED)
        .body(newEmployee);
  }

  @DeleteMapping(path = "/{id}")
  public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
    employeeService.deleteEmployee(id);

    return new ResponseEntity<>(HttpStatus.OK);
  }

  @PutMapping
  public ResponseEntity<Employee> updateEmployee(@RequestBody Employee employee) {
    Employee updatedEmployee = employeeService.updateEmployee(employee);

    return ResponseEntity.status(HttpStatus.OK)
        .body(updatedEmployee);
  }

  @GetMapping
  public ResponseEntity<List<Employee>> getAllEmployees() {
    return ResponseEntity.status(HttpStatus.OK)
        .body(employeeService.getAllEmployees());
  }

  @GetMapping(path = "/{id}")
  public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
    return ResponseEntity.status(HttpStatus.OK)
        .body(employeeService.getEmployeeById(id));
  }

}
