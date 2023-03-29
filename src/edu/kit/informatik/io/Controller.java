package edu.kit.informatik.io;

import edu.kit.informatik.exceptions.IllegalInputException;
import edu.kit.informatik.game.Changes;
import edu.kit.informatik.game.Player;
import edu.kit.informatik.game.PlayerController;
import edu.kit.informatik.io.commands.BuyAcreage;
import edu.kit.informatik.io.commands.BuyVegetable;
import edu.kit.informatik.io.commands.Command;
import edu.kit.informatik.io.commands.EndTurn;
import edu.kit.informatik.io.commands.Harvest;
import edu.kit.informatik.io.commands.Plant;
import edu.kit.informatik.io.commands.Quit;
import edu.kit.informatik.io.commands.Sell;
import edu.kit.informatik.io.commands.ShowBarn;
import edu.kit.informatik.io.commands.ShowBoard;
import edu.kit.informatik.io.commands.ShowMarket;

import java.util.Scanner;
import java.util.regex.Matcher;

/**
 * This class represents a controller that controls what each player should do
 * during their turns. It takes user input from the command line and runs the
 * corresponding command.
 * 
 * @author ulqch
 * @version 1.0
 */
public class Controller implements PlayerController {

    private static final String TURN_BEGIN = "It is %s's turn!";
    private static final String BARN_SPOILED = "The vegetables in your barn are spoiled.";
    private static final String NEWLY_GROWN_VEGETABLE = "1 vegetable has grown since your last turn.";
    private static final String NEWLY_GROWN_VEGETABLES = "%d vegetables have grown since your last turn.";

    private static final String ERROR_COMMAND_NOT_FOUND = "Command not found.";

    private final Command[] commands = new Command[] {
        new ShowBarn(),
        new ShowMarket(),
        new ShowBoard(),
        new BuyVegetable(),
        new Plant(),
        new Sell(),
        new BuyAcreage(),
        new Harvest(),
        new EndTurn(),
        new Quit()
    };

    private final Scanner scanner;

    /**
     * Creates a Controller from a scanner that can be used to read user input.
     *
     * @param scanner Command line scanner
     */
    public Controller(Scanner scanner) {
        this.scanner = scanner;
    }

    private String getBeginMessage(Player player) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(System.lineSeparator());
        stringBuilder.append(TURN_BEGIN.formatted(player.getName()));
        Changes changes = player.getFarmChanges();
        int numberOfNewlyGrownVegetables = changes.getNumberOfNewlyGrownVegetables();
        if (numberOfNewlyGrownVegetables == 1) {
            stringBuilder.append(System.lineSeparator()).append(NEWLY_GROWN_VEGETABLE);
        } else if (numberOfNewlyGrownVegetables > 1) {
            String message = NEWLY_GROWN_VEGETABLES.formatted(numberOfNewlyGrownVegetables);
            stringBuilder.append(System.lineSeparator()).append(message);
        }
        if (changes.hasBarnSpoiled()) {
            stringBuilder.append(System.lineSeparator()).append(BARN_SPOILED);
        }
        return stringBuilder.toString();
    }

    /**
     * This method takes the player whose turn it is, reads user input and passes
     * the player to corresponding commands that perform actions with this player.
     *
     * @param player Player whose turn it is
     */
    @Override
    public void doTurn(Player player) {
        String beginMessage = getBeginMessage(player);
        System.out.println(beginMessage);

        while (player.hasMovesLeft()) {
            String input = this.scanner.nextLine();
            boolean commandFound = false;
            for (final Command command : commands) {
                final Matcher matcher = command.getRegExPattern().matcher(input);
                if (matcher.matches()) {
                    commandFound = true;
                    String result = command.execute(matcher, player);
                    if (result != null) {
                        System.out.println(result);
                    }
                }
            }

            if (!commandFound) {
                System.out.println(new IllegalInputException(ERROR_COMMAND_NOT_FOUND).getMessage());
            }
        }

    }

}
