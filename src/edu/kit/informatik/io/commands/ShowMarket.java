package edu.kit.informatik.io.commands;

import java.util.regex.Matcher;

import edu.kit.informatik.game.Player;
import edu.kit.informatik.game.VegetableMarket;
import edu.kit.informatik.io.DataTable;

/**
 * This class models a command that shows the current state of the
 * vegetable market
 * 
 * @author ulqch
 * @version 1.0
 */
public class ShowMarket extends Command {

    private static final String REGEX = "show market";

    /**
     * This constructor creates a new Show Market Command
     */
    public ShowMarket() {
        super(REGEX);
    }

    @Override
    public String execute(Matcher input, Player player) {
        VegetableMarket market = player.getCommonVegetableMarket();
        StringBuilder stringBuilder = new StringBuilder();
        DataTable table = new DataTable(market.getEntries());

        for (int row = 0; row < table.getNumberOfEntries(); row++) {
            stringBuilder.append(table.getRow(row));
            if (row < table.getNumberOfEntries() - 1) {
                stringBuilder.append(System.lineSeparator());
            }
        }

        return stringBuilder.toString();
    }

}
