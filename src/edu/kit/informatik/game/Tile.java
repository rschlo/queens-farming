package edu.kit.informatik.game;

/**
 * This class models a tile (barn or acreage). Every tile has a
 * countdown that gets decreased when this tile gets updated.
 * 
 * @author ulqch
 * @version 1.0
 */
public abstract class Tile {

    private int countdown = -1;

    /**
     * This constructor makes a copy of another tile
     * 
     * @param tileToCopy tile to copy
     */
    public Tile(Tile tileToCopy) {
        this.countdown = tileToCopy.countdown;
    }

    /**
     * This constructor creates tile
     */
    public Tile() {

    }

    /**
     * This method decreases the countdown. If the coundown is over the method
     * onCountdown() gets called
     */
    public void update() {
        if (countdown == -1)
            return;
        this.countdown -= 1;
        if (countdown == 0) {
            this.countdown = -1;
            this.onCountdown();
        }
    }

    /**
     * This method sets the countdown to a specified value. The method update() then
     * must be called this specified amount until onCountdown() gets called.
     * 
     * @param time Time until the contdown is over
     */
    public void setCountdown(int time) {
        this.countdown = time;
    }

    /**
     * This method removes the current countdown.
     */
    public void removeCountdown() {
        this.countdown = -1;
    }

    /**
     * This method returns whether this tile has a countdown.
     * 
     * @return True if this tile has a countdown
     */
    public boolean hasCountdown() {
        return this.countdown != -1;
    }

    /**
     * This method returns the current value of the countdown.
     * 
     * @return The current value of the countdown
     */
    public int getCountdown() {
        return this.countdown;
    }

    /**
     * This method gets called when the countdown ist over.
     */
    public abstract void onCountdown();
}