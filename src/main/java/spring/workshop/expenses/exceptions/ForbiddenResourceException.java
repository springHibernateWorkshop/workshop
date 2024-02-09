package spring.workshop.expenses.exceptions;

public class ForbiddenResourceException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ForbiddenResourceException(String msg) {
        super(msg);
    }
}
