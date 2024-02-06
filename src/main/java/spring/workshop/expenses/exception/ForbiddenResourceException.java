package spring.workshop.expenses.exception;

public class ForbiddenResourceException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ForbiddenResourceException(String msg) {
        super(msg);
    }
}
