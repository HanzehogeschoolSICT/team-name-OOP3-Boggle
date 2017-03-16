package View;

import Model.BoardGenerator;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Cell;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * Created by femkeh on 13/03/17.
 */
public class BoggleView extends Application {
    private Cell[][] cell = new Cell[4][4];
    private char[][] board;
    private Label lbResults = new Label("No words have been found.");
    private Button btStart = new Button("Start");
    private Button btReset = new Button ("Reset");

    public BoggleView() {
        BoardGenerator boardGen = new BoardGenerator();
        this.board = boardGen.getBoard();
    }

    public static void startBoggleView(String[] args) {
        BoggleView.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        GridPane pane = new GridPane();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                pane.add(cell[i][j] = new Cell(), j, i);
                Label label = new Label("" + board[i][j]);
                label.setFont(Font.font("Cambria", 32));
                label.setTranslateY(i*120);
                label.setTranslateX(j*120 + 65);
                pane.getChildren().add(label);
            }
        }

        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(pane);
        GridPane bottom = new GridPane();
        ColumnConstraints column1 = new ColumnConstraints(50);
        ColumnConstraints column2 = new ColumnConstraints(240);
        ColumnConstraints column3 = new ColumnConstraints(120);
        bottom.getColumnConstraints().add(column1);
        bottom.getColumnConstraints().add(column2);
        bottom.getColumnConstraints().add(column3);
        Label label = new Label("");
        bottom.add(label, 1, 0);
        bottom.add(lbResults, 1, 1);
        bottom.add(btStart, 2, 1);
        bottom.add(btReset, 3, 1);
        bottom.setPrefHeight(60);
        borderPane.setBottom(bottom);

        Scene scene = new Scene(borderPane,  500, 500);
        primaryStage.setTitle("Boggle"); // Set the stage title
        primaryStage.setScene(scene); // Place the scene in the stage
        primaryStage.show(); // Display the stage
    }

    public class Cell extends Pane {
        public Cell() {
            setStyle("-fx-border-color: black");
            this.setPrefSize(2000, 2000);
        }
    }
}
