package pl.zajonz.exchange.exception;

import org.springframework.http.HttpStatus;
import org.springframework.mail.MailSendException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessage handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        ValidationErrorMessage error = new ValidationErrorMessage();
        ex.getFieldErrors().forEach(fe -> error.addViolation(fe.getField(), fe.getDefaultMessage()));
        return error;
    }

    @ExceptionHandler(MailSendException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessage handleMethodMailSendException(MailSendException ex){
        ValidationErrorMessage error = new ValidationErrorMessage();
        ex.getFailedMessages().forEach((fe,me) -> error.addViolation(fe.toString(), me.getMessage()));
        return error;
    }
}
