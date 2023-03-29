package edu.kit.informatik.exceptions;

/**
 * This class represents an exception that is thrown when a player's
 * method is called but it is not the player's turn.
 * 
 * @author ulqch
 * @version 1.0
 */
public class NoMovesLeftException extends RuntimeException {

    /**
     * This constructor creates a new NoMovesLeftException. The message that gets
     * passed as an argument to this constructor should contain exact information
     * about why this exepction was thrown.
     * 
     * @param message message containing information about why this exception was
     *                thrown
     */
    public NoMovesLeftException(String message) {
        super(message);
    }
}