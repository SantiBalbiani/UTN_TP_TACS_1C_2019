package findYourPlace.controller.exceptionHandling;

import findYourPlace.service.impl.exception.CouldNotRetrieveElementException;
import findYourPlace.service.impl.exception.CouldNotSaveElementException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class RestResponseEntityExceptionHandler
        extends ResponseEntityExceptionHandler {

    @ExceptionHandler({CouldNotSaveElementException.class})
    public ResponseEntity<Object> handleDuplicate(CouldNotSaveElementException ex) {
        return new ResponseEntity(HttpStatus.CONFLICT);
    }

    @ExceptionHandler({CouldNotRetrieveElementException.class})
    public ResponseEntity<Object> handleGet(CouldNotRetrieveElementException ex) {
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }
}