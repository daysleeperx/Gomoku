package com.mygdx.game.player;

import com.mygdx.game.board.Board;
import com.mygdx.game.move.Coordinate;

import java.util.ArrayList;
import java.util.List;

import static com.mygdx.game.board.Board.WIN_COUNT;

/**
 * AI computer strategy that implements minimax algorithm
 * with alpha-beta pruning.
 */
public class Computer implements ComputerStrategy {
    /**
     * Best move stored as a instance variable.
     */
    private Coordinate bestMove;
    /**
     * Killer move constant referring to the best possible move.
     */
    private static final long KILLER_MOVE = 10000000;
    /**
     * Terminal state constant referring to a winning state in a game (five in a row).
     */
    private static final long TERMINAL_STATE = 2000000000;
    /**
     * Time limit constant.
     */
    private static final int TIME_LIMIT = 1000;
    /**
     * Maximum minimax depth.
     */
    private int maxDepth;
    private int myPlayer;
    private long start;

    /**
     * Get best move from a position.
     *
     * @param board Board object.
     * @param player int
     * @return Coordinate object.
     */
    @Override
    public Coordinate getMove(Board board, int player) {
        myPlayer = player;
        start = System.currentTimeMillis();
        maxDepth = (board.getWidth() == 20) ? 3 : 4;

        if (boardIsEmpty(board)) return new Coordinate(4, (int) (Math.random() * 3 + 3));
        alphaBeta(board, maxDepth, Long.MIN_VALUE, Long.MAX_VALUE, myPlayer);
        return bestMove;
    }


    /**
     * Return possible moves. Counting only empty squares near occupied squares.
     *
     * @param board SimpleBoard object
     * @return List of Location objects
     */
    private List<Coordinate> getPossibleMoves(Board board) {
        List<Coordinate> possibleMoves = new ArrayList<Coordinate>();
        int[][] b = board.getBoard();
        for (int row = 0; row < b.length; row++) {
            for (int col = 0; col < b[row].length; col++) {
                // check next squares
                if (b[row][col] == Board.EMPTY) {
                    boolean hasStone = false;
                    for (int rr = row - 1; rr <= row + 1; rr++) {
                        for (int cc = col - 1; cc <= col + 1; cc++) {
                            if (rr >= 0 && rr < b.length && cc >= 0 && cc < b[0].length) {
                                if (b[rr][cc] != Board.EMPTY) {
                                    hasStone = true;
                                    break;
                                }
                            }
                        }
                        if (hasStone) break;
                    }

                    if (hasStone) possibleMoves.add(new Coordinate(row, col));
                }
            }
        }
        return possibleMoves;
    }

    /**
     * Return score of current consecutive squares on the board.
     *
     * @param count    int
     * @param openEnds int
     * @param myTurn   boolean
     * @return int
     */
    private static long gomokuScore(int count, int openEnds, boolean myTurn) {
        if (openEnds == 0 && count < 5) {
            return 0;
        }

        switch (count) {
            case 4:
                switch (openEnds) {
                    case 1:
                        if (myTurn) return KILLER_MOVE;
                        return 50;

                    case 2:
                        if (myTurn) return KILLER_MOVE;
                        return 50000;
                }

            case 3:
                switch (openEnds) {
                    case 1:
                        if (myTurn) return 7;
                        return 5;

                    case 2:
                        if (myTurn) return 10000;
                        return 50;
                }

            case 2:
                switch (openEnds) {
                    case 1:
                        return 3;

                    case 2:
                        return 5;
                }

            case 1:
                switch (openEnds) {
                    case 1:
                        return 1;

                    case 2:
                        return 2;
                }

            default:
                return TERMINAL_STATE; // if 5 in a row
        }
    }

    /**
     * Count all sets: horizontal, vertical, diagonal. And return the total score of the sets
     * based on the the gomokuScore ratings by subtracting opponents score from myPlayers score.
     *
     * @param board       SimpleBoard object
     * @param player      int
     * @param currentTurn int
     * @return long
     */
    public long heuristic(Board board, int player, int currentTurn) {
        int opponent = player * -1;
        long playerScore = 0;
        long opponentScore = 0;

        for (int row = 0; row < board.getHeight(); row++) {
            for (int col = 0; col < board.getWidth(); col++) {
                // vertical
                if (row == 0) {
                    playerScore += countDirection(board, 0, col, 1, 0, currentTurn, player);
                    opponentScore += countDirection(board, 0, col, 1, 0, currentTurn, opponent);
                }
                // diagonal top right
                if (row == 0 && col >= 4) {
                    playerScore += countDirection(board, 0, col, 1, -1, currentTurn, player);
                    opponentScore += countDirection(board, 0, col, 1, -1, currentTurn, opponent);
                }
                // remaining diagonals from 1 to length - 5 row
                if (col == board.getWidth() - 1 && row >= 1 && row <= board.getHeight() - 5) {
                    playerScore += countDirection(board, row, col, 1, -1, currentTurn, player);
                    opponentScore += countDirection(board, row, col, 1, -1, currentTurn, opponent);

                }
                // diagonal top left
                if (row == 0 && col <= board.getWidth() - WIN_COUNT) {
                    playerScore += countDirection(board, 0, col, 1, 1, currentTurn, player);
                    opponentScore += countDirection(board, 0, col, 1, 1, currentTurn, opponent);
                }
                // remaining diagonals top left from 1 to length - 5 row
                if (col == 0 && row >= 1 && row <= board.getHeight() - WIN_COUNT) {
                    playerScore += countDirection(board, row, col, 1, 1, currentTurn, player);
                    opponentScore += countDirection(board, row, col, 1, 1, currentTurn, opponent);
                }
            }
            // horizontal
            playerScore += countDirection(board, row, 0, 0, 1, currentTurn, player);
            opponentScore += countDirection(board, row, 0, 0, 1, currentTurn, opponent);
        }

        return playerScore - opponentScore;

    }

