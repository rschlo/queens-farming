package edu.kit.informatik.io.commands;

import java.util.regex.Matcher;

import edu.kit.informatik.exceptions.IllegalInputException;
import edu.kit.informatik.exceptions.IllegalMoveException;
import edu.kit.informatik.game.AcreageType;
import edu.kit.informatik.game.Pair;
import edu.kit.informatik.game.Player;
import edu.kit.informatik.game.Position;

/**
 * This class models a command that buys an acreage at a specified
 * position for the current player.
 * 
 * @author ulqch
 * @version 1.0
 */
public class BuyAcreage extends Command {

    private static final String REGEX = "buy land (-?\\d+) (-?\\d+)";
    private static final String MESSAGE = "You have bought a %s for %d gold.";
    private static final int X_COORDINATE_GROUP = 1;
    private static final int Y_COORDINATE_GROUP = 2;
    private static final String ERROR_INPUT_NUMBER_RANGE = "Your input was not a number in the range of %d to %d.";

    /**
     * This constructor creates a new BuyAcreage command.
     */
    public BuyAcreage() {
        super(REGEX);
    }

    @Override
    public String execute(Matcher input, Player player) {
        Pair<AcreageType, Integer> receipt;
        try {
            int x = Integer.parseInt(input.group(X_COORDINATE_GROUP));
            int y = Integer.parseInt(input.group(Y_COORDINATE_GROUP));
            receipt = player.buy(new Position(x, y));
        } catch (NumberFormatException numberFormatException) {
            Exception exception = new IllegalInputException(
                    ERROR_INPUT_NUMBER_RANGE.formatted(Integer.MIN_VALUE, Integer.MAX_VALUE));
            return exception.getMessage();
        } catch (IllegalMoveException illegalMoveException) {
            return illegalMoveException.getMessage();
        }
        return MESSAGE.formatted(receipt.getKey().getName(), receipt.getValue());
    }

}
