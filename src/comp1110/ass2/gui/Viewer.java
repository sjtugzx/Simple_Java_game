package comp1110.ass2.gui;

import comp1110.ass2.WarringStatesGame;
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
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javax.swing.text.View;
import java.util.*;


/**
 * A very simple viewer for card layouts in the Warring States game.
 * <p>
 * NOTE: This class is separate from your main game class.  This
 * class does not play a game, it just illustrates various card placements.
 */

/**
 *  Designer Zhixin Guo (u6371529)
 */
public class Viewer extends Application {

    private static final int VIEWER_WIDTH = 933;
    private static final int VIEWER_HEIGHT = 700;

    private static final String URI_BASE = "assets/";

    private final Group root = new Group();
    private final Group controls = new Group();
    private final Group grid=new Group();
    TextField textField;
    String playerNUMBER;
    String moveSequence;

    String boardSetting;
    //setup the board
    private final String[] boardSetUp=
            {       "g0Aa0Bf1Ca1Dc5Ee1Fa4Ge3He2Ia2Jc2Kd0Lf0Mb4Nd4Oa6Pc3Qe0Ra5Sc1Td1Uc4Vb5Wb0Xa7Yf2Zb10a31z92b33b64d35g16b27d28c09",
                    "g1Aa0Bc0Ce0De3Ed4Fb6Ga4Hg0Ib5Ja7Kb1Lz9Me1Nd0Of0Pf1Qb2Rc1Sd3Ta5Ub4Va2Wc5Xd1Ya3Zc20d21c32f23a64c45b36b07a18e29",
                    "b5Ae0Bc3Ca7Da1Ec1Fg1Gg0Ha0If0Jb2Kb1La3Ma2Nb0Oc5Pe2Qd0Rd2Sd4Td3Ua4Va5Wb6Xb3Yb4Zz90f11a62e33c04f25c46c27d18e19",
                    "c3Aa6Ba1Ca5Dd0Ee3Fa3Gc0Hb1Ic5Jz9Kb3Lb5Mf1Nf0Ob4Pc4Qa0Rd2Sa7Te0Ug1Ve1Wg0Xb6Yb0Zd40d11f22c13b24c25a26d37a48e29",
                    "e2Ab4Bc0Cb1Dd4Ed0Fz9Gg0Ha4Ia7Jf2Kc2Lc5Mb2Nf0Oe3Pb6Qa6Re0Sf1Tc1Uc4Vg1Wa3Xa0Yb0Zc30e11a22b33b54a15d26a57d18d39",
                    "g1Ab2Ba4Ce2Dd4Eb4Fc3Gf1Ha2Ig0Jc2Kd2Le1Ma1Nb6Oc0Pc1Qe0Rf0Sf2Tb3Uc4Vc5Wb5Xd1Ya7Za00z91d02b03a54a65d36b17e38a39",
                    "b4Aa2Bz9Cf1Dd0Ea7Ff0Gb0Hb5Id4Jd2Kf2Lc3Mc4Nd1Oa0Pa1Qa4Re2Se1Tc5Uc0Vg0Wb6Xb1Ya3Za60d31c22a53b24e35g16e07b38c19",
                    "c5Aa6Bf0Cb0Da2Ea5Fc0Gb2Ha3Ib6Jd4Kb3Lb1Mc1Nc4Od3Pg0Qd1Re3Se2Ta0Ud2Ve1Wz9Xd0Ye0Zf20a11c22a73f14b55c36g17b48a49",
                    "c2Az9Bb4Cb2Dc1Ea6Fa7Ga4Hg0Ia1Jd1Ke0Lf0Mb1Nc0Of1Pd0Qg1Rd3Sc4Te2Ub5Vf2We1Xb0Ya5Zb30d21a32b63a04d45c36c57e38a29",
                    "a4Aa2Bb2Cc0Dc5Eb4Fa5Gc4Hf1Ia0Jf0Ke1Lb5Mc2Na3Of2Pz9Qb1Rd0Sd2Td3Ub6Vc1We2Xe3Yb0Zb30g01a12a73c34a65d46d17e08g19",
                    "b5Ae0Bb0Ca2De2Ec3Fa7Gf0Hd2Ia1Jc1Kd1La4Mb6Nd3Oa5Pc5Qe1Ra0Sf1Tg1Ub1Vb4Wa3Xc4Yb2Za60d41c22g03f24e35c06d07b38z99",
                    "e2Ad4Bb6Cf1Da3Ed0Fa5Ga0Hg0Ia7Je0Kc4Lg1Md2Ne1Oc1Pf0Qc3Rd1Sb3Tc2Uc0Va2Wb2Xa1Ya4Zd30b11c52f23b54b45e36a67b08z99",
                    "d4Ad1Ba7Cb3Db1Ee1Fd3Gc3Hb6Ic2Ja2Kf0Lc5Me3Ng0Oz9Pd2Qg1Rc0Sa5Tb4Ud0Va1Wf2Xe2Ya6Za40b01b22b53e04a05a36c17f18c49",
                    "b3Ab0Bd2Ce2Da7Ea4Ff0Gd4He1Ia0Jg0Kb6Lc5Mz9Nc0Oe3Pe0Qa3Rb4Sa2Tf2Ug1Vc1Wc4Xa1Yc2Za50f11c32b23d14d05d36b57a68b19",
                    "f1Aa7Ba0Cb6Da5Ec3Fb0Gc2Hg0Ie3Ja6Kc4La4Mf2Ne1Of0Pd2Qb3Rd3Sb2Tb1Ue0Ve2Wc0Xd1Yc5Zb40d01b52a33d44a15c16z97a28g19",
                    "e1Af2Bc4Ce0Dg1Ea7Fa0Gg0Hc3Ib4Jd3Kc1Lb5Mc0Ne2Od1Pd2Qa2Rb3Sc5Td4Ub1Vf0Wb0Xa1Ya3Ze30a41z92c23a64b25a56b67f18d09",
                    "b0Ac0Bf1Cb4De1Ea3Fc2Gz9Hb3Ia5Jc5Ke2Lb1Mf2Nd2Og0Pf0Qc4Rb2Sg1Ta7Ub5Vd4Wc3Xd1Ye0Ze30c11a62a03d34a25b66a17a48d09",
                    "a7Aa0Bb5Cg1Dd0Ea6Fe3Ga4Hg0Ie2Je1Ka3Lb3Md1Nd2Oz9Pb4Qd4Rc3Sf1Tc4Ua5Vb2Wb1Xc1Yf0Zb60d31c52b03f24c25a26a17c08e09",
                    "e3Ad4Ba5Cd1Dc1Eb3Fc5Gd2Hg0Ie0Ja2Kb5Lf1Md3Na6Oz9Pb1Qc3Rf2Sc4Tb0Uc0Ve1Wd0Xg1Ye2Zb60a71a32a03b24a45b46f07c28a19",
                    "g0Ac1Bb4Ca5Da2Ea6Ff0Gb1Ha3Id3Ja0Kz9Lc5Mb0Nf1Od2Pe1Qc2Re3Sb6Td0Ub5Va1Wb2Xc3Yb3Zc00e21e02a73d14f25a46g17c48d49"};



