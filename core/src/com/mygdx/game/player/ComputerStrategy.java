package com.mygdx.game.player;

import com.mygdx.game.board.Board;
import com.mygdx.game.move.Coordinate;

/**
 * Represent Computer strategy,
 */
public interface ComputerStrategy {
    /**
     * Get best move from a given state.
     *
     * @param board Board object
     * @param player int
     * @return Coordinate object
     */
    Coordinate getMove(Board board, int player);
}
