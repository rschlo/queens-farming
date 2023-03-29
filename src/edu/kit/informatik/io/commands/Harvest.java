package edu.kit.informatik.io.commands;

import java.util.regex.Matcher;

import edu.kit.informatik.exceptions.IllegalInputException;
import edu.kit.informatik.exceptions.IllegalMoveException;
import edu.kit.informatik.game.Pair;
import edu.kit.informatik.game.Player;
import edu.kit.informatik.game.Position;
import edu.kit.informatik.game.VegetableType;

/**
 * This class models a command that harvests a specified amount of
 * vegetables from a specfied acreage of a player.
 * 
 * @author ulqch
 * @version 1.0
 */
public class Harvest extends Command {

    private static final String REGEX = "harvest (-?\\d+) (-?\\d+) (\\d+)";
    private static final String MESSAGE = "You have harvested %d %s.";
    private static final int X_COORDINATE_GROUP = 1;
    private static final int Y_COORDINATE_GROUP = 2;
    private static final int AMOUNT_GROUP = 3;
    private static final String ERROR_INPUT_NUMBER_RANGE = "Your input was not a number in the range of %d to %d.";

    /**
     * This constructor creates a new Harvest command.
     */
    public Harvest() {
        super(REGEX);
    }

    @Override
    public String execute(Matcher input, Player player) {
        Pair<VegetableType, Integer> container;
        try {
            int x = Integer.parseInt(input.group(X_COORDINATE_GROUP));
            int y = Integer.parseInt(input.group(Y_COORDINATE_GROUP));
            int amount = Integer.parseInt(input.group(AMOUNT_GROUP));
            container = player.harvest(new Position(x, y), amount);
        } catch (IllegalMoveException illegalMoveException) {
            return illegalMoveException.getMessage();
        } catch (NumberFormatException numberFormatException) {
            Exception exception = new IllegalInputException(
                    ERROR_INPUT_NUMBER_RANGE.formatted(Integer.MIN_VALUE, Integer.MAX_VALUE));
            return exception.getMessage();
        }

        VegetableType vegetable = container.getKey();
        String vegetableText = container.getValue() == 1 ? vegetable.getSingularName() : vegetable.getPluralName();

        return MESSAGE.formatted(container.getValue(), vegetableText);
    }

}
