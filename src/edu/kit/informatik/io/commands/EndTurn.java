package edu.kit.informatik.io.commands;

import java.util.regex.Matcher;

import edu.kit.informatik.game.Player;

/**
 * This class models a command that ends the turn of the current
 * player.
 * 
 * @author ulqch
 * @version 1.0
 */
public class EndTurn extends Command {

    private static final String REGEX = "end turn";

    /**
     * This constructor creates a new EndTurn command.
     */
    public EndTurn() {
        super(REGEX);
    }

    @Override
    public String execute(Matcher input, Player player) {
        player.endTurn();
        return null;
    }

}