    static String[] ROWS = {"4YSMGA", "5ZTNHB", "60UOIC", "71VPJD", "82WQKE", "93XRLF"};
    static String[] COLS = {"456789", "YZ0123", "STUVWX", "MNOPQR", "GHIJKL", "ABCDEF"};
    public static String board;
    public static String locationsInMiddle;


    //the taskcode isMoveLegal
    public static boolean isMoveLegal(String placement, char locationChar) {
        // FIXME Task 5: determine whether a given move is legal
        //author: Zhixin Guo Revisor:Raiyan Ahsan (u6437444).

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

    //the taskcode getLocationIndex
    static int getLocationIndex(String placement, char locationChar) {
        for (int i = 0; i < placement.length() / 3; i++) {
            if (placement.charAt(i * 3 + 2) == locationChar) {
                return i * 3 + 2;
            }
        }
        return -1;
    }
    //the task code kingdoms
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

    //      Map<String,String> cardAccordingMap=new HashMap<>();
    //creat this method for storing the card characters as the key and the supporters as the value
    HashMap creatCard()
    {
        HashMap<String,String> cardAccordingMap=new HashMap<>();
        cardAccordingMap.put("a0","秦：孝公");
        cardAccordingMap.put("a1","秦：白起");
        cardAccordingMap.put("a2","秦：商鞅");
        cardAccordingMap.put("a3","秦：昭襄王");
        cardAccordingMap.put("a4","秦：政王");
        cardAccordingMap.put("a5","秦：芈月");
        cardAccordingMap.put("a6","秦：赵姬");
        cardAccordingMap.put("a7","秦：惠文王");

        cardAccordingMap.put("b0","齐：建王");
        cardAccordingMap.put("b1","齐：孟尝君");
        cardAccordingMap.put("b2","齐：钟无艳");
        cardAccordingMap.put("b3","齐：君王后");
        cardAccordingMap.put("b4","齐：宣王");
        cardAccordingMap.put("b5","齐：孙膑");
        cardAccordingMap.put("b6","齐：襄王");

        cardAccordingMap.put("c0","楚：幽王");
        cardAccordingMap.put("c1","楚：哀王");
        cardAccordingMap.put("c2","楚：考烈王");
        cardAccordingMap.put("c3","楚：春申君");
        cardAccordingMap.put("c4","楚：吴起");
        cardAccordingMap.put("c5","楚：屈原");

        cardAccordingMap.put("d0","赵：孝成王");
        cardAccordingMap.put("d1","赵：李牧");
        cardAccordingMap.put("d2","赵：武灵王");
        cardAccordingMap.put("d3","赵：廉颇");
        cardAccordingMap.put("d4","赵：平原君");

        cardAccordingMap.put("e0","赵：安王");
        cardAccordingMap.put("e1","赵：恒惠王");
        cardAccordingMap.put("e2","赵：韩非");
        cardAccordingMap.put("e3","赵：韩哀侯");

        cardAccordingMap.put("f0","魏：安禧王");
        cardAccordingMap.put("f1","魏：信陵君");
        cardAccordingMap.put("f2","魏：魏文侯");

        cardAccordingMap.put("g0","燕：太子丹");
        cardAccordingMap.put("g1","燕：喜王");

        cardAccordingMap.put("z9","张仪");









        return cardAccordingMap;
    }

    //set beautiful color for good looking
    Color setTextColor(String card)
    {
        char nationality=card.charAt(0);
        if (nationality=='a')
        {
            return Color.RED;
        }
        if (nationality=='b')
        {
            return Color.ROYALBLUE;
        }
        if (nationality=='c')
        {
            return Color.GRAY;
        }
        if (nationality=='d')
        {
            return Color.GREEN;
        }
        if (nationality=='e')
        {
            return Color.BLUE;
        }
        if (nationality=='f')
        {
            return Color.BROWN;
        }
        if (nationality=='g')
        {
            return Color.PURPLE;
        }
        else {
            return Color.CRIMSON;
        }
    }

    // task code getSupporters
    public static String getSupporters(String setup, String moveSequence, int numPlayers, int playerId) {
        // FIXME Task 7: get the list of supporters for a given player after a sequence of moves
        //author: Raiyan Ahsan (u6437444). Reviser: Zhixini Guo (u6371520)
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
    //task code: getFlags
    public static int[] getFlags(String setup, String moveSequence, int numPlayers) {
        // FIXME Task 8: determine which player controls the flag of each kingdom after a given sequence of moves
        //author: Raiyan Ahsan (u6437444). Reviser: Zhixini Guo (u6371520)
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
    //setup the scoreBoard

    /**
     * these code create a score board with the credit of the players and the winner for now
     * @param setup the String of Setting up the game board
     * @param moveSequence  the String of the move sequence
     * @param numPlayers    the numbers of the players
     */
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

        Button [] playerID=new Button[5];

        for (int i=0;i<5;i++)
        {

            playerID[i]=new Button("");
            playerID[i].setPrefSize(250,30);
            playerSituation.add(playerID[i],1,i);
        }


        for (int i=0;i<numPlayers;++i)
        {
            System.out.println("player"+i);
            String supporters=getSupporters(setup, moveSequence, numPlayers, i);
            haveSupporters[i]=supporters;
            System.out.println(supporters);

            playerID[i].setText("player"+" "+i+" get supporters of"+" "+supporters);

//            playerSituation.add(playerID[i],1,i);
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
        playerID[4].setText("winner is player "+winner+" now");



        HBox hbPlayer=new HBox();
        hbPlayer.getChildren().add(playerSituation);        //make these component together
        hbPlayer.setLayoutY(100);                       //set layout y
        hbPlayer.setLayoutX(650);                       //set layout x
        controls.getChildren().add(hbPlayer);
    }


    /**
     * Set up the game board with buttons, users is able to click the card to play the game.
     * @param placement a string of Setting up the game board
     * @param numPlayers the mumber of the players
     */

    void makePlacement(String placement, int numPlayers ) {
        // FIXME Task 4: implement the simple placement viewer

        int listLength=placement.length()/3;
        final HashMap cardMap = creatCard();


        String [] orientationList=new String [listLength];
        String [] cardList=new String[listLength];


        int jj=0;
        for(int i=0; i<listLength;++i)
        {
            cardList[i]=Character.toString(placement.charAt(jj))+Character.toString(placement.charAt(jj+1));
            orientationList[i]=Character.toString(placement.charAt(jj+2));
            jj=jj+3;


        }



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
        for(int i=0;i<listLength;++i)

        {
            swap[i]=(int)orientationList[i].charAt(0);
        }

        Label path=new Label("");


        for(int i=0; i<listLength; ++i)
        {

            if((orientationList[i].charAt(0)<=70)&&(orientationList[i].charAt(0)>=65))
            {

                tempColumn=5;
                tempRow=((int)orientationList[i].charAt(0))-5*13;

                button1[tempColumn][tempRow].setText((String) (cardMap.get(cardList[i])));
                button1[tempColumn][tempRow].setPrefSize(95,95);
                button1[tempColumn][tempRow].setTextFill(setTextColor(cardList[i]));





                String replacement=placement;
                if (isMoveLegal(placement,orientationList[i].charAt(0)))
                {

                    Button x=button1[tempColumn][tempRow];
                    String currentsupporters=getSupporters(placement,orientationList[i],1,0);

                    String remove="z9"+placement.charAt(placement.indexOf('z') + 2);
                    String currentReplace=cardList[i];
                    String currentMove=orientationList[i];

                    String replace = replacement.replace(currentReplace, "z9");



                    String replace2=replace.replace(remove,"");
                    for (int k=0;k<placement.length();++k)
                    {
                        for (int p = 0; p < currentsupporters.length(); p = p + 2)
                        {
                            if ((currentsupporters.charAt(p)==placement.charAt(k))&&(currentsupporters.charAt(p+1)==placement.charAt(k+1)))
                            {
                                replace2=replace2.replace(String.valueOf(placement.charAt(k))+String.valueOf(placement.charAt(k+1))+String.valueOf(placement.charAt(k+2)),"");
                            }
                        }
                    }
                    String replaced=replace2;
                    boolean problem;
                    x.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent e) {


                            if (moveSequence==null)
                            {
                                moveSequence=currentMove;
                            }else {
                                moveSequence+=currentMove;
                            }

                            System.out.println(moveSequence);
                            makePlacement(replaced,numPlayers);
                            System.out.println(moveSequence);

                            System.out.println(boardSetting);
                            System.out.println(numPlayers);
                            playerSituation(boardSetting,moveSequence,numPlayers);


                        }

                    });





                }

            }

            if((orientationList[i].charAt(0)<=76)&&(orientationList[i].charAt(0)>=71))
            {
                tempColumn=4;
                tempRow=((int)orientationList[i].charAt(0))-71;


                button1[tempColumn][tempRow].setText((String) (cardMap.get(cardList[i])));
                button1[tempColumn][tempRow].setPrefSize(95,95);
                button1[tempColumn][tempRow].setTextFill(setTextColor(cardList[i]));
                String replacement=placement;
                if (isMoveLegal(placement,orientationList[i].charAt(0)))
                {

                    Button x=button1[tempColumn][tempRow];
                    String currentsupporters=getSupporters(placement,orientationList[i],1,0);

                    String remove="z9"+placement.charAt(placement.indexOf('z') + 2);
                    String currentReplace=cardList[i];
                    String currentMove=orientationList[i];

                    String replace = replacement.replace(currentReplace, "z9");



                    String replace2=replace.replace(remove,"");
                    for (int k=0;k<placement.length();++k)
                    {
                        for (int p = 0; p < currentsupporters.length(); p = p + 2)
                        {
                            if ((currentsupporters.charAt(p)==placement.charAt(k))&&(currentsupporters.charAt(p+1)==placement.charAt(k+1)))
                            {
                                replace2=replace2.replace(String.valueOf(placement.charAt(k))+String.valueOf(placement.charAt(k+1))+String.valueOf(placement.charAt(k+2)),"");
                            }
                        }
                    }
                    String replaced=replace2;
                    boolean problem;
                    x.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent e) {


                            if (moveSequence==null)
                            {
                                moveSequence=currentMove;
                            }else {
                                moveSequence+=currentMove;
                            }

                            System.out.println(moveSequence);
                            makePlacement(replaced,numPlayers);
                            System.out.println(moveSequence);
                            System.out.println(boardSetting);
                            System.out.println(numPlayers);
                            playerSituation(boardSetting,moveSequence,numPlayers);


                        }

                    });





                }

            }


            if((orientationList[i].charAt(0)<=82)&&(orientationList[i].charAt(0)>=77))
            {
                tempColumn=3;
                tempRow=((int)orientationList[i].charAt(0))-77;

                button1[tempColumn][tempRow].setText((String) (cardMap.get(cardList[i])));
                button1[tempColumn][tempRow].setPrefSize(95,95);
                button1[tempColumn][tempRow].setTextFill(setTextColor(cardList[i]));
                String replacement=placement;
                if (isMoveLegal(placement,orientationList[i].charAt(0)))
                {

                    Button x=button1[tempColumn][tempRow];
                    String currentsupporters=getSupporters(placement,orientationList[i],1,0);

                    String remove="z9"+placement.charAt(placement.indexOf('z') + 2);
                    String currentReplace=cardList[i];
                    String currentMove=orientationList[i];

                    String replace = replacement.replace(currentReplace, "z9");



                    String replace2=replace.replace(remove,"");
                    for (int k=0;k<placement.length();++k)
                    {
                        for (int p = 0; p < currentsupporters.length(); p = p + 2)
                        {
                            if ((currentsupporters.charAt(p)==placement.charAt(k))&&(currentsupporters.charAt(p+1)==placement.charAt(k+1)))
                            {
                                replace2=replace2.replace(String.valueOf(placement.charAt(k))+String.valueOf(placement.charAt(k+1))+String.valueOf(placement.charAt(k+2)),"");
                            }
                        }
                    }
                    String replaced=replace2;
                    boolean problem;
                    x.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent e) {


                            if (moveSequence==null)
                            {
                                moveSequence=currentMove;
                            }else {
                                moveSequence+=currentMove;
                            }

                            System.out.println(moveSequence);
                            makePlacement(replaced,numPlayers);
                            System.out.println(moveSequence);
                            System.out.println(boardSetting);
                            System.out.println(numPlayers);
                            playerSituation(boardSetting,moveSequence,numPlayers);


                        }

                    });





                }

            }

            if((orientationList[i].charAt(0)<=88)&&(orientationList[i].charAt(0)>=83))
            {
                tempColumn=2;
                tempRow=((int)orientationList[i].charAt(0))-83;

                button1[tempColumn][tempRow].setText((String) (cardMap.get(cardList[i])));
                button1[tempColumn][tempRow].setPrefSize(95,95);
                button1[tempColumn][tempRow].setTextFill(setTextColor(cardList[i]));
                String replacement=placement;
                if (isMoveLegal(placement,orientationList[i].charAt(0)))
                {

                    Button x=button1[tempColumn][tempRow];
                    String currentsupporters=getSupporters(placement,orientationList[i],1,0);

                    String remove="z9"+placement.charAt(placement.indexOf('z') + 2);
                    String currentReplace=cardList[i];
                    String currentMove=orientationList[i];

                    String replace = replacement.replace(currentReplace, "z9");



                    String replace2=replace.replace(remove,"");
                    for (int k=0;k<placement.length();++k)
                    {
                        for (int p = 0; p < currentsupporters.length(); p = p + 2)
                        {
                            if ((currentsupporters.charAt(p)==placement.charAt(k))&&(currentsupporters.charAt(p+1)==placement.charAt(k+1)))
                            {
                                replace2=replace2.replace(String.valueOf(placement.charAt(k))+String.valueOf(placement.charAt(k+1))+String.valueOf(placement.charAt(k+2)),"");
                            }
                        }
                    }
                    String replaced=replace2;
                    boolean problem;
                    x.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent e) {


                            if (moveSequence==null)
                            {
                                moveSequence=currentMove;
                            }else {
                                moveSequence+=currentMove;
                            }

                            System.out.println(moveSequence);
                            makePlacement(replaced,numPlayers);
                            System.out.println(moveSequence);
                            System.out.println(boardSetting);
                            System.out.println(numPlayers);
                            playerSituation(boardSetting,moveSequence,numPlayers);


                        }

                    });





                }

            }

            if((orientationList[i].charAt(0)<=90)&&(orientationList[i].charAt(0)>=89))
            {
                tempColumn=1;
                tempRow=((int)orientationList[i].charAt(0))-89;

                button1[tempColumn][tempRow].setText((String) (cardMap.get(cardList[i])));
                button1[tempColumn][tempRow].setPrefSize(95,95);
                button1[tempColumn][tempRow].setTextFill(setTextColor(cardList[i]));
                String replacement=placement;
                if (isMoveLegal(placement,orientationList[i].charAt(0)))
                {

                    Button x=button1[tempColumn][tempRow];
                    String currentsupporters=getSupporters(placement,orientationList[i],1,0);

                    String remove="z9"+placement.charAt(placement.indexOf('z') + 2);
                    String currentReplace=cardList[i];
                    String currentMove=orientationList[i];

                    String replace = replacement.replace(currentReplace, "z9");



                    String replace2=replace.replace(remove,"");
                    for (int k=0;k<placement.length();++k)
                    {
                        for (int p = 0; p < currentsupporters.length(); p = p + 2)
                        {
                            if ((currentsupporters.charAt(p)==placement.charAt(k))&&(currentsupporters.charAt(p+1)==placement.charAt(k+1)))
                            {
                                replace2=replace2.replace(String.valueOf(placement.charAt(k))+String.valueOf(placement.charAt(k+1))+String.valueOf(placement.charAt(k+2)),"");
                            }
                        }
                    }
                    String replaced=replace2;
                    boolean problem;
                    x.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent e) {


                            if (moveSequence==null)
                            {
                                moveSequence=currentMove;
                            }else {
                                moveSequence+=currentMove;
                            }

                            System.out.println(moveSequence);
                            makePlacement(replaced,numPlayers);
                            System.out.println(moveSequence);
                            System.out.println(boardSetting);
                            System.out.println(numPlayers);
                            playerSituation(boardSetting,moveSequence,numPlayers);


                        }

                    });





                }
            }

            if((orientationList[i].charAt(0)<=51)&&(orientationList[i].charAt(0)>=48))
            {
                tempColumn=1;
                tempRow=((int)orientationList[i].charAt(0))-46;

                button1[tempColumn][tempRow].setText((String) (cardMap.get(cardList[i])));
                button1[tempColumn][tempRow].setPrefSize(95,95);
                button1[tempColumn][tempRow].setTextFill(setTextColor(cardList[i]));
                String replacement=placement;
                if (isMoveLegal(placement,orientationList[i].charAt(0)))
                {

                    Button x=button1[tempColumn][tempRow];
                    String currentsupporters=getSupporters(placement,orientationList[i],1,0);

                    String remove="z9"+placement.charAt(placement.indexOf('z') + 2);
                    String currentReplace=cardList[i];
                    String currentMove=orientationList[i];

                    String replace = replacement.replace(currentReplace, "z9");



                    String replace2=replace.replace(remove,"");
                    for (int k=0;k<placement.length();++k)
                    {
                        for (int p = 0; p < currentsupporters.length(); p = p + 2)
                        {
                            if ((currentsupporters.charAt(p)==placement.charAt(k))&&(currentsupporters.charAt(p+1)==placement.charAt(k+1)))
                            {
                                replace2=replace2.replace(String.valueOf(placement.charAt(k))+String.valueOf(placement.charAt(k+1))+String.valueOf(placement.charAt(k+2)),"");
                            }
                        }
                    }
                    String replaced=replace2;
                    boolean problem;
                    x.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent e) {


                            if (moveSequence==null)
                            {
                                moveSequence=currentMove;
                            }else {
                                moveSequence+=currentMove;
                            }

                            System.out.println(moveSequence);
                            makePlacement(replaced,numPlayers);
                            System.out.println(moveSequence);
                            System.out.println(boardSetting);
                            System.out.println(numPlayers);
                            playerSituation(boardSetting,moveSequence,numPlayers);


                        }

                    });





                }

            }

            if((orientationList[i].charAt(0)<=57)&&(orientationList[i].charAt(0)>=52))
            {
                tempColumn=0;
                tempRow=((int)orientationList[i].charAt(0))-52;

                button1[tempColumn][tempRow].setText((String) (cardMap.get(cardList[i])));
                button1[tempColumn][tempRow].setPrefSize(95,95);
                button1[tempColumn][tempRow].setTextFill(setTextColor(cardList[i]));
                String replacement=placement;
                if (isMoveLegal(placement,orientationList[i].charAt(0)))
                {

                    Button x=button1[tempColumn][tempRow];
                    String currentsupporters=getSupporters(placement,orientationList[i],1,0);

                    String remove="z9"+placement.charAt(placement.indexOf('z') + 2);
                    String currentReplace=cardList[i];
                    String currentMove=orientationList[i];

                    String replace = replacement.replace(currentReplace, "z9");



                    String replace2=replace.replace(remove,"");
                    for (int k=0;k<placement.length();++k)
                    {
                        for (int p = 0; p < currentsupporters.length(); p = p + 2)
                        {
                            if ((currentsupporters.charAt(p)==placement.charAt(k))&&(currentsupporters.charAt(p+1)==placement.charAt(k+1)))
                            {
                                replace2=replace2.replace(String.valueOf(placement.charAt(k))+String.valueOf(placement.charAt(k+1))+String.valueOf(placement.charAt(k+2)),"");
                            }
                        }
                    }
                    String replaced=replace2;
                    boolean problem;
                    x.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent e) {


                            if (moveSequence==null)
                            {
                                moveSequence=currentMove;
                            }else {
                                moveSequence+=currentMove;
                            }

                            System.out.println(moveSequence);
                            makePlacement(replaced,numPlayers);
                            System.out.println(moveSequence);
                            System.out.println(boardSetting);
                            System.out.println(numPlayers);
                            playerSituation(boardSetting,moveSequence,numPlayers);


                        }

                    });





                }

            }


        }




        HBox hb1=new HBox();
        hb1.getChildren().addAll(gridPane,path);
        controls.getChildren().add(hb1);
    }

    /**
     * Create a basic text field for input and a refresh button.
     */
    void makeControls() {
        Label label1 = new Label("Players' Number:");
//        label1.setLayoutX(0);
        textField = new TextField();
        textField.setPrefWidth(30);
//        textField.setLayoutX(0);
        Button play=new Button("Play");
        Button button = new Button("Replay");


        makePlacement(boardSetUp[0],0);
        boardSetting=boardSetUp[0];
        play.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
//                playerSituation(boardSetUp[0],"",Integer.parseInt(textField.getText()));
                playerNUMBER=textField.getText();

                String setBoarding;
                Random rand=new Random();
                int i=rand.nextInt(20);
                setBoarding=boardSetUp[i];
                boardSetting=setBoarding;
                makePlacement(setBoarding,Integer.parseInt(playerNUMBER));
                System.out.println(playerNUMBER);
//                playerSituation(boardSetUp[0],"",Integer.parseInt(playerNUMBER));
            }
        });
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
//                System.out.println(textField.getText());
                String setBoarding;
                Random rand=new Random();
                int i=rand.nextInt(20);
                setBoarding=boardSetUp[i];
                boardSetting=setBoarding;

                playerNUMBER=textField.getText();
                makePlacement(setBoarding,Integer.parseInt(playerNUMBER));
                System.out.println(playerNUMBER);






            }
        });
        HBox hb = new HBox();
        hb.getChildren().addAll(label1,textField,play,button);
        hb.setSpacing(10);
        hb.setLayoutX(100);
        hb.setLayoutY(VIEWER_HEIGHT - 50);
//        hb.setLayoutY(0);
        controls.getChildren().addAll(hb);



    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Warring States Game");
        Scene scene = new Scene(root, VIEWER_WIDTH, VIEWER_HEIGHT);



        root.getChildren().add(controls);

        makeControls();


        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
