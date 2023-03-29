package edu.kit.informatik.io.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.kit.informatik.game.Player;

/**
 * This class models a command a user can execute. This command does
 * actions on a player.
 * 
 * @author ulqch
 * @version 1.0
 */
public abstract class Command {

    private final Pattern regEx;

    /**
     * This contructor creates a new Command instance. It takes a regEx that defines
     * what user input should execute this command.
     * 
     * @param regEx RegEx that defines what user input should execute this command
     */
    public Command(String regEx) {
        this.regEx = Pattern.compile(regEx);
    }

    /**
     * This method returns the regEx pattern of this command.
     * 
     * @return The regEx pattern of this command
     */
    public Pattern getRegExPattern() {
        return this.regEx;
    }

    /**
     * This method takes structured user arguments (Matcher) and a player object. It
     * executes the purpose of this command by calling methods of the player. Then
     * it returns a string result of the command.
     * 
     * @param input  Structured user input (regEx Matcher)
     * @param player Player whose turn it is
     * @return A String result of the command
     */
    public abstract String execute(Matcher input, Player player);

}
