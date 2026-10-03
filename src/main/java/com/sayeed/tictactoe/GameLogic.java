package com.sayeed.tictactoe;

public class GameLogic {

    public static final int SIZE = 3;
    public static final String EMPTY = "";
    public static final String PLAYER_X = "X";
    public static final String PLAYER_O = "O";

    private final String[][] board;
    private String currentPlayer;
    private boolean gameOver;

    public GameLogic() {
        board = new String[SIZE][SIZE];
        resetGame();
    }

    public void resetGame() {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                board[row][col] = EMPTY;
            }
        }
        currentPlayer = PLAYER_X;
        gameOver = false;
    }

    public boolean makeMove(int row, int col) {
        if (gameOver || !board[row][col].equals(EMPTY)) {
            return false;
        }
        board[row][col] = currentPlayer;
        return true;
    }

    public void switchPlayer() {
        currentPlayer = currentPlayer.equals(PLAYER_X)
                ? PLAYER_O
                : PLAYER_X;
    }

    public int[][] getWinningCells() {
        String player = currentPlayer;

        for (int row = 0; row < SIZE; row++) {
            if (board[row][0].equals(player)
                    && board[row][1].equals(player)
                    && board[row][2].equals(player)) {
                return new int[][]{{row, 0}, {row, 1}, {row, 2}};
            }
        }

        for (int col = 0; col < SIZE; col++) {
            if (board[0][col].equals(player)
                    && board[1][col].equals(player)
                    && board[2][col].equals(player)) {
                return new int[][]{{0, col}, {1, col}, {2, col}};
            }
        }

        if (board[0][0].equals(player)
                && board[1][1].equals(player)
                && board[2][2].equals(player)) {
            return new int[][]{{0, 0}, {1, 1}, {2, 2}};
        }

        if (board[0][2].equals(player)
                && board[1][1].equals(player)
                && board[2][0].equals(player)) {
            return new int[][]{{0, 2}, {1, 1}, {2, 0}};
        }

        return null;
    }

    public boolean isDraw() {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (board[row][col].equals(EMPTY)) {
                    return false;
                }
            }
        }
        return true;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public String getCurrentPlayer() {
        return currentPlayer;
    }

    public String getCell(int row, int col) {
        return board[row][col];
    }
}
