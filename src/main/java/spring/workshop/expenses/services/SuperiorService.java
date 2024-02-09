package spring.workshop.expenses.services;

import java.util.List;

import spring.workshop.expenses.entities.Superior;

public interface SuperiorService {

    Superior getSuperiorById(Long id);

    List<Superior> getAllSuperiors();

    Superior createSuperior(Superior superior);

}
