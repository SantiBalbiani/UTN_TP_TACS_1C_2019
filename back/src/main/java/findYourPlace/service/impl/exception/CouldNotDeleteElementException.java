package findYourPlace.service.impl.exception;

import java.util.NoSuchElementException;

public class CouldNotDeleteElementException extends NoSuchElementException {

    public CouldNotDeleteElementException(String msg) {
        super(msg);
    }

}
