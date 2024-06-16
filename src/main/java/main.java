import com.sun.tools.javac.Main;
import commandsplitter.CommandSplitter;
import exceptions.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class main {

    private static final Logger logger = LogManager.getLogger(Main.class.getName());
    private static Scanner obtainInputScanner(String[] args) throws FileNotFoundException {
        if (args.length == 0)
            return new Scanner(System.in);
        return new Scanner(new FileReader(args[0]));
    }

    private static void run(Scanner scanner, Calculator calculator) {
        CommandSplitter splitter = new CommandSplitter();

        while (scanner.hasNext()) {
            String userInput = scanner.next();
            if (userInput.strip().equalsIgnoreCase("EXIT"))
                return;
            if (userInput.strip().startsWith("#"))
                continue;

            splitter.splitCommand(userInput);
            try {
                calculator.execute(splitter.getCommandName(), splitter.getCommandArgs());
            } catch (MathematicalException | EmptyNumStackException e) {
                logger.error("MathematicalException or EmptyNumStackException occurred: {}", e.getMessage(), e);
                System.out.println(e.getMessage() + "\nRestarting...");
                calculator.restart();
            } catch (CommandException | CommandProductionException e) {
                logger.error("CommandException or CommandProductionException occurred: {}", e.getMessage(), e);
                System.out.println(e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner;
        try {
            scanner = obtainInputScanner(args);
        } catch (FileNotFoundException e) {
            System.out.println("File " + args[0] + " is unavailable.");
            return;
        }
        scanner.useDelimiter("\n");

        Calculator calculator;
        try {
            calculator = new Calculator();
        } catch (ConfigurationException e) {
            System.out.println(e.getMessage());
            return;
        }

        run(scanner, calculator);
    }
}