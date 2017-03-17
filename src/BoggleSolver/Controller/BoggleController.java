package BoggleSolver.Controller;

import BoggleSolver.Model.BoardGenerator;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * Created by femkeh on 15/03/17.
 */
public class BoggleController extends Application {
    @FXML private GridPane gridPane;
    @FXML private Pane pane;
    private static int boardSize = 4;
    private BoardGenerator boardGen = new BoardGenerator();

    public static void startBoggleController(String[] args) {
        BoggleController.launch(args);
    }

    @SuppressWarnings("unused")
    public void initialize() {
        this.loadGrid();
    }

    private void loadGrid() {
        char[][] board = boardGen.getBoard();
        int i;
        int j;
        // Waarom zijn die laatste 2 columns zo klein? Ook als ik in de constraints aangeef dat ze
        // allemaal 50 zijn en in de view over width alles weg heb gehaald
        for (i = 0; i < boardSize; i++) {
            ColumnConstraints column = new ColumnConstraints(50);
            gridPane.getColumnConstraints().add(column);
        }
        for (i = 0; i < boardSize; i++) {
            for (j = 0; j < boardSize; j++) {
                Label label = new Label();
                label.setText(Character.toString(board[i][j]));
                label.setFont(Font.font("Cambria", 32));
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
        this.initialize();
    }
}
