package comp1110.ass2;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import static java.lang.Character.isDigit;
import static java.lang.Character.isISOControl;
import static java.lang.Character.isUpperCase;

/**
 * This class provides the text interface for the Warring States game
 */
public class WarringStatesGame {
public static String board;
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
    static boolean isCardPlacementWellFormed(String cardPlacement) {
        // FIXME Task 2: determine whether a card placement is well-formed]

        char kingdom = cardPlacement.charAt(0);
        char num = cardPlacement.charAt(1);
        char position = cardPlacement.charAt(2);

        return cardPlacement.length() == 3
                && (isUpperCase(position) || isDigit(position))
                && position < 91
                && isDigit(num)
                && ((kingdom == 'a' && num < 57)
                || (kingdom == 'b' && num < 55)
                || (kingdom == 'c' && num < 54)
                || (kingdom == 'd' && num < 53)
                || (kingdom == 'e' && num < 52)
                || (kingdom == 'f' && num < 51)
                || (kingdom == 'g' && num < 50)
                || (kingdom == 'z' && num == 57));
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
    static boolean isPlacementWellFormed(String placement) {
        // FIXME Task 3: determine whether a placement is well-formed
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
        int yi_index = placement.indexOf('z');
        char yi_position = placement.charAt(yi_index + 2);
        if (!((locationChar >= 'A' && locationChar <= 'Z') || (locationChar >= '0' && locationChar <= '9'))) {
            return false;
        }
        int loc = getLocationIndex(placement,locationChar);
        if (loc == -1) {
            return false;
        }
        char kingdomToBeCaptured = placement.charAt(loc - 2);
        String locationsInMiddle = "";
        String locationsOutOfRange = "";

        for (String col : COLS) {
            if (col.contains(yi_position + "") && col.contains(locationChar + "")) {
                int yi_int = col.indexOf(yi_position);
                int loc_int = col.indexOf(locationChar);
                if (yi_int > loc_int) {
                    locationsInMiddle += col.substring(loc_int,yi_int);
                    locationsOutOfRange += col.substring(0,loc_int);
                }
                else {
                    locationsInMiddle += col.substring(yi_int + 1,loc_int + 1);
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
                    locationsInMiddle += row.substring(loc_int,yi_int);
                    locationsOutOfRange += row.substring(0,loc_int);
                }
                else {
                    locationsInMiddle += row.substring(yi_int + 1,loc_int + 1);
                    locationsOutOfRange += row.substring(loc_int + 1);
                }

                continue;
            }
        }
        if (locationsInMiddle.equals("")) {
            return false;
        }

        String kingdoms = kingdoms(placement,locationsOutOfRange);
        for (int i = 0; i < kingdoms.length(); i++) {
            if (kingdoms.charAt(i) == kingdomToBeCaptured) {
                return false;
            }
        }
        System.out.println("Updating Board");
        board = update(placement,locationsInMiddle,kingdomToBeCaptured,locationChar);
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
            int index = getLocationIndex(placement,locations.charAt(i));
            if (index != -1) {
                kingdoms += placement.charAt(index - 2);
            }
        }
        return kingdoms;
    }

    static String update(String placement, String locationsInMiddle, char kingdomToBeCaptured, char locationChar) {
        for (int i = 0 ; i < locationsInMiddle.length(); i++) {
            int locInt = getLocationIndex(placement,locationsInMiddle.charAt(i));
            if (locInt != -1) {
            char kingdom = placement.charAt(locInt - 2);
            if (kingdom == kingdomToBeCaptured) {
                placement = placement.substring(0,locInt-2) + placement.substring(locInt + 1);
                int yi_position = placement.indexOf('z');
                placement = placement.substring(0,yi_position) + placement.substring(yi_position+3) + "z9" + locationChar;
            }}
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
    static boolean isMoveSequenceValid(String setup, String moveSequence) {
        // FIXME Task 6: determine whether a placement sequence is valid
        //valid sequence
        board = setup;
        for (int i = 0; i < moveSequence.length(); i++) {
            System.out.println("Board : " + board);
            if (!isMoveLegal(board,moveSequence.charAt(i))) return false;
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

        return null;
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
        return null;
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
        char yi_position = placement.charAt(placement.indexOf('z') + 2);
        String loc = "";
        for (String col : COLS) {
            if (col.contains(yi_position + "")) {
                int yi_index = col.indexOf(yi_position);
                System.out.println(yi_index);
                loc += col.substring(0, yi_index) + col.substring(yi_index + 1);
                continue;
            }
        }
        for (String row : ROWS) {
            if (row.contains(yi_position + "")) {
                int yi_index = row.indexOf(yi_position);
                System.out.println(yi_index);
                loc += row.substring(0, yi_index) + row.substring(yi_index + 1);
                loc += row;
                continue;
            }
        }

        for (int i = 0; i < loc.length(); i++) {
            if (isMoveLegal(placement, loc.charAt(i))) {
                return loc.charAt(i);
            }
        }
        return '\0';
    }
}
