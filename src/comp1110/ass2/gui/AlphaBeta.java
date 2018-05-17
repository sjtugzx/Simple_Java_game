package comp1110.ass2.gui;

import comp1110.ass2.WarringStatesGame;

import java.util.ArrayList;
import java.util.List;

public class AlphaBeta {
    public static char makeMove(String placement,String moveSequence,int numberOfPlayers,int playerID) {
        String moves = WarringStatesGame.possibleMoves(placement);
        List<Integer> scores = new ArrayList<>();
        for (int i = 0; i < moves.length(); i++) {
            WarringStatesGame.board = placement;
            WarringStatesGame.isMoveLegal(WarringStatesGame.board,moves.charAt(i));
            scores.add(alphaBeta(WarringStatesGame.board,moveSequence + moves.charAt(i),-1000000,100000000,numberOfPlayers,7,1,playerID));
        }



        WarringStatesGame.board = placement;
        int index = 0;
        for (int i = 0; i < scores.size(); i++) {
            if (scores.get(i) > scores.get(index)) {
                index = i;
            }
        }
        return moves.charAt(index);
    }
    public static int alphaBeta(String placement,String moveSequence, int alpha,int beta, int numberOfPlayers, int depth,int count,int playerID) {
        if (depth == 0 || WarringStatesGame.generateMove(placement) == '\0') {
            return score(placement,moveSequence,numberOfPlayers,playerID);
        }
        if (count % numberOfPlayers == 0) {
            int v = -10000000;
            String moves = WarringStatesGame.possibleMoves(placement);
            for (int i = 0; i < moves.length(); i++) {
                WarringStatesGame.board = placement;
                WarringStatesGame.isMoveLegal(WarringStatesGame.board,moves.charAt(i));
                v = Math.max(v,alphaBeta(WarringStatesGame.board,moveSequence + moves.charAt(i),alpha,beta,numberOfPlayers,depth - 1,count + 1,playerID));
                alpha = Math.max(alpha,v);
                if (beta <= alpha) {
                    break;
                }
            }
            return v;
        }

        else {
            int v = 10000000;
            String moves = WarringStatesGame.possibleMoves(placement);
            for (int i = 0; i < moves.length(); i++) {
                WarringStatesGame.board = placement;
                WarringStatesGame.isMoveLegal(WarringStatesGame.board,moves.charAt(i));
                v = Math.min(v,alphaBeta(WarringStatesGame.board,moveSequence + moves.charAt(i), alpha,beta,numberOfPlayers,depth - 1,count + 1,playerID));
                beta = Math.min(beta,v);
                if (beta <= alpha) {
                    break;
                }
            }
            return v;
        }
    }
    public static int score(String placement, String moveSequence, int numberOfPlayers, int playerID) {
        int value = 0;
        for (int i = 0; i < numberOfPlayers; i++) {
            if (i == playerID) {
                String supporters = WarringStatesGame.getSupporters(placement,moveSequence,numberOfPlayers,i);
                value += supporters.length()/2;
            }
            else {
                String supporters = WarringStatesGame.getSupporters(placement,moveSequence,numberOfPlayers,i);
                value -= supporters.length()/2;
            }
        }
        return value;
    }
}
