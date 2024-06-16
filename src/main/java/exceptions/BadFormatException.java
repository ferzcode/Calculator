package exceptions;

public class BadFormatException extends CommandException {
    public BadFormatException() {}

    public BadFormatException(String message) {
        super(message);
    }

    public BadFormatException(String message, Throwable cause) {
        super(message, cause);
    }
}