package edu.kit.informatik.exceptions;

/**
 * This class represents an exception that gets thrown if a game rule
 * was broken.
 * 
 * @author ulqch
 * @version 1.0
 */
public class IllegalMoveException extends GameException {

    /**
     * This constructor creates a new IllegalMoveException. The message that gets
     * passed as an argument to this constructor should contain exact information
     * about why this exepction was thrown.
     * 
     * @param message message containing information about why this exception was
     *                thrown
     */
    public IllegalMoveException(String message) {
        super(message);
    }
}
