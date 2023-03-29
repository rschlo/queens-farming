package edu.kit.informatik.game;

/**
 * This class stores all changes of the farm since the last turn. It
 * stores whether the barn has been spoiled and if there are any newly
 * grown vegetables.
 * 
 * @author ulqch
 * @version 1.0
 */
public class Changes {

    private final boolean hasBarnSpoiled;
    private final int newlyGrownVegetables;

    /**
     * This constructor creates a new Changes object.
     * 
     * @param hasBarnSpoiled       If the barn has been spoiled
     * @param newlyGrownVegetables The number of newly grown vegetables
     */
    public Changes(boolean hasBarnSpoiled, int newlyGrownVegetables) {
        this.hasBarnSpoiled = hasBarnSpoiled;
        this.newlyGrownVegetables = newlyGrownVegetables;
    }

    /**
     * This constructor creates a new Changes object by copying an existing one.
     * @param changesToCopy changes to copy
     */
    public Changes(Changes changesToCopy) {
        this.hasBarnSpoiled = changesToCopy.hasBarnSpoiled;
        this.newlyGrownVegetables = changesToCopy.newlyGrownVegetables;
    }

    /**
     * This method checks whether the barn has been spoiled.
     * 
     * @return true if the barn has been spoiled
     */
    public boolean hasBarnSpoiled() {
        return this.hasBarnSpoiled;
    }

    /**
     * This method returns the number of newly grown vegetables on the farm.
     * 
     * @return The number of newly grown vegetables on the farm
     */
    public int getNumberOfNewlyGrownVegetables() {
        return this.newlyGrownVegetables;
    }

}