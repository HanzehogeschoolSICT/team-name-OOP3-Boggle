package BoggleSolver.Model.BoggleSolver;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by peterzen on 2017-03-18.
 * Part of the team-name-OOP3-Boggle project.
 * With the help of https://www.youtube.com/watch?v=1dyaASFf-Uc
 * With the help of https://github.com/bilash/boggle-solver
 * With the help of http://exceptional-code.blogspot.nl/2012/02/solving-boggle-game-recursion-prefix.html
 */
public class Solver {
    private BoggleBoard board;
    private DictTree dictTree;
    private int maxWordSize = 0;

    public List<String> foundWords;

    public Solver(BoggleBoard board, DictTree dictTree) {
        this.board = board;
        this.dictTree = dictTree;
        foundWords = new LinkedList<>();
        maxWordSize = dictTree.getMaxWordSize();
    }

    public void findBoggleWords() {
        /*
            How to solve Boggle, dictionary first approach
         */
        DictNode rootNode = dictTree.getRootNode();
        searchWords(rootNode, new char[maxWordSize], 0);
    }

    private void searchWords(DictNode node, char[] currentBranch, int currentDepth) {
        if (node == null) return;

        if (currentDepth >= 3 && node.wordEnd) {
            String word = new String(currentBranch, 0, currentDepth);
            if (inBoard(word)) {
                foundWords.add(word);
            } else {
                // backtrack: by returning the method here, we never delve deeper down the current branch
                return;
            }
        }

        for (int i = 0; i < node.nextNodes.length; i++) {
            if (node.nextNodes[i] != null) {
                // for each character in the nextNodes array, that exists:
                // (for all possible branches):

                // 1 - add the character to the currentBranch char[]
                currentBranch[currentDepth] = (char) (i + 'a');

                // 2 - and do a dynamic/recursive call to this method (traverse deeper down the trie)
                // with the new node being the one just added, and the depth incremented
                searchWords(node.nextNodes[i], currentBranch, currentDepth + 1);
            }
        }

    }

    private boolean inBoard(final String searchWord) {
        char[] letters = searchWord.toCharArray();
        boolean foundWord;

        // find first character freely (no adjacent neighbors needed)
        List<BoggleLetter> firstCandidates = findFirstLetterInBoard(letters[0]);
        if (firstCandidates == null) return false; // letter was not found in board, stop looking

        for (BoggleLetter letter : firstCandidates) {
            BoggleWord boggWord = new BoggleWord(letters.length);
            boggWord.addLetter(letter);

            // start checking neighbors, to see if we can make the word
            foundWord = checkNeighbors(letters, letter, boggWord);
            if (foundWord) return true;
        }

        return false;
    }

    private boolean checkNeighbors(final char[] letters, BoggleLetter letter, BoggleWord boggWord) {
        int[] neighborsX = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] neighborsY = {-1, 0, 1, -1, 1, -1, 0, 1};

        for (int i = 0; i < neighborsX.length; i++) {
            int row = letter.getRow() + neighborsX[i];
            int col = letter.getCol() + neighborsY[i];

            if (!board.isInBounds(row, col)) continue;

            char checkChar = board.getCharAt(row, col);
            if (checkChar == letters[boggWord.getLetterIndex()]) {
                // we found a matching neighbor:
                BoggleLetter match = new BoggleLetter(row, col, checkChar);

                if (boggWord.contains(match)) continue; // continue: we cannot allow duplicate letters
                boggWord.addLetter(match);

                if (boggWord.getLetterIndex() == letters.length) {
                    // word is complete and in board!
                    return true;
                }

                // continue looking with matched neighbor:
                return checkNeighbors(letters, match, boggWord);
            }
        }
        return false;
    }

    private List<BoggleLetter> findFirstLetterInBoard(char letter) {
        List<BoggleLetter> letters = new LinkedList<>();
        for (int row = 0; row < board.getBoardSize(); row++) {
            for (int col = 0; col < board.getBoardSize(); col++) {
                if (board.getCharAt(row, col) == letter) {
                    letters.add(new BoggleLetter(row, col, letter));
                }
            }
        }
        return letters.size() > 0 ? letters : null;
    }
}