    /**
     * Count current line for consecutive stones.
     *
     * @param board       SimpleBoard object
     * @param startY      int
     * @param startX      int
     * @param directionY  int
     * @param directionX  int
     * @param currentTurn int
     * @param player      int
     * @return long
     */
    private long countDirection(Board board, int startY, int startX, int directionY, int directionX, int currentTurn, int player) {
        long score = 0;
        int count = 0;
        int openEnds = 0;
        int row = startY;
        int col = startX;
        int i = 0;

        while (inBounds(board, row, col)) {
            if (board.getValue(row, col) == player) {
                count++;
            } else if (board.isEmpty(row, col) && count > 0) {
                openEnds++;
                score += gomokuScore(count,
                        openEnds, currentTurn == player);
                count = 0;
                openEnds = 1;
            } else if (board.isEmpty(row, col)) {
                openEnds = 1;
            } else if (count > 0) {
                score += gomokuScore(count,
                        openEnds, currentTurn == player);
                count = 0;
                openEnds = 0;
            } else {
                openEnds = 0;
            }

            i++;
            row = startY + i * directionY;
            col = startX + i * directionX;
        }

        if (count > 0) {
            score += gomokuScore(count, openEnds, currentTurn == player);
        }

        return score;
    }

    private boolean inBounds(Board board, int row, int col) {
        return (row >= 0 && row < board.getHeight() && col >= 0 && col < board.getWidth());
    }

    /**
     * Alpha-beta algorithm.
     *
     * @param board  SimpleBoard object
     * @param depth  int
     * @param alpha  long
     * @param beta   long
     * @param player int
     * @return long
     */
    private long alphaBeta(Board board, int depth, long alpha, long beta, int player) {
        if (depth == 0 || isTerminal(board))
            return heuristic(board, myPlayer, player);

        List<Coordinate> possibleMoves = getPossibleMoves(board);


        if (player == myPlayer) {
            long bestScore = Long.MIN_VALUE;

            for (Coordinate move : possibleMoves) {
                // make move
                board.setValue(move, player);

                long currentScore = alphaBeta(board, depth - 1, alpha, beta, player * -1);
                if (depth == maxDepth && currentScore > bestScore) {
                    bestMove = move;
                }

                bestScore = Math.max(currentScore, bestScore);
                alpha = Math.max(alpha, bestScore);
                // undo move
                board.setValue(move, Board.EMPTY);

                if (beta <= alpha) break;
            }

            return bestScore;
        } else {
            long bestScore = Long.MAX_VALUE;

            for (Coordinate move : possibleMoves) {
                // make move
                board.setValue(move, player);
                long currentScore = alphaBeta(board, depth - 1, alpha, beta, myPlayer);
                bestScore = Math.min(currentScore, bestScore);
                beta = Math.min(beta, bestScore);
                // undo move
                board.setValue(move, Board.EMPTY);
                if (beta <= alpha) break;
            }
            return bestScore;
        }
    }

    /**
     * Checks if current node is terminal.
     *
     * @param board SimpleBoard object
     * @return {@code true}, otherwise {@code false}
     */
    private boolean isTerminal(Board board) {
        if (boardIsFull(board)) return true; // if draw

        // try to find win
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
        return true;
    }

    /**
     * Check if board is empty.
     *
     * @param board SimpleBoard object
     * @return {@code true}, otherwise {@code false}
     */
    private boolean boardIsEmpty(Board board) {
        for (int row = 0; row < board.getHeight(); row++) {
            for (int col = 0; col < board.getWidth(); col++) {
                if (!board.isEmpty(row, col)) return false;
            }
        }
        return true;
    }

    /**
     * Check if board is full.
     *
     * @param board SimpleBoard object
     * @return @return {@code true}, otherwise {@code false}
     */
    private boolean boardIsFull(Board board) {
        for (int row = 0; row < board.getHeight(); row++) {
            for (int col = 0; col < board.getWidth(); col++) {
                if (board.isEmpty(row, col)) return false;
            }
        }
        return true;
    }

    /**
     * Check if time limit is exceeded.
     *
     * @return {@code true}, otherwise {@code false}
     */
    private boolean isTimeOut() {
        return System.currentTimeMillis() - start >= TIME_LIMIT;
    }
}
