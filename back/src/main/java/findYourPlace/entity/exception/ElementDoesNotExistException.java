package findYourPlace.entity.exception;

public class ElementDoesNotExistException extends NullPointerException {

    public ElementDoesNotExistException(String msg) {
        super(msg);
    }

}
