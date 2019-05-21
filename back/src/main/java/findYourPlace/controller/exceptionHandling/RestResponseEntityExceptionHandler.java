package findYourPlace.controller.exceptionHandling;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.NoSuchElementException;

@RestControllerAdvice
public class RestResponseEntityExceptionHandler
        extends ResponseEntityExceptionHandler {


    @ExceptionHandler({DuplicateKeyException.class})
    public ResponseEntity<Object> handleDuplicate(DuplicateKeyException ex) {
        return new ResponseEntity(HttpStatus.CONFLICT);
    }

    @ExceptionHandler({NoSuchElementException.class})
    public ResponseEntity<Object> handleNotFound(NoSuchElementException ex) {
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }
}