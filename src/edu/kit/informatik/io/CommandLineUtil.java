package edu.kit.informatik.io;

import java.util.Scanner;
import java.util.regex.Pattern;

import edu.kit.informatik.exceptions.IllegalInputException;
import edu.kit.informatik.game.GameMaster;
import edu.kit.informatik.game.GameResult;
import edu.kit.informatik.game.Player;

/**
 * This is a utilary class to help setup a new game and to print the result
 * of a game.
 * 
 * @author ulqch
 * @version 1.0
 */
public final class CommandLineUtil {

    private static final String ASCII_ART = String.join(System.lineSeparator(),
            "                           _.-^-._    .--.    ",
            "                        .-'   _   '-. |__|    ",
            "                       /     |_|     \\|  |    ",
            "                      /               \\  |    ",
            "                     /|     _____     |\\ |    ",
            "                      |    |==|==|    |  |    ",
            "  |---|---|---|---|---|    |--|--|    |  |    ",
            "  |---|---|---|---|---|    |==|==|    |  |    ",
            "^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^",
            "^^^^^^^^^^^^^^^ QUEENS FARMING ^^^^^^^^^^^^^^^",
            "^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");

    private static final String ASK_NUMBER_OF_PLAYERS = "How many players?";
    private static final String ASK_INITIAL_GOLD = "With how much gold should each player start?";
    private static final String ASK_GOLD_TO_WIN = "With how much gold should a player win?";
    private static final String ASK_NAME_OF_PLAYER = "Enter the name of player %d:";
    private static final String ASK_SEED = "Please enter the seed used to shuffle the tiles:";
    private static final String PLAYER_GOLD_STATISTIC = "Player %d (%s): %d";

    private static final String QUIT = "quit";
    private static final int MIN_VALUE_INITIAL_GOLD = 0;
    private static final int MIN_VALUE_GOLD_TO_WIN = 1;

    private static final String ERROR_DOES_NOT_MATCH_PATTERN = "Input does not match the expected pattern %s";
    private static final String ERROR_INPUT_NUMBER_RANGE = "Your input was not a number in the range of %d to %d.";

    private static final String REGEX_NUMBER = "-?[\\d]+";
    private static final String REGEX_PLAYER_NAME = "[a-zA-Z]+";

    private CommandLineUtil() {
    }

    /**
     * This method reads user input from the command line. If the input does not
     * match an excepted pattern an error message is printed and the user can try
     * again. If the user enters "quit", then null gets returned.
     * 
     * @param scanner         command line scanner
     * @param expectedPattern the regex the user input should match
     * @return user input or null if the user entered "quit"
     */
    private static String read(Scanner scanner, String expectedPattern) {
        Pattern pattern = Pattern.compile(expectedPattern);
        String input = scanner.nextLine();
        if (input.equals(QUIT)) {
            return null;
        }
        while (!pattern.matcher(input).matches()) {
            String errorMessage = ERROR_DOES_NOT_MATCH_PATTERN.formatted(expectedPattern);
            System.out.println(new IllegalInputException(errorMessage).getMessage());
            input = scanner.nextLine();
            if (input.equals(QUIT)) {
                return null;
            }
        }
        return input;
    }

    /**
     * This method prints a question and reads user input from the command line. The
     * user input must be a integer value higher than a specified minimum value.
     * 
     * @param scanner  command line scanner
     * @param question question that gets printed
     * @param minValue minimum value that gets accepted
     * @return Integer that got parsed from user input or null if user entered
     *         "quit"
     */
    private static Integer askForValidNumber(Scanner scanner, String question, int minValue) {
        Integer result = null;
        System.out.println(question);
        while (result == null) {
            String input = read(scanner, REGEX_NUMBER);
            if (input == null)
                return null;
            try {
                result = Integer.parseInt(input);
                if (result < minValue) {
                    result = null;
                    String errorMessage = ERROR_INPUT_NUMBER_RANGE.formatted(minValue, Integer.MAX_VALUE);
                    System.out.println(new IllegalInputException(errorMessage).getMessage());
                }
            } catch (NumberFormatException numberFormatException) {
                String errorMessage = ERROR_INPUT_NUMBER_RANGE.formatted(Integer.MIN_VALUE, Integer.MAX_VALUE);
                System.out.println(new IllegalInputException(errorMessage).getMessage());
            }
        }
        return result;
    }

    /**
     * This method builds a new Queens Farming game instance based on user inputs.
     * If the user enters "quit" during this initialization null gets returned.
     * 
     * @param scanner command line scanner
     * @return a Queens Farming game instance
     */
    public static GameMaster buildGame(Scanner scanner) {
        System.out.println(ASCII_ART);

        Integer numberOfPlayers = askForValidNumber(scanner, ASK_NUMBER_OF_PLAYERS, 1);
        if (numberOfPlayers == null) {
            return null;
        }

        String[] playerNames = new String[numberOfPlayers];
        for (int i = 0; i < numberOfPlayers; i++) {
            System.out.println(ASK_NAME_OF_PLAYER.formatted(i + 1));
            playerNames[i] = read(scanner, REGEX_PLAYER_NAME);
            if (playerNames[i] == null)
                return null;
        }

        Integer initalGold = askForValidNumber(scanner, ASK_INITIAL_GOLD, MIN_VALUE_INITIAL_GOLD);
        if (initalGold == null)
            return null;
        Integer goldToWin = askForValidNumber(scanner, ASK_GOLD_TO_WIN, MIN_VALUE_GOLD_TO_WIN);
        if (goldToWin == null)
            return null;
        Integer seed = askForValidNumber(scanner, ASK_SEED, Integer.MIN_VALUE);
        if (seed == null)
            return null;

        return new GameMaster(seed, goldToWin, initalGold, playerNames);
    }

    /**
     * This method returns a message containing information about how much gold
     * every player has and which player has won the game.
     * 
     * @param gameResult The game result of a Queens Farming game
     * @return message containing information about the result of the game
     */
    public static String createEndMessage(GameResult gameResult) {
        StringBuilder stringBuilder = new StringBuilder();

        Player[] players = gameResult.getPlayers();

        for (int i = 0; i < players.length; i++) {
            String playerName = players[i].getName();
            int amountOfGold = players[i].getAmountOfGold();
            stringBuilder.append(PLAYER_GOLD_STATISTIC.formatted(i + 1, playerName, amountOfGold));
            stringBuilder.append(System.lineSeparator());
        }

        Player[] winners = gameResult.getWinners();

        if (winners.length == 1) {
            stringBuilder.append(winners[0].getName()).append(" has won!");
        } else {
            for (int i = 0; i < winners.length; i++) {
                if (i == winners.length - 1) {
                    stringBuilder.append(winners[i].getName()).append(" have won!");
                } else if (i == winners.length - 2) {
                    stringBuilder.append(winners[i].getName()).append(" and ");
                } else {
                    stringBuilder.append(winners[i].getName()).append(", ");
                }
            }
        }

        return stringBuilder.toString();
    }

}