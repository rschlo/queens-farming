package edu.kit.informatik.io.commands;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

import edu.kit.informatik.exceptions.IllegalMoveException;
import edu.kit.informatik.game.Pair;
import edu.kit.informatik.game.Player;
import edu.kit.informatik.game.VegetableType;

/**
 * This class models a command that sells specified vegetables of a
 * player barn for gold.
 * 
 * @author ulqch
 * @version 1.0
 */
public class Sell extends Command {

    private static final String REGEX = Sell.buildRegEx();
    private static final String SELL_ALL_ARGUMENT = " all";
    private static final String INCOMPLETE_REGEX = "sell(%s|(%s)*)";
    private static final String REGEX_OR = "|";
    private static final String MESSAGE = "You have sold %d %s for %d gold.";
    private static final String VEGETABLE_SINGULAR = "vegetable";
    private static final String VEGETABLE_PLURAL = "vegetables";
    private static final String ARGUMENT_SEPERATOR = " ";
    private static final int ARGUMENT_GROUP = 1;

    /**
     * This constructor creates a new Sell command.
     */
    public Sell() {
        super(REGEX);
    }

    private static String buildRegEx() {
        StringBuilder stringBuilder = new StringBuilder();
        VegetableType[] vegetables = VegetableType.values();
        for (int i = 0; i < vegetables.length; i++) {
            stringBuilder.append(ARGUMENT_SEPERATOR);
            stringBuilder.append(vegetables[i].getSingularName());
            if (i < vegetables.length - 1) {
                stringBuilder.append(REGEX_OR);
            }
        }
        return INCOMPLETE_REGEX.formatted(SELL_ALL_ARGUMENT, stringBuilder.toString());
    }

    @Override
    public String execute(Matcher input, Player player) {
        List<Pair<VegetableType, Integer>> listOfSales;

        String argument = input.group(ARGUMENT_GROUP);

        if (argument.equals(SELL_ALL_ARGUMENT)) {
            try {
                listOfSales = player.sellAll();
            } catch (IllegalMoveException e) {
                return e.getMessage();
            }
        } else {
            Map<VegetableType, Integer> vegetablesToSell = new HashMap<>();
            if (!argument.isEmpty()) {
                for (String string : argument.split(ARGUMENT_SEPERATOR)) {
                    VegetableType vegetable = VegetableType.parse(string);
                    if (vegetable == null)
                        continue;
                    vegetablesToSell.putIfAbsent(vegetable, 0);
                    vegetablesToSell.put(vegetable, vegetablesToSell.get(vegetable) + 1);
                }
            }

            try {
                listOfSales = player.sell(vegetablesToSell);
            } catch (IllegalMoveException e) {
                return e.getMessage();
            }
        }

        int totalProfit = listOfSales.stream().mapToInt(Pair::getValue).sum();
        int amountOfSoldVegetables = listOfSales.size();

        String vegetableText = amountOfSoldVegetables == 1 ? VEGETABLE_SINGULAR : VEGETABLE_PLURAL;
        return MESSAGE.formatted(amountOfSoldVegetables, vegetableText, totalProfit);
    }

}
