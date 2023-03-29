package edu.kit.informatik.io.commands;

import java.util.regex.Matcher;

import edu.kit.informatik.exceptions.IllegalMoveException;
import edu.kit.informatik.game.Pair;
import edu.kit.informatik.game.Player;
import edu.kit.informatik.game.VegetableType;

/**
 * This class models a command that buys a vegetable from the market
 * for the current player.
 * 
 * @author ulqch
 * @version 1.0
 */
public class BuyVegetable extends Command {

    private static final String REGEX = BuyVegetable.buildRegEx();
    private static final String INCOMPLETE_REGEX = "buy vegetable (%s)";
    private static final String REGEX_OR = "|";
    private static final String MESSAGE = "You have bought a %s for %d gold.";
    private static final int VEGETABLE_GROUP = 1;

    /**
     * This constructor creates a new BuyVegetable command.
     */
    public BuyVegetable() {
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
        VegetableType vegetable = VegetableType.parse(input.group(VEGETABLE_GROUP));

        Pair<VegetableType, Integer> receipt;
        try {
            receipt = player.buy(vegetable);
        } catch (IllegalMoveException e) {
            return e.getMessage();
        }
        return MESSAGE.formatted(receipt.getKey().getSingularName(), receipt.getValue());
    }

}
