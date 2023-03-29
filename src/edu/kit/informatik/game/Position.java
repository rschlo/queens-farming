package edu.kit.informatik.game;

import java.util.Objects;

/**
 * This class represents a two-dimensional position.
 * 
 * @author ulqch
 * @version 1.0
 */
public class Position {

    private final int x;
    private final int y;

    /**
     * This constructor creates a Position from a x and y coordinate
     * 
     * @param x x-coordinate
     * @param y y-coordinate
     */
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * This method returns the x coordinate of this position.
     * 
     * @return The x coordinate of this position
     */
    public int getX() {
        return this.x;
    }

    /**
     * This method return the y coordinate of this position.
     * 
     * @return The y coordinate of this position
     */
    public int getY() {
        return this.y;
    }

    /**
     * This method calculates the manhatten distance to another position.
     * 
     * @param position Another position
     * @return The manhatten distance to the given position
     */
    public int getManhattenDistanceTo(Position position) {
        int deltaX = Math.abs(position.getX() - this.getX());
        int deltaY = Math.abs(position.getY() - this.getY());
        return deltaX + deltaY;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Position otherPosition))
            return false;
        return otherPosition.x == this.x && otherPosition.y == this.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.x, this.y);
    }

}
