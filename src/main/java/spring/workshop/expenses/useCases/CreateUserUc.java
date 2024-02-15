package spring.workshop.expenses.useCases;

import spring.workshop.expenses.entities.Person;
import spring.workshop.expenses.entities.User;

public interface CreateUserUc {

    public Person createUser(User user, String name, Long superiorId);

}
