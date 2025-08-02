package exceptions;

public class InvalidCpfFormatException extends RuntimeException {
    public InvalidCpfFormatException(String message) {
      super(message);
    }
}
