package commands;

import context.Context;
import exceptions.CommandException;

public interface Command {
    void execute(Context context, String[] args) throws CommandException;
}