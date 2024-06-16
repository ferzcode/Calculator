package commands;

import context.Context;
import exceptions.EmptyNumStackException;
import exceptions.MathematicalException;

import java.lang.Math;
import java.util.EmptyStackException;

public class SqrtCommand implements Command {
    public void execute(Context context, String[] args) throws EmptyNumStackException,
            MathematicalException {
        double number;
        try {
            number = context.pop();
        } catch (EmptyStackException e) {
            throw new EmptyNumStackException("No numbers in the stack.", e);
        }

        if (number < 0)
            throw new MathematicalException("Square root of negative.");
        context.push(Math.sqrt(number));
    }
}