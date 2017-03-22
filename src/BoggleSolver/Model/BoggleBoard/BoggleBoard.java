package BoggleSolver.Model.BoggleBoard;

import java.util.Random;

/**
 * Created by peterzen on 2017-03-18.
 * Modified by femkeh on 2017-03-20.
 * Part of the team-name-OOP3-Boggle project.
 * With the help of https://www.youtube.com/watch?v=1dyaASFf-Uc
 * With the help of https://github.com/bilash/boggle-solver
 * With the help of http://exceptional-code.blogspot.nl/2012/02/solving-boggle-game-recursion-prefix.html
 */
public class BoggleBoard {
    private int boardSize;
    private char[][] boggleBoard;

    /**
     * Create a new BoggleBoard.
     *
     *
     * NOTICE: creating a static board will only work for a boardSize of 4.
     * If a different boardSize is given, a random board shall be used instead.
     * @param boardSize The amount of cols/rows that should be used
     * @param isStatic Setup the static, 4*4, default board?
     */
    public BoggleBoard(int boardSize, boolean isStatic) {
        this.boardSize = boardSize;
        this.boggleBoard = new char[boardSize][boardSize];

        if (isStatic && boardSize == 4) {
            setupStaticBoard();
        } else {
            setupRandomBoard();
        }
    }

    private void setupRandomBoard() {
        String alphabet = "abcdefghikjmnlopqrstuvwxyz";
        Random r = new Random();
        System.out.println("index 0 test: " + boggleBoard[0][0]);
        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                boggleBoard[row][col] = alphabet.charAt(r.nextInt(alphabet.length()));
            }
        }
    }

    // NOTICE: only works for boardSize == 4
    private void setupStaticBoard() {
        boggleBoard = new char[][]{
                {'d', 'g', 'h', 'i'},
                {'k', 'l', 'p', 's'},
                {'y', 'e', 'u', 't'},
                {'e', 'o', 'r', 'n'}
        };
    }

    @Override
    public String toString() {
        String result = "BoggleBoard: \n";
        for (char[] row : boggleBoard) {
            for (char col : row) {
                result += col + " ";
            }
            result += "\n";
        }
        return result;
    }

    public boolean isInBounds(int x, int y) {
        try {
            char c = boggleBoard[x][y];
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
        return true;
    }

    public int getBoardSize() {
        return boardSize;
    }

    public char[][] getBoard() {
        return boggleBoard;
    }

    public char getCharAt(int row, int col) {
        return boggleBoard[row][col];
    }
}
