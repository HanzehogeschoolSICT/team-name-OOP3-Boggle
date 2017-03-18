package BoggleSolver.Model.BoggleSolver;

import BoggleSolver.Start;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by peterzen on 2017-03-17.
 * Part of the team-name-OOP3-Boggle project.
 */
public class DictTree {
    private InputStream dictFile;
    private final DictNode rootNode = new DictNode('\0'); // set the rootNode by default to a 0 character

    public DictTree(String dictFilePath) throws URISyntaxException {
        dictFile = Start.class.getResourceAsStream(dictFilePath);
    }

    public void readFileIntoTree() throws IOException {
        // use a scanner to retrieve results from the dictFile (using the word-boundary regex delimiter)
        java.util.Scanner s = new java.util.Scanner(dictFile).useDelimiter("\\b");
        while (s.hasNext()) // loops over all the words in the dictFile
            rootNode.insert(s.next());
    }

    public boolean contains(final String word) {
        DictNode node = rootNode; // start at the rootNode
        char[] letters = word.toCharArray();

        int i = 0;
        while (i < letters.length && node.nextNodes.get(letters[i]) != null) {
            node = node.nextNodes.get(letters[i]); // traverse a node deeper, for each character found
            i++;
        }

        return (i == letters.length) && node.wordEnd;
    }

    private class DictNode {
        public final char letter;
        public Map<Character, DictNode> nextNodes = new HashMap<>(); // for the 26 characters of the alphabet
        public boolean wordEnd;

        private DictNode(char c) {
            this.letter = c;
        }

        public void insert(final String word) {
            DictNode node = rootNode;
            char[] letters = word.toCharArray();

            insertLetters(letters, 0, node);
        }

        private void insertLetters(final char[] letters, int index, DictNode currentNode) {
            if (currentNode.nextNodes.get(letters[index]) == null) {
                currentNode.nextNodes.put(letters[index], new DictNode(letters[index]));

                if (index == letters.length - 1)    // set the wordEnd flag when at the last of the letters
                    currentNode.nextNodes.get(letters[index]).wordEnd = true;
            }

            // recursively go a node deeper and increment the index, for the next letter
            if (index != letters.length - 1) {
                DictNode nextNode = currentNode.nextNodes.get(letters[index]);
                index++;
                insertLetters(letters, index, nextNode);
            }
        }

    }

}
