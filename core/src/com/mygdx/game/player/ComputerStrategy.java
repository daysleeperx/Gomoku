package com.mygdx.game.player;

import com.mygdx.game.board.Board;
import com.mygdx.game.move.Coordinate;

/**
 * Represent Computer strategy,
 */
public interface ComputerStrategy {
    /**
     * Get best move from a position.
     * @param board
     * @param player
     * @return
     */
    Coordinate getMove(Board board, int player);
}
