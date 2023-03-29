package edu.kit.informatik.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * This class represents an acreage market. It contains a randomized
 * list of all acreages that exist in a game. It also offers methods to
 * calculate the price of an acreage. When a player buys an acreage,
 * that acreage is no longer for sale and gets eliminated from the
 * list.
 * 
 * @author ulqch
 * @version 1.0
 */
public class AcreageMarket {

    private static final int PRICE_CALCULATION_FACTOR = 10;

    private final List<Acreage> acreages;
    private int index;

    /**
     * This constructor creates a new acreage market. The number of players is used
     * to determine the list of all acreages that exist in the game. The seed is
     * than used to randomly shuffle this list.
     * 
     * @param seed The seed to be used to randomize the acreages
     * @param numberOfPlayers The number of players
     */
    public AcreageMarket(int seed, int numberOfPlayers) {
        this.index = 0;
        Random random = new Random(seed);
        this.acreages = new ArrayList<>();
        for (AcreageType acreageType : AcreageType.values()) {
            int initiallyUsedByPlayer = Collections.frequency(Player.INITIAL_ACREAGE_TYPES.values(), acreageType);
            for (int i = 0; i < (acreageType.getNumberPerPlayer() - initiallyUsedByPlayer) * numberOfPlayers; i++) {
                this.acreages.add(new Acreage(acreageType));
            }
        }
        Collections.shuffle(acreages, random);
    }

    /**
     * This method calculates the price of an acreage based on the distance to the
     * barn.
     * 
     * @param position of the acreage of which the price should be calculated
     * @return Price of an acreage based on the distance to the barn
     */
    public int calculatePrice(Position position) {
        int manhattenDistance = Farm.BARN_POSITION.getManhattenDistanceTo(position);
        return PRICE_CALCULATION_FACTOR * (manhattenDistance - 1);
    }

    /**
     * This method checks whether there are any acreages for sale.
     * 
     * @return true if there are acreages for sale
     */
    public boolean hasAcreagesLeft() {
        return index < acreages.size();
    }

    /**
     * This method returns a new acreage.
     * 
     * @return A new acreage
     */
    public Acreage getNewAcreage() {
        return this.acreages.get(index++);
    }

}
