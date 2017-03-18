package BoggleSolver.Model.BoggleDict;

/**
 * Created by peterzen on 2017-03-18.
 * Part of the team-name-OOP3-Boggle project.
 * With the help of https://www.youtube.com/watch?v=1dyaASFf-Uc
 * With the help of https://github.com/bilash/boggle-solver
 * With the help of http://exceptional-code.blogspot.nl/2012/02/solving-boggle-game-recursion-prefix.html
 */
public class DictNode {
    public final char letter;
    public DictNode[] nextNodes = new DictNode[26]; // for the characters of the alphabet (a-z only!)
    public boolean wordEnd;

    public DictNode(char c) {
        this.letter = c;
    }

    public void insertLetters(final String word, int index, DictNode currentNode) throws ArrayIndexOutOfBoundsException {
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
