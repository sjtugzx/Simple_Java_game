package comp1110.ass2;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import static java.lang.Character.isDigit;
import static java.lang.Character.isUpperCase;

/**
 * This class provides the text interface for the Warring States game
 */
public class WarringStatesGame {

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
    static boolean isPlacementWellFormed(String placement) {
        // FIXME Task 3: determine whether a placement is well-formed

        if (placement == null)
        {
            return false;
        }


        int length = placement.length();
        //condition 1
        if (length > 108 || length < 3 || length % 3 != 0 )
        {
            return false;
        }
        //condition 2

        for(int i = 0; i < length; i += 3) {
            char kingdonm = placement.charAt(i);
            char num = placement.charAt(i + 1);
            char position = placement.charAt(i + 2);

            if ((kingdonm == 'a') && ((num <= 55) && (num >= 48)) && ((position <= 90 && position >= 65) || (position >= 48) && (position <= 57))) {
                continue;
            }
            if ((kingdonm == 'b') && ((num <= 54) && (num >= 48)) && ((position <= 90 && position >= 65) || (position >= 48) && (position <= 57))) {
                continue;
            }
            if ((kingdonm == 'c') && ((num <= 53) && (num >= 48)) && ((position <= 90 && position >= 65) || (position >= 48) && (position <= 57))) {
                continue;
            }
            if ((kingdonm == 'd') && ((num <= 52) && (num >= 48)) && ((position <= 90 && position >= 65) || (position >= 48) && (position <= 57))) {
                continue;
            }
            if ((kingdonm == 'e') && ((num <= 51) && (num >= 48)) && ((position <= 90 && position >= 65) || (position >= 48) && (position <= 57))) {
                continue;
            }
            if ((kingdonm == 'f') && ((num <= 50) && (num >= 48)) && ((position <= 90 && position >= 65) || (position >= 48) && (position <= 57))) {
                continue;
            }
            if ((kingdonm == 'g') && ((num <= 49) && (num >= 48)) && ((position <= 90 && position >= 65) || (position >= 48) && (position <= 57))) {
                continue;
            }
            if ((kingdonm == 'z') && (num == '9') && ((position <= 90 && position >= 65) || (position >= 48) && (position <= 57))) {
                continue;
            } else {
                System.out.println(placement+"3");
                return false;

            }

        }

        //condition3 and condition 4
        List<String> usedCard=new ArrayList<String>();
        List<String> usedPlace=new ArrayList<String>();
        int t=0;
        for(int i=0;i<length;i+=3)
        {

            usedCard.add(Character.toString(placement.charAt(i))+Character.toString(placement.charAt(i+1)));
            usedPlace.add(Character.toString(placement.charAt(i+2)));
            t++;
            if(t>1) {


                for (int j = 0; j < (t - 1); ++j) {
                    if (usedCard.get(t - 1).equals(usedCard.get(j))) {
                        return false;
                    }
                    if (usedPlace.get(t - 1).equals(usedPlace.get(j))) {
                        return false;
                    }
                }
            }




        }


        return true;
    }

    /**
     * Determine whether a given move is legal given a provided valid placement:
     * - the location char is in the range A .. Z or 0..9
     * - there is a card at the chosen location;
     * - the location is in the same row or column of the grid as Zhang Yi's current position; and
     * - drawing a line from Zhang Yi's current location through the card at the chosen location,
     *   there are no other cards along the line from the same kingdom as the chosen card
     *   that are further away from Zhang Yi.
     * @param placement    the current placement string
     * @param locationChar a location for Zhang Yi to move to
     * @return true if Zhang Yi may move to that location
     */
    public static boolean isMoveLegal(String placement, char locationChar) {
        // FIXME Task 5: determine whether a given move is legal

        char kingdom = placement.charAt(0);
        char num = placement.charAt(1);
        char position = placement.charAt(2);
        boolean available() {
            String Positions = "";
            for (int i = 0; i < placement.length(), i += 3) {
                Positions += placement.charAt(i + 2);
            }
             return Positions.indexOf(locationChar) >= 0;
        }

        return  (isUpperCase(position) || isDigit(position))
                && position < 91;
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
        return false;
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
     * @param placement the current placement string
     * @return a location character representing Zhang Yi's destination for the move
     */
    public static char generateMove(String placement) {
        // FIXME Task 10: generate a legal move
        return '\0';
    }
}
