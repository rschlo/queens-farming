package edu.kit.informatik.io;

import edu.kit.informatik.game.Pair;
import edu.kit.informatik.game.VegetableType;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a table with two columns (key - value). It provides
 * functionality to print table data in a formatted way.
 * 
 * @author ulqch
 * @version 1.0
 */
public class DataTable {

    private static final int SPACE_BETWEEN_COLUMNS = 1;
    private static final String HORIZONTAL_DIVIDER_CHAR = "-";
    private static final String FILLING_SPACE = " ";
    private static final String COLON = ":";

    private final List<Pair<String, String>> entries;

    /**
     * This constructor creates a new DataTable from a list of value pairs. The
     * first column is filled with the plural names of the vegetable types and the
     * second one is filled with the integer number associated with these
     * vegetables (e.g. amount).
     *
     * @param entries List of vegetable type - integer pairs with which this table
     *                should be filled
     */
    public DataTable(List<Pair<VegetableType, Integer>> entries) {
        List<Pair<String, String>> stringEntries = new ArrayList<>();
        for (Pair<VegetableType, Integer> entry : entries) {
            String vegetable = entry.getKey().getPluralName();
            String amount = Integer.toString(entry.getValue());
            stringEntries.add(new Pair<String, String>(vegetable, amount));
        }
        this.entries = stringEntries;
    }

    /**
     * This method adds a key (fist column) value (second column) pair to this
     * table.
     *
     * @param key   Key
     * @param value Value
     */
    public void addEntry(String key, String value) {
        this.entries.add(new Pair<String, String>(key, value));
    }

    private int getWidth() {
        int keyMaxLength = 0;
        int valueMaxLength = 0;
        for (Pair<String, String> entry : this.entries) {
            if (entry.getKey().length() > keyMaxLength) {
                keyMaxLength = entry.getKey().length();
            }
            if (entry.getValue().length() > valueMaxLength) {
                valueMaxLength = entry.getValue().length();
            }
        }
        return keyMaxLength + COLON.length() + SPACE_BETWEEN_COLUMNS + valueMaxLength;
    }

    /**
     * This method returns the number of entries (rows) in this table
     *
     * @return Number of entries (rows) in this table
     */
    public int getNumberOfEntries() {
        return this.entries.size();
    }

    /**
     * This method returns a formatted row of this table. Which row that should be
     * must be specified by an index.
     *
     * @param index Index of a row
     * @return Formatted row of this table
     */
    public String getRow(int index) {
        String key = this.entries.get(index).getKey();
        String value = this.entries.get(index).getValue();

        return key + COLON
                + FILLING_SPACE.repeat(this.getWidth() - value.length() - key.length() - COLON.length())
                + value;
    }

    /**
     * This method returns a horizontal divider. It has the same length as a normal
     * row.
     *
     * @return A horizontal divider
     */
    public String getHorizontalDivider() {
        return HORIZONTAL_DIVIDER_CHAR.repeat(this.getWidth());
    }

}
