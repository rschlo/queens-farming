package edu.kit.informatik.game;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import edu.kit.informatik.exceptions.IllegalMoveException;

import java.util.Set;

/**
 * This class represents a barn that can store vegetables and gold.
 * 
 * @author ulqch
 * @version 1.0
 */
public class Barn extends Tile {

    /**
     * This is the abbreviation of the barn used in string representations.
     */
    public static final String ABBREVIATION = "B";

    private static final String ERROR_REMOVE_NEGATIVE_AMOUNT = "Cannot remove a negative amount of vegetables";
    private static final String ERROR_GOLD_NEGATIVE = "Cannot set the gold value to a negative value";
    private static final String ERROR_NOT_ENOUGH_GOLD = "You do not have enough gold in your barn";
    private static final String ERROR_NOT_ENOUGH_VEGETABLES = "You do not have enough vegetables in your barn";

    private static final int ROUNDS_UNTIL_SPOIL = 6;

    private int gold;
    private final Map<VegetableType, Integer> vegetableStock;

    /**
     * This constructor creates a new Barn with an initial gold amount.
     * 
     * @param gold Gold amount that is initially in the barn
     */
    public Barn(int gold) {
        if (gold < 0) {
            throw new IllegalArgumentException(ERROR_GOLD_NEGATIVE);
        }
        this.gold = gold;
        this.vegetableStock = new HashMap<>();
        this.clearVegetables();
    }

    /**
     * This constructor creates a new Barn by copying another existing barn.
     * 
     * @param barnToCopy The barn to copy
     */
    public Barn(Barn barnToCopy) {
        super(barnToCopy);
        this.gold = barnToCopy.getAmountOfGold();
        this.vegetableStock = barnToCopy.getStock();
    }

    private void clearVegetables() {
        for (VegetableType vegetable : VegetableType.values()) {
            this.vegetableStock.put(vegetable, 0);
        }
    }

    /**
     * This method returns the vegetable stock. It's a map with the vegetable types
     * as keys and the number of vegetables as values.
     * 
     * @return The vegetable stock stored in this barn
     */
    public Map<VegetableType, Integer> getStock() {
        HashMap<VegetableType, Integer> result = new HashMap<>();
        for (VegetableType vegetable : this.vegetableStock.keySet()) {
            if (this.vegetableStock.get(vegetable) > 0) {
                result.put(vegetable, this.vegetableStock.get(vegetable));
            }
        }
        return result;
    }

    /**
     * This method returns a set of the vegetable types stored in this barn.
     * 
     * @return A set of the vegetable types stored in this barn.
     */
    public Set<VegetableType> getVegetableTypes() {
        Set<VegetableType> storedVegetables = new HashSet<>();
        for (VegetableType vegetable : this.vegetableStock.keySet()) {
            if (this.vegetableStock.get(vegetable) > 0) {
                storedVegetables.add(vegetable);
            }
        }
        return storedVegetables;
    }

    /**
     * This method stores a vegetable container in this barn.
     * 
     * @param vegetableType The vegetable type to be stored in this barn
     * @param amount        The amount of vegetables to be stored in this barn
     */
    public void store(VegetableType vegetableType, int amount) {
        vegetableStock.put(vegetableType, vegetableStock.get(vegetableType) + amount);

        if (!this.hasCountdown()) {
            this.setCountdown(ROUNDS_UNTIL_SPOIL);
        }
    }

    /**
     * This method buys a vegetable at the common market. It returns a receipt that
     * contains the vegetable type that got bought and the price of it.
     * 
     * @param vegetable The vegetable that should be bought
     * @param market    The common market at which the vegetable should be bought.
     * @return A key-value pair that contains the vegetable type that got bought and
     *         the price of it.
     * @throws IllegalMoveException if there is not enough gold in the barn
     */
    public Pair<VegetableType, Integer> buyAtMarket(VegetableType vegetable, VegetableMarket market)
            throws IllegalMoveException {
        int gold = this.getAmountOfGold();
        int price = market.getPrice(vegetable);
        if (gold < price) {
            throw new IllegalMoveException(ERROR_NOT_ENOUGH_GOLD);
        }
        this.setAmountOfGold(gold - price);
        this.store(vegetable, 1);
        return new Pair<VegetableType, Integer>(vegetable, price);
    }

