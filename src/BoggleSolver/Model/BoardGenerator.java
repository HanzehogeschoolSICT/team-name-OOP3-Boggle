package BoggleSolver.Model;

import java.util.Random;

/**
 * Created by femkeh on 15/03/17.
 */
public class BoardGenerator {
    private int boardSize = 4;
    private char[][] boggleBoard = new char[boardSize][boardSize];

    public BoardGenerator() {
        String alphabet = "abcdefghikjmnlopqrstuvwxyz";
        Random r = new Random();
        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                boggleBoard[row][col] = alphabet.charAt(r.nextInt(alphabet.length()));
            }
        }
    }

    public char[][] getBoard() {
        return this.boggleBoard;
    }

}
