package spring.workshop.expenses.useCases.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import spring.workshop.expenses.entities.Employee;
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
    public User createUser(User user, String name, Long superiorId) {
        User newUser = userService.addUser(user);
        if (newUser.getRole().equals("EMPLOYEE")) {
            employeeService.addEmployee(new Employee(name, newUser, superiorService.getSuperiorById(superiorId)));
        } else if (newUser.getRole().equals("SUPERIOR")) {
            superiorService.createSuperior(new Superior(name, user));
        } else {
            throw new IllegalArgumentException(
                    "It's not allowed to create user with Role other than EMPLOYEE or SUPERIOR");
        }

        return newUser;
    }

}
