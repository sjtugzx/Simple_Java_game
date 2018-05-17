package comp1110.ass2.gui;

import comp1110.ass2.WarringStatesGame;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Optional;
import java.util.Random;

public class Game extends Application {
    private static final int BOARD_WIDTH = 933;
    private static final int BOARD_HEIGHT = 700;
    static String initialPlacement;
    static String moveSequence = "";
    static int numberOfHumans,numberOfAI,count;
    static int numberOfPlayers = -1;


    static Group controls = new Group();

    // FIXME Task 9: Implement a basic playable Warring States game in JavaFX
    // Author: Raiyan Ahsan
    // FIXME Task 11: Allow players of your Warring States game to play against your simple agent
    // Author: Raiyan Ahsan
    // FIXME Task 12: Integrate a more advanced opponent into your game
    // Author: Raiyan Ahsan
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Warring States");
        Scene scene = new Scene(controls, BOARD_WIDTH, BOARD_HEIGHT);
        String placement = generateRandomPlacement();
        initialPlacement = placement;
        makePlacement(placement);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    static String generateRandomPlacement() {
        Random random = new Random();
        int i = random.nextInt(TestUtility.PLACEMENTS.length);

        return TestUtility.PLACEMENTS[i];
    }

    void getPlayers() {
        Text text = new Text("Select the number of players");
        VBox vBox = new VBox();
        HBox hBox = new HBox();
        ComboBox humans = new ComboBox();
        humans.getItems().addAll("1", "2", "3", "4");
        humans.setPromptText("Players");

        ComboBox AI = new ComboBox();
        AI.getItems().addAll("0", "1", "2", "3", "4");
        hBox.getChildren().addAll(humans, AI);
        AI.setPromptText("AI");

        Button confirm = new Button("Confirm");
        confirm.setPrefSize(50.0, 10.0);


        //change to new scene where players add their name
        confirm.setOnAction(e -> {
            numberOfHumans = humans.getValue().toString().charAt(0) - '0';
            numberOfAI = AI.getValue().toString().charAt(0) - '0';
            numberOfPlayers = numberOfHumans + numberOfAI;
            moveSequence = "";

            if (numberOfPlayers > 4) {
                Alert manyPlayers = new Alert(Alert.AlertType.ERROR);
                manyPlayers.setTitle("Too many players");
                manyPlayers.setHeaderText("Please reduce number of player");
                manyPlayers.setContentText("Maximum number of players allowed is 4");
                manyPlayers.showAndWait();
                getPlayers();
            } else if (numberOfPlayers < 2) {
                Alert manyPlayers = new Alert(Alert.AlertType.ERROR);
                manyPlayers.setTitle("Not enough players");
                manyPlayers.setHeaderText("Please add more player");
                manyPlayers.setContentText("Minimum number of players allowed is 2");
                manyPlayers.showAndWait();
                getPlayers();
            }

            makePlacement(initialPlacement);
        });
        vBox.getChildren().addAll(hBox,confirm);
        controls.getChildren().add(vBox);
    }

    void makePlacement(String placement) {
        controls.getChildren().clear();
        if (numberOfPlayers < 0 || numberOfPlayers > 4) {
            getPlayers();
        }
        else {

            if (WarringStatesGame.generateMove(placement) == '\0') {
                System.out.println("Iside plac");
                Alert manyPlayers = new Alert(Alert.AlertType.INFORMATION);
                manyPlayers.setTitle("Game Over");
                int[] flags = WarringStatesGame.getFlags(initialPlacement, moveSequence, numberOfPlayers);
                int winner = mode(flags, numberOfPlayers);
                manyPlayers.setContentText("Congratulations! \n \n" + "Player " + (winner + 1) + " has Won");
                manyPlayers.showAndWait();
            } else {
                if (count % numberOfPlayers < numberOfHumans) {
                    WarringStatesGame.board = placement;
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
                            int test = i;
                            int test1 = j;
                            button1[i][j] = new Button("");
                            button1[i][j].setOnAction(e -> {
                                char location = button1[test][test1].getText().charAt(2);
                                if (WarringStatesGame.isMoveLegal(WarringStatesGame.board, location)) {
                                    moveSequence = moveSequence + location;
                                    count++;
                                    makePlacement(WarringStatesGame.board);
                                }
                            });
                            button1[i][j].setPrefSize(95, 95);
                            gridPane.add(button1[i][j], i, j);
                        }
                    }

                    //add button and judge the location
                    for (int i = 0; i < listLength; i++) {

                        if (!Character.isDigit(orientationList[i].charAt(0))) {
                            int col = 5 - ((orientationList[i].charAt(0) - 65) / 6);
                            int row = (orientationList[i].charAt(0) - 65) % 6;

                            button1[col][row].setText(cardList[i] + orientationList[i]);
                        } else {
                            int col = 1 - (orientationList[i].charAt(0) - 46) / 6;
                            int row = (orientationList[i].charAt(0) - 46) % 6;

                            button1[col][row].setText(cardList[i] + orientationList[i]);
                        }
                    }


                    HBox hb1 = new HBox();
                    hb1.getChildren().add(gridPane);
                    controls.getChildren().add(hb1);
                }
                else {
                    char move = AlphaBeta.makeMove(placement,moveSequence,numberOfPlayers,count);
                    WarringStatesGame.board = placement;
                    WarringStatesGame.isMoveLegal(WarringStatesGame.board,move);
                    moveSequence += move;
                    count++;
                    makePlacement(WarringStatesGame.board);
                }
            }
        }
    }

    public static int mode(int[] playerData,int numberOfPlayers){
        HashMap<Integer,Integer> map = new HashMap<>(numberOfPlayers);
        int max  = 1;
        int temp = 0;

        for(int i = 0; i < playerData.length; i++) {
            if (playerData[i] == -1) continue;
            if (map.get(playerData[i]) != null) {
                int count = map.get(playerData[i]);
                count++;
                map.put(playerData[i], count);
                if(count > max) {
                    max  = count;
                    temp = playerData[i];
                }
            } else map.put(playerData[i],1);
        }
        return temp;
    }

}
