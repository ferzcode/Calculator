package factory;

import commands.Command;
import exceptions.CommandProductionException;
import exceptions.ConfigurationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

public class CommandFactory {
    private final HashMap<String, Class<Command>> commandClasses;
    private final HashMap<String, Command> producedCommands = new HashMap<>();
    private static final Logger logger = LogManager.getLogger(CommandFactory.class);

    public CommandFactory() throws ConfigurationException {
        try {
            FactoryConfigurator configurator = new FactoryConfigurator("FactoryConfig.properties");
            commandClasses = configurator.getCommandClasses();
            logger.info("CommandFactory initialized successfully.");
        } catch (ConfigurationException e) {
            logger.error("Failed to initialize CommandFactory: {}", e.getMessage());
            throw e;
        }
    }

    public Command receiveCommand(String commandName) throws CommandProductionException {
        if (commandName == null || !commandClasses.containsKey(commandName))
            throw new CommandProductionException("Invalid command name.");

        if (!producedCommands.containsKey(commandName))
            produceNewCommand(commandName);
        return producedCommands.get(commandName);
    }

    private void produceNewCommand(String commandName) throws CommandProductionException {
        Command newCommand;
        try {
            newCommand = commandClasses.get(commandName).getDeclaredConstructor().newInstance();
            logger.info("Produced new instance of {} command.", commandName);
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException
                 | InvocationTargetException e) {
            logger.error("Failed to produce new instance of {} command: {}", commandName, e.getMessage(), e);
            throw new CommandProductionException(e.getMessage(), e);
        }

        producedCommands.put(commandName, newCommand);
    }
}
