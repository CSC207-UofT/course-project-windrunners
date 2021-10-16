#Phase 0 Progress Report

**Andy, Elliot, Rachel, Shaul, Shridhar, Yash**


Our group is creating a Scrabble game that allows two human players to play against each other. Broadly, the Scrabble board is a 15 x 15 board of squares; some of the squares are equipped with certain special bonuses that will alter how a word that a player plays is calculated. When it is a player’s turn, they can choose from one of three options: playing a word on the board, reshuffling their rack by putting some tiles back in the bag and redrawing, or passing their turn. If a word is placed on the board by a player, we check if it is a valid play by searching for the word in the Scrabble dictionary. A game of Scrabble ends when there are no letter tiles remaining, or both players have passed in a row. Then, the player with the highest total is the winner.

Currently, our CRC model consists of five entities, two use cases, and one controller. The entities are the Tile, the Bag (containing the tiles that are not on either player’s rack or on the board), the Square (a unit of the Board), the Board (a 2D array of squares), and the Dictionary (containing all valid Scrabble words). The use cases are the Player (which uses the Tile class by storing tiles in the rack, for example) and the BoardRenderer (which takes the Board class and creates a visualization of it in the console). The controller is the Game, which runs the “game loop” until the game ends.

Our scenario walk-through describes the start of the game and a move being made by the first player, with the move being the placement of a word on the board (rather than a reshuffling of tiles or a pass of their turn).

The Game class controls the state of the game. It has a main method which consists (mostly) of a loop that (in turn) calls each player’s makeMove method. This will return a string of letters and a start position and a direction (down or right). The Game then passes this to the Board along with its Dictionary. The Board checks that the position is valid and uses the Dictionary to check that each word to be placed is a valid word. If those are both true, then it places each tile and tallies the points, which it returns. The Game then adds that to the Players total. The game then calls its Bag’s getTile method to replenish the Players tiles.

The Board is a collection of Squares, each of which comes in one of 5 types: normal square, double letter, triple letter, double word, triple word. Each square has a Tile instance variable, which is empty if there is no tile placed on it. Each Tile has a letter and point value.

For Phase 0, we divided our work as follows:

- Specification: Andy and Rachel 
- CRC Model: Shaul and Yash 
- Scenario Walkthrough: Shaul and Yash 
- Skeleton Program: Andy and Elliot 
- Progress Report: Rachel and Shridhar

Each group member will begin writing more code for the various classes and implementing additional ones (with the workload being divided by having different people work on different classes).

As of now, our implementation has many smaller classes, each doing a smaller unit of work. This allows an easy way to fix and update each class separately.

Further, every class has methods and attributes that relate to one another (high cohesion). Every class has one responsibility, and its variables and methods are cohesive and work towards achieving that.

One open question that we were struggling with was whether certain central information about the Scrabble game (such as a Player’s score) should be stored within the Player or the Game class. While the score is an attribute that is central to a player, we also thought that it could be central to the game as well, since the score is needed, for example, to determine the winner (although this could also be done with a getter method). We also need to ensure that a Player cannot alter their score – their score should come strictly from the moves that they have made. 