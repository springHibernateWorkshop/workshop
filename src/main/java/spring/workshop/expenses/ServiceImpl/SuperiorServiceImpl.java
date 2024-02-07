package spring.workshop.expenses.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import spring.workshop.expenses.entities.Superior;
import spring.workshop.expenses.exception.ResourceNotFoundException;
import spring.workshop.expenses.repos.SuperiorRepository;
import spring.workshop.expenses.services.SuperiorService;

public class SuperiorServiceImpl implements SuperiorService {

    @Autowired
    private SuperiorRepository superiorRepository;


    @Override
    public Superior getSuperiorById(Long id) {
        return superiorRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("No Superior with id: " + id));
    }


    @Override
    public List<Superior> getAllSuperiors() {
        return superiorRepository.findAll();
    }


    @Override
    public Superior createSuperior(Superior superior){
        return superiorRepository.save(superior);
    }

    
}
