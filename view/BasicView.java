package view;

import java.util.ArrayList;
import java.util.Scanner;
import model.ChessModel;
import model.ChessState;


public abstract class BasicView implements ChessView {
    
    ChessState state;
    String [][]board;
    String player1, player2;
    Scanner input;
    
    public BasicView(String player1, String player2) {
        this.state = null;
        this.board = new String[8][8];
        this.player1 = player1;
        this.player2 = player2;
        this.input = new Scanner(System.in);
    }
    
    @Override
    public void updateView(ChessState state) {
        this.state = state;
        this.board = state.getBoard();
        printBoard();
    }
    
    @Override
    public String[] getMove() {
        String sender = getCurrentPlayer();
        String move;
        do {
            move = getUserInput();
        }while(meta(move));
        return new String[]{sender, move};
    }
        
    private String getCurrentPlayer() {
        boolean isWhite = state == null || state.getCurrentPlayerColor() == ChessModel.Color.white;
        return isWhite ? player1 : player2;
    }
    
    private boolean meta(String move) {
        move = move.toUpperCase();
        switch(move) {
            case "Q":
            case "QUIT":
                System.exit(0);
                
            case "H":
            case "HIST":
            case "HISTORY":
                displayHistory();
                return true;
            
            default:
                return false;
        }
    }
    
    ArrayList<String> getHistory() {
        return state.getHistory();
    }
    
    abstract String getUserInput();
    abstract void displayHistory();

}