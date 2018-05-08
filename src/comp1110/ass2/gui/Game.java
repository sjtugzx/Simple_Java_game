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

import java.util.*;

import static java.lang.Character.isDigit;
import static java.lang.Character.isUpperCase;



public class Game extends Application {
//    private static final int BOARD_WIDTH = 933;
    private static final int BOARD_WIDTH = 1000;  //set board width:1000
    private static final int BOARD_HEIGHT = 700; //set board height: 700 (a little change)

    public static String board;
    public static String locationsInMiddle;

    // FIXME Task 9: Implement a basic playable Warring States game in JavaFX

    //author: Zhixin Guo (u6371520)


    private static final String URI_BASE = "assets/";

    private final Group root = new Group();
    private final Group controls = new Group();
    private final Group grid=new Group();
    TextField textField;
    TextField textFieldPlayer;
    TextField textFieldMoveSequence;
    static String[] ROWS = {"4YSMGA", "5ZTNHB", "60UOIC", "71VPJD", "82WQKE", "93XRLF"};
    static String[] COLS = {"456789", "YZ0123", "STUVWX", "MNOPQR", "GHIJKL", "ABCDEF"};

    /**
     * Draw a placement in the window, removing any previously drawn one
     *
     * @param placement A valid placement string
     */
    ////////////////////////////////////////////function of task5///////////////////////////////////////////////////////
    static boolean isMoveLegal(String placement, char locationChar) {
        // FIXME Task 5: determine whether a given move is legal
        int yi_index = placement.indexOf('z');
        char yi_position = placement.charAt(yi_index + 2);
        if (!((locationChar >= 'A' && locationChar <= 'Z') || (locationChar >= '0' && locationChar <= '9'))) {
            return false;
        }
        int loc = getLocationIndex(placement, locationChar);
        if (loc == -1) {
            return false;
        }
        char kingdomToBeCaptured = placement.charAt(loc - 2);
        String locationsOutOfRange = "";
        String location = "";

        for (String col : COLS) {
            if (col.contains(yi_position + "") && col.contains(locationChar + "")) {
                int yi_int = col.indexOf(yi_position);
                int loc_int = col.indexOf(locationChar);
                if (yi_int > loc_int) {
                    location += col.substring(loc_int, yi_int);
                    locationsOutOfRange += col.substring(0, loc_int);
                } else {
                    location += col.substring(yi_int + 1, loc_int + 1);
                    locationsOutOfRange += col.substring(loc_int + 1);
                }

                continue;
            }
        }

        for (String row : ROWS) {
            if (row.contains(yi_position + "") && row.contains(locationChar + "")) {
                int yi_int = row.indexOf(yi_position);
                int loc_int = row.indexOf(locationChar);
                if (yi_int > loc_int) {
                    location += row.substring(loc_int, yi_int);
                    locationsOutOfRange += row.substring(0, loc_int);
                } else {
                    location += row.substring(yi_int + 1, loc_int + 1);
                    locationsOutOfRange += row.substring(loc_int + 1);
                }

                continue;
            }
        }
        if (location.equals("")) {
            return false;
        }

        String kingdoms = kingdoms(placement, locationsOutOfRange);
        for (int i = 0; i < kingdoms.length(); i++) {
            if (kingdoms.charAt(i) == kingdomToBeCaptured) {
                return false;
            }
        }
        String kinCap = "";
        for (int i = 0; i < location.length();i++) {
            int locInt = getLocationIndex(placement,location.charAt(i));
            if (locInt != -1){
                char kingdom = placement.charAt(locInt - 2);
                if (kingdom == kingdomToBeCaptured) {
                    kinCap += placement.substring(locInt - 2,locInt);
                }
            }
        }
        locationsInMiddle = kinCap;
        board = update(placement, location, kingdomToBeCaptured, locationChar);
        return true;
    }

    static int getLocationIndex(String placement, char locationChar) {
        for (int i = 0; i < placement.length() / 3; i++) {
            if (placement.charAt(i * 3 + 2) == locationChar) {
                return i * 3 + 2;
            }
        }
        return -1;
    }

    static String kingdoms(String placement, String locations) {
        String kingdoms = "";
        for (int i = 0; i < locations.length(); i++) {
            int index = getLocationIndex(placement, locations.charAt(i));
            if (index != -1) {
                kingdoms += placement.charAt(index - 2);
            }
        }
        return kingdoms;
    }

