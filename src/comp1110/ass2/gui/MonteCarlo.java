//package comp1110.ass2.gui;
//
//import comp1110.ass2.WarringStatesGame;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class MonteCarlo {
//
//    public class State {
//
//        private String board;
//        private int playerNo;
//        private int visitCount;
//        private double winScore;
//
//        public State(String board) {
//            this.board = board;
//        }
//
//        public State(State state) {
//            this.board = state.board;
//            this.playerNo = state.getPlayerNo();
//            this.visitCount = state.getVisitCount();
//            this.winScore = state.getWinScore();
//        }
//
//        int getPlayerNo() {
//            return playerNo;
//        }
//
//        void setPlayerNo(int playerNo) {
//            this.playerNo = playerNo;
//        }
//
//        int getOpponent() {
//            return 3 - playerNo;
//        }
//
//        public int getVisitCount() {
//            return visitCount;
//        }
//
//        public void setVisitCount(int visitCount) {
//            this.visitCount = visitCount;
//        }
//
//        double getWinScore() {
//            return winScore;
//        }
//
//        void setWinScore(double winScore) {
//            this.winScore = winScore;
//        }
//
//        public List<State> getAllPossibleStates() {
//            List<State> possibleStates = new ArrayList<>();
//            List<String> availablePositions = possibleMoves(board);
//            availablePositions.forEach(p -> {
//                State newState = new State(this.board);
//                newState.setPlayerNo(3 - this.playerNo);
//                newState.getBoard().performMove(newState.getPlayerNo(), p);
//                possibleStates.add(newState);
//            });
//            return possibleStates;
//        }
//
//        void incrementVisit() {
//            this.visitCount++;
//        }
//
//        void addScore(double score) {
//            if (this.winScore != Integer.MIN_VALUE)
//                this.winScore += score;
//        }
//
//        void randomPlay() {
//            List<Position> availablePositions = this.board.getEmptyPositions();
//            int totalPossibilities = availablePositions.size();
//            int selectRandom = (int) (Math.random() * ((totalPossibilities - 1) + 1));
//            this.board.performMove(this.playerNo, availablePositions.get(selectRandom));
//        }
//
//        void togglePlayer() {
//            this.playerNo = 3 - this.playerNo;
//        }
//    }
//
//}
