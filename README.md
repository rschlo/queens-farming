# Queens Farming
## Final task 1 of the programming module at the Karlsruhe Institute of Technology (KIT)

This is a multiplayer console game. The objective of each player is to grow vegetables on their field to multiply and eventually sell them. When a player has earned a predetermined amount of gold, they win. 

For more information, see [task.pdf](./task.pdf)

There are carrots, salads, tomatoes und mushrooms.  

| command                                              | description                                                  |
| ---------------------------------------------------- | ------------------------------------------------------------ |
| plant [x-coordinate] [y-coordinate] [vegetable name] | If a child has the right vegetable in the barn, it can be planted on an empty tile, with the tile type determining which vegetables can grow, and a countdown begins based on the vegetable's growth time. |
| harvest [x-coordinate] [y-coordinate] [amount]       | A player harvests vegetables from a tile and stores them in a barn; the tile's countdown is adjusted or removed based on remaining vegetables. |
| sell [list of vegetable names]                       | A player can sell vegetables from their barn, receiving immediate payment, with the market changing after their turn, not instantly after the sale. |
| buy vegetable [vegetable name]                       | With gold, a player can buy a vegetable, which is then stored directly in the barn. |
| buy land [x-coordinate] [y-coordinate]               | A child can also buy additional tiles with gold to expand their play area, with the type of tile being random. |
| show barn                                            |                                                              |
| show board                                           |                                                              |
| show market                                          |                                                              |
|                                                      |                                                              |

