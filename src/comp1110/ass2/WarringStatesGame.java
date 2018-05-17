package comp1110.ass2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static java.lang.Character.isDigit;
import static java.lang.Character.isUpperCase;


/**
 * This class provides the text interface for the Warring States game
 */
public class WarringStatesGame {
    public static String board;
    public static String locationsInMiddle;
    /**
     * Determine whether a card placement is well-formed according to the following:
     * - it consists of exactly three characters
     * - the first character is in the range a..g (kingdom) or is z (Zhang Yi)
     * - the second character is numeric, and is a valid character number for that kingdom (9 for Zhang Yi)
     * - the third character is in the range A .. Z or 0..9 (location)
     *
     * @param cardPlacement A string describing a card placement
     * @return true if the card placement is well-formed
     */

    static String[] stringArray(String col, char zhangLocation, char locationChar) {
        String[] condition = new String[2];
        int zhangIndex = col.indexOf(zhangLocation);
        int locToMove = col.indexOf(locationChar);
        if (zhangIndex < locToMove) {
            condition[0] = col.substring(zhangIndex, locToMove + 1);
            condition[1] = col.substring(locToMove + 1);
        } else {
            condition[0] = col.substring(locToMove, zhangIndex);
            condition[1] = col.substring(0, locToMove);
        }
        System.out.println(condition[0] + " " + condition[1]);
        return condition;
    }





    public static boolean isCardPlacementWellFormed(String cardPlacement) {
        // FIXME Task 2: determine whether a card placement is well-formed]
        //author: Zhixin Guo Revisor:Raiyan Ahsan (u6437444).
        char kingdom = cardPlacement.charAt(0);
        char num = cardPlacement.charAt(1);
        char position = cardPlacement.charAt(2);

        return  cardPlacement.length() == 3
                && (isUpperCase(position) || isDigit(position))
                && position < 91
                && isDigit(num)
                && ((kingdom == 'a' && num <  57)
                ||  (kingdom == 'b' && num <  55)
                ||  (kingdom == 'c' && num <  54)
                ||  (kingdom == 'd' && num <  53)
                ||  (kingdom == 'e' && num <  52)
                ||  (kingdom == 'f' && num <  51)
                ||  (kingdom == 'g' && num <  50)
                ||  (kingdom == 'z' && num == 57));
    }





    /**
     * Determine whether a placement string is well-formed:
     * - it consists of exactly N three-character card placements (where N = 1 .. 36);
     * - each card placement is well-formed
     * - no card appears more than once in the placement
     * - no location contains more than one card
     *
     * @param placement A string describing a placement of one or more cards
     * @return true if the placement is well-formed
     */
    public static boolean isPlacementWellFormed(String placement) {
        // FIXME Task 3: determine whether a placement is well-formed
        //author: Zhixin Guo Revisor:Raiyan Ahsan (u6437444).

        if (placement == null) {
            return false;
        }
        int length = placement.length();
        if (length % 3 != 0 || length == 0) {
            return false;
        }
        List<String> cards = new ArrayList<>();
        List<Character> locs = new ArrayList<>();
        for (int i = 0; i < placement.length() / 3; i++) {
            String card = placement.substring(i * 3, i * 3 + 3);
            if (cards.contains(card.substring(0, 2)) || locs.contains(card.charAt(2))) {
                return false;
            }
            if (!isCardPlacementWellFormed(card)) {
                return false;
            }
            cards.add(card.substring(0, 2));
            locs.add(card.charAt(2));
        }
        return true;
    }

    /**
     * Determine whether a given move is legal given a provided valid placement:
     * - the location char is in the range A .. Z or 0..9
     * - there is a card at the chosen location;
     * <p>
     * - the location is in the same row or column of the grid as Zhang Yi's current position; and
     * <p>
     * <p>
     * - drawing a line from Zhang Yi's current location through the card at the chosen location,
     * there are no other cards along the line from the same kingdom as the chosen card
     * <p>
     * that are further away from Zhang Yi.
     *
     * @param placement    the current placement string
     * @param locationChar a location for Zhang Yi to move to
     * @return true if Zhang Yi may move to that location
     */
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