    static String update(String placement, String locationsInMiddle, char kingdomToBeCaptured, char locationChar) {
        for (int i = 0; i < locationsInMiddle.length(); i++) {
            int locInt = getLocationIndex(placement, locationsInMiddle.charAt(i));
            if (locInt != -1) {
                char kingdom = placement.charAt(locInt - 2);
                if (kingdom == kingdomToBeCaptured) {
                    placement = placement.substring(0, locInt - 2) + placement.substring(locInt + 1);
                    int yi_position = placement.indexOf('z');
                    placement = placement.substring(0, yi_position) + placement.substring(yi_position + 3) + "z9" + locationChar;
                }
            }
        }
        return placement;
    }
///////////////////////////////////////////////function of task 7 /////////////////////////////////////////////////////////////
public static String getSupporters(String setup, String moveSequence, int numPlayers, int playerId) {
    // FIXME Task 7: get the list of supporters for a given player after a sequence of moves
    board = setup;                  //set up String board
    String captured = "";           //captured for getting locations
    for (int i = 0; i < moveSequence.length(); i++) {           //traverse the string moveSequence
        isMoveLegal(board, moveSequence.charAt(i));             //judge whether the moveSequence is legal by recall the previous function isMoveLegal (task 2)
        if (i % numPlayers == playerId) {                       //judge the character of this move belogs to the player (according to the playerID)
            captured += locationsInMiddle;                      //captuer the card
        }


    }
    ArrayList<String> cap = new ArrayList<>();                 //creat a arraylist named cap for storing the card of the characters which has been captured
    for (int i = 0; i < captured.length()/2; i++) {
        String card = captured.substring(i*2,i*2 + 2);          //get the characters according to the location
        cap.add(card);                                            //add card to the list of capture
    }
    Collections.sort(cap);                                      //sort cap
    String sortd = "";
    for (int i = 0; i < cap.size();i++) {
        sortd += cap.get(i);
    }
    return sortd;                                               //return the characters which had been captured by the chosen player (according to its ID) with an order
}

/////////////////////////////////////////function of task 8/////////////////////////////////////////////////////////////////////
    public static int[] getFlags(String setup, String moveSequence, int numPlayers) {
        // FIXME Task 8: determine which player controls the flag of each kingdom after a given sequence of moves
        int[][] possessions = new int[numPlayers][7];
        int[] flags = {-1,-1,-1,-1,-1,-1,-1};  // initial flags for the element has not been controlled.
        board = setup;                          //initial board
        for (int i = 0; i < moveSequence.length(); i++) {        //traverse the move sequence
            String capturedCards = getSupporters(board,Character.toString(moveSequence.charAt(i)),numPlayers,0);     //recall the function (task7) to get the supporters of every player.
            char capturedKingdom = capturedCards.charAt(0);                     //identify the captured kingdom.
            int currentFlagHolder = flags[capturedKingdom - 'a'];
            possessions[i%numPlayers][capturedKingdom-'a'] += capturedCards.length()/2;
            if (currentFlagHolder == -1) {
                flags[capturedKingdom-'a'] = i%numPlayers;    //if no one control the flag the user hold the flag directly
            }
            else {
                int maximumHeld = possessions[currentFlagHolder][capturedKingdom - 'a'];        //if anyone have the flag, compare the holder's supporters
                if (possessions[i%numPlayers][capturedKingdom-'a'] >= maximumHeld) {
                    flags[capturedKingdom-'a'] = i%numPlayers;
                }
            }
        }
        return flags;    //return the situation of the flags
    }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    void makePlacement(String placement, String moveSequence) {
        // FIXME Task 4: implement the simple placement viewer
        //get string
        int listLength=placement.length()/3;
        String [] orientationList=new String [listLength];
        String [] cardList=new String[listLength];
        String [] charMoveSequence=new String[moveSequence.length()];
        int jj=0;

        for(int i=0; i<listLength;++i)
        {


            cardList[i]=Character.toString(placement.charAt(jj))+Character.toString(placement.charAt(jj+1));
            orientationList[i]=Character.toString(placement.charAt(jj+2));

            jj=jj+3;



        }
        for (int i=0;i<moveSequence.length();++i)
        {
            charMoveSequence[i]=Character.toString(moveSequence.charAt(i));
        }

        System.out.println(orientationList.length);
//        System.out.println(toString(orientationList));

        for (int i=0;i<moveSequence.length();i++)
        {

            for (int j=0;j<orientationList.length;++j)
            {
//                System.out.println(orientationList[j]);
//                System.out.println(charMoveSequence[i]);
                if (orientationList[j].equals(charMoveSequence[i]))
                {
                    //if the movesequence include the characters the characters need to be collected.
                    //erase these characters from the board
                    orientationList[j]=orientationList[orientationList.length-1];
                    orientationList= Arrays.copyOf(orientationList,orientationList.length-1);
                    //erase the orientation information
                    cardList[j]=cardList[cardList.length-1];
                    cardList=Arrays.copyOf(cardList,cardList.length-1);
                    //erase the card information
                    System.out.println(orientationList.length);
                    break;

                }
            }

        }
//        Label gameSituation=new Label();



        //new grid
        GridPane gridPane=new GridPane();

        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(8,8,8,8));