    /**
     * This method sells specified amounts of vegetables at the common market. It
     * returns a list of key-value pairs. Each pair contains the vegetable type and
     * the price for which it was sold. A vegetable type may occur multiple times in
     * such a list.
     * 
     * @param vegetables Types and amounts of the vegetables that should be sold
     * @param market     The common market at which the vegetables should be sold
     * @return A list of key-value pairs. Each pair contains the vegetable type and
     *         the price for which it was sold. A vegetable type may occur in the
     *         list multiple times.
     * @throws IllegalMoveException if there were not enough vegetables in the barn
     */
    public List<Pair<VegetableType, Integer>> sellAtMarket(Map<VegetableType, Integer> vegetables,
            VegetableMarket market)
            throws IllegalMoveException {

        int profit = 0;
        List<Pair<VegetableType, Integer>> listOfSales = new ArrayList<>();

        for (VegetableType vegetable : vegetables.keySet()) {
            int amount = vegetables.get(vegetable);
            if (this.getAmountOf(vegetable) < amount) {
                throw new IllegalMoveException(ERROR_NOT_ENOUGH_VEGETABLES);
            }
            for (int i = 0; i < amount; i++) {
                int price = market.getPrice(vegetable);
                Pair<VegetableType, Integer> pair = new Pair<>(vegetable, price);
                listOfSales.add(pair);
                profit += price;
            }
        }

        for (VegetableType vegetable : vegetables.keySet()) {
            int amount = vegetables.get(vegetable);
            market.sell(vegetable, amount);
            this.remove(vegetable, amount);
        }

        int gold = this.getAmountOfGold();
        this.setAmountOfGold(gold + profit);
        return listOfSales;
    }

    /**
     * This method returns the number of stored vegetables of a specified type.
     * 
     * @param vegetable Vegetable type
     * @return Number of stored vegetables of a specified type
     */
    public int getAmountOf(VegetableType vegetable) {
        return this.vegetableStock.get(vegetable);
    }

    /**
     * This method removes a number of vegetables of a specified type from this
     * barn.
     * 
     * @param vegetable The vegetable type of which vegetable should be removed
     * @param amount    The amount of vegetables of the specified type that should
     *                  be removed
     */
    public void remove(VegetableType vegetable, int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException(ERROR_REMOVE_NEGATIVE_AMOUNT);
        }

        int currentAmount = this.vegetableStock.get(vegetable);
        if (amount > currentAmount) {
            this.vegetableStock.put(vegetable, 0);
        } else {
            this.vegetableStock.put(vegetable, currentAmount - amount);
        }

        if (!this.hasVegetablesStored()) {
            this.removeCountdown();
        }
    }

    /**
     * This method checks whether this barn has any vegetables stored.
     * 
     * @return true if there are vegetables in this barn.
     */
    public boolean hasVegetablesStored() {
        return this.getVegetableTypes().size() != 0;
    }

    /**
     * This method sets the amount of gold that is stored in this barn.
     * 
     * @param gold amount of gold that should be in the barn.
     */
    public void setAmountOfGold(int gold) {
        if (gold < 0) {
            throw new IllegalArgumentException(ERROR_GOLD_NEGATIVE);
        }
        this.gold = gold;
    }

    /**
     * This method returns the amount of gold that is stored in this barn.
     * 
     * @return The amount of gold that is stored in this barn
     */
    public int getAmountOfGold() {
        return this.gold;
    }

    @Override
    public void onCountdown() {
        this.clearVegetables();
    }

    /**
     * This method returns the total sum of vegetables that are stored in this barn.
     * 
     * @return total sum of vegetables that are stored in this barn
     */
    public int getTotalSumOfVegetables() {
        int sum = 0;
        for (VegetableType vegetable : this.getVegetableTypes()) {
            sum += this.getAmountOf(vegetable);
        }
        return sum;
    }

    /**
     * This method returns a list of key-value pairs containing the vegetable type and
     * the number of vegetables of this type stored in this barn. Vegetable types of
     * which no vegetable is stored in this barn are not included in the list.
     * 
     * @return A list of key-value pairs containing the vegetable type and the number of
     *         vegetables of this type stored in this barn. Vegetable types of which
     *         no vegetable is stored in this barn are not included in the list.
     */
    public List<Pair<VegetableType, Integer>> getEntries() {

        List<Pair<VegetableType, Integer>> pairs = new ArrayList<>();
        
        for (Map.Entry<VegetableType, Integer> entry : this.getStock().entrySet()) {
            pairs.add(new Pair<VegetableType, Integer>(entry.getKey(), entry.getValue()));
        }

        pairs.sort(new Comparator<Pair<VegetableType, Integer>>() {

            @Override
            public int compare(Pair<VegetableType, Integer> entry1, Pair<VegetableType, Integer> entry2) {
                int compareAmounts = entry1.getValue().compareTo(entry2.getValue());
                if (compareAmounts == 0) {
                    return entry1.getKey().getPluralName().compareTo(entry2.getKey().getPluralName());
                } else {
                    return compareAmounts;
                }
            }

        });

        return pairs;

    }

}
