package exceptions;

public class CommandProductionException extends Exception {
    public CommandProductionException() {}

    public CommandProductionException(String message) {
        super(message);
    }

    public CommandProductionException(String message, Throwable cause) {
        super(message, cause);
    }
}