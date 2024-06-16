package factory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import commands.Command;
import exceptions.ConfigurationException;

import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class FactoryConfigurator {
    private static final Logger logger = LogManager.getLogger(FactoryConfigurator.class);

    private final HashMap<String, Class<Command>> commandClasses = new HashMap<>();

    public FactoryConfigurator(String configFileName) throws ConfigurationException {
        logger.debug("Initializing FactoryConfigurator with config file: {}", configFileName);
        configure(configFileName);
    }

    public HashMap<String, Class<Command>> getCommandClasses() {
        return commandClasses;
    }

    private void configure(String configFileName) throws ConfigurationException {
        InputStream in = FactoryConfigurator.class.getResourceAsStream("/" + configFileName);
        if (in == null) {
            logger.error("Configuration file {} not found", configFileName);
            throw new ConfigurationException("Configuration file not found.");
        }
        Scanner configScanner = new Scanner(in);
        while (configScanner.hasNext()) {
            String[] configPair = configScanner.next().split("\\s*=\\s*");
            try {
                addCommandClass(configPair);
            } catch (ConfigurationException e){
                logger.error("Error configuring command: {}", e.getMessage());
            }

        }
    }

    private void addCommandClass(String[] configPair) throws ConfigurationException {
        if (configPair.length != 2) {
            logger.error("Bad format for config file: {}", Arrays.toString(configPair));
            throw new ConfigurationException("Bad format for config file.");
        }

        try {
            commandClasses.put(configPair[0], (Class<Command>) Class.forName(configPair[1]));
            logger.debug("Added command class: {} -> {}", configPair[0], configPair[1]);
        } catch (ClassNotFoundException | ClassCastException e) {
            logger.error("Invalid class for a command: {}", configPair[1]);
            throw new ConfigurationException("Invalid class for a command: " + configPair[1]);
        }
    }
}
