package com.mygdx.game.board;

import com.mygdx.game.gui.intersection.IntersectionValue;

import java.util.Arrays;

/**
 * Represent Board class as a 2D array.
 * @see com.mygdx.game.gui.intersection.IntersectionValue
 */
public class Board {
    private final static int WIDTH = 15;
    private final static int HEIGHT = 15;
    /**
     * Board array.
     */
    private int[][] boardArray = new int[WIDTH][HEIGHT];

    /**
     *
     * @param col int
     * @param row int
     * @param value Intersection object
     *
     * @return {@code true}, otherwise {@code false}
     */
    public boolean setValue(int col, int row, IntersectionValue value) {
        if (inBounds(col, row) && getValue(col, row) == 0) {
            boardArray[row][col] = value.getValue();
            return true;
        }
        return false;
    }

    /**
     * Return specific value from the board.
     */
    public int getValue(int col, int row) {
        return boardArray[row][col];
    }

    /**
     * Check if coordinate is in bounds.
     *
     * @param col int
     * @param row int
     *
     * @return {@code true}, otherwise {@code false}
     */
    public boolean inBounds(int col, int row) {
        return col >= 0 && col <= WIDTH &&
                row >= 0 && row <= HEIGHT;
    }

    /**
     * Print board.
     */
    public void printBoard() {
        for (int row = 0; row < HEIGHT; row++) {
            System.out.println(Arrays.toString(boardArray[row]));
        }
    }

    public static void main(String[] args) {
        Board board = new Board();
        board.setValue(1, 1, IntersectionValue.WHITE);
        board.printBoard();
    }
}
