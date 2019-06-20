package findYourPlace.service.impl.exception;

import java.util.NoSuchElementException;

public class CouldNotRetrieveElementException extends NoSuchElementException {

    public CouldNotRetrieveElementException(String msg) {
        super(msg);
    }
}
