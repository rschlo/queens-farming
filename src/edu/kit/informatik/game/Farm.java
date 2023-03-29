package edu.kit.informatik.game;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import edu.kit.informatik.exceptions.IllegalMoveException;

/**
 * This class models a farm that has a barn and some acreages on which
 * vegetables can be grown.
 * 
 * @author ulqch
 * @version 1.0
 */
public class Farm {

    /**
     * This is the position of the barn on this farm.
     */
    public static final Position BARN_POSITION = new Position(0, 0);

    private static final String ERROR_POSITION_NOT_FOUND = "There is no acreage at the specified position";
    private static final String ERROR_NOT_PURCHASABLE = "Acreage is not purchasable";
    private static final String ERROR_NOT_ENOUGH_VEGETABLES_TO_PLANT = "You do not have enough vegetables to plant";
    private static final String ERROR_NOT_ENOUGH_GOLD = "You do not have enough gold in your barn";
    private static final String ERROR_NO_ACREAGES_LEFT = "There are no acreages left in this game";

    private static final int AMOUNT_NEEDED_TO_PLANT_VEGETABLE = 1;

    private final Map<Position, Acreage> acreages;
    private final Barn barn;

    /**
     * This constructor creates a new farm with a given barn and a given set of
     * acreages.
     * 
     * @param barn     barn on this farm
     * @param acreages inital acreages on this farm
     */
    public Farm(Barn barn, Map<Position, Acreage> acreages) {
        this.barn = new Barn(barn);
        this.acreages = new HashMap<>(acreages);
    }

    /**
     * This constructor creates a new farm by copying the data from another farm
     * object.
     * 
     * @param farmToCopy farm to copy
     */
    public Farm(Farm farmToCopy) {
        this.barn = farmToCopy.getBarn();
        this.acreages = new HashMap<>(farmToCopy.acreages);
    }

    /**
     * This method sells all vegetables in the barn at the common market. It
     * returns a list of key-value pairs. Each pair contains the vegetable type and
     * the price for which it was sold. A vegetable type may occur multiple times in
     * such a list.
     * 
     * @param market The common market at which the vegetables should be sold
     * @return A list of key-value pairs. Each pair contains the vegetable type and
     *         the price for which it was sold. A vegetable type may occur in the
     *         list multiple times. 
     * @throws IllegalMoveException if a game rule was broken during this action.
     */
    public List<Pair<VegetableType, Integer>> sellAllAtMarket(VegetableMarket market) throws IllegalMoveException {
        return this.sellAtMarket(this.barn.getStock(), market);
    }

    /**
     * This method sells specified amounts of vegetables at the common market. It
     * returns a list of key-value pairs. Each pair contains the vegetable type and
     * the price for which it was sold. A vegetable type may occur multiple times in
     * such a list.
     * 
     * @param vegetables Types and amounts of the vegetables that should be sold
     * @param market     The common market at which the vegetables should be sold
     * @return A list of key-value pairs. Each pair contains the vegetable type and
     *         the price for which it was sold. A vegetable type may occur in the
     *         list multiple times.
     * @throws IllegalMoveException if there were not enough vegetables in the barn
     */
    public List<Pair<VegetableType, Integer>> sellAtMarket(Map<VegetableType, Integer> vegetables,
            VegetableMarket market)
            throws IllegalMoveException {
        return this.barn.sellAtMarket(vegetables, market);
    }

    /**
     * This method buys a vegetable at the common market. It returns a key-value
     * pair that
     * contains the vegetable type that got bought and the price of it.
     * 
     * @param vegetable The vegetable that should be bought
     * @param market    The common market at which the vegetable should be bought.
     * @return A key-value pair that contains the vegetable type that got bought and
     *         the
     *         price of it.
     * @throws IllegalMoveException if there is not enough gold in the barn
     */
    public Pair<VegetableType, Integer> buyAtMarket(VegetableType vegetable, VegetableMarket market)
            throws IllegalMoveException {
        return this.barn.buyAtMarket(vegetable, market);
    }

    /**
     * This method buys an acreage at the common acreage market at a specified
     * position.
     * 
     * @param position      The position where the new acreage should be
     * @param acreageMarket The common acreage market
     * @return A key-value pair containing the type and the price of the acreage
     *         that just
     *         got bought
     * @throws IllegalMoveException if the position is not available, the acreage
     *                              market has no acreages left or the player does
     *                              not have enough gold in the barn
     */
    public Pair<AcreageType, Integer> buyAcreage(Position position, AcreageMarket acreageMarket)
            throws IllegalMoveException {
        if (!this.isPurchasable(position)) {
            throw new IllegalMoveException(ERROR_NOT_PURCHASABLE);
        }
        if (!acreageMarket.hasAcreagesLeft()) {
            throw new IllegalMoveException(ERROR_NO_ACREAGES_LEFT);
        }

        int gold = this.barn.getAmountOfGold();
        int price = acreageMarket.calculatePrice(position);
        if (gold < price) {
            throw new IllegalMoveException(ERROR_NOT_ENOUGH_GOLD);
        }

        this.barn.setAmountOfGold(gold - price);
        Acreage acreage = acreageMarket.getNewAcreage();
        this.acreages.put(position, acreage);
        return new Pair<AcreageType, Integer>(acreage.getType(), price);
    }

