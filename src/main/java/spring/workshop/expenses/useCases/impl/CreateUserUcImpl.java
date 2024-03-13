package spring.workshop.expenses.useCases.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;
import spring.workshop.expenses.entities.Employee;
import spring.workshop.expenses.entities.Person;
import spring.workshop.expenses.entities.Superior;
import spring.workshop.expenses.entities.User;
import spring.workshop.expenses.services.EmployeeService;
import spring.workshop.expenses.services.SuperiorService;
import spring.workshop.expenses.services.UserService;
import spring.workshop.expenses.useCases.CreateUserUc;

@Component
public class CreateUserUcImpl implements CreateUserUc {

    @Autowired
    UserService userService;

    @Autowired
    EmployeeService employeeService;

    @Autowired
    SuperiorService superiorService;

    @Override
    @Transactional
    @PreAuthorize("hasAuthority('CREATE_USERS')")
    public Person createUser(User user, String name, Long superiorId) {

        User newUser = userService.addUser(user);

        if (newUser.getRole().getAuthority().equals("ROLE_EMPLOYEE")) {
            return employeeService
                    .addEmployee(new Employee(name, newUser, superiorService.getSuperiorById(superiorId)));
        } else if (newUser.getRole().getAuthority().equals("ROLE_SUPERIOR")) {
            return superiorService.createSuperior(new Superior(name, user));
        } else {
            throw new IllegalArgumentException(
                    "It's not allowed to create user with Role other than EMPLOYEE or SUPERIOR");
        }
    }

}