    /**
     * Determine whether a move sequence is valid.
     * To be valid, the move sequence must be comprised of 1..N location characters
     * showing the location to move for Zhang Yi, and each move must be valid
     * given the placement that would have resulted from the preceding sequence
     * of moves.
     *
     * @param setup        A placement string representing the board setup
     * @param moveSequence a string of location characters representing moves
     * @return True if the placement sequence is valid
     */
    public static boolean isMoveSequenceValid(String setup, String moveSequence) {
        // FIXME Task 6: determine whether a placement sequence is valid
        //author:Raiyan Ahsan (u6437444).
        //valid sequence
        board = setup;
        for (int i = 0; i < moveSequence.length(); i++) {
            System.out.println("Board : " + board);
            if (!isMoveLegal(board, moveSequence.charAt(i))) return false;
        }
        return true;
    }

    /**
     * Get the list of supporters for the chosen player, given the provided
     * setup and move sequence.
     * The list of supporters is a sequence of two-character card IDs, representing
     * the cards that the chosen player collected by moving Zhang Yi.
     *
     * @param setup        A placement string representing the board setup
     * @param moveSequence a string of location characters representing moves
     * @param numPlayers   the number of players in the game, must be in the range [2..4]
     * @param playerId     the player number for which to get the list of supporters, [0..(numPlayers-1)]
     * @return the list of supporters for the given player
     */
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
        System.out.println(sortd);
        return sortd;                                               //return the characters which had been captured by the chosen player (according to its ID) with an order
    }

    /**
     * Given a setup and move sequence, determine which player controls the flag of each kingdom
     * after all the moves in the sequence have been played.
     *
     * @param setup        A placement string representing the board setup
     * @param moveSequence a string of location characters representing a sequence of moves
     * @param numPlayers   the number of players in the game, must be in the range [2..4]
     * @return an array containing the player ID who controls each kingdom, where
     * - element 0 contains the player ID of the player who controls the flag of Qin
     * - element 1 contains the player ID of the player who controls the flag of Qi
     * - element 2 contains the player ID of the player who controls the flag of Chu
     * - element 3 contains the player ID of the player who controls the flag of Zhao
     * - element 4 contains the player ID of the player who controls the flag of Han
     * - element 5 contains the player ID of the player who controls the flag of Wei
     * - element 6 contains the player ID of the player who controls the flag of Yan
     * If no player controls a particular house, the element for that house will have the value -1.
     */
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

    /**
     * Generate a legal move, given the provided placement string.
     * A move is valid if:
     * - the location char is in the range A .. Z or 0..9
     * - there is a card at the chosen location;
     * - the destination location is different to Zhang Yi's current location;
     * - the destination is in the same row or column of the grid as Zhang Yi's current location; and
     * - drawing a line from Zhang Yi's current location through the card at the chosen location,
     * there are no other cards along the line from the same kingdom as the chosen card
     * that are further away from Zhang Yi.
     * If there is no legal move available, return the null character '\0'.
     *
     * @param placement the current placement string
     * @return a location character representing Zhang Yi's destination for the move
     */
    static String[] ROWS = {"4YSMGA", "5ZTNHB", "60UOIC", "71VPJD", "82WQKE", "93XRLF"};
    static String[] COLS = {"456789", "YZ0123", "STUVWX", "MNOPQR", "GHIJKL", "ABCDEF"};

    public static char generateMove(String placement) {
        // FIXME Task 10: generate a legal move
        //author: Raiyan Ahsan (u6437444).
        String legalMoves = possibleMoves(placement);
        if (legalMoves.length() == 0) {
            return '\0';
        }
        Random random= new Random();
        int i = random.nextInt(legalMoves.length());
        return legalMoves.charAt(i);

    }

    public static String possibleMoves (String placement) {
        char yi_position = placement.charAt(placement.indexOf('z') + 2);
        String loc = "";
        for (String col : COLS) {
            if (col.contains(yi_position + "")) {
                int yi_index = col.indexOf(yi_position);
                loc += col.substring(0, yi_index) + col.substring(yi_index + 1);
                continue;
            }
        }
        for (String row : ROWS) {
            if (row.contains(yi_position + "")) {
                int yi_index = row.indexOf(yi_position);
                loc += row.substring(0, yi_index) + row.substring(yi_index + 1);
                continue;
            }
        }

        String legalMoves = "";
        for (int i = 0; i < loc.length(); i++) {
            if (isMoveLegal(placement, loc.charAt(i))) {

                legalMoves += loc.charAt(i);
            }
        }
        return legalMoves;
    }

//    public static List<String> possibleStates (String legalMoves, String placement) {
//        List<String> states = new ArrayList<>();
//        for (int i = 0; i < legalMoves.length(); i++) {
//        }
//    }

//    public static String updateBoard(String placement, char locChar) {
//        int yi_index = placement.indexOf('z');
//    }
}
