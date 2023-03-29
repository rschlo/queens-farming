package edu.kit.informatik.io.commands;

import java.util.regex.Matcher;

import edu.kit.informatik.game.Acreage;
import edu.kit.informatik.game.Barn;
import edu.kit.informatik.game.Farm;
import edu.kit.informatik.game.Player;
import edu.kit.informatik.game.Position;
import edu.kit.informatik.game.VegetableType;

/**
 * This class models a command that shows the current state of the
 * board of a player.
 * 
 * @author ulqch
 * @version 1.0
 */
public class ShowBoard extends Command {

    private static final String REGEX = "show board";
    private static final String FILLING = " ";
    private static final int TILE_WIDTH = 5;
    private static final String SEPERATOR = "|";
    private static final String NO_COUNTDOWN_SYMBOL = "*";
    private static final String OCCUPIED_TO_CAPACITY_SYMBOL = "/";

    /**
     * This constructor creates a new ShowBoard command
     */
    public ShowBoard() {
        super(REGEX);
    }

    @Override
    public String execute(Matcher input, Player player) {
        StringBuilder stringBuilder = new StringBuilder();

        Farm farm = player.getFarm();
        int heighestY = farm.getNorthEastCorner().getY();

        for (int yCoordinate = heighestY; yCoordinate >= 0; yCoordinate--) {
            for (int i = 0; i < 3; i++) {
                String line = buildLine(farm, yCoordinate, i);
                stringBuilder.append(line);
                // Break the line.
                if (i < 2 || yCoordinate > 0) {
                    stringBuilder.append(System.lineSeparator());
                }
            }
        }
        return stringBuilder.toString();
    }

    private static String buildLine(Farm farm, int yCoordinate, int i) {

        StringBuilder stringBuilder = new StringBuilder();

        // Calculate the dimension of the farm.
        int heighestX = farm.getSouthWestCorner().getX();
        int lowestX = farm.getNorthEastCorner().getX();

        for (int xCoordinate = lowestX; xCoordinate <= heighestX; xCoordinate++) {
            Position position = new Position(xCoordinate, yCoordinate);

            // Add a seperator to the result string if there is a tile at the current
            // position or if there is a tile at the position left to it.
            if (farm.isThereATile(position) || farm.isThereATile(new Position(xCoordinate - 1, yCoordinate))) {
                stringBuilder.append(SEPERATOR);
            } else {
                stringBuilder.append(FILLING);
            }

            // If there is a tile at the current position then add the i-th line of the
            // string representation of the tile to the result string.
            if (farm.isThereATile(position)) {
                if (position.equals(farm.getBarnPosition())) {
                    stringBuilder.append(get3Lines(farm.getBarn())[i]);
                } else {
                    Acreage acreage = farm.getAcreage(position);
                    stringBuilder.append(get3Lines(acreage)[i]);
                }
            } else {
                stringBuilder.append(FILLING.repeat(TILE_WIDTH));
            }
        }

        // Add a seperator to the result string if the last position in the row had a
        // tile on it.
        if (farm.isThereATile(new Position(heighestX, yCoordinate))) {
            stringBuilder.append(SEPERATOR);
        } else {
            stringBuilder.append(FILLING);
        }

        return stringBuilder.toString();

    }

    private static String[] get3Lines(Barn barn) {
        String[] result = new String[3];
        result[0] = FILLING.repeat(TILE_WIDTH);
        String text = Barn.ABBREVIATION + FILLING + (barn.hasCountdown() ? barn.getCountdown() : NO_COUNTDOWN_SYMBOL);
        result[1] = pad(text);
        result[2] = FILLING.repeat(TILE_WIDTH);
        return result;
    }

    private static String[] get3Lines(Acreage acreage) {
        String[] result = new String[3];

        String text = acreage.getType().getAbbreviation() + FILLING
                + (acreage.hasCountdown() ? acreage.getCountdown() : NO_COUNTDOWN_SYMBOL);
        result[0] = pad(text);

        VegetableType vegetableType = acreage.getVegetableType();
        if (vegetableType != null) {
            result[1] = pad(vegetableType.getAbbreviation());
        } else {
            result[1] = FILLING.repeat(TILE_WIDTH);
        }

        text = acreage.getAmountOfVegetables() + OCCUPIED_TO_CAPACITY_SYMBOL + acreage.getCapacity();

        result[2] = pad(text);
        return result;
    }

    private static String pad(String text) {
        int startIndex = (int) Math.ceil(ShowBoard.TILE_WIDTH / 2.0f - text.length() / 2.0f);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(ShowBoard.FILLING.repeat(startIndex));
        stringBuilder.append(text);
        while (stringBuilder.length() < ShowBoard.TILE_WIDTH) {
            stringBuilder.append(ShowBoard.FILLING);
        }
        return stringBuilder.toString();
    }

}
