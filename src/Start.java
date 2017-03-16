import Model.BoardGenerator;
import javafx.application.Application;
import javafx.stage.Stage;
import View.BoggleView;

/**
 * Created by peterzen on 2017-03-13.
 * Part of the team-name-OOP3-Boggle project.
 */
// Moet start de Application extenden? Zit nu iig (tijdelijk) in view
public class Start {

    public static void main(String[] args) {
        System.out.println("Hello world");

        BoggleView.startBoggleView(args);
//        Start.launch(args);


    }

//    @Override
//    public void start(Stage stage) throws Exception {
//
//    }
}
