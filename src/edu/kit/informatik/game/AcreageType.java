package edu.kit.informatik.game;

/**
 * This enum represents types of acreages.
 * 
 * @author ulqch
 * @version 1.0
 */
public enum AcreageType {

    /**
     * This acreage type represents a garden.
     */
    GARDEN("Garden", "G", 4, 2, VegetableType.values()),

    /**
     * This acreage type represents a field.
     */
    FIELD("Field", "Fi", 4, 4, new VegetableType[] {VegetableType.CARROT, VegetableType.SALAD, VegetableType.TOMATO}),

    /**
     * This acreage type represents a large field.
     */
    LARGE_FIELD("Large Field", "LFi", 2, 8,
            new VegetableType[] {VegetableType.CARROT, VegetableType.SALAD, VegetableType.TOMATO}),

    /**
     * This acreage type represents a forest.
     */
    FOREST("Forest", "Fo", 2, 4, new VegetableType[] {VegetableType.CARROT, VegetableType.MUSHROOM}),

    /**
     * This acreage type represents a large forest.
     */
    LARGE_FOREST("Large Forest", "LFo", 1, 8, new VegetableType[] {VegetableType.CARROT, VegetableType.MUSHROOM});

    private final VegetableType[] possibleVegetables;
    private final int capacity;
    private final int numberPerPlayer;
    private final String name;
    private final String abbreviation;

    private AcreageType(String name, String abbreviation, int numberPerPlayer, int capacity,
            VegetableType[] possibleVegetables) {
        this.name = name;
        this.abbreviation = abbreviation;
        this.numberPerPlayer = numberPerPlayer;
        this.capacity = capacity;
        this.possibleVegetables = possibleVegetables;
    }

    /**
     * This method returns an array of vegetable types that are able to grow on this type
     * of acreage.
     * 
     * @return An array of vegetables that are able to grow on this type of acreage
     */
    public VegetableType[] getPossibleVegetables() {
        return this.possibleVegetables;
    }

    /**
     * This method returns the capacity of this type of acreage.
     * 
     * @return Capacity of this type of acreage
     */
    public int getCapacity() {
        return this.capacity;
    }

    /**
     * This method returns the number of acreages that exist is the game per player.
     * 
     * @return Number of acreages of this type that exist is the game per player
     */
    public int getNumberPerPlayer() {
        return this.numberPerPlayer;
    }

    /**
     * This method returns the name of this acreage type
     * 
     * @return Name of this acreage type
     */
    public String getName() {
        return this.name;
    }

    /**
     * This method returns the abbreviation of the name of this type of acreage.
     * 
     * @return Abbreviation of the name of this type of acreage
     */
    public String getAbbreviation() {
        return this.abbreviation;
    }
}
