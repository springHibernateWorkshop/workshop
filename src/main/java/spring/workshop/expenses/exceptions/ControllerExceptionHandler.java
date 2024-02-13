package spring.workshop.expenses.exceptions;

import java.time.LocalDate;

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
        ErrorMessage message = new ErrorMessage(
                HttpStatus.NOT_FOUND.value(),
                LocalDate.now(),
                ex.getMessage(),
                request.getDescription(false));

        return message;
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(value = HttpStatus.CONFLICT)
    public ErrorMessage dataIntegrityViolationExceptionHandler(Exception ex, WebRequest request) {
        ErrorMessage message = new ErrorMessage(
                HttpStatus.CONFLICT.value(),
                LocalDate.now(),
                ex.getMessage(),
                request.getDescription(false));

        return message;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessage badRequestExceptionHandler(Exception ex, WebRequest request) {
        ErrorMessage message = new ErrorMessage(
                HttpStatus.NOT_FOUND.value(),
                LocalDate.now(),
                ex.getMessage(),
                request.getDescription(false));

        return message;
    }

    @ExceptionHandler(UnsupportedOperationException.class)
    @ResponseStatus(value = HttpStatus.NOT_IMPLEMENTED)
    public ErrorMessage unsupportedOperationExceptionHandler(Exception ex, WebRequest request) {
        ErrorMessage message = new ErrorMessage(
                HttpStatus.NOT_IMPLEMENTED.value(),
                LocalDate.now(),
                ex.getMessage(),
                request.getDescription(false));

        return message;
    }

    // @ExceptionHandler(RuntimeException.class)
    // @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    // public ErrorMessage unAuthorizedExceptionHandler(Exception ex, WebRequest
    // request) {
    // ErrorMessage message = new ErrorMessage(
    // HttpStatus.UNAUTHORIZED.value(),
    // LocalDate.now(),
    // ex.getMessage(),
    // request.getDescription(false));

    // return message;
    // }

    @ExceptionHandler(ForbiddenResourceException.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    public ErrorMessage forbiddenResourceException(ForbiddenResourceException ex, WebRequest request) {
        ErrorMessage message = new ErrorMessage(
                HttpStatus.FORBIDDEN.value(),
                LocalDate.now(),
                ex.getMessage(),
                request.getDescription(false));

        return message;
    }

}
