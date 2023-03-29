package edu.kit.informatik.game;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import edu.kit.informatik.exceptions.IllegalMoveException;
import edu.kit.informatik.exceptions.NoMovesLeftException;

import static java.util.Map.entry;

/**
 * This class models a player of the Queens Farming game.
 * 
 * @author ulqch
 * @version 1.0
 */
public class Player {

    /**
     * This is a Map of positions and acreage types that every player initially
     * has on their farm.
     */
    public static final Map<Position, AcreageType> INITIAL_ACREAGE_TYPES = Map.ofEntries(
            entry(new Position(-1, 0), AcreageType.GARDEN), entry(new Position(1, 0), AcreageType.GARDEN),
            entry(new Position(0, 1), AcreageType.FIELD));

    private static final String ERROR_NO_MOVES_LEFT = "Player does not have any moves left";
    private static final Changes INITAL_FARM_CHANGES = new Changes(false, 0);

    private final String name;
    private final Farm farm;
    private final VegetableMarket vegetableMarket;
    private final AcreageMarket acreageMarket;

    private int moves;

    private Changes farmChanges;

    private boolean hasQuit = false;

    /**
     * This constructor creates a Player. It takes the players name, the amount of
     * gold the player initially has, a reference to the common vegetable market and
     * a reference to the common acreage market.
     * 
     * @param name            The name of the player
     * @param gold            The amount of gold the player has initially
     * @param vegetableMarket A reference to the common vegetable market
     * @param acreageMarket   A reference to the common acreage market
     */
    protected Player(String name, int gold, VegetableMarket vegetableMarket, AcreageMarket acreageMarket) {
        this.name = name;
        this.vegetableMarket = vegetableMarket;
        this.acreageMarket = acreageMarket;
        this.farmChanges = new Changes(INITAL_FARM_CHANGES);

        // set up the farm
        Barn barn = new Barn(gold);
        for (VegetableType vegetable : VegetableType.values()) {
            barn.store(vegetable, 1);
        }
        Map<Position, Acreage> initialAcreages = new HashMap<>();
        for (Map.Entry<Position, AcreageType> entry : INITIAL_ACREAGE_TYPES.entrySet()) {
            initialAcreages.put(entry.getKey(), new Acreage(entry.getValue()));
        }
        this.farm = new Farm(barn, initialAcreages);
    }

    /**
     * This method returns the amount of gold the player has in their barn.
     * 
     * @return The amount of gold the player has in their barn
     */
    public int getAmountOfGold() {
        return this.farm.getBarn().getAmountOfGold();
    }

    /**
     * This method updates the players farm and adapts the prices of the common
     * vegetable market
     */
    protected void update() {
        this.farmChanges = this.farm.update();
        this.vegetableMarket.adapt();
    }

    /**
     * This method sets the number of moves this player has left before their turn
     * ends.
     * 
     * @param moves Number of moves this player has left
     */
    protected void allowMoves(int moves) {
        this.moves = moves;
    }

    /**
     * This method checks whether this player has moves left.
     * 
     * @return true if this player has moves left
     */
    public boolean hasMovesLeft() {
        return this.moves > 0;
    }

    /**
     * This method ends this players turn by setting the number of moves this player
     * has left to zero.
     */
    public void endTurn() {
        this.moves = 0;
    }

    /**
     * This method plants a vegetable at a specified position on the farm.
     * 
     * @param position  Position of the acreage where the vegetable should be
     *                  planted
     * @param vegetable Vegetable type that should be planted
     * @throws IllegalMoveException If the acreage at the given position does not
     *                              exist or if the acreage is not suitable for this
     *                              type of vegetable
     */
    public void plant(Position position, VegetableType vegetable) throws IllegalMoveException {
        if (this.moves <= 0) {
            throw new NoMovesLeftException(ERROR_NO_MOVES_LEFT);
        }
        this.farm.plantOnAcreage(position, vegetable);
        this.moves--;
    }

    /**
     * This method harvests a specified amount of vegetables from an acreage at a
     * given position on the farm.
     * 
     * @param position Position of the acreage that should be harvested from
     * @param amount   Amount of vegetables that should be harvested
     * @return A key-value pair containing the type and the amount of the harvested
     *         vegetables
     * @throws IllegalMoveException if the position was not available, the specified
     *                              amount was illegal or if there are not enough
     *                              vegetables to harvest
     */
    public Pair<VegetableType, Integer> harvest(Position position, int amount) throws IllegalMoveException {
        if (this.moves <= 0) {
            throw new NoMovesLeftException(ERROR_NO_MOVES_LEFT);
        }
        Pair<VegetableType, Integer> container = this.farm.harvestAcreage(position, amount);
        this.moves--;
        return container;
    }

