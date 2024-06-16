package commands;

import context.Context;
import exceptions.BadFormatException;

public class DefineCommand implements Command {
    private boolean nameIsCorrect(String name) {
        for (int i = 0; i < name.length(); i++) {
            if (!Character.isAlphabetic(name.charAt(i)))
                return false;
        }
        return true;
    }

    public void execute(Context context, String[] args) throws BadFormatException {
        if (args.length != 2)
            throw new BadFormatException("2 arguments expected for Define, " + args.length
                    + " arguments were given.");

        if (!nameIsCorrect(args[0]))
            throw new BadFormatException("Invalid variable name: " + args[0]
                    + ". The name should only contain letters.");

        try {
            context.addParameter(args[0], Double.parseDouble(args[1]));
        } catch (NumberFormatException e) {
            throw new BadFormatException("Invalid variable value: " + args[1]
                    + ". The value should represent a floating point number.");
        }
    }
}