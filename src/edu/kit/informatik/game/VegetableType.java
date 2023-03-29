package edu.kit.informatik.game;

/**
 * This enum models the types of vegetable in the game.
 * 
 * @author ulqch
 * @version 1.0
 */
public enum VegetableType {

    /**
     * This vegetable type represents a carrot.
     */
    CARROT(1, "carrot", "carrots", "C"),

    /**
     * This vegetable type represents a salad.
     */
    SALAD(2, "salad", "salads", "S"),

    /**
     * This vegetable type represents a tomato.
     */
    TOMATO(3, "tomato", "tomatoes", "T"),

    /**
     * This vegetable type represents a mushroom.
     */
    MUSHROOM(4, "mushroom", "mushrooms", "M");

    private final int timeToGrow;
    private final String singular;
    private final String plural;
    private final String abbreviation;

    private VegetableType(int timeToGrow, String singular, String plural, String abbreviation) {
        this.timeToGrow = timeToGrow;
        this.singular = singular;
        this.plural = plural;
        this.abbreviation = abbreviation;
    }

    /**
     * This method returns the time for the vegetable to grow.
     * 
     * @return The time for the vegetable to grow
     */
    public int getTimeToGrow() {
        return this.timeToGrow;
    }

    /**
     * This method returns the singular name of this vegetable.
     * 
     * @return Singular name of this vegetable
     */
    public String getSingularName() {
        return this.singular;
    }

    /**
     * This method returns the plural name of this vegetable.
     * 
     * @return Plural name of this vegetable
     */
    public String getPluralName() {
        return this.plural;
    }

    /**
     * This method returns the abbreviation of this vegetable.
     * 
     * @return The abbreviation of this vegetable
     */
    public String getAbbreviation() {
        return this.abbreviation;
    }

    /**
     * This method converts a string to a vegetable type based on the content of the
     * string. If it fails to do so, null gets returned.
     * 
     * @param string String that contains the name of a vegetable type
     * @return Vegetable type that corresponds with the given string or null if its
     *         not possible to read a vegetable type from the string.
     */
    public static VegetableType parse(String string) {
        for (VegetableType vegetable : VegetableType.values()) {
            if (vegetable.getSingularName().equals(string) || vegetable.getPluralName().equals(string)) {
                return vegetable;
            }
        }
        return null;
    }
}
