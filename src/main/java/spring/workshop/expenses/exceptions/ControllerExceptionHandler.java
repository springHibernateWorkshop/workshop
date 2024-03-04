package spring.workshop.expenses.exceptions;

import java.time.LocalDate;

import org.hibernate.PropertyValueException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorMessage resourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        ex.printStackTrace();
        ErrorMessage message = new ErrorMessage(
                HttpStatus.NOT_FOUND.value(),
                LocalDate.now(),
                ex.getMessage(),
                request.getDescription(false));

        return message;
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessage dataIntegrityViolationExceptionHandler(Exception ex,
            WebRequest request) {
        ex.printStackTrace();
        ErrorMessage message = new ErrorMessage(
                HttpStatus.BAD_REQUEST.value(),
                LocalDate.now(),
                ex.getMessage(),
                request.getDescription(false));

        return message;
    }

    @ExceptionHandler(PropertyValueException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessage propertyValueExceptionHandler(Exception ex, WebRequest request) {
        ex.printStackTrace();
        ErrorMessage message = new ErrorMessage(
                HttpStatus.BAD_REQUEST.value(),
                LocalDate.now(),
                ex.getMessage(),
                request.getDescription(false));

        return message;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessage illegalArgumentExceptionHandler(Exception ex, WebRequest request) {
        ex.printStackTrace();
        ErrorMessage message = new ErrorMessage(
                HttpStatus.BAD_REQUEST.value(),
                LocalDate.now(),
                ex.getMessage(),
                request.getDescription(false));

        return message;
    }

    @ExceptionHandler(UnsupportedOperationException.class)
    @ResponseStatus(value = HttpStatus.NOT_IMPLEMENTED)
    public ErrorMessage unsupportedOperationExceptionHandler(Exception ex, WebRequest request) {
        ex.printStackTrace();
        ErrorMessage message = new ErrorMessage(
                HttpStatus.NOT_IMPLEMENTED.value(),
                LocalDate.now(),
                ex.getMessage(),
                request.getDescription(false));

        return message;
    }

    @ExceptionHandler(ForbiddenResourceException.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    public ErrorMessage forbiddenResourceException(ForbiddenResourceException ex, WebRequest request) {
        ex.printStackTrace();
        ErrorMessage message = new ErrorMessage(
                HttpStatus.FORBIDDEN.value(),
                LocalDate.now(),
                ex.getMessage(),
                request.getDescription(false));

        return message;
    }

}
