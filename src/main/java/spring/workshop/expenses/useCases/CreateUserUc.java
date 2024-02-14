package spring.workshop.expenses.useCases;

import spring.workshop.expenses.entities.User;

public interface CreateUserUc {

    public User createUser(User user, String name, Long superiorId);

}
