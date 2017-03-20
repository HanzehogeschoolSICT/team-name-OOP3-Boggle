package BoggleSolver.Controller;

import BoggleSolver.Model.BoardGenerator;
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

/**
 * Created by femkeh on 15/03/17.
 */
public class BoggleController extends Application {
    @FXML private GridPane gridPane;
    @FXML private Pane pane;
    @FXML private HBox hbox;
    @FXML private Label lbOutput;
    @FXML private Button btnStart;
    @FXML private Button btnReset;
    private static int boardSize = 4;
    private BoardGenerator boardGen;

    public static void startBoggleController(String[] args) {
        BoggleController.launch(args);
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
        boardGen = new BoardGenerator();
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
        Parent root = FXMLLoader.load(BoggleSolver.Start.class.getResource("View/BoggleView.fxml"));

        stage.setScene(new Scene(root));
        stage.show();
    }

    public void startBoggleSolver(ActionEvent actionEvent) {
        // iets uit start die maakt dat het zoeken op gang komt
        // waarom worden mijn methode namen niet geel?
        // this.startBoggleSolver(actionEvent); dat deed et iig niet..
    }

    public void resetBoard(ActionEvent actionEvent) {
        gridPane.getChildren().clear();
        this.loadGrid();
    }
}
