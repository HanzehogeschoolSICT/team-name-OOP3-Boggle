package Controller;

import Model.BoardGenerator;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * Created by femkeh on 15/03/17.
 */
public class BoggleController {
    @FXML private GridPane gridpane;
    private BoardGenerator boardGen = new BoardGenerator();

    char[][] board = boardGen.getBoard();

    @SuppressWarnings("unused")
    public void initialize() {
        this.loadGrid();
    }

    private void loadGrid() {
        // eerst grid 4*4 of whatever maken
        // dan door array heen loopen en per cel zetten (i en j gebruiken)
    }

}
