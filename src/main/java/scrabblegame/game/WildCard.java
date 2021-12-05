package main.java.scrabblegame.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.Map.entry;

/**
 * Each WildCard tile has symbol "~" and 0 points associated with it. However, the WildCard tile can be a proxy
 * for any character in the game.
 */

public class WildCard extends Tile{
    private final char letter;
    private final int value;

    /**
     * Class Constructor. The points for a WildCard tile are constant (0).
     *
     * @param None (Only one value is possible, passing a parameter is unnecessary)
     */
    public WildCard(){
        this.letter = "~";
        this.value = 0;
    }
}
