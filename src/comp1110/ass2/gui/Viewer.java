package comp1110.ass2.gui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;


/**
 * A very simple viewer for card layouts in the Warring States game.
 * <p>
 * NOTE: This class is separate from your main game class.  This
 * class does not play a game, it just illustrates various card placements.
 */
public class Viewer extends Application {

    private static final int VIEWER_WIDTH = 933;
    private static final int VIEWER_HEIGHT = 700;

    private static final String URI_BASE = "assets/";

    private final Group root = new Group();
    private final Group controls = new Group();
    private final Group grid = new Group();
    TextField textField;


    /**
     * Draw a placement in the window, removing any previously drawn one
     *
     * @param placement A valid placement string
     */

    void makePlacement(String placement) {
        // FIXME Task 4: implement the simple placement viewer
        // numberOfCards
        int listLength = placement.length() / 3;
        // locations list
        String[] orientationList = new String[listLength];
        // cardtype list
        String[] cardList = new String[listLength];

        // add stuff
        for (int i = 0; i < listLength; i++) {
            cardList[i] = ("" + placement.charAt(3 * i)) + placement.charAt(3 * i + 1);
            orientationList[i] = Character.toString(placement.charAt(3 * i + 2));
        }
        // garbage
        GridPane gridPane = new GridPane();

        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(8, 8, 8, 8));

        Button[][] button1 = new Button[6][6];

        // for all colums
        for (int i = 0; i < 6; i++) {
            // for all rows
            for (int j = 0; j < 6; j++) {
                button1[i][j] = new Button("");
                button1[i][j].setPrefSize(95, 95);
                gridPane.add(button1[i][j], i, j);
            }
        }

        //add button and judge the location
        for (int i = 0; i < listLength; i++) {
            System.out.println(orientationList[i].charAt(0));
            if (!Character.isDigit(orientationList[i].charAt(0))) {
                int col = 5 - ((orientationList[i].charAt(0) - 65) / 6);
                int row = (orientationList[i].charAt(0) - 65) % 6;
                System.out.println(col + " " + row);
                button1[col][row].setText(cardList[i]+orientationList[i]);
            }

            else {
                int col = 1 - (orientationList[i].charAt(0) - 46) / 6;
                int row = (orientationList[i].charAt(0) - 46) % 6;
                System.out.println(col + " " + row);
                button1[col][row].setText(cardList[i]+orientationList[i]);
            }
        }


        HBox hb1 = new HBox();
        hb1.getChildren().add(gridPane);
        controls.getChildren().add(hb1);
    }

    /**
     * Create a basic text field for input and a refresh button.
     */
    private void makeControls() {
        Label label1 = new Label("Placement:");
        textField = new TextField();
        textField.setPrefWidth(300);
        Button button = new Button("Refresh");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                System.out.println(textField.getText());
                makePlacement(textField.getText());
//                text=textField.getText();
                textField.clear();

            }
        });
        HBox hb = new HBox();
        hb.getChildren().addAll(label1, textField, button);
        hb.setSpacing(10);
        hb.setLayoutX(130);
        hb.setLayoutY(VIEWER_HEIGHT - 50);
//        hb.setLayoutY(0);
        controls.getChildren().addAll(hb);

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Warring States Viewer");
        Scene scene = new Scene(root, VIEWER_WIDTH, VIEWER_HEIGHT);


        root.getChildren().add(controls);

        makeControls();


        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
