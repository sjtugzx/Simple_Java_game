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
        //condition 1
        if (length > 108 || length < 3 || length % 3 != 0) {
            return false;
        }
        //condition 2

        for (int i = 0; i < length; i += 3) {
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
                System.out.println(placement + "3");
                return false;

            }

        }
        //condition3 and condition 4
        List<String> usedCard = new ArrayList<String>();
        List<String> usedPlace = new ArrayList<String>();
        int t = 0;
//        System.out.println(length);
        for (int i = 0; i < length; i += 3) {

            usedCard.add(Character.toString(placement.charAt(i)) + Character.toString(placement.charAt(i + 1)));
            usedPlace.add(Character.toString(placement.charAt(i + 2)));
            t++;
//            System.out.println(usedCard.get(t-1));
//            System.out.println(usedPlace.get(t-1));
            if (t > 1) {


                for (int j = 0; j < (t - 1); ++j) {
//                    System.out.println(usedCard.get(j));
//                    System.out.println(usedPlace.get(j));
                    if (usedCard.get(t - 1).equals(usedCard.get(j))) {
//                        System.out.println("error 3");
                        return false;
                    }
                    if (usedPlace.get(t - 1).equals(usedPlace.get(j))) {
//                        System.out.println("error 4");
                        return false;
                    }
//                    System.out.println(j);
                }
            }
//            System.out.println(t);
//            System.out.println(i);


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
        //location range
        if ((locationChar < 48) || ((locationChar > 57) && (locationChar < 65)) || (locationChar > 90)) {
            return false;
        }

//      // card at chosen location
        int flag = 0;
        int placementLength = placement.length();
        char[] location = new char[placementLength / 3];
        String[] card = new String[placementLength / 3];
        int cont = 0;
        String aimCardKingdomCharacter = "";
//        System.out.println(locationChar);
        for (int j = 0; j < placementLength / 3; j++) {

            location[j] = placement.charAt(cont + 2);
            card[j] = String.valueOf(placement.charAt(cont)) + String.valueOf(placement.charAt(cont + 1));
            cont += 3;
        }
        for (int k = 0; k < placementLength / 3; ++k) {

            if (location[k] == locationChar) {
//                System.out.println(location[k]);
                aimCardKingdomCharacter = card[k];
                flag = 1;
                break;
            }
        }
        System.out.println(flag);
        if (flag == 0) {
            System.out.println("error 1");
            return false;
        }


        //check the row or column
        //get zhangyi's location
        //judgement
        char zhangYiLocation = ' ';
        int zhangYiColumn = -1;
        int zhangYiRow = -1;
        for (int k = 0; k < placementLength / 3; ++k) {
//            System.out.println(card[k]);
            if (card[k].equals("z9")) {
//                System.out.println("you zhangyi");
                zhangYiLocation = location[k];
                System.out.println(zhangYiLocation);
                if ((zhangYiLocation <= 70) && (zhangYiLocation >= 65)) {
                    zhangYiColumn = 5;
                    zhangYiRow = (int) zhangYiLocation - 5 * 13;
                }
                if ((zhangYiLocation <= 76) && (zhangYiLocation >= 71)) {
                    zhangYiColumn = 4;
                    zhangYiRow = (int) zhangYiLocation - 71;
                }
                if ((zhangYiLocation <= 82) && (zhangYiLocation >= 77)) {
                    zhangYiColumn = 3;
                    zhangYiRow = (int) zhangYiLocation - 77;
                }

                if ((zhangYiLocation <= 88) && (zhangYiLocation >= 83)) {
                    zhangYiColumn = 2;
                    zhangYiRow = (int) zhangYiLocation - 83;
                }
                if ((zhangYiLocation <= 90) && (zhangYiLocation >= 89)) {
                    zhangYiColumn = 1;
                    zhangYiRow = (int) zhangYiLocation - 89;
                }
                if ((zhangYiLocation <= 51) && (zhangYiLocation >= 48)) {
                    zhangYiColumn = 1;
                    zhangYiRow = (int) zhangYiLocation - 46;
                }
                if ((zhangYiLocation <= 57) && (zhangYiLocation >= 52)) {
                    zhangYiColumn = 0;
                    zhangYiRow = (int) zhangYiLocation - 52;
                }
                System.out.println(zhangYiLocation);
                break;
            }

        }
        if (zhangYiColumn == -1 || zhangYiRow == -1) {
            System.out.println("error 2");
            return false;
        }
        //get aim location column and row
        //judgement
        int aimLocationColumn = -1;
        int aimLocationRow = -1;
        char rowBegin = ' ';
        char columnBegin = ' ';
        if ((locationChar <= 70) && (locationChar >= 65)) {
            aimLocationColumn = 5;
            aimLocationRow = (int) locationChar - 5 * 13;
            columnBegin = 'A';


        }
        if ((locationChar <= 76) && (locationChar >= 71)) {
            aimLocationColumn = 4;
            aimLocationRow = (int) locationChar - 71;
            columnBegin = 'G';
        }
        if ((locationChar <= 82) && (locationChar >= 77)) {
            aimLocationColumn = 3;
            aimLocationRow = (int) locationChar - 77;
            columnBegin = 'M';
        }

        if ((locationChar <= 88) && (locationChar >= 83)) {
            aimLocationColumn = 2;
            aimLocationRow = (int) locationChar - 83;
            columnBegin = 'S';
        }
        if ((locationChar <= 90) && (locationChar >= 89)) {
            aimLocationColumn = 1;
            aimLocationRow = (int) locationChar - 89;
            columnBegin = 'Y';
        }
        if ((locationChar <= 51) && (locationChar >= 48)) {
            aimLocationColumn = 1;
            aimLocationRow = (int) locationChar - 46;
            columnBegin = 'Y';

        }
        if ((locationChar <= 57) && (locationChar >= 52)) {
            aimLocationColumn = 0;
            aimLocationRow = (int) locationChar - 52;
            rowBegin = '4';
        }
        if (aimLocationColumn == -1 || aimLocationRow == -1) {
            System.out.println("error 3");
            return false;
        }

        if (((zhangYiColumn != aimLocationColumn) && (zhangYiRow != aimLocationRow)) || (zhangYiLocation == locationChar)) {
            System.out.println("error 4");
            return false;
        }

        char[] column0 = new char[]{'4', '5', '6', '7', '8', '9'};
        char[] column1 = new char[]{'Y', 'Z', '0', '1', '2', '3'};
        char[] column2 = new char[]{'S', 'T', 'U', 'V', 'W', 'X'};
        char[] column3 = new char[]{'M', 'N', 'O', 'P', 'Q', 'R'};
        char[] column4 = new char[]{'G', 'H', 'I', 'J', 'K', 'L'};
        char[] column5 = new char[]{'A', 'B', 'C', 'D', 'E', 'F'};
//      the furthest card
        //get kingdom
        char kingdom;
        char test;
        String testKingdom = "";
        kingdom = aimCardKingdomCharacter.charAt(0);
        //get other card from the same line
        //from the same column
        System.out.print(zhangYiColumn);
        System.out.println(zhangYiRow);
        System.out.print(aimLocationColumn);

        System.out.println(aimLocationRow);

        if (zhangYiColumn == aimLocationColumn) {
            System.out.println("same column");

            if (aimLocationRow < zhangYiRow) {
                System.out.println("<");
                if (aimLocationRow == 0) {
                    System.out.println("==0");
                    return true;
                }
                //get other element
                if (aimLocationColumn == 0) {
                    for (int i = 0; i < aimLocationRow; ++i) {
                        for (int j = 0; j < placementLength / 3; ++j) {
                            if (column0[i] == location[j]) {
                                testKingdom = card[j];
                                if (testKingdom.charAt(0) == kingdom) {
                                    System.out.println("error 5");
                                    return false;
                                }
                            }
                        }
                    }
                }
                if (aimLocationColumn == 1) {
                    for (int i = 0; i < aimLocationRow; ++i) {
                        for (int j = 0; j < placementLength / 3; ++j) {
                            if (column1[i] == location[j]) {
                                testKingdom = card[j];
                                if (testKingdom.charAt(0) == kingdom) {
                                    System.out.println("error 5");
                                    return false;
                                }
                            }
                        }
                    }
                }
                if (aimLocationColumn == 2) {
                    for (int i = 0; i < aimLocationRow; ++i) {
                        for (int j = 0; j < placementLength / 3; ++j) {
                            if (column2[i] == location[j]) {
                                testKingdom = card[j];
                                if (testKingdom.charAt(0) == kingdom) {
                                    System.out.println("error 5");
                                    return false;
                                }
                            }
                        }
                    }
                }
                if (aimLocationColumn == 3) {
                    for (int i = 0; i < aimLocationRow; ++i) {
                        for (int j = 0; j < placementLength / 3; ++j) {
                            if (column3[i] == location[j]) {
                                testKingdom = card[j];
                                if (testKingdom.charAt(0) == kingdom) {
                                    System.out.println("error 5");
                                    return false;
                                }
                            }
                        }
                    }
                }
                if (aimLocationColumn == 4) {
                    for (int i = 0; i < aimLocationRow; ++i) {
                        for (int j = 0; j < placementLength / 3; ++j) {
                            if (column4[i] == location[j]) {
                                testKingdom = card[j];
                                if (testKingdom.charAt(0) == kingdom) {
                                    System.out.println("error 5");
                                    return false;
                                }
                            }
                        }
                    }
                }
                if (aimLocationColumn == 5) {
                    for (int i = 0; i < aimLocationRow; ++i) {
                        for (int j = 0; j < placementLength / 3; ++j) {
                            if (column5[i] == location[j]) {
                                testKingdom = card[j];
                                if (testKingdom.charAt(0) == kingdom) {
                                    System.out.println("error 5");
                                    return false;
                                }
                            }
                        }
                    }
                }
            }
            if (aimLocationRow > zhangYiRow) {
                System.out.println(">");

                if (aimLocationRow == 5) {
                    return true;
                }
                if (aimLocationColumn == 0) {
                    for (int i = aimLocationRow + 1; i <= 5; ++i) {
                        for (int j = 0; j < placementLength / 3; ++j) {
                            if (column0[i] == location[j]) {
                                testKingdom = card[j];
                                if (testKingdom.charAt(0) == kingdom) {
                                    System.out.println("error 5");
                                    return false;
                                }
                            }
                        }
                    }
                }
                if (aimLocationColumn == 1) {
                    for (int i = aimLocationRow + 1; i <= 5; ++i) {
                        for (int j = 0; j < placementLength / 3; ++j) {
                            if (column1[i] == location[j]) {
                                testKingdom = card[j];
                                if (testKingdom.charAt(0) == kingdom) {
                                    System.out.println("error 5");
                                    return false;
                                }
                            }
                        }
                    }
                }
                if (aimLocationColumn == 2) {
                    for (int i = aimLocationRow + 1; i <= 5; ++i) {
                        for (int j = 0; j < placementLength / 3; ++j) {
                            if (column2[i] == location[j]) {
                                testKingdom = card[j];
                                if (testKingdom.charAt(0) == kingdom) {
                                    System.out.println("error 5");
                                    return false;
                                }
                            }
                        }
                    }
                }
                if (aimLocationColumn == 3) {
                    for (int i = aimLocationRow + 1; i <= 5; ++i) {
                        for (int j = 0; j < placementLength / 3; ++j) {
                            if (column3[i] == location[j]) {
                                testKingdom = card[j];
                                if (testKingdom.charAt(0) == kingdom) {
                                    System.out.println("error 5");
                                    return false;
                                }
                            }
                        }
                    }
                }
                if (aimLocationColumn == 4) {
                    for (int i = aimLocationRow + 1; i <= 5; ++i) {
                        for (int j = 0; j < placementLength / 3; ++j) {
                            if (column4[i] == location[j]) {
                                testKingdom = card[j];
                                if (testKingdom.charAt(0) == kingdom) {
                                    System.out.println("error 5");
                                    return false;
                                }
                            }
                        }
                    }
                }
                if (aimLocationColumn == 5) {
                    for (int i = aimLocationRow + 1; i <= 5; ++i) {
                        for (int j = 0; j < placementLength / 3; ++j) {
                            if (column5[i] == location[j]) {
                                testKingdom = card[j];
                                if (testKingdom.charAt(0) == kingdom) {
                                    System.out.println("error 5");
                                    return false;
                                }
                            }
                        }
                    }
                }


            }
        }
        char[] row0 = new char[]{'4', 'Y', 'S', 'M', 'G', 'A'};
        char[] row1 = new char[]{'5', 'Z', 'T', 'N', 'H', 'B'};
        char[] row2 = new char[]{'6', '0', 'U', 'O', 'I', 'C'};
        char[] row3 = new char[]{'7', '1', 'V', 'P', 'J', 'D'};
        char[] row4 = new char[]{'8', '2', 'W', 'Q', 'K', 'E'};
        char[] row5 = new char[]{'9', '3', 'X', 'R', 'L', 'F'};
        if (zhangYiRow == aimLocationRow) {
            System.out.println("same Row");
            System.out.print(zhangYiColumn);
            System.out.println(zhangYiRow);
            System.out.print(aimLocationColumn);
            System.out.println(aimLocationRow);
            if (aimLocationColumn < zhangYiColumn) {
                System.out.println("<");
                if (aimLocationColumn == 0) {
                    return true;
                }
                if (aimLocationRow == 0) {
                    for (int i = 0; i < aimLocationColumn; ++i) {
                        for (int j = 0; j < placementLength / 3; ++j) {
                            if (row0[i] == location[j]) {
                                testKingdom = card[j];
                                if (testKingdom.charAt(0) == kingdom) {
                                    System.out.println("error 6");
                                    return false;
                                }
                            }
                        }
                    }
                }
                if (aimLocationRow == 1) {
                    for (int i = 0; i < aimLocationColumn; ++i) {
                        for (int j = 0; j < placementLength / 3; ++j) {
                            if (row1[i] == location[j]) {
                                testKingdom = card[j];
                                if (testKingdom.charAt(0) == kingdom) {
                                    System.out.println("error 6");
                                    return false;
                                }
                            }
                        }
                    }
                }
                if (aimLocationRow == 2) {
                    for (int i = 0; i < aimLocationColumn; ++i) {
                        for (int j = 0; j < placementLength / 3; ++j) {
                            if (row2[i] == location[j]) {
                                testKingdom = card[j];
                                if (testKingdom.charAt(0) == kingdom) {
                                    System.out.println("error 6");
                                    return false;
                                }
                            }
                        }
                    }
                }
                if (aimLocationRow == 3) {
                    for (int i = 0; i < aimLocationColumn; ++i) {
                        for (int j = 0; j < placementLength / 3; ++j) {
                            if (row3[i] == location[j]) {
                                testKingdom = card[j];
                                if (testKingdom.charAt(0) == kingdom) {
                                    System.out.println("error 6");
                                    return false;
                                }
                            }
                        }
                    }
                }
                if (aimLocationRow == 4) {
                    for (int i = 0; i < aimLocationColumn; ++i) {
                        for (int j = 0; j < placementLength / 3; ++j) {
                            if (row4[i] == location[j]) {
                                testKingdom = card[j];
                                if (testKingdom.charAt(0) == kingdom) {
                                    System.out.println("error 6");
                                    return false;
                                }
                            }
                        }
                    }
                }
                if (aimLocationRow == 5) {
                    for (int i = 0; i < aimLocationColumn; ++i) {
                        for (int j = 0; j < placementLength / 3; ++j) {
                            if (row5[i] == location[j]) {
                                testKingdom = card[j];
                                if (testKingdom.charAt(0) == kingdom) {
                                    System.out.println("error 6");
                                    return false;
                                }
                            }
                        }
                    }
                }

            }
            if (aimLocationColumn > zhangYiColumn) {
                System.out.println(">");
                if (aimLocationColumn == 5) {
                    return true;
                }
                if (aimLocationRow == 0) {
                    for (int i = aimLocationColumn + 1; i <= 5; ++i) {
                        for (int j = 0; j < placementLength / 3; ++j) {
                            if (row0[i] == location[j]) {
                                testKingdom = card[j];
                                if (testKingdom.charAt(0) == kingdom) {
                                    System.out.println("error 6");
                                    return false;
                                }
                            }
                        }
                    }
                }
                if (aimLocationRow == 0) {
                    for (int i = aimLocationColumn + 1; i <= 5; ++i) {
                        for (int j = 0; j < placementLength / 3; ++j) {
                            if (row1[i] == location[j]) {
                                testKingdom = card[j];
                                if (testKingdom.charAt(0) == kingdom) {
                                    System.out.println("error 6");
                                    return false;
                                }
                            }
                        }
                    }
                }
                if (aimLocationRow == 2) {
                    for (int i = aimLocationColumn + 1; i <= 5; ++i) {
                        for (int j = 0; j < placementLength / 3; ++j) {
                            if (row2[i] == location[j]) {
                                testKingdom = card[j];
                                if (testKingdom.charAt(0) == kingdom) {
                                    System.out.println("error 6");
                                    return false;
                                }
                            }
                        }
                    }
                }
                if (aimLocationRow == 3) {
                    for (int i = aimLocationColumn + 1; i <= 5; ++i) {
                        for (int j = 0; j < placementLength / 3; ++j) {
                            if (row3[i] == location[j]) {
                                testKingdom = card[j];
                                if (testKingdom.charAt(0) == kingdom) {
                                    System.out.println("error 6");
                                    return false;
                                }
                            }
                        }
                    }
                }
                if (aimLocationRow == 4) {
                    for (int i = aimLocationColumn + 1; i <= 5; ++i) {
                        for (int j = 0; j < placementLength / 3; ++j) {
                            if (row4[i] == location[j]) {
                                testKingdom = card[j];
                                if (testKingdom.charAt(0) == kingdom) {
                                    System.out.println("error 6");
                                    return false;
                                }
                            }
                        }
                    }
                }
                if (aimLocationRow == 5) {
                    for (int i = aimLocationColumn + 1; i <= 5; ++i) {
                        for (int j = 0; j < placementLength / 3; ++j) {
                            if (row5[i] == location[j]) {
                                testKingdom = card[j];
                                if (testKingdom.charAt(0) == kingdom) {
                                    System.out.println("error 6");
                                    return false;
                                }
                            }
                        }
                    }
                }
            }
        }


        return true;
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
        int moveLength = moveSequence.length();
        char[] location1 = new char[moveLength];
        for (int i = 0; i < moveLength; ++i) {
            location1[i] = moveSequence.charAt(i);
            if ((location1[i] < 48) || ((location1[i] > 57) && (location1[i] < 65)) || (location1[i] > 90)) {
                return false;
            }
        }

        for (int kk = 0; kk < moveLength; ++kk) {


            int flag = 0;
            int placementLength = setup.length();
            char[] location = new char[placementLength / 3];
            String[] card = new String[placementLength / 3];
            int cont = 0;
            String aimCardKingdomCharacter = "";
//        System.out.println(locationChar);
            for (int j = 0; j < placementLength / 3; j++) {

                location[j] = setup.charAt(cont + 2);
                card[j] = String.valueOf(setup.charAt(cont)) + String.valueOf(setup.charAt(cont + 1));
                cont += 3;
            }
            for (int k = 0; k < placementLength / 3; ++k) {

                if (location[k] == location1[kk]) {
//                System.out.println(location[k]);
                    aimCardKingdomCharacter = card[k];
                    flag = 1;
                    break;
                }
            }
            System.out.println(flag);
            if (flag == 0) {
                System.out.println("error 1");
                return false;
            }


            //check the row or column
            //get zhangyi's location
            char zhangYiLocation = ' ';
            int zhangYiColumn = -1;
            int zhangYiRow = -1;
            for (int k = 0; k < placementLength / 3; ++k) {
//            System.out.println(card[k]);
                if (card[k].equals("z9")) {
//                System.out.println("you zhangyi");
                    zhangYiLocation = location[k];
                    System.out.println(zhangYiLocation);
                    if ((zhangYiLocation <= 70) && (zhangYiLocation >= 65)) {
                        zhangYiColumn = 5;
                        zhangYiRow = (int) zhangYiLocation - 5 * 13;
                    }
                    if ((zhangYiLocation <= 76) && (zhangYiLocation >= 71)) {
                        zhangYiColumn = 4;
                        zhangYiRow = (int) zhangYiLocation - 71;
                    }
                    if ((zhangYiLocation <= 82) && (zhangYiLocation >= 77)) {
                        zhangYiColumn = 3;
                        zhangYiRow = (int) zhangYiLocation - 77;
                    }

                    if ((zhangYiLocation <= 88) && (zhangYiLocation >= 83)) {
                        zhangYiColumn = 2;
                        zhangYiRow = (int) zhangYiLocation - 83;
                    }
                    if ((zhangYiLocation <= 90) && (zhangYiLocation >= 89)) {
                        zhangYiColumn = 1;
                        zhangYiRow = (int) zhangYiLocation - 89;
                    }
                    if ((zhangYiLocation <= 51) && (zhangYiLocation >= 48)) {
                        zhangYiColumn = 1;
                        zhangYiRow = (int) zhangYiLocation - 46;
                    }
                    if ((zhangYiLocation <= 57) && (zhangYiLocation >= 52)) {
                        zhangYiColumn = 0;
                        zhangYiRow = (int) zhangYiLocation - 52;
                    }
                    System.out.println(zhangYiLocation);
                    break;
                }

            }
            if (zhangYiColumn == -1 || zhangYiRow == -1) {
                System.out.println("error 2");
                return false;
            }

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
                int zhangInt = col.indexOf(yi_position);
                System.out.println(zhangInt);
                loc += col.substring(0, zhangInt) + col.substring(zhangInt + 1);
                continue;
            }
        }
        for (String row : ROWS) {
            if (row.contains(yi_position + "")) {
                int zhangInt = row.indexOf(yi_position);
                System.out.println(zhangInt);
                loc += row.substring(0, zhangInt) + row.substring(zhangInt + 1);
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
