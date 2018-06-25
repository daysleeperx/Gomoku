package com.mygdx.game.move;

/**
 * Represent move class.
 */

public class Coordinate {
    private int row, col;

    /**
     * Class constructor.
     *
     * @param row int
     * @param col int
     */
    public Coordinate(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    @Override
    public String toString() {
        return "Coordinate{" +
                "row=" + row +
                ", col=" + col +
                '}';
    }
}
