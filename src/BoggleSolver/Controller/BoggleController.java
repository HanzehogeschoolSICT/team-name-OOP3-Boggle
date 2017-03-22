package BoggleSolver.Controller;

import BoggleSolver.Model.BoardGenerator;
import BoggleSolver.Model.BoggleBoard.BoggleBoard;
import BoggleSolver.Model.BoggleDict.DictTree;
import BoggleSolver.Model.BoggleSolver.Solver;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * Created by femkeh on 15/03/17.
 */
public class BoggleController extends Application {
    @FXML private GridPane gridPane;
    private static int boardSize = 4;
    private static boolean isStatic = false;
    //private static DictTree dictTree;
    private BoggleBoard boardGen;
    //private Solver solver = new Solver(boardGen, dictTree);

    public BoggleController() {
//        this.boardSize = boardSize;
//        this.isStatic = isStatic;
    }

    public static void startBoggleController(String[] args) {
        //throws IOException, URISyntaxException
        BoggleController.launch(args);
       // dictTree = new DictTree("/dict.txt");
        //dictTree.readFileIntoTree();
    }

    @SuppressWarnings("unused")
    public void initialize() {
        this.drawGrid();
        this.loadGrid();
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
        char[][] board = boardGen.getBoard();
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
        System.out.println(getClass().getResource("../View/BoggleView.fxml"));
        System.out.println("FXML loader: " + FXMLLoader.load(getClass().getResource("../View/View.fxml")));
        Parent root = FXMLLoader.load(getClass().getResource("../View/BoggleView.fxml"));
                //FXMLLoader.load(BoggleSolver.Start.class.getResource("/View/BoggleView.fxml"));

        stage.setScene(new Scene(root));
        stage.show();
    }

    public void startBoggleSolver(ActionEvent actionEvent) {
        // iets uit start die maakt dat het zoeken op gang komt
        //solver.findBoggleWords();
    }

    public void resetBoard(ActionEvent actionEvent) {
        gridPane.getChildren().clear();
        this.loadGrid();
    }
}
