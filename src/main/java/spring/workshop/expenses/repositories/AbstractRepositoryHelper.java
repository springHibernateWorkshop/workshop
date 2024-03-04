package spring.workshop.expenses.repositories;

import org.springframework.stereotype.Component;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Component
public class AbstractRepositoryHelper<T> {

    @PersistenceContext
    EntityManager entityManager;

    AbstractRepository<T> abstractRepository;

    public AbstractRepositoryHelper() {
    }

    public void setExpenseRepository(ExpenseRepository expenseRepository) {
        this.abstractRepository = (AbstractRepository<T>) expenseRepository;
    }

    public void setEmployeeRepository(EmployeeRepository employeeRepository) {
        this.abstractRepository = (AbstractRepository<T>) employeeRepository;
    }

    public T saveAndRefresh(T object) {
        T savedObject = abstractRepository.saveAndFlush(object);
        entityManager.refresh(savedObject);
        return savedObject;
    }

}
