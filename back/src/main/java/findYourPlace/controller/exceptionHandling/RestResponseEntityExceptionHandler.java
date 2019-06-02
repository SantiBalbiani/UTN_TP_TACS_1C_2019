package findYourPlace.controller.exceptionHandling;

import findYourPlace.service.impl.exception.CouldNotDeleteElementException;
import findYourPlace.service.impl.exception.CouldNotModifyElementException;
import findYourPlace.service.impl.exception.CouldNotRetrieveElementException;
import findYourPlace.service.impl.exception.CouldNotSaveElementException;
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


    @ExceptionHandler({CouldNotSaveElementException.class})
    public ResponseEntity<Object> handleDuplicate(CouldNotSaveElementException ex) {
        return new ResponseEntity(HttpStatus.CONFLICT);
    }

    @ExceptionHandler({CouldNotDeleteElementException.class})
    public ResponseEntity<Object> handleDelete(CouldNotDeleteElementException ex) {
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({CouldNotModifyElementException.class})
    public ResponseEntity<Object> handleModify(CouldNotModifyElementException ex) {
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({CouldNotRetrieveElementException.class})
    public ResponseEntity<Object> handleGet(CouldNotRetrieveElementException ex) {
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }


}