package spring.workshop.expenses.exceptions;

public class PropertyValueException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public PropertyValueException(String msg) {
        super(msg);
    }
}