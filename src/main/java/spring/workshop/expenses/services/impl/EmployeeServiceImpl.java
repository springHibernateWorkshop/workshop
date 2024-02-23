package spring.workshop.expenses.services.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import spring.workshop.expenses.entities.Employee;
import spring.workshop.expenses.entities.User;
import spring.workshop.expenses.exceptions.ResourceNotFoundException;
import spring.workshop.expenses.repositories.EmployeeRepository;
import spring.workshop.expenses.services.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger LOG = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private EmployeeRepository employeeRepository;

    public EmployeeServiceImpl() {
    };

    @Override
    @Transactional
    public Employee addEmployee(Employee employee) {
        Employee savedEmployee = employeeRepository.saveAndFlush(employee);
        LOG.info("Employee with id = " + employee.getId() + " created successfully.");
        entityManager.refresh(savedEmployee);
        return savedEmployee;
    }

    @Override
    public void deleteEmployee(Long id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        if (employee.isPresent()) {
            employeeRepository.deleteById(id);
            LOG.info("Employee with id = " + id + " deleted successfully.");
        } else {
            throw new ResourceNotFoundException("Employee with id = " + id + " not found.");
        }
    }

    @Override
    @Transactional
    public Employee updateEmployee(Employee employee) {
        Optional<Employee> oldEmployee = employeeRepository.findById(employee.getId());
        if (oldEmployee.isPresent()) {
            Employee updatedEmployee = oldEmployee.get();
            updatedEmployee.setName(employee.getName());
            updatedEmployee.setUser(employee.getUser());
            updatedEmployee.setSuperior(employee.getSuperior());
            Employee savedEmployee = employeeRepository.saveAndFlush(updatedEmployee);
            LOG.info("Employee with id = " + updatedEmployee.getId() + " updated succesfully.");
            entityManager.refresh(savedEmployee);
            return savedEmployee;

        } else {
            throw new ResourceNotFoundException("Employee with id = " + employee.getId() + " not found.");
        }
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee getEmployeeById(Long id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        if (!employee.isPresent())
            throw new ResourceNotFoundException("Employee with id = " + id + " not found.");

        return employee.get();
    }

    @Override
    public Employee getEmployeeByUser(User user) {
        Optional<Employee> employee = employeeRepository.findByUser(user);
        if (!employee.isPresent())
            throw new ResourceNotFoundException("Employee for user with id = " + user.getId() + " not found.");

        return employee.get();
    }
}
