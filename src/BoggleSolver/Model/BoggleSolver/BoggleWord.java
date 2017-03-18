package BoggleSolver.Model.BoggleSolver;

/**
 * Created by peterzen on 2017-03-18.
 * Part of the team-name-OOP3-Boggle project.
 * With the help of https://www.youtube.com/watch?v=1dyaASFf-Uc
 * With the help of https://github.com/bilash/boggle-solver
 * With the help of http://exceptional-code.blogspot.nl/2012/02/solving-boggle-game-recursion-prefix.html
 */
public class BoggleWord {
    private BoggleLetter[] letters;
    private int letterIndex = 0;

    public BoggleWord(int maxWordSize) {
        letters = new BoggleLetter[maxWordSize];
    }

    public void addLetter(BoggleLetter letter) {
        letters[letterIndex] = letter;
        letterIndex++;
    }

    @Override
    public String toString() {
        String res = "";
        for (BoggleLetter letter : letters) {
            try {
                res += letter.toString();
            } catch (NullPointerException ignored) {
            }
        }
        return res;
    }

    public boolean contains(BoggleLetter letter) {
        for (BoggleLetter boggleLetter : letters) {
            try {
                if (boggleLetter.equals(letter))
                    return true;
            } catch (NullPointerException ignored) {
            }
        }
        return false;
    }

    public int getLetterIndex() {
        return letterIndex;
    }
}
