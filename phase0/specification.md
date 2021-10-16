Create a program that allows two human players to play a game of Scrabble.

There is a 15 x 15 board that players can play words on. Some of the squares on the board have a special bonus, such as a “triple word score,” which triples the point value of the first word that has a tile that is placed on the square. Each player has a rack of 7 letter tiles that they can play from.

On each turn, they can either play a word onto the board, shuffle their rack by putting a select number of tiles (specified by the player) back into the bag and drawing that number of tiles again from the bag, or pass. When a player places a word, they will choose the square (specified by coordinates) where they want the beginning of their word. Then, they will choose a direction (either right or down), and then the word to be placed. This will be checked against the internal dictionary to verify that it is a valid word. The first time a player places a word on the board, a tile of the word must be on the center square of the board. Every time a word is played, the word is scored by adding up all the letter’s scores, and then it is added to the player’s total.

Each tile has associated to it a location (bag, player’s rack, or square on board), a letter, and a point value. Also, the number of each type of tile is fixed; for instance, the sum of the number of A tiles on the board, in the bag, and on all the players’ racks is always the same. The bag contains all the tiles that are not on a player’s rack or on the board. It keeps track of how many tiles of what kind are in it.

A game of Scrabble ends when there are no letter tiles remaining, or both players have passed in a row. Then, the player with the highest total score is the winner. 