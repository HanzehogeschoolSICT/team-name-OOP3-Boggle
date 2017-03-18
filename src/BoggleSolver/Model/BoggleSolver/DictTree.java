package BoggleSolver.Model.BoggleSolver;

import BoggleSolver.Start;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;

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
        // With the help of http://stackoverflow.com/questions/28050795/whats-the-fastest-way-to-read-from-inputstream
        BufferedReader br = new BufferedReader(new InputStreamReader(dictFile));
        String word;
        while ((word = br.readLine()) != null) {
            if (word.length() < 3) continue;

            try {
                rootNode.insertLetters(word, 0, rootNode);
            } catch (ArrayIndexOutOfBoundsException ignored) {
            }
        }
    }

    public boolean contains(final String word) {
        DictNode node = rootNode; // start at the rootNode
        char[] letters = word.toCharArray();

        int i = 0;
        while (i < letters.length && node.nextNodes[letters[i] - 'a'] != null) {
            node = node.nextNodes[letters[i] - 'a']; // traverse a node deeper, for each character found
            i++;
        }

        return (i == letters.length) && node.wordEnd;
    }

    private class DictNode {
        public final char letter;
        public DictNode[] nextNodes = new DictNode[26]; // for the characters of the alphabet (a-z only!)
        public boolean wordEnd;

        private DictNode(char c) {
            this.letter = c;
        }

        private void insertLetters(final String word, int index, DictNode currentNode) throws ArrayIndexOutOfBoundsException {
            if (currentNode.nextNodes[word.charAt(index) - 'a'] == null) {
                currentNode.nextNodes[word.charAt(index) - 'a'] = new DictNode(word.charAt(index));

                if (index == word.length() - 1)    // set the wordEnd flag when at the last of the letters
                    currentNode.nextNodes[word.charAt(index) - 'a'].wordEnd = true;
            }

            // recursively go a node deeper and increment the index, for the next letter
            if (index != word.length() - 1) {
                DictNode nextNode = currentNode.nextNodes[word.charAt(index) - 'a'];
                index++;
                insertLetters(word, index, nextNode);
            }
        }

    }

}
