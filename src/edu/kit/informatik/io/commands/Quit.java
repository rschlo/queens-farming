package edu.kit.informatik.io.commands;

import java.util.regex.Matcher;

import edu.kit.informatik.game.Player;

/**
 * This class models a command that quits the game.
 * 
 * @author ulqch
 * @version 1.0
 */
public class Quit extends Command {

    private static final String REGEX = "quit";

    /**
     * This constructor creates a new Quit command.
     */
    public Quit() {
        super(REGEX);
    }

    @Override
    public String execute(Matcher input, Player player) {
        player.quitGame();
        return null;
    }

}
