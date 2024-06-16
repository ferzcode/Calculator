import commands.*;
import context.Context;
import exceptions.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class Tests {
    private Context context;
    private Command command;

    @org.junit.jupiter.api.Test
    public void testAddition() throws CommandException {
        context = new Context();
        context.push(3.5);
        context.push(2.252);
        command = new AddCommand();
        command.execute(context, null);

        assertEquals(5.752, context.pop(), 0.0001);
    }

    @org.junit.jupiter.api.Test
    public void testAdditionStackException() throws CommandException {
        try {
            context = new Context();
            context.push(3.50);
            command = new AddCommand();
            command.execute(context, null);

        } catch (CommandException e) {
            assertEquals("No numbers in the stack.", e.getMessage());
        }
    }

    @org.junit.jupiter.api.Test
    public void testSubtraction() throws CommandException {
        context = new Context();
        context.push(3.5);
        context.push(2.252);
        command = new SubCommand();
        command.execute(context, null);

        assertEquals(1.248, context.pop(), 0.0001);
    }

    @org.junit.jupiter.api.Test
    public void testSubtractionStackException() throws CommandException {
        try {
            context = new Context();
            context.push(3.50);
            command = new SubCommand();
            command.execute(context, null);

        } catch (CommandException e) {
            assertEquals("No numbers in the stack.", e.getMessage());
        }
    }

    @org.junit.jupiter.api.Test
    public void testMultiply() throws CommandException {
        context = new Context();
        context.push(3.5);
        context.push(2.252);
        command = new MulCommand();
        command.execute(context, null);

        assertEquals(7.882, context.pop(), 0.0001);
    }

    @org.junit.jupiter.api.Test
    public void testMultiplyStackException() throws CommandException {
        try {
            context = new Context();
            context.push(3.50);
            command = new MulCommand();
            command.execute(context, null);

        } catch (CommandException e) {
            assertEquals("No numbers in the stack.", e.getMessage());
        }
    }

    @org.junit.jupiter.api.Test
    public void testDividing() throws CommandException {
        context = new Context();
        context.push(13);
        context.push(2.5);
        command = new DivCommand();
        command.execute(context, null);

        assertEquals(5.2, context.pop(), 0.0001);
    }

    @org.junit.jupiter.api.Test
    public void testDividingStackException() throws CommandException {
        try {
            context = new Context();
            context.push(3.50);
            command = new DivCommand();
            command.execute(context, null);

        } catch (CommandException e) {
            assertEquals("No numbers in the stack.", e.getMessage());
        }
    }

    @org.junit.jupiter.api.Test
    public void testDividingZeroException() throws CommandException {
        try {
            context = new Context();
            context.push(3.50);
            context.push(0);
            command = new DivCommand();
            command.execute(context, null);

        } catch (CommandException e) {
            assertEquals("Division by zero.", e.getMessage());
        }
    }

    @org.junit.jupiter.api.Test
    public void testDefineVariable() throws CommandException {
        Context context = new Context();
        String[] args = {"x", "5.0"};
        Command command = new DefineCommand();
        command.execute(context, args);

        assertEquals(5.0, context.getValue("x"), 0.001);
    }

    @org.junit.jupiter.api.Test
    public void testDefineVariableBadFormat() throws CommandException {
        try {
            Context context = new Context();
            String[] args = {"x", "five"}; // Неправильный формат числа
            Command command = new DefineCommand();
            command.execute(context, args);

        } catch (BadFormatException e) {
            assertEquals("Invalid variable value: five. The value should represent a floating point number.", e.getMessage());
        }
    }

    @org.junit.jupiter.api.Test
    public void testDefineVariableInvalidName() throws CommandException {
        try {
            Context context = new Context();
            String[] args = {"2x", "5.0"}; // Некорректное имя переменной
            Command command = new DefineCommand();
            command.execute(context, args);

        } catch (BadFormatException e) {
            assertEquals("Invalid variable name: 2x. The name should only contain letters.", e.getMessage());
        }
    }

    @org.junit.jupiter.api.Test
    public void testDefineVariableInvalidArguments() throws CommandException {
        try {
            Context context = new Context();
            String[] args = {"x"};
            Command command = new DefineCommand();
            command.execute(context, args);

        } catch (BadFormatException e) {
            assertEquals("2 arguments expected for Define, 1 arguments were given.", e.getMessage());
        }
    }

    @org.junit.jupiter.api.Test
    public void testSqrtCommandWithNegativeArgument() {
        Context context = new Context();
        context.push(-2);
        Command command = new SqrtCommand();

        MathematicalException exception = assertThrows(MathematicalException.class, () -> {
            command.execute(context, null);
        });

        assertEquals("Square root of negative.", exception.getMessage());
    }

    @org.junit.jupiter.api.Test
    public void testScriptExecution() throws CommandException, ConfigurationException, CommandProductionException, IOException {
        Calculator calculator = new Calculator();

        calculator.execute("PUSH", new String[]{"5"});
        calculator.execute("PUSH", new String[]{"10"});
        calculator.execute("PLUS", null);
        calculator.execute("PUSH", new String[]{"3"});
        calculator.execute("DIVIDE", null);
        calculator.execute("PUSH", new String[]{"20"});
        calculator.execute("PUSH", new String[]{"4"});
        calculator.execute("MULTIPLY", null);
        calculator.execute("PUSH", new String[]{"8"});
        calculator.execute("DIVIDE", null);

        ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));

        calculator.execute("PRINT", null);

        String consoleOutput = outputStreamCaptor.toString().trim();
        String expectedOutput = "10.0";
        assertEquals(expectedOutput, consoleOutput);
    }
}