        final int column=6;
        final int row=6;
        int tempColumn=0;
        int tempRow=0;
        Button button1[][]=new Button[column][row];

        for( int i=0; i<column; i++)
        {
            for(int j=0; j<row; j++)
            {
                button1[i][j]=new Button("");
                button1[i][j].setPrefSize(95,95);
                gridPane.add(button1[i][j],i,j);
            }
        }

        int [] swap=new int [listLength];
        for(int i=0;i<orientationList.length;++i)

        {
            System.out.println(orientationList[i]);
            swap[i]=(int)orientationList[i].charAt(0);
            System.out.println(swap[i]);
        }
        //add button and judge the location
        for(int i=0; i<orientationList.length; ++i)
        {       //according to the information for the board set up and the movesequence add buttons for every grid to show the characters situation
            if((orientationList[i].charAt(0)<=70)&&(orientationList[i].charAt(0)>=65))
            {
                tempColumn=5;
                tempRow=((int)orientationList[i].charAt(0))-5*13;
                System.out.println(tempColumn);
                System.out.println(tempRow);
                button1[tempColumn][tempRow].setText(cardList[i]+orientationList[i]);
                button1[tempColumn][tempRow].setPrefSize(95,95);
            }

            if((orientationList[i].charAt(0)<=76)&&(orientationList[i].charAt(0)>=71))
            {
                tempColumn=4;
                tempRow=((int)orientationList[i].charAt(0))-71;
                System.out.println(tempColumn);
                System.out.println(tempRow);
                button1[tempColumn][tempRow].setText(cardList[i]+orientationList[i]);
                button1[tempColumn][tempRow].setPrefSize(95,95);
            }


            if((orientationList[i].charAt(0)<=82)&&(orientationList[i].charAt(0)>=77))
            {
                tempColumn=3;
                tempRow=((int)orientationList[i].charAt(0))-77;
                System.out.println(tempColumn);
                System.out.println(tempRow);
                button1[tempColumn][tempRow].setText(cardList[i]+orientationList[i]);
                button1[tempColumn][tempRow].setPrefSize(95,95);
            }

            if((orientationList[i].charAt(0)<=88)&&(orientationList[i].charAt(0)>=83))
            {
                tempColumn=2;
                tempRow=((int)orientationList[i].charAt(0))-83;
                System.out.println(tempColumn);
                System.out.println(tempRow);
                button1[tempColumn][tempRow].setText(cardList[i]+orientationList[i]);
                button1[tempColumn][tempRow].setPrefSize(95,95);
            }

            if((orientationList[i].charAt(0)<=90)&&(orientationList[i].charAt(0)>=89))
            {
                tempColumn=1;
                tempRow=((int)orientationList[i].charAt(0))-89;
                System.out.println(tempColumn);
                System.out.println(tempRow);
                button1[tempColumn][tempRow].setText(cardList[i]+orientationList[i]);
                button1[tempColumn][tempRow].setPrefSize(95,95);
            }

            if((orientationList[i].charAt(0)<=51)&&(orientationList[i].charAt(0)>=48))
            {
                tempColumn=1;
                tempRow=((int)orientationList[i].charAt(0))-46;
                System.out.println(tempColumn);
                System.out.println(tempRow);
                button1[tempColumn][tempRow].setText(cardList[i]+orientationList[i]);
                button1[tempColumn][tempRow].setPrefSize(95,95);
            }

            if((orientationList[i].charAt(0)<=57)&&(orientationList[i].charAt(0)>=52))
            {
                tempColumn=0;
                tempRow=((int)orientationList[i].charAt(0))-52;
                System.out.println(tempColumn);
                System.out.println(tempRow);
                button1[tempColumn][tempRow].setText(cardList[i]+orientationList[i]);
                button1[tempColumn][tempRow].setPrefSize(95,95);
            }



        }
        //this label for recording the situation fo the game. Notice the player whether the game is over or the game still need to be continue
        Label gameSituation=new Label();

        if (orientationList.length==0)
        {
            System.out.println("Game is Over");     //no characters game is over
            gameSituation=new Label("Game is Over");
        }else {
            System.out.println("Please Continue");      //game still need to be continued
            gameSituation=new Label("Please Continue");
        }

