package edu.kit.informatik.game;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * This class represents a vegetable market. It calculates the price of
 * vegetables based on how many vegetables were sold by players.
 * 
 * @author ulqch
 * @version 1.0
 */
public class VegetableMarket {

    private static final int INITIAL_INDICATOR = 2;
    private static final int DIFFERENCE_PER_SHIFT = 2;

    private final Map<VegetableType, Integer> indicators;
    private final Map<VegetableType, int[]> prices;
    private final Set<Pair<VegetableType, VegetableType>> pairs;
    private Map<VegetableType, Integer> soldVegetables = new HashMap<>();

    /**
     * This constructor creates a new vegetable market. It takes a price table which
     * contains all possible prices each vegetable can have and it takes a set of
     * vegetable pairs that determines which prices are linked.
     * 
     * @param prices A price table which contains all possible prices each vegetable can have
     * @param pairs  A set of vegetable pairs that determines which prices are linked
     */
    public VegetableMarket(Map<VegetableType, int[]> prices, Set<Pair<VegetableType, VegetableType>> pairs) {
        this.prices = new HashMap<>(prices);
        this.pairs = new HashSet<>(pairs);
        this.indicators = new HashMap<>();
        for (VegetableType vegetableType : this.prices.keySet()) {
            this.indicators.put(vegetableType, INITIAL_INDICATOR);
        }

        for (VegetableType vegetable : VegetableType.values()) {
            this.soldVegetables.put(vegetable, 0);
        }
    }

    /**
     * This constructor takes another vegetable market and makes an exact copy of
     * it.
     * 
     * @param marketToCopy vegetable market to copy
     */
    public VegetableMarket(VegetableMarket marketToCopy) {
        this.prices = new HashMap<>(marketToCopy.prices);
        this.pairs = new HashSet<>(marketToCopy.pairs);
        this.soldVegetables = new HashMap<>(marketToCopy.soldVegetables);
        this.indicators = new HashMap<>(marketToCopy.indicators);
    }

    /**
     * This method returns the price of a specified vegetable type.
     * 
     * @param vegetable Vegetable type of which the price should be returned.
     * @return The price of the specified vegetable type
     */
    public int getPrice(VegetableType vegetable) {
        int indicator = this.indicators.get(vegetable);
        return this.prices.get(vegetable)[indicator];
    }

    /**
     * This method keeps track of the players sales so that after each round the
     * prices of the vegetables can be accurately recalculated.
     * 
     * @param vegetable Vegetable type that got sold
     * @param amount    Amount of vegetables that got sold
     */
    public void sell(VegetableType vegetable, int amount) {
        this.soldVegetables.put(vegetable, this.soldVegetables.get(vegetable) + amount);
    }

    /**
     * This method recalculates the prices for each vegetable based on what
     * vegetables were sold in the past (see {@link #sell(VegetableType, int) Sell}
     * method).
     */
    public void adapt() {
        for (Pair<VegetableType, VegetableType> pair : this.pairs) {
            VegetableType first = pair.getKey();
            VegetableType second = pair.getValue();
            int delta = soldVegetables.get(first) - soldVegetables.get(second);
            int deltaHalf = delta / DIFFERENCE_PER_SHIFT;
            // Does't matter if first indicator or second indicator since they are equal.
            int newIndicator = this.indicators.get(first) + deltaHalf;
            if (newIndicator >= this.prices.get(first).length || newIndicator >= this.prices.get(second).length) {
                newIndicator = Math.min(this.prices.get(first).length, this.prices.get(second).length) - 1;
            }
            if (newIndicator < 0) {
                newIndicator = 0;
            }

            this.indicators.put(first, newIndicator);
            this.indicators.put(second, newIndicator);
        }

        // Reset sold vegetables
        for (VegetableType vegetable : VegetableType.values()) {
            this.soldVegetables.put(vegetable, 0);
        }
    }

    /**
     * This method returns a list of value pairs. Each value pair contains the
     * vegetable type and the current price of the vegetable.
     * 
     * @return a list of value pairs. Each value pair contains the vegetable type
     *         and the current price of the vegetable.
     */
    public List<Pair<VegetableType, Integer>> getEntries() {
        List<VegetableType> order = Arrays.asList(
                VegetableType.MUSHROOM, VegetableType.CARROT, VegetableType.TOMATO, VegetableType.SALAD);
        List<Pair<VegetableType, Integer>> entries = new ArrayList<>();
        for (VegetableType vegetableType : order) {
            entries.add(new Pair<VegetableType, Integer>(vegetableType, this.getPrice(vegetableType)));
        }
        return entries;
    }

}
