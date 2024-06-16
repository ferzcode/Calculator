package commands;

import context.Context;
import exceptions.EmptyNumStackException;

import java.util.EmptyStackException;

public class MulCommand implements Command {
    public void execute(Context context, String[] args) throws EmptyNumStackException {
        try {
            context.push(context.pop() * context.pop());
        } catch (EmptyStackException e) {
            throw new EmptyNumStackException("No numbers in the stack.", e);
        }
    }
}