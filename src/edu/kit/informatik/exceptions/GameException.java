package edu.kit.informatik.exceptions;

/**
 * This class represents a game exception that gets thrown if something went
 * wrong.
 * 
 * @author ulqch
 * @version 1.0
 */
public abstract class GameException extends Exception {

    private static final String ERROR_PREFIX = "Error: ";

    /**
     * This constructor creates a new GameException. The message that gets
     * passed as an argument to this constructor should contain exact information
     * about why this exepction was thrown.
     * 
     * @param message message containing information about why this exception was
     *                thrown
     */
    public GameException(String message) {
        super(ERROR_PREFIX + message);
    }

}
