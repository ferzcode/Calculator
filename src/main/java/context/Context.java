package context;

import java.util.EmptyStackException;
import java.util.Stack;
import java.util.HashMap;

public class Context {
    private final Stack<Double> numStack;
    private final HashMap<String, Double> parameters;

    public Context() {
        numStack = new Stack<>();
        parameters = new HashMap<>();
    }

    public void push(double number) {
        numStack.push(number);
    }

    public double pop() throws EmptyStackException {
        return numStack.pop();
    }

    public double peek() throws EmptyStackException {
        return numStack.peek();
    }

    public void addParameter(String name, double value) {
        parameters.put(name, value);
    }

    public Double getValue(String name) { return parameters.get(name); }
}