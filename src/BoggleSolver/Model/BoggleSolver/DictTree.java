package BoggleSolver.Model.BoggleSolver;

import BoggleSolver.Start;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;

/**
 * Created by peterzen on 2017-03-18.
 * Part of the team-name-OOP3-Boggle project.
 * With the help of https://www.youtube.com/watch?v=1dyaASFf-Uc
 * With the help of https://github.com/bilash/boggle-solver
 * With the help of http://exceptional-code.blogspot.nl/2012/02/solving-boggle-game-recursion-prefix.html
 */
public class DictTree {
    private InputStream dictFile;
    private final DictNode rootNode = new DictNode('\0'); // set the rootNode by default to a 0 character
    private int maxWordSize = 0;

    public DictTree(String dictFilePath) throws URISyntaxException {
        dictFile = Start.class.getResourceAsStream(dictFilePath);
    }

    public DictNode getRootNode() {
        return rootNode;
    }

    public void readFileIntoTree() throws IOException {
        // With the help of http://stackoverflow.com/questions/28050795/whats-the-fastest-way-to-read-from-inputstream
        BufferedReader br = new BufferedReader(new InputStreamReader(dictFile));
        String word;
        while ((word = br.readLine()) != null) {
            if (word.length() < 3) continue;
            if (word.length() > maxWordSize) maxWordSize = word.length();

            try {
                rootNode.insertLetters(word, 0, rootNode);
            } catch (ArrayIndexOutOfBoundsException ignored) {
            }
        }
    }

    public int getMaxWordSize() {
        return maxWordSize;
    }

}
