## Replace this README.md!

This is a standard README.md file for you to replace. You'll want to include your own content here!

MD (Markdown) files use [Markdown formatting](https://guides.github.com/features/mastering-markdown/): 
these look very much like .txt files, so you can write them in a very similar way.

Project
- Scrabble game, with different AI players and possibly a GUI
- Classes:
o Board class
▪ Points for the squares (triple word score)
▪ Letter on each square, if not empty
o Tile class
▪ Letter
▪ Point value
o Player class
▪ The number of points accumulated so far
▪ Their current words on board
o Bag class
▪ How many tiles of each type are left
▪ Initializer
▪ Drawing tiles
o Controller class
▪ Bag
▪ Player
• Whose turn it is
▪ Board
- Wild cards – no points – can be any letter
o Player needs to specify what letter they want for the wild card
o Extends the tile class, add a function
o Abstract tile class --> subclasses: normal tile, wild card
- Game ends when the bag is empty and no player has a valid/legal move
- You can swap as many tiles as you want (1 – 7) (as long as there are >= 7 tiles in the bag) instead
of placing a word as your move
- Bag needs a swap function, and a taking out function
Extra Features:
- Multiple Difficulties of AI
- Adding Challenges
- Making a GUI that doesn't suck
Remaining questions:
- Should we have the tiles that are in the player’s rack be in the Board or the Player class?
Online Scrabble: https://www.pogo.com/games/scrabble/play
