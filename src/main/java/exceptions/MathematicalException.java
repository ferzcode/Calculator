package exceptions;

public class MathematicalException extends CommandException {
    public MathematicalException() {}

    public MathematicalException(String message) {
        super(message);
    }

    public MathematicalException(String message, Throwable cause) {
        super(message, cause);
    }
}