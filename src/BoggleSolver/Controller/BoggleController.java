package BoggleSolver.Controller;

import BoggleSolver.Model.BoardGenerator;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * Created by femkeh on 15/03/17.
 */
public class BoggleController extends Application {
    @FXML private GridPane gridPane;
    private static int boardSize = 4;
    private BoardGenerator boardGen = new BoardGenerator();
    private Label label;

    public static void startBoggleController(String[] args) {
        BoggleController.launch(args);
    }

    @SuppressWarnings("unused")
    public void initialize() {
        this.loadGrid();
    }

    private void loadGrid() {
        char[][] board = boardGen.getBoard();
        // eerst grid 4*4 of whatever maken // eerst grid maken wou niet, lukt dit?
        // dan door array heen loopen en per cel zetten (i en j gebruiken)
        int i;
        int j;
        for (i = 0; i < boardSize; i++) {
            for (j = 0; j < boardSize; j++) {
                label.setText("" + board[i][j]);
                label.setFont(Font.font("Cambria", 32));
                gridPane.add(label, j, i);
            }
        }

    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Boggle");
        Parent root = FXMLLoader.load(getClass().getResource("../View/BoggleView.fxml"));

        stage.setScene(new Scene(root, 1000, 500));
        stage.show();
    }

    public void startBoggleSolver(ActionEvent actionEvent) {
        // iets uit start die maakt dat het zoeken op gang komt
        // waarom worden mijn methode namen niet geel?
    }

    public void resetBoard(ActionEvent actionEvent) {
        this.initialize();
    }
}