    /**
     * This method sells all vegetables in the barn at the common market. It
     * returns a list of key-value pairs. Each pair contains the vegetable type and
     * the price for which it was sold. A vegetable type may occur multiple times in
     * such a list.
     * 
     * @return A list of key-value pairs. Each pair contains the vegetable type and
     *         the price for which it was sold. A vegetable type may occur multiple
     *         times in such a list.
     * @throws IllegalMoveException if a game rule was broken during this action
     */
    public List<Pair<VegetableType, Integer>> sellAll() throws IllegalMoveException {
        if (this.moves <= 0) {
            throw new NoMovesLeftException(ERROR_NO_MOVES_LEFT);
        }
        List<Pair<VegetableType, Integer>> listOfSales = this.farm.sellAllAtMarket(this.vegetableMarket);
        this.moves--;
        return listOfSales;
    }

    /**
     * This method sells specified amounts of vegetables at the common market. It
     * returns a list of key-value pairs. Each pair contains the vegetable type and
     * the price for which it was sold. A vegetable type may occur multiple times in
     * such a list.
     * 
     * @param vegetables Types and amounts of the vegetables that should be sold
     * @return A list of key-value pairs. Each pair contains the vegetable type and
     *         the price for which it was sold. A vegetable type may occur multiple
     *         times in such a list.
     * @throws IllegalMoveException if there were not enough vegetables in the barn
     */
    public List<Pair<VegetableType, Integer>> sell(Map<VegetableType, Integer> vegetables) throws IllegalMoveException {
        if (this.moves <= 0) {
            throw new NoMovesLeftException(ERROR_NO_MOVES_LEFT);
        }
        List<Pair<VegetableType, Integer>> listOfSales = this.farm.sellAtMarket(vegetables, this.vegetableMarket);
        this.moves--;
        return listOfSales;
    }

    /**
     * This method buys a vegetable at the common market. It returns a key-value
     * pair that
     * contains the vegetable type that got bought and the price of it.
     * 
     * @param vegetable The vegetable that should be bought
     * @return A key-value pair that contains the vegetable type that got bought and
     *         the
     *         price of it.
     * @throws IllegalMoveException if there is not enough gold in the barn
     */
    public Pair<VegetableType, Integer> buy(VegetableType vegetable) throws IllegalMoveException {
        if (this.moves <= 0) {
            throw new NoMovesLeftException(ERROR_NO_MOVES_LEFT);
        }

        Pair<VegetableType, Integer> receipt = this.farm.buyAtMarket(vegetable, this.vegetableMarket);
        this.moves--;
        return receipt;
    }

    /**
     * This method buys an acreage at the common acreage market at a specified
     * position.
     * 
     * @param position The position where the new acreage should be
     * @return A key-value pair containing the type and the price of the acreage
     *         that just got bought
     * @throws IllegalMoveException if the position is not available, the acreage
     *                              market has no acreages left or the player does
     *                              not have enough gold in the barn
     */
    public Pair<AcreageType, Integer> buy(Position position) throws IllegalMoveException {
        if (this.moves <= 0) {
            throw new NoMovesLeftException(ERROR_NO_MOVES_LEFT);
        }
        Pair<AcreageType, Integer> receipt = this.farm.buyAcreage(position, acreageMarket);

        this.moves--;
        return receipt;
    }

    /**
     * This method returns a copy of the common vegetable market.
     * 
     * @return A copy of the common vegetable market.
     */
    public VegetableMarket getCommonVegetableMarket() {
        return new VegetableMarket(this.vegetableMarket);
    }

    /**
     * This method returns the name of the player.
     * 
     * @return The name of the player
     */
    public String getName() {
        return this.name;
    }

    /**
     * This method returns the farm changes since last turn.
     * 
     * @return Changes since last turn
     */
    public Changes getFarmChanges() {
        return this.farmChanges;
    }

    /**
     * This method returns the barn of the players farm.
     * 
     * @return The barn of the players farm
     */
    public Barn getBarn() {
        return this.farm.getBarn();
    }

    /**
     * This method returns the players farm.
     * 
     * @return The players farm
     */
    public Farm getFarm() {
        return new Farm(this.farm);
    }

    /**
     * This method quits the game. It ends the turn of this player by setting the
     * number of moves left in the turn to zero. It also prevents the game loop to
     * start another round.
     */
    public void quitGame() {
        this.endTurn();
        this.hasQuit = true;
    }

    /**
     * This method checks whether this player has quit the game.
     * 
     * @return true if this player has quit the game
     */
    public boolean hasQuit() {
        return this.hasQuit;
    }

}