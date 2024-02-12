/**
 * The Rights enum represents the different rights or permissions that can be assigned to users.
 */
package spring.workshop.expenses.security;

public enum Right {
    VIEW_EXPENSES(1),
    CREATE_EXPENSES(2),
    DELETE_EXPENSES(3),
    EDIT_EXPENSES(4),
    SUBMIT_EXPENSES(5),
    APPROVE_REJECT_EXPENSES(6),
    VIEW_REPORTS(7),
    CREATE_EMPLOYEES_SUPERIORS(8),
    DELETE_EMPLOYEES_SUPERIORS(9),
    REASSIGN_EMPLOYEES(10),
    VIEW_SHOPS(11),
    CREATE_SHOPS(12),
    DELETE_SHOPS(13),
    EDIT_SHOPS(14),
    VIEW_CATEGORIES(15),
    CREATE_CATEGORIES(16),
    DELETE_CATEGORIES(17),
    EDIT_CATEGORIES(18);

    private int id;

    private Right(int id) {
        this.id = id;
    }

    /**
     * Returns the id of a rights.
     *
     * @return the id of the rights
     */
    public int getId() {
        return this.id;
    }

    /**
     * Returns the rights with the given id.
     *
     * @param id the id of the rights
     * @return the rights with the given id
     */
    public static Right getRightsById(int id) {
        for (Right rights : Right.values()) {
            if (rights.getId() == id) {
                return rights;
            }
        }
        return null;
    }
}
