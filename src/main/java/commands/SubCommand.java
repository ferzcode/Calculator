package commands;

import context.Context;
import exceptions.EmptyNumStackException;

import java.util.EmptyStackException;

public class SubCommand implements Command {
    public void execute(Context context, String[] args) throws EmptyNumStackException {
        double a, b;
        try {
            b = context.pop();
            a = context.pop();
        } catch (EmptyStackException e) {
            throw new EmptyNumStackException("No numbers in the stack.", e);
        }
        context.push(a - b);
    }
}