package edu.kit.informatik.io.commands;

import java.util.regex.Matcher;

import edu.kit.informatik.game.Barn;
import edu.kit.informatik.game.Player;
import edu.kit.informatik.io.DataTable;

/**
 * This class models a command that shows the current contents of the
 * barn of a player.
 * 
 * @author ulqch
 * @version 1.0
 */
public class ShowBarn extends Command {

    private static final String REGEX = "show barn";
    private static final String BARN_TITLE = "Barn";
    private static final String BARN_MESSAGE = " (spoils in %d %s)";
    private static final String TURN_SINGULAR = "turn";
    private static final String TURN_PLURAL = "turns";
    private static final String SUM_VEGETABLES_KEY = "Sum";
    private static final String AMOUNT_GOLD_KEY = "Gold";

    /**
     * This constructor creates a new ShowBarn Command
     */
    public ShowBarn() {
        super(REGEX);
    }

    @Override
    public String execute(Matcher input, Player player) {
        Barn barn = player.getBarn();

        DataTable table = new DataTable(barn.getEntries());
        table.addEntry(SUM_VEGETABLES_KEY, Integer.toString(barn.getTotalSumOfVegetables()));
        table.addEntry(AMOUNT_GOLD_KEY, Integer.toString(barn.getAmountOfGold()));

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(BARN_TITLE);
        if (barn.hasCountdown()) {
            String barnMessage = BARN_MESSAGE.formatted(barn.getCountdown(),
                    barn.getCountdown() == 1 ? TURN_SINGULAR : TURN_PLURAL);
            stringBuilder.append(barnMessage);
        }

        int row;
        for (row = 0; row < barn.getEntries().size(); row++) {
            stringBuilder.append(System.lineSeparator());
            stringBuilder.append(table.getRow(row));
        }
        if (row != 0) {
            stringBuilder.append(System.lineSeparator());
            stringBuilder.append(table.getHorizontalDivider());
            stringBuilder.append(System.lineSeparator());
            stringBuilder.append(table.getRow(row));
            stringBuilder.append(System.lineSeparator());
        }

        row++;
        stringBuilder.append(System.lineSeparator());
        stringBuilder.append(table.getRow(row));
        return stringBuilder.toString();
    }

}
