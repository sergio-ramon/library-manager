package exceptions;

public class PublicationDateFormatInvalidException extends RuntimeException {
    public PublicationDateFormatInvalidException(String message) {
      super(message);
    }
}
