package edu.kit.informatik.game;

import java.util.Map;
import java.util.Set;

import static java.util.Map.entry;

import java.util.Arrays;
import java.util.HashSet;

/**
 * This class models a game master that knows about the participating
 * players and the amount of gold that is needed to win. This class
 * controls the game loop.
 * 
 * @author ulqch
 * @version 1.0
 */
public class GameMaster {
    
    private static final Map<VegetableType, int[]> MARKET_PRICES = Map.ofEntries(
        entry(VegetableType.MUSHROOM, new int[] {12, 15, 16, 17, 20}),
        entry(VegetableType.CARROT, new int[] {3, 2, 2, 2, 1}),
        entry(VegetableType.TOMATO, new int[] {3, 5, 6, 7, 9}),
        entry(VegetableType.SALAD, new int[] {6, 5, 4, 3, 2}));

    private static final Set<Pair<VegetableType, VegetableType>> MARKET_PAIRS 
        = new HashSet<Pair<VegetableType, VegetableType>>(Arrays.asList(
                new Pair<VegetableType, VegetableType>(VegetableType.CARROT, VegetableType.MUSHROOM),
                new Pair<VegetableType, VegetableType>(VegetableType.SALAD, VegetableType.TOMATO)));

    private static final int MOVES_PER_PLAYER = 2;

    private final int goldToWin;
    private final Player[] players;

    /**
     * This constructor creates a new GameMaster object. It takes a seed which is
     * used to randomize the types of the acreages the players can buy, the starting
     * amount of gold, the amount of gold that is needed to win the game and the
     * names of the players that want to participate in this game.
     * 
     * @param seed       The seed which is used to randomize the types of acreages
     *                   the players can buy
     * @param goldToWin  The amount of gold needed to win the game
     * @param initalGold The amount of gold every player gets when the game starts
     * @param names      The names of the players that want to participate in this
     *                   game
     */
    public GameMaster(int seed, int goldToWin, int initalGold, String... names) {
        this.goldToWin = goldToWin;

        VegetableMarket market = new VegetableMarket(MARKET_PRICES, MARKET_PAIRS);
        AcreageMarket acreageManager = new AcreageMarket(seed, names.length);

        this.players = new Player[names.length];
        for (int i = 0; i < names.length; i++) {
            this.players[i] = new Player(names[i], initalGold, market, acreageManager);
        }
    }

    private boolean hasSomeoneWon() {
        for (Player player : this.players) {
            if (player.getAmountOfGold() >= this.goldToWin) {
                return true;
            }
        }
        return false;
    }

    /**
     * This method starts the game loop. For every players turn the player
     * controller is called for it to do the moves for the current player. If
     * someone has one or someone has quit the loop breaks and a GameResult gets
     * returned containing information about the result of this game.
     * 
     * @param controller The player controller that does the moves for every player
     * @return The result of this game
     */
    public GameResult play(PlayerController controller) {
        boolean shouldEnd = false;
        do {
            // Round begins...
            for (Player player : this.players) {
                player.allowMoves(MOVES_PER_PLAYER);

                // Let the controller do the moves
                controller.doTurn(player);

                if (player.hasQuit()) {
                    shouldEnd = true;
                    break;
                }

                player.update();
            }
        } while (!this.hasSomeoneWon() && !shouldEnd);

        return new GameResult(this.players, this.goldToWin);
    }

}
