package BoggleSolver;

import BoggleSolver.Controller.BoggleController;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;

/**
 * Created by peterzen on 2017-03-13.
 * Part of the team-name-OOP3-Boggle project.
 * With the help of https://www.youtube.com/watch?v=1dyaASFf-Uc
 * With the help of https://github.com/bilash/boggle-solver
 * Edits to boggle-solver code:
 * - edited variable names for clarification and educational purposes
 * - refactored DictNode class to use a Map for broader character support
 * - refactored some syntax forms
 * - refactored isInBoard method so it checks if the first character actually exists in the board
 * With the help of http://exceptional-code.blogspot.nl/2012/02/solving-boggle-game-recursion-prefix.html
 */
public class Start {
    private static final int boardSize = 4;
    private static char[][] boggleBoard = new char[boardSize][boardSize];
    private static DictNode dictRoot;
    private List<String> foundWords = new LinkedList<>();

    private static class DictNode {
        public final char letter;
        public Map<Character, DictNode> nextNodes = new HashMap<>(); // for the 26 characters of the alphabet
        public boolean wordEnd;

        public DictNode(final char letter) {
            this.letter = letter;
        }

        public void insert(final String word) {
            DictNode node = dictRoot;
            char[] letters = word.toCharArray();
            for (int i = 0; i < letters.length; i++) {
                // add a character node to current depth if needed and set the wordEnd tag if needed:
                if (node.nextNodes.get(letters[i]) == null) { // letters[i] - 'a' results in value of: 0-26
                    node.nextNodes.put(letters[i], new DictNode(letters[i]));

                    if (i == letters.length - 1) {
                        node.nextNodes.get(letters[i]).wordEnd = true;
                    }
                }

                // increase depth for next character in word
                node = node.nextNodes.get(letters[i]);
            }
        }

        public boolean contains(final String word) {
            DictNode node = dictRoot;
            char[] letters = word.toCharArray();
            int i = 0;
            while (i < letters.length && node.nextNodes.get(letters[i]) != null) {
                node = node.nextNodes.get(letters[i]);
                i++;
            }

            return (i == letters.length) && node.wordEnd;

        }
    }

    public static void readDict(String dictFile) throws IOException, URISyntaxException {
        File fin = new File(Start.class.getClassLoader().getResource(dictFile).toURI());
        List<String> words = Files.readAllLines(fin.toPath(), StandardCharsets.ISO_8859_1);

        dictRoot = new DictNode('\0'); // set the rootNode by default to a 0 char
        for (String word : words) {
            dictRoot.insert(word);
        }
    }

    public static void main(String[] args) throws IOException, URISyntaxException {
        new Start();
        BoggleController.startBoggleController(args);
    }

    private void findBoggleWords(DictNode node, char[] currentSearch, int currentDepth) {
        if (node == null)
            return; // a null-node means we have ended on the bottom of the DictNode Tri

        if (node.wordEnd && currentDepth > 3) { // we have found a legit word
            // create the word String from our currentSearch characters
            String word = new String(currentSearch, 0, currentDepth);
            if (isInBoard(word)) {
                foundWords.add(word);
            }
        }

        // @TODO: "super" is skipped? But "supe" is included, actually last char isnt included properly

        // delve a level deeper into the dictionary by adding the nextNodes (next characters)
        // and recursively calling this function with an incremented depth
        node.nextNodes.forEach((character, nextNode) -> {
            currentSearch[currentDepth] = character;
            findBoggleWords(nextNode, currentSearch, currentDepth + 1);
        });
    }

    private boolean isInBoard(final String searchWord) {
        // the 8 directions from a character we have to check
        int[] moveX = {1, 1, 0, -1, -1, -1, 0, 1};
        int[] moveY = {0, 1, 1, 1, 0, -1, -1, -1};

        char[] letters = searchWord.toCharArray();
        boolean[][][] lettersFound = new boolean[letters.length][boardSize][boardSize];

        // find first character
        boolean firstFound = false;
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (boggleBoard[i][j] == letters[0]) {
                    lettersFound[0][i][j] = true;
                    firstFound = true;
                }
            }
        }
        if (!firstFound)
            return false;

        for (int letterIndex = 1; letterIndex < letters.length; letterIndex++) {
            // loop through all letters of the searchWord
            for (int i = 0; i < boardSize; i++) {
                for (int j = 0; j < boardSize; j++) {
                    // loop through all tiles of the boggleBoard
                    for (int direction = 0; direction < 8; direction++) {
                        // loop through directions
                        int x = i + moveX[direction];
                        int y = j + moveY[direction];

                        // positions are in bounds for board && previous letter was found && character is valid
                        if (isInBounds(x, y)
                                && (lettersFound[letterIndex - 1][x][y])
                                && (boggleBoard[i][j] == letters[letterIndex])) {
                            lettersFound[letterIndex][i][j] = true;
                            if (letterIndex == letters.length - 1) {
//                                System.out.println("word: " + searchWord + ", last letter: " + letters[letterIndex]); //@TODO: debug
                                return true; // we finished the word
                            }
                        }
                    }
                }
            }
        }

        return false;
    }

    private boolean isInBounds(int x, int y) {
        try {
            char c = boggleBoard[x][y];
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
        return true;
    }

    /**
     * Notice: Only works for boardSize == 4!
     */
    private void fillStaticBoggleBoard() {
        boggleBoard = new char[][]{
                {'d', 'g', 'h', 'i'},
                {'k', 'l', 'p', 's'},
                {'y', 'e', 'u', 't'},
                {'e', 'o', 'r', 'n'}
        };
    }

    private void fillRandomBoggleBoard() {
        String alphabet = "abcdefghikjmnlopqrstuvwxyz";
        Random r = new Random();
        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                boggleBoard[row][col] = alphabet.charAt(r.nextInt(alphabet.length()));
            }
        }
    }

    private void printBoggleBoard() {
        System.out.println("BoggleBoard:");
        for (char[] row : boggleBoard) {
            for (char col : row) {
                System.out.print(col + " ");
            }
            System.out.println();
        }
    }

    public Start() throws IOException, URISyntaxException {
        readDict("dict.txt");
        fillStaticBoggleBoard();
        long start = System.currentTimeMillis();

        findBoggleWords(dictRoot, new char[50], 0);

        long end = System.currentTimeMillis();
        foundWords.forEach(System.out::println); // print all found words
        System.out.println("Total time spent = " + (end - start) + " milli seconds.");

        printBoggleBoard();
        System.out.println("Words found: " + foundWords.size());

        // assert that both actually contain the word (should both be true for the static boggleBoard
        System.out.println(dictRoot.contains("super"));
        System.out.println(foundWords.contains("super"));
    }
}
