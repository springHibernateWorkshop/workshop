package spring.workshop.expenses.enums;

import java.util.Set;

public enum Role {
    EMPLOYEE(Set.of(Right.VIEW_EXPENSES, Right.CREATE_EXPENSES, Right.DELETE_EXPENSES, Right.EDIT_EXPENSES,
            Right.SUBMIT_EXPENSES)),
    SUPERIOR(Set.of(Right.VIEW_EXPENSES, Right.APPROVE_REJECT_EXPENSES, Right.VIEW_REPORTS)),
    ACCOUNTANT(Set.of(Right.VIEW_REPORTS)),
    ADMINISTRATOR(Set.of(Right.CREATE_EMPLOYEES_SUPERIORS, Right.DELETE_EMPLOYEES_SUPERIORS, Right.REASSIGN_EMPLOYEES,
            Right.VIEW_SHOPS,
            Right.CREATE_SHOPS, Right.DELETE_SHOPS, Right.EDIT_SHOPS, Right.VIEW_CATEGORIES, Right.CREATE_CATEGORIES,
            Right.DELETE_CATEGORIES, Right.EDIT_CATEGORIES));

    private Set<Right> rights;

    private Role(Set<Right> rights) {
        this.rights = rights;
    }

    public Set<Right> getRights() {
        return rights;
    }

}
