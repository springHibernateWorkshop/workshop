package spring.workshop.expenses.services;

import java.util.List;

import spring.workshop.expenses.entities.Superior;
import spring.workshop.expenses.entities.User;

public interface SuperiorService {

    Superior getSuperiorById(Long id);

    List<Superior> getAllSuperiors();

    Superior createSuperior(Superior superior);

    Superior getSuperiorByUser(User user);

}