    /**
     * This method plants a vegetable on an acreage at a specified position.
     * 
     * @param position  Position of the acreage
     * @param vegetable The type of vegetable that should be planted
     * @throws IllegalMoveException if the position is not available, there are not
     *                              enough vegetables in the barn, the acreage is
     *                              occupied, or the acreage is not suitable for the
     *                              specified vegetable
     */
    public void plantOnAcreage(Position position, VegetableType vegetable)
            throws IllegalMoveException {
        if (!this.acreages.containsKey(position)) {
            throw new IllegalMoveException(ERROR_POSITION_NOT_FOUND);
        }
        if (this.barn.getAmountOf(vegetable) < AMOUNT_NEEDED_TO_PLANT_VEGETABLE) {
            throw new IllegalMoveException(ERROR_NOT_ENOUGH_VEGETABLES_TO_PLANT);
        }
        Acreage acreage = this.acreages.get(position);
        acreage.plant(vegetable);
        this.barn.remove(vegetable, AMOUNT_NEEDED_TO_PLANT_VEGETABLE);
    }

    /**
     * This method harvests a specified amount of vegetables from an acreage a given
     * position.
     * 
     * @param position Position of the acreage that should be harvested from
     * @param amount   Amount of vegetables that should be harvested
     * @return A key-value pair containing the type and the amount of the harvested
     *         vegetables
     * @throws IllegalMoveException if the position was not available, the specified
     *                              amount was illegal or if there are not enough
     *                              vegetables to harvest
     */
    public Pair<VegetableType, Integer> harvestAcreage(Position position, int amount) throws IllegalMoveException {
        if (!this.acreages.containsKey(position)) {
            throw new IllegalMoveException(ERROR_POSITION_NOT_FOUND);
        }

        Acreage acreage = this.acreages.get(position);
        Pair<VegetableType, Integer> container = acreage.harvest(amount);
        this.barn.store(container.getKey(), container.getValue());
        return container;
    }

    /**
     * This method updates this farm. It lets the vegetables on the acreages grow
     * and decreases the time in which the barn is going to spoil.
     * 
     * @return A changes object containing information about the things that got
     *         updated.
     */
    public Changes update() {
        boolean hadBarnCountdown = this.barn.hasCountdown();
        this.barn.update();
        boolean barnSpoiled = hadBarnCountdown && !this.barn.hasCountdown();

        int totalVegetablesLastTurn = this.getAmountOfVegetables();
        for (Acreage acreage : this.acreages.values()) {
            acreage.update();
        }
        int newlyGrownVegetables = this.getAmountOfVegetables() - totalVegetablesLastTurn;
        return new Changes(barnSpoiled, newlyGrownVegetables);
    }

    /**
     * This method returns the total amount of vegetables on this farm.
     * 
     * @return The total amount of vegetables on this farm.
     */
    private int getAmountOfVegetables() {
        int amount = 0;
        for (Acreage acreage : this.acreages.values()) {
            amount += acreage.getAmountOfVegetables();
        }
        return amount;
    }

    /**
     * This method checks whether an acreage at a specified position is purchasable.
     * 
     * @param position position of a possible acreage
     * @return true if an acreage at a specified position is purchasable
     */
    public boolean isPurchasable(Position position) {
        if (this.acreages.containsKey(position)) {
            return false;
        }
        if (position.getY() < 0 || position.equals(BARN_POSITION)) {
            return false;
        }

        Position[] neighborsOfPosition = new Position[] {
            new Position(position.getX(), position.getY() - 1),
            new Position(position.getX() - 1, position.getY()),
            new Position(position.getX() + 1, position.getY())
        };

        for (Position neighbor : neighborsOfPosition) {
            if (this.acreages.containsKey(neighbor)) {
                return true;
            }
        }
        return false;
    }

    /**
     * This method checks whether there is a tile at a specified position.
     * 
     * @param position Position to be checked
     * @return true if there is a tile at the specified position
     */
    public boolean isThereATile(Position position) {
        return position.equals(BARN_POSITION) || this.acreages.containsKey(position);
    }

    /**
     * This method returns the acreage at a specified position.
     * 
     * @param position Position of acreage
     * @return The acreage at a specified position
     * @throws IllegalArgumentException if there is no acreage at the specified
     *                                  position
     */
    public Acreage getAcreage(Position position) {
        if (!acreages.containsKey(position)) {
            throw new IllegalArgumentException(ERROR_POSITION_NOT_FOUND);
        }
        Acreage acreage = this.acreages.get(position);
        return new Acreage(acreage);
    }

    /**
     * This method returns the position of the barn.
     * 
     * @return The position of the barn
     */
    public Position getBarnPosition() {
        return BARN_POSITION;
    }

    /**
     * This method returns the position of the south-west corner of the farm (the
     * position with the lowest x coordinate and lowest y coordinate).
     * 
     * @return The position of the south-west corner of the farm
     */
    public Position getSouthWestCorner() {
        int heighestX = 0;
        int lowestY = 0;
        for (Position position : this.acreages.keySet()) {
            if (position.getX() > heighestX) {
                heighestX = position.getX();
            }
            if (position.getY() < lowestY) {
                lowestY = position.getY();
            }
        }
        return new Position(heighestX, lowestY);
    }

    /**
     * This method returns the position of the north-east corner of the farm (the
     * position with the highest x coordinate and highest y coordinate).
     * 
     * @return The position of the north-east corner of the farm
     */
    public Position getNorthEastCorner() {
        int lowestX = 0;
        int heighestY = 0;
        for (Position position : this.acreages.keySet()) {
            if (position.getX() < lowestX) {
                lowestX = position.getX();
            }
            if (position.getY() > heighestY) {
                heighestY = position.getY();
            }
        }
        return new Position(lowestX, heighestY);
    }

    /**
     * This method returns a copy of the barn.
     * 
     * @return A copy of the barn
     */
    public Barn getBarn() {
        return new Barn(this.barn);
    }

}
