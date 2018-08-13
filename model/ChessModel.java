package model;

import java.util.Deque;
import java.util.LinkedList;

public class ChessModel {
    public enum Color {
        black,
        white
    }
    
    public enum PieceType {
        pawn,
        knight,
        bishop,
        rook,
        queen,
        king
    }
    
    private final Deque<ChessState> states;
    private final ChessPlayer player1, player2;
    
    public ChessModel(String player1, String player2) throws Exception {
        this.states = new LinkedList<>();
        this.player1 = new ChessPlayer(player1, Color.white);
        this.player2 = new ChessPlayer(player2, Color.black);
        
        getNextState("start");
    }
    
    // API
    public void restart() throws Exception {
        states.clear();
        getNextState("start");
    }
    
    public void undo() {
        rollBackState();
    }
      
    public void move(String sender, String move) throws Exception, CloneNotSupportedException {
        validateSender(sender);
        getNextState(move);
    }
    
    public String[] getAllMovesFrom(String piece) throws Exception {
        if(!properPositionFormat(piece)) {
            throw new Exception("Improper format");
        }
        
        return getCurrentState().getAllMovesFrom(piece);
    }
    
    // validation
    private void validateSender(String sender) throws Exception {
        ChessPlayer player = sender.equals(player1.getName()) ? player1 : player2;
        if(player == null) {
            throw new Exception("Player does not exist.");
        }
        else if(!matchesCurrentPlayer(player)) {
            throw new Exception("Wrong turn");
        }
    }
    
    private boolean matchesCurrentPlayer(ChessPlayer player) {
        return player.getColor() == getCurrentState().getCurrentPlayerColor();
    }
    
    private void validate(String move) throws Exception {
        if(syntaxError(move)) {
            throw new Exception("Improperly formatted move");
        }
        else if(!getCurrentState().canExecute(move)) {
            throw new Exception("Invalid move");
        }
    }
    
    private boolean syntaxError(String move) {
        String []split = move.split(" ");
        
        if(split.length == 1) {
            return move.equals("0-0") || move.equals("O-O-O") || move.equals("0-0-0") || move.equals("O-O-O");
        }
        else if(split.length != 2) {
            return false;
        }
        
        String start = split[0], end = split[1];
        if(start.length() != end.length()) {
            return false;
        }
        else if(start.length() == 3 && start.charAt(0) != end.charAt(0)) {
            return false;
        }
        
        return properPositionFormat(start) && properPositionFormat(end);
    }
    
    private boolean properPositionFormat(String pos) {
        if(pos.length() != 2 && pos.length() != 3) {
            return false;
        }
        
        pos = pos.substring(pos.length()-2, pos.length());
        char col = pos.charAt(1);
        int row = pos.charAt(2) - '0';
                    
        return row >= 1 && row <= 8 && col >= 'A' && col <= 'H';        
    }
    
    // util/convenience methods
    private void getNextState(String move) throws Exception, CloneNotSupportedException {
        if(!move.equals("start")) {
            validate(move);
        }
        ChessState next = ChessState.getNextState(getCurrentState(), move);
        setCurrentState(next);
    }
        
    private ChessState rollBackState() {
        return states.removeFirst();
    }
    
    private ChessState getCurrentState() {
        return states.peekFirst();
    }
    
    private void setCurrentState(ChessState next) {
        states.addFirst(next);
    }
}