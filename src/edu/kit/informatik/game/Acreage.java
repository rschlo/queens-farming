package edu.kit.informatik.game;

import edu.kit.informatik.exceptions.IllegalMoveException;

/**
 * This class represents an acreage.
 * 
 * @author ulqch
 * @version 1.0
 */
public class Acreage extends Tile {

    private static final String ERROR_VEGETABLE_NOT_SUITABLE = "You cannot grow this vegetable on this acreage";
    private static final String ERROR_ACREAGE_OCCUPIED = "Something is already being grown on this field";
    private static final String ERROR_NOT_ENOUGH_VEGETABLES = "There are not enough vegetables on this acreage";
    private static final String ERROR_ILLEGAL_HARVEST_AMOUNT = "Cannot harvest an amount smaller or equal to zero";

    private static final int GROW_FACTOR = 2;

    private final AcreageType type;
    private VegetableType vegetableType;
    private int amountOfVegetables;

    /**
     * This constructor creates a new acreage of a specified acreage type.
     * 
     * @param type The type of the acreage
     */
    public Acreage(AcreageType type) {
        this.type = type;
        this.vegetableType = null;
        this.amountOfVegetables = 0;
    }

    /**
     * This constructor creates a new acreage by copying an existing one.
     * 
     * @param acreageToCopy Acreage to copy
     */
    public Acreage(Acreage acreageToCopy) {
        super(acreageToCopy);
        this.type = acreageToCopy.getType();
        this.vegetableType = acreageToCopy.getVegetableType();
        this.amountOfVegetables = acreageToCopy.getAmountOfVegetables();
    }

    /**
     * This method checks whether there are vegetables on this acreage.
     * 
     * @return true if this acreage is empty
     */
    public boolean isEmpty() {
        return this.vegetableType == null;
    }

    /**
     * This method returns the type of this acreage.
     * 
     * @return The type of this acreage
     */
    public AcreageType getType() {
        return this.type;
    }

    /**
     * This method returns the type of vegetable that grows on this acreage. If this
     * acreage is empty null gets returned.
     * 
     * @return The type of vegetable that grows on this acreage. If it's empty null
     *         gets returned.
     */
    public VegetableType getVegetableType() {
        return this.vegetableType;
    }

    private boolean isSuitableFor(VegetableType vegetable) {
        for (VegetableType possibleVegetable : this.type.getPossibleVegetables()) {
            if (vegetable == possibleVegetable) {
                return true;
            }
        }
        return false;
    }

    /**
     * This method plants a vegetable on an acreage at a specified position.
     * 
     * @param vegetable The type of vegetable that should be planted
     * @throws IllegalMoveException the acreage is occupied or the acreage is not
     *                              suitable for the specified vegetable
     */
    public void plant(VegetableType vegetable) throws IllegalMoveException {
        if (this.vegetableType != null) {
            throw new IllegalMoveException(ERROR_ACREAGE_OCCUPIED);
        }
        if (!this.isSuitableFor(vegetable)) {
            throw new IllegalMoveException(ERROR_VEGETABLE_NOT_SUITABLE);
        }
        this.vegetableType = vegetable;
        this.amountOfVegetables = 1;
        this.setCountdown(vegetable.getTimeToGrow());
    }

    /**
     * This method harvests a specified amount of vegetables from an acreage a given
     * position.
     * 
     * @param amountToHarvest Amount of vegetables that should be harvested
     * @return A key-value pair containing the type and the amount of the harvested
     *         vegetables
     * @throws IllegalMoveException the specified amount was illegal or if there are
     *                              not enough vegetables to harvest
     */
    public Pair<VegetableType, Integer> harvest(int amountToHarvest) throws IllegalMoveException {
        if (amountToHarvest <= 0) {
            throw new IllegalMoveException(ERROR_ILLEGAL_HARVEST_AMOUNT);
        }
        if (amountToHarvest > this.amountOfVegetables || this.isEmpty()) {
            throw new IllegalMoveException(ERROR_NOT_ENOUGH_VEGETABLES);
        }
        Pair<VegetableType, Integer> container = new Pair<>(this.vegetableType, amountToHarvest);
        this.amountOfVegetables -= amountToHarvest;

        if (this.amountOfVegetables == 0) {
            this.vegetableType = null;
            this.removeCountdown();
        } else if (!this.hasCountdown()) {
            this.setCountdown(this.vegetableType.getTimeToGrow());
        }
        return container;
    }

    /**
     * This method returns the amount of vegetables on this acreage.
     * 
     * @return The amount of vegetables on this acreage
     */
    public int getAmountOfVegetables() {
        return this.amountOfVegetables;
    }

    /**
     * This method returns the capacity of this acreage.
     * 
     * @return The capacity of this acreage
     */
    public int getCapacity() {
        return this.type.getCapacity();
    }

    /**
     * This method gets executed when the countdown is over (depends on the
     * vegetable type currently planted on this acreage). Then the vegetables on
     * this acreage grow (the number of vegetables gets multiplied by a grow
     * factor).
     */
    @Override
    public void onCountdown() {
        this.amountOfVegetables *= GROW_FACTOR;
        if (this.amountOfVegetables >= this.type.getCapacity()) {
            this.amountOfVegetables = this.type.getCapacity();
        } else {
            this.setCountdown(this.vegetableType.getTimeToGrow());
        }
    }

}
