package BoggleSolver;

import javafx.scene.layout.Pane;

import java.io.*;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Created by peterzen on 2017-03-13.
 * Part of the team-name-OOP3-Boggle project.
 */
public class Start {
    private int rows, cols;
    private char[][] boggleBoard;
    private List<String> foundWords;
    private static final List<String> dictionary = loadDictionary("dict-1.txt");
    private static int longestDictWordSize;

    private static List<String> loadDictionary(String dictFile) {
        try {
            File fin = new File(Start.class.getClassLoader().getResource(dictFile).toURI());

            BufferedReader br;
            try {
                br = new BufferedReader(new FileReader(fin));

                List<String> words = new ArrayList<>();
                String line;
                try {
                    while ((line = br.readLine()) != null) {
                        if (line.length() > longestDictWordSize) {
                            longestDictWordSize = line.length();
                        }
                        words.add(line);
                    }
                    br.close();

                    return words;

                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void main(String[] args) {
        new Start();
//        System.out.println(dictionary.get(2)); // prints: aanbellen
    }

    public Start() {
        rows = 4;
        cols = 4;
        foundWords = new LinkedList<>();
        boggleBoard = new char[rows][cols];

        fillStaticBoggleBoard();
        printBoggleBoard();
        System.out.println("Longest Dictionary word: " + longestDictWordSize);
        // Find all boggle words known by the current dictionary in the current boggleBoard
        for (int rowIndex = 0; rowIndex < boggleBoard.length; rowIndex++) {
            for (int colIndex = 0; colIndex < boggleBoard[rowIndex].length; colIndex++) {
                // start finding words with the character at position rowIndex, colIndex
                findWords(rowIndex, colIndex, new BoggWord());
            }
        }
        System.out.println();
        System.out.println("Found: " + foundWords.size() + " words!");
        foundWords.forEach(System.out::println);
    }

    private void findWords(int rowIndex, int colIndex, BoggWord currentWord) {
//        System.out.println(currentWord);

        if (isOutOfBounds(rowIndex, colIndex))
            return;

        BoggLetter currentLetter = new BoggLetter(rowIndex, colIndex, boggleBoard[rowIndex][colIndex]);
        if (currentWord.hasLetter(currentLetter))
            return; // No new BoggLetter to add
        // BoggLetter is new: add to the currentWord
        currentWord.add(currentLetter);

        if (currentWord.length() >= 3) {
            if (dictionary.contains(currentWord.toString())) {
                System.out.println("Found this word: " + currentWord.toString());
                foundWords.add(currentWord.toString());
            }

            if (currentWord.length() >= longestDictWordSize) {
                return; // Stop checking for currentWord (there are no more words in the dictionary, longer than this)
            }
        }

        // add new characters to currentWord
        // this recursively calls the current method with all adjacent character possibilities
        for (int i = rowIndex - 1; i <= rowIndex + 1; i++) {
            for (int j = colIndex - 1; j <= colIndex + 1; j++) {
                findWords(i, j, new BoggWord(currentWord));
            }
        }
    }

    private boolean isOutOfBounds(int rowIndex, int colIndex) {
        try {
            char c = boggleBoard[rowIndex][colIndex];
        } catch (IndexOutOfBoundsException e) {
            return true;
        }
        return false;
    }

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
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
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

    class BoggWord {
        private ArrayList<BoggLetter> boggLetters;

        public BoggWord() {
            this.boggLetters = new ArrayList<>();
        }

        public BoggWord(BoggWord clone) {
            this.boggLetters = clone.getAll();
        }

        public void add(BoggLetter boggLetter) {
            this.boggLetters.add(boggLetter);
        }

        public int length() {
            return boggLetters.size();
        }

        @Override
        public String toString() {
            String result = "";
            for (BoggLetter boggLetter : boggLetters) {
                result += boggLetter.getChar();
            }
            return result;
        }

        public boolean hasLetter(BoggLetter boggLetter) {
            return boggLetters.contains(boggLetter);
        }

        public ArrayList<BoggLetter> getAll() {
            return boggLetters;
        }
    }

    class BoggLetter {
        private int row, col;
        private char c;

        public BoggLetter(int row, int col, char c) {
            this.row = row;
            this.col = col;
            this.c = c;
        }

        @Override
        public String toString() {
            return "row: " + row + ", col: " + col + ", char: " + c;
        }

        public char getChar() {
            return this.c;
        }

        @Override
        public int hashCode() {
            return row + col + c;
        }

        @Override
        public boolean equals(Object o) {
            return hashCode() == o.hashCode() ||
                    o instanceof BoggLetter &&
                            ((BoggLetter) o).row == row &&
                            ((BoggLetter) o).col == col &&
                            ((BoggLetter) o).c == c;
        }
    }
}
