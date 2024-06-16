package commands;

import context.Context;
import exceptions.EmptyNumStackException;
import java.util.EmptyStackException;

public class PrintCommand implements Command {
    public void execute(Context context, String[] args) throws EmptyNumStackException {
        try {
            System.out.println(context.peek());
        } catch (EmptyStackException e) {
            throw new EmptyNumStackException("No numbers in the stack.", e);
        }
    }
}