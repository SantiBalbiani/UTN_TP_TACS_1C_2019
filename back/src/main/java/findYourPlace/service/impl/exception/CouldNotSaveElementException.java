package findYourPlace.service.impl.exception;

import org.springframework.dao.DuplicateKeyException;

public class CouldNotSaveElementException extends DuplicateKeyException {

    public CouldNotSaveElementException(String msg) {
        super(msg);
    }
}
