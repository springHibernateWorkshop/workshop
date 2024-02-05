package spring.workshop.expenses.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import spring.workshop.expenses.entities.Employee;
import spring.workshop.expenses.exception.ResourceNotFoundException;
import spring.workshop.expenses.repos.EmployeeRepository;
import spring.workshop.expenses.services.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository employeeRepository;
    private static final Logger LOG = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Employee addEmployee(Employee employee) {
        Employee savedEmployee = employeeRepository.save(employee);
        LOG.info("Employee with id = " + employee.getId() + " created successfully.");
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
    public Employee updateEmployee(Employee employee) {
        Optional<Employee> oldEmployee = employeeRepository.findById(employee.getId());
        if (oldEmployee.isPresent()) {
            Employee updatedEmployee = oldEmployee.get();
            updatedEmployee.setName(employee.getName());
            updatedEmployee.setUser(employee.getUser());
            updatedEmployee.setSuperior(employee.getSuperior());
            Employee savedEmployee = employeeRepository.save(updatedEmployee);
            LOG.info("Employee with id = " + updatedEmployee.getId() + " updated succesfully.");
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
}
