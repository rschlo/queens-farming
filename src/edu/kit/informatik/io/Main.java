package edu.kit.informatik.io;

import java.util.Scanner;

import edu.kit.informatik.exceptions.IllegalInputException;
import edu.kit.informatik.game.GameMaster;
import edu.kit.informatik.game.GameResult;

/**
 * This is a command line game called Queens Farming.
 * 
 * @author ulqch
 * @version 1.0
 */
public final class Main {

    private static final String ERROR_COMMAND_LINE_ARGUMENTS = "Command line arguments are not allowed.";

    private Main() {
    }

    /**
     * This method organizes the initialization of the game, the actual game play
     * and the result of the game.
     * 
     * @param args Command line arguments. These should be empty. Otherwise an error
     *             gets thrown.
     */
    public static void main(String[] args) {

        if (args.length != 0) {
            Exception exception = new IllegalInputException(ERROR_COMMAND_LINE_ARGUMENTS);
            System.out.println(exception.getMessage());
            return;
        }

        Scanner scanner = new Scanner(System.in);

        Controller commandLineController = new Controller(scanner);
        GameMaster gameMaster = CommandLineUtil.buildGame(scanner);

        if (gameMaster == null) {
            scanner.close();
            return;
        }

        GameResult result = gameMaster.play(commandLineController);

        String endMessage = CommandLineUtil.createEndMessage(result);
        System.out.println(endMessage);

        scanner.close();

    }

}
