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
    private final Group grid=new Group();
    TextField textField;


    /**
     * Draw a placement in the window, removing any previously drawn one
     *
     * @param placement A valid placement string
     */

     void makePlacement(String placement) {
        // FIXME Task 4: implement the simple placement viewer
         int listLength=placement.length()/3;
        String [] orientationList=new String [listLength];
        String [] cardList=new String[listLength];
        int jj=0;
        for(int i=0; i<listLength;++i)
        {
//            for (int j=0; j<placement.length();j=j+3)
//            {

                cardList[i]=Character.toString(placement.charAt(jj))+Character.toString(placement.charAt(jj+1));
                orientationList[i]=Character.toString(placement.charAt(jj+2));
//                continue;
//            }
//            System.out.println(orientationList[i]);
            jj=jj+3;


        }
//         System.out.println(orientationList);
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
             System.out.println(orientationList[i]);
             swap[i]=(int)orientationList[i].charAt(0);
             System.out.println(swap[i]);
         }

         for(int i=0; i<listLength; ++i)
        {
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




         HBox hb1=new HBox();
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