        HBox hb1=new HBox();
//        hb1.getChildren().addAll(gridPane,gameSituation);
        hb1.getChildren().add(gridPane);
        HBox hbGame=new HBox();
        hbGame.setLayoutX(700);                 //set layout x
        hbGame.setLayoutY(50);                      //set layout y
        hbGame.getChildren().add(gameSituation);
        controls.getChildren().add(hb1);
        controls.getChildren().add(hbGame);
    }

    void playerSituation(String setup, String moveSequence, int numPlayers)   //function for get player situation
    {
        //make a grid to store the information of the players situation
        GridPane playerSituation=new GridPane();
        playerSituation.setAlignment(Pos.CENTER);
        playerSituation.setHgap(20);
        playerSituation.setVgap(20);
        playerSituation.setPadding(new Insets(8,8,8,8));
        final int playerColumn=1;
        final int playerRow=numPlayers+1;
        int [] flags=new int[7];
        flags=getFlags( setup,  moveSequence,  numPlayers);
        String [] haveSupporters=new String [numPlayers];

        //labels for showing player's ID and the player's supporters
        Label [] playerID=new Label[numPlayers];
        for (int i=0;i<numPlayers;++i)
        {
            String supporters=getSupporters(setup, moveSequence, numPlayers, i);
            haveSupporters[i]=supporters;
            System.out.println(supporters);

            playerID[i]=new Label("player"+" "+i+"get supporters of"+" "+supporters);

            playerSituation.add(playerID[i],1,i);
        }
        //get information for every player has how many supporters
        int [] platerGetFlags=new int[numPlayers];
        for (int i=0; i<numPlayers;++i)
        {
            for (int j=0;j<7;++j)
            {
                if (flags[j]==i)
                {
                    platerGetFlags[i]++;
                }
            }
        }
        //according to the rule make sure who is the winner
        int max=0;
        int winner=0;
        for (int i=0;i<numPlayers;++i)
        {
            if (max<platerGetFlags[i])
            {
                max=platerGetFlags[i];
                winner=i;               //max number of the flags is the winner
            }
            if (max==platerGetFlags[i])
            {
                if(haveSupporters[winner].length()<haveSupporters[i].length())
                {
                    winner=i;           //if flags number are equal, then compare the characters
                }
            }
        }
        Label winnerPlayer=new Label("winner is player "+winner+" now");        //laber of the winner
        playerSituation.add(winnerPlayer,1,numPlayers+1);



        HBox hbPlayer=new HBox();
        hbPlayer.getChildren().add(playerSituation);        //make these component together
        hbPlayer.setLayoutY(100);                       //set layout y
        hbPlayer.setLayoutX(650);                       //set layout x
        controls.getChildren().add(hbPlayer);
    }


    void flagSituation()
    {
        ;
    }

    /**
     * Create a basic text field for input and a refresh button.
     */
    private void makeControls() {
        Label label1 = new Label("Placement:");             //label for placement
        textField = new TextField();
        textField.setPrefWidth(250);                                //text area width
        Label label2 = new Label("players number");             //label for players number
        textFieldPlayer=new TextField();
        textFieldPlayer.setPrefWidth(50);                           //text area width
        Label label3=new Label("MoveSequence");             //label for move sequence
        textFieldMoveSequence=new TextField();
        textFieldMoveSequence.setPrefWidth(200);                //text area width
        Button button = new Button("Move");                     //add button
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {     //handle event
                System.out.println(textField.getText());
                makePlacement(textField.getText(),textFieldMoveSequence.getText());   //recall makePlacement
                System.out.println(textFieldMoveSequence.getText());
                System.out.println(textFieldPlayer.getText());
                int num=Integer.parseInt(textFieldPlayer.getText());
                playerSituation(textField.getText(),textFieldMoveSequence.getText(),num);   //recall playersituation
                flagSituation();                                            //recall flagsituation
//                text=textField.getText();
//                textField.clear();
                textFieldMoveSequence.clear();                      //clear the movesequence for add new movesequence

            }
        });
        HBox hb = new HBox();
        hb.getChildren().addAll(label1, textField, label2, textFieldPlayer, label3, textFieldMoveSequence, button);
        hb.setSpacing(10);                              //set component space
        hb.setLayoutX(20);                              //set component layout x
        hb.setLayoutY(BOARD_HEIGHT - 50);                 //set component layout y
//        hb.setLayoutY(0);
        controls.getChildren().addAll(hb);

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Warring States Viewer");
        Scene scene = new Scene(root, BOARD_WIDTH, BOARD_HEIGHT);



        root.getChildren().add(controls);

        makeControls();


        primaryStage.setScene(scene);
        primaryStage.show();
    }
    // FIXME Task 11: Allow players of your Warring States game to play against your simple agent

    // FIXME Task 12: Integrate a more advanced opponent into your game

//    @Override
//    public void start(Stage primaryStage) throws Exception {
//
//    }
}

