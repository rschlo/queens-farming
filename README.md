# Queens Farming

**Queens Farming** is a turn-based farming simulation game in which players plant, harvest, and sell vegetables to earn gold, expand their farmland, and ultimately accumulate enough gold to win. This project was developed as part of the final assignment for the **Programming** module at the **Karlsruhe Institute of Technology** during the winter term of 2022/23. For more information, see [task.pdf](./task.pdf). 



## Game Play

### Overview of game elements
Players cultivate four distinct vegetables, each requiring a set number of rounds to mature. These crops are grown on specific types of farm tiles, each with varying capacities and crop compatibility constraints. 

| Vegetable    | Growth Duration |
| ------------ | --------------- |
| Carrot (C)   | 1 round         |
| Salad (S)    | 2 rounds        |
| Tomato (T)   | 3 rounds        |
| Mushroom (M) | 4 rounds        |


| Tile Type          | Capacity | Allowed Vegetables    |
| ------------------ | -------- | --------------------- |
| Barn (B)           | infinity | Storage Only          |
| Garden (G)         | 2        | All                   |
| Field (Fi)         | 4        | Carrot, Salad, Tomato |
| Large Field (LFi)  | 8        | Carrot, Salad, Tomato |
| Forest (Fo)        | 4        | Carrot, Mushroom      |
| Large Forest (LFo) | 8        | Carrot, Mushroom      |

### Game Flow

At the start, one selects the number of participants, player names, initial gold, winning gold threshold, and a seed value for randomization. Each player begins with one vegetable of each type stored in their barn.

Players take turns sequentially and may issue the following commands:  

| Command                          | Description                                 |
| -------------------------------- | ------------------------------------------- |
| `show barn`                      | Display barn contents and gold balance.     |
| `show board`                     | Display current farm layout and statuses.   |
| `show market`                    | Display current vegetable market prices.    |
| `sell [vegetables]/all`          | Sell specific or all stored vegetables.     |
| `buy vegetable [vegetable_name]` | Purchase a single vegetable unit.           |
| `buy land [x] [y]`               | Purchase a new tile at coordinates `(x,y)`. |
| `harvest [x] [y] [amount]`       | Harvest vegetables from a tile.             |
| `plant [x] [y] [vegetable_name]` | Plant a vegetable from barn onto a tile.    |
| `end turn`                       | End the current player's turn.              |
| `quit`                           | Exit the game immediately.                  |

The game ends automatically once a player reaches or surpasses the target gold amount after completing a full round, or manually at any time using the `quit` command. The player who first reaches the gold threshold, or the player with the most gold at game-end, is declared the winner.
