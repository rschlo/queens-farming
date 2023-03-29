package edu.kit.informatik.game;

import java.util.ArrayList;

/**
 * This class models the result of a game. It contains information
 * about the amount of gold every player obtained and about who has won
 * the game.
 * 
 * @author ulqch
 * @version 1.0
 */
public class GameResult {

    private final Player[] players;
    private final int goldToWin;

    /**
     * This constructor creates a game result from an array of players that
     * participated in the game and the number of gold needed to win the game.
     * 
     * @param players   Array of players that participated in the game
     * @param goldToWin Number of gold needed to win the game
     */
    public GameResult(Player[] players, int goldToWin) {
        this.players = new Player[players.length];
        System.arraycopy(players, 0, this.players, 0, players.length);
        this.goldToWin = goldToWin;
    }

    /**
     * This method returns an array of players that won the game.
     * 
     * @return an array of players that won the game
     */
    public Player[] getWinners() {
        int largestAmountOfGold = 0;
        for (Player player : this.players) {
            if (player.getAmountOfGold() > largestAmountOfGold) {
                largestAmountOfGold = player.getAmountOfGold();
            }
        }
        if (largestAmountOfGold > this.goldToWin) {
            largestAmountOfGold = this.goldToWin;
        }

        ArrayList<Player> richestPlayers = new ArrayList<>();
        for (Player player : this.players) {
            if (player.getAmountOfGold() >= largestAmountOfGold) {
                richestPlayers.add(player);
            }
        }

        return richestPlayers.toArray(new Player[richestPlayers.size()]);
    }

    /**
     * This method returns an array of the players that participated in the game.
     * 
     * @return an array of players that participated in the game
     */
    public Player[] getPlayers() {
        return this.players;
    }

}