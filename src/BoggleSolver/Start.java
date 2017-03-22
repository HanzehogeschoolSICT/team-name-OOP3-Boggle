package BoggleSolver;

import BoggleSolver.Controller.BoggleController;
import BoggleSolver.Model.BoggleBoard.BoggleBoard;
import BoggleSolver.Model.BoggleDict.DictTree;
import BoggleSolver.Model.BoggleSolver.Solver;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * Created by peterzen on 2017-03-13.
 * Modified by femkeh on 2017-03-20.
 * Part of the team-name-OOP3-Boggle project.
 * With the help of https://www.youtube.com/watch?v=1dyaASFf-Uc
 * With the help of https://github.com/bilash/boggle-solver
 * With the help of http://exceptional-code.blogspot.nl/2012/02/solving-boggle-game-recursion-prefix.html
 */
public class Start {

    public static void main(String[] args) throws IOException, URISyntaxException {
//        new Start();
        BoggleController.startBoggleController(args);
    }


    public Start() throws IOException, URISyntaxException {
        DictTree dictTree = new DictTree("/dict.txt");
        dictTree.readFileIntoTree();
        System.out.println("DictTree is ingeladen");
        //BoggleController boggleController = new BoggleController( args, 4, false, dictTree);
        //long dictStart = System.currentTimeMillis();
        //System.out.println("Reading dict in: " + (System.currentTimeMillis() - dictStart) + "ms.");

//        BoggleBoard board = new BoggleBoard(4, true);
//        Solver solver = new Solver(board, dictTree);
//
//        long searchStart = System.currentTimeMillis();
//        solver.findBoggleWords();
//        long searchEnd = System.currentTimeMillis();
//
//        solver.foundWords.forEach(System.out::println);
//        System.out.println(board);
//        System.out.println("Words found: " + solver.foundWords.size());
//        System.out.println("Found boggle words in: " + (searchEnd - searchStart) + "ms.");
    }
}
