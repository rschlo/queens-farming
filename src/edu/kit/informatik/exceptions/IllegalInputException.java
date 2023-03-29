package edu.kit.informatik.exceptions;

/**
 * This class represents an exception that gets thrown if some user
 * input could not be properly interpreted by the game.
 * 
 * @author ulqch
 * @version 1.0
 */
public class IllegalInputException extends GameException {

    /**
     * This constructor creates a new IllegalInputException. The message that gets
     * passed as an argument to this constructor should contain exact information
     * about why this exepction was thrown.
     * 
     * @param message message containing information about why this exception was
     *                thrown
     */
    public IllegalInputException(String message) {
        super(message);
    }

}
