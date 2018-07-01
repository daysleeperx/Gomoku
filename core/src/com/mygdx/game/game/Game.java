package com.mygdx.game.game;

import com.mygdx.game.board.Board;
import com.mygdx.game.player.Computer;

import static com.mygdx.game.board.Board.WIN_COUNT;

/**
 * Represent Game class.
 */
public class Game {
    /**
     * Board associated with the game.
     */
    private Board board;
    /**
     * Computer opponent.
     */
    private Computer computer;
    /**
     * Game status.
     */
    private GameStatus status;
    /**
     * Create game.
     */
    public void createGame() {
        board = new Board();
        computer = new Computer();
        status = GameStatus.OPEN;
    }

    public GameStatus getStatus() {
        return status;
    }

    public Board getBoard() {
        return board;
    }

    public Computer getComputer() {
        return computer;
    }

    public boolean isGameOver() {
        return checkForWin() || isDraw();
    }

    /**
     * Check if the position is winning.
     *
     * @return {@code true}, otherwise {@code false}
     */
    public boolean checkForWin() {
        for (int row = 0; row < board.getHeight(); row++) {
            for (int col = 0; col < board.getWidth(); col++) {
                int player = board.getValue(row, col);
                if (player != Board.EMPTY) {
                    if (row <= board.getHeight() - WIN_COUNT) {
                        if (countFive(board, row, col, 1, 0)) {
                            return true;
                        }
                        if (col > 3 && countFive(board, row, col, 1, -1)) {
                            return true;
                        }
                    }
                    if (col <= board.getWidth() - WIN_COUNT) {
                        if (countFive(board, row, col, 0, 1)) {
                            return true;
                        }
                        if (row <= board.getHeight() - WIN_COUNT && countFive(board, row, col, 1, 1)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * Count five in a row.
     *
     * @param board       SimpleBoard object
     * @param row         int
     * @param column      int
     * @param deltaRow    int
     * @param deltaColumn int
     * @return {@code true}, otherwise {@code false}
     */
    private boolean countFive(Board board, int row, int column, int deltaRow, int deltaColumn) {
        int player = board.getValue(row, column);
        for (int i = 1; i < WIN_COUNT; i++) {
            if (board.getBoard()[row + i * deltaRow][column + i * deltaColumn] != player) return false;
        }
        setWinner(player);
        return true;
    }

    /**
     * Set winner if five consecutive stones are found.
     *
     * @param player int
     */
    private void setWinner(int player) {
        switch (player) {
            case 1 :
                status = GameStatus.WHITE_WON;
                break;
            case -1:
                status = GameStatus.BLACK_WON;
        }
    }

    public boolean isDraw() {
        if (!checkForWin() && board.isFull()) {
            status = GameStatus.DRAW;
            return true;
        }
        return false;
    }
}
