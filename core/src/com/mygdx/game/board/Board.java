package com.mygdx.game.board;

import com.mygdx.game.move.Coordinate;

import java.util.Arrays;

/**
 * Represent Board class as a 2D array.
 *
 * @see Coordinate
 */
public class Board {
    /**
     * Board value of White player.
     */
    public final static int WHITE = 1;
    /**
     * Board value of Black player.
     */
    public final static int BLACK = -1;
    /**
     * Board value of Empty coordinate.
     */
    public final static int EMPTY = 0;
    /**
     * Board dimensions.
     */
    public final static int WIDTH = 15;
    public final static int HEIGHT = 15;
    /**
     * Board array.
     */
    private int[][] board = new int[WIDTH][HEIGHT];

    /**
     * Win count.
     */
    public final static int WIN_COUNT = 5;

    public int getWidth() {
        return WIDTH;
    }

    public int getHeight() {
        return HEIGHT;
    }

    public int[][] getBoard() {
        return board;
    }

    public boolean setValue(Coordinate coord, int value) {
        return setValue(coord.getRow(), coord.getCol(), value);
    }

    /**
     * Set value in a specific coordinate.
     *
     * @param col   int
     * @param row   int
     * @param value Intersection object
     * @return {@code true}, otherwise {@code false}
     */
    public boolean setValue(int row, int col, int value) {
        if (inBounds(row, col)) {
            board[row][col] = value;
            return true;
        }
        return false;
    }

    /**
     * Return specific value from the board.
     *
     * @param coord Coordinate object
     * @return int
     */
    public int getValue(Coordinate coord) {
        return board[coord.getRow()][coord.getCol()];
    }

    /**
     * Return specific value from the board.
     */
    public int getValue(int row, int col) {
        return board[row][col];
    }

    public boolean isEmpty(int row, int col) {
        return board[row][col] == EMPTY;
    }

    /**
     * Check if coordinate is in bounds.
     *
     * @param col int
     * @param row int
     * @return {@code true}, otherwise {@code false}
     */
    public boolean inBounds(int col, int row) {
        return col >= 0 && col <= WIDTH &&
                row >= 0 && row <= HEIGHT;
    }

    /**
     * Check if board is full.
     *
     * @return {@code true}, otherwise {@code false}
     */
    public boolean isFull() {
        for (int row = 0; row < HEIGHT; row++) {
            for (int col = 0; col < HEIGHT; col++) {
                if (isEmpty(row, col)) return false;
            }
        }
        return true;
    }

    /**
     * Print board.
     */
    public void printBoard() {
        for (int row = 0; row < HEIGHT; row++) {
            System.out.println(Arrays.toString(board[row]));
        }
        System.out.println("***********************************");
    }

    public static void main(String[] args) {
        Board board = new Board();
        board.setValue(1, 1, 1);
        board.printBoard();
    }
}

