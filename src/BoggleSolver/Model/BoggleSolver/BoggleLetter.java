package BoggleSolver.Model.BoggleSolver;

/**
 * Created by peterzen on 2017-03-18.
 * Part of the team-name-OOP3-Boggle project.
 * With the help of https://www.youtube.com/watch?v=1dyaASFf-Uc
 * With the help of https://github.com/bilash/boggle-solver
 * With the help of http://exceptional-code.blogspot.nl/2012/02/solving-boggle-game-recursion-prefix.html
 */
public class BoggleLetter {
    private int rowIndex;
    private int colIndex;
    private char c;

    public BoggleLetter(int rowIndex, int colIndex, char c) {
        this.rowIndex = rowIndex;
        this.colIndex = colIndex;
        this.c = c;
    }

    public int getRow() {
        return rowIndex;
    }

    public int getCol() {
        return colIndex;
    }

    public char getChar() {
        return c;
    }

    @Override
    public String toString() {
        return "" + c;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof BoggleLetter &&
                ((BoggleLetter) o).colIndex == this.getCol() &&
                ((BoggleLetter) o).rowIndex == this.getRow() &&
                ((BoggleLetter) o).c == this.getChar();
    }

}
