package spring.workshop.expenses.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import spring.workshop.expenses.entities.Superior;
import spring.workshop.expenses.exceptions.ResourceNotFoundException;
import spring.workshop.expenses.repositories.AbstractRepositoryHelper;
import spring.workshop.expenses.repositories.SuperiorRepository;
import spring.workshop.expenses.services.SuperiorService;

@Service
public class SuperiorServiceImpl implements SuperiorService {

    private SuperiorRepository superiorRepository;

    @Autowired
    private AbstractRepositoryHelper<Superior> abstractRepositoryHelper;

    @Autowired
    private void setSuperiorRepository(SuperiorRepository superiorRepository) {
        this.superiorRepository = superiorRepository;
        abstractRepositoryHelper.setRepository(superiorRepository);
    }

    @Override
    public Superior getSuperiorById(Long id) {
        return superiorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No Superior with id: " + id));
    }

    @Override
    public List<Superior> getAllSuperiors() {
        return superiorRepository.findAll();
    }

    @Override
    public Superior createSuperior(Superior superior) {
        return abstractRepositoryHelper.saveAndRefresh(superior);
    }

}
