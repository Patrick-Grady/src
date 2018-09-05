package model;

import java.util.Deque;
import java.util.LinkedList;
import java.util.ArrayList;

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
        move = move.toUpperCase();
        switch(move) {
            case "R":
            case "RESTART":
                restart();
                return;
            case "U":
            case "UNDO":
                ChessState last = getLastState();
                if(last != null && last.getCurrentPlayerColor() != getCurrentState().getCurrentPlayerColor()) 
                    undo();
                return;
            default:
                validateSender(sender);
                getNextState(move);
        }         
    }
    
    public String[] getAllMovesFrom(String piece) throws Exception {
        if(!properPositionFormat(piece)) {
            throw new Exception("Improper format: " + piece);
        }
        
        return getCurrentState().getAllMovesFrom(piece);
    }
    
    public ChessState getBoardState() {
        return getCurrentState();
    }
    
    public String getState() throws Exception {
        ChessState state = getCurrentState();
        if(state == null) {
            return "start";
        }

        ArrayList<String> results = new ArrayList<>();
        results.add(state.getCurrentPlayerColor().toString());
        
        if(state.checkmated()) {
            results.add("checkmated");
        }
        else if(state.stalemated()) {
            results.add("stalemated");
        }
        else if(state.inCheck()) {
            results.add("checked");
        }
        
        return String.join(" ", results);
    }
    
    public String[][]getBoard() {
        return getCurrentState().getBoard();
    }
    
// validation
    
    // sender
    private void validateSender(String sender) throws Exception {
        ChessPlayer player = getPlayer(sender);
        if(player == null) {
            throw new Exception("Player does not exist: " + sender);
        }
        else if(!isCurrentPlayer(player)) {
            throw new Exception("Not your turn: " + sender);
        }
    }
    
    private boolean isCurrentPlayer(ChessPlayer player) {
        return player.getColor() == getCurrentState().getCurrentPlayerColor();
    }
    
    // move
    private void validate(String move) throws Exception {
        if(syntaxError(move)) {
            throw new Exception("Improperly formatted move: " + move);
        }
        else if(!getCurrentState().canExecute(move)) {
            throw new Exception("Invalid move: " + move);
        }
    }
    
    private boolean syntaxError(String move) {
        String []split = move.split(" ");
        
        if(split.length == 1) {
            return !(move.equals("0-0") || move.equals("O-O-O") || move.equals("0-0-0") || move.equals("O-O-O"));
        }
        else if(split.length != 2) {
            return true;
        }
        
        String start = split[0], end = split[1];
        if(start.length() != end.length()) {
            return true;
        }
        else if(start.length() == 3 && start.charAt(0) != end.charAt(0)) {
            return true;
        }
        
        return !properPositionFormat(start) || !properPositionFormat(end);
    }
    
    private boolean properPositionFormat(String pos) {
        if(pos.length() != 2 && pos.length() != 3) {
            return false;
        }
        
        pos = pos.substring(pos.length()-2, pos.length());
        char col = pos.charAt(0);
        int row = pos.charAt(1) - '0';
                    
        return row >= 1 && row <= 8 && col >= 'A' && col <= 'H';        
    }
    
// util/convenience methods
    private void getNextState(String move) throws Exception, CloneNotSupportedException {
        if(!move.equalsIgnoreCase("start")) {
            validate(move);
        }
        ChessState next = ChessState.getNextState(getCurrentState(), move);
        setCurrentState(next);
    }
        
    private ChessState rollBackState() {
        return states == null || states.size() < 2 ? null : states.removeFirst();
    }
    
    private ChessState getCurrentState() {
        return states.peekFirst();
    }
    
    private void setCurrentState(ChessState next) {
        states.addFirst(next);
    }
    
    private ChessState getLastState() {
        if(states == null || states.size() < 2)
            return null;
        ChessState current = rollBackState();
        ChessState last = getCurrentState();
        setCurrentState(current);
        return last;
    }
    
    private ChessPlayer getPlayer(String name) {
        return name.equals(player1.getName()) ? player1 : player2;
    }
}