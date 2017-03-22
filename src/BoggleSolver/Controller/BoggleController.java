package BoggleSolver.Controller;

import BoggleSolver.Model.BoggleBoard.BoggleBoard;
import BoggleSolver.Model.BoggleDict.DictTree;
import BoggleSolver.Model.BoggleSolver.Solver;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * Created by femkeh on 15/03/17.
 */
public class BoggleController extends Application {
    @FXML private GridPane gridPane;
    @FXML private Label lbOutput;
    @FXML private Button btnStart;
    private static int boardSize = 4;
    private static boolean isStatic = false;
    private static DictTree dictTree;
    private BoggleBoard boardGen;
    private Solver solver;
    private char[][] board;

    public BoggleController() {
    }

    public static void startBoggleController(String[] args) {
        //throws IOException, URISyntaxException
        BoggleController.launch(args);
       // dictTree = new DictTree("/dict.txt");
        //dictTree.readFileIntoTree();
    }

    @SuppressWarnings("unused")
    public void initialize() throws IOException, URISyntaxException {
        this.drawGrid();
        this.loadGrid();
        dictTree = new DictTree("/dict.txt");
        dictTree.readFileIntoTree();
        solver = new Solver(boardGen, dictTree);
    }

    private void drawGrid() {
        int i;
        for (i = 0; i < boardSize; i++) {
            ColumnConstraints column = new ColumnConstraints(gridPane.getPrefWidth()/boardSize);
            RowConstraints row = new RowConstraints(gridPane.getPrefHeight()/boardSize);
            gridPane.getColumnConstraints().add(column);
            gridPane.getRowConstraints().add(row);
        }
    }

    private void loadGrid() {
        boardGen = new BoggleBoard(boardSize, isStatic);
        board = boardGen.getBoard();
        int i;
        int j;
        for (i = 0; i < boardSize; i++) {
            for (j = 0; j < boardSize; j++) {
                Label label = new Label();
                label.setText(Character.toString(board[i][j]));
                label.setFont(Font.font("Cambria", 32));
                gridPane.setHalignment(label, HPos.CENTER);
                // gridPane.setGridLinesVisible(true);
                gridPane.add(label, j, i);
            }
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Boggle");
        Parent root = FXMLLoader.load(getClass().getResource("../View/BoggleView.fxml").toURI().toURL());
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void startBoggleSolver(ActionEvent actionEvent) {
        btnStart.setDisable(true);
        long searchStart = System.currentTimeMillis();
        solver.findBoggleWords();
        lbOutput.setText("There are " + solver.foundWords.size() + " words found.");
        long searchEnd = System.currentTimeMillis();

        solver.foundWords.forEach(System.out::println);
        System.out.println(boardGen);
        System.out.println("Words found: " + solver.foundWords.size());
        System.out.println("Found boggle words in: " + (searchEnd - searchStart) + "ms.");
    }

    public void resetBoard(ActionEvent actionEvent) {
        gridPane.getChildren().clear();
        lbOutput.setText("There are no words found.");
        this.loadGrid();
        boardGen.setBoard(board);
        solver.setBoggleBoard(boardGen);
        solver.resetFoundWords();
        btnStart.setDisable(false);
    }
}
