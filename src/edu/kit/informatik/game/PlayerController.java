package edu.kit.informatik.game;

/**
 * This interface models a controller of the players. It controls what
 * the current player should do during their turn.
 * 
 * @author ulqch
 * @version 1.0
 */
public interface PlayerController {

    /**
     * This method gets called with the current player as the parameter and calls
     * methods of the given player to perform game moves.
     * 
     * @param player The current player
     */
    void doTurn(Player player);

}
