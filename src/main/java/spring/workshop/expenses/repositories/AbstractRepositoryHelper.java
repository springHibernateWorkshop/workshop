package spring.workshop.expenses.repositories;

import org.springframework.stereotype.Component;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Component
public class AbstractRepositoryHelper<T> {

    @PersistenceContext
    EntityManager entityManager;

    public T saveAndRefresh(AbstractRepository<T> abstractRepository, T object) {
        T savedObject = abstractRepository.saveAndFlush(object);
        entityManager.refresh(savedObject);
        return savedObject;
    }

}
