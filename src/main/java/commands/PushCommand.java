package commands;

import context.Context;
import exceptions.BadFormatException;

public class PushCommand implements Command {
    public void execute(Context context, String[] args) throws BadFormatException {
        if (args.length != 1) {
            throw new BadFormatException("1 argument expected for Push, " + args.length
                    + " arguments were given.");
        }

        Double number;
        try {
            number = Double.parseDouble(args[0]);
        } catch (NumberFormatException e) {
            number = context.getValue(args[0]);
            if (number == null)
                throw new BadFormatException("Unknown variable: " + args[0] + ".");
        }
        context.push(number);
    }
}