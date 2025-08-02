package exceptions;

public class InvalidBookGenderException extends InvalidArgumentException {
    public InvalidBookGenderException(String message) {
      super(message);
    }
}
