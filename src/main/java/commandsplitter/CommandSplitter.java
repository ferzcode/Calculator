package commandsplitter;

import java.util.Arrays;

public class CommandSplitter {
    private String commandName;
    private String[] commandArgs;

    public void splitCommand(String command) {
        command = command.strip();

        String[] splitCommand = command.split("\\s+");
        commandName = splitCommand[0].toUpperCase();
        try {
            commandArgs = Arrays.copyOfRange(splitCommand, 1, splitCommand.length);
        } catch (ArrayIndexOutOfBoundsException e) {
            commandArgs = new String[] {};
        }
    }

    public String getCommandName() {
        return commandName;
    }

    public String[] getCommandArgs() {
        return commandArgs;
    }
}