package exceptions;

public class EmptyNumStackException extends CommandException {
    public EmptyNumStackException() {}

    public EmptyNumStackException(String message) {
        super(message);
    }

    public EmptyNumStackException(String message, Throwable cause) {
        super(message, cause);
    }
}