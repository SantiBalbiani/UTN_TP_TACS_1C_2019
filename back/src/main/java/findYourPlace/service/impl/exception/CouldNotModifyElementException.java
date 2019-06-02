package findYourPlace.service.impl.exception;

import java.util.NoSuchElementException;

public class CouldNotModifyElementException extends NoSuchElementException {

    public CouldNotModifyElementException(String msg) {
        super(msg);
    }

}
