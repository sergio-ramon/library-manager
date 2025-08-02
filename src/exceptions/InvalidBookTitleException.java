package exceptions;

public class InvalidBookTitleException extends InvalidArgumentException {
    public InvalidBookTitleException(String message) {
        super(message);
    }
}
