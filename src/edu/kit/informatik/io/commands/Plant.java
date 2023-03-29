package edu.kit.informatik.io.commands;

import java.util.regex.Matcher;

import edu.kit.informatik.exceptions.IllegalInputException;
import edu.kit.informatik.exceptions.IllegalMoveException;
import edu.kit.informatik.game.Player;
import edu.kit.informatik.game.Position;
import edu.kit.informatik.game.VegetableType;

/**
 * This class models a command that plants a specified vegetable on an
 * acreage for a player.
 * 
 * @author ulqch
 * @version 1.0
 */
public class Plant extends Command {

    private static final String REGEX = Plant.buildRegEx();
    private static final String INCOMPLETE_REGEX = "plant (-?\\d+) (-?\\d+) (%s)";
    private static final String REGEX_OR = "|";
    private static final int X_COORDINATE_GROUP = 1;
    private static final int Y_COORDINATE_GROUP = 2;
    private static final int VEGETABLE_GROUP = 3;
    private static final String ERROR_INPUT_NUMBER_RANGE = "Your input was not a number in the range of %d to %d.";

    /**
     * This constructor creates a new Plant command.
     */
    public Plant() {
        super(REGEX);
    }

    private static String buildRegEx() {
        StringBuilder stringBuilder = new StringBuilder();
        VegetableType[] vegetables = VegetableType.values();
        for (int i = 0; i < vegetables.length; i++) {
            stringBuilder.append(vegetables[i].getSingularName());
            if (i < vegetables.length - 1) {
                stringBuilder.append(REGEX_OR);
            }
        }
        return INCOMPLETE_REGEX.formatted(stringBuilder.toString());
    }

    @Override
    public String execute(Matcher input, Player player) {
        try {
            int x = Integer.parseInt(input.group(X_COORDINATE_GROUP));
            int y = Integer.parseInt(input.group(Y_COORDINATE_GROUP));
            VegetableType vegetable = VegetableType.parse(input.group(VEGETABLE_GROUP));
            player.plant(new Position(x, y), vegetable);
        } catch (NumberFormatException numberFormatException) {
            Exception exception = new IllegalInputException(
                    ERROR_INPUT_NUMBER_RANGE.formatted(Integer.MIN_VALUE, Integer.MAX_VALUE));
            return exception.getMessage();
        } catch (IllegalMoveException illegalMoveException) {
            return illegalMoveException.getMessage();
        }
        return null;
    }

}
