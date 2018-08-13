package view;

import java.util.Scanner;

public class ChessView {
    String [][]board;
    String player1, player2;
    boolean firstPlayer;
    Scanner input;
    
    public ChessView(String player1, String player2) {
        this.board = new String[8][8];
        this.player1 = player1;
        this.player2 = player2;
        this.input = new Scanner(System.in);
        this.firstPlayer = true;
    }
    
    public void updateView(String [][]board) {
        this.board = board;
        printBoard();
    }
    
    public void printBoard() {
        int lineLength = 0;
        for(int i = 0; i < 8; i++) {
            String line = String.join("|", board[i]);
            line += "|";
            if(i == 0) {
                lineLength = line.length();
                for(int j = 0; j < lineLength; j++) {
                    System.out.print("_");
                }
                System.out.println();
            }
            System.out.println(line);
        }
        for(int i = 0; i < lineLength; i++) {
            System.out.print("_");
        }
        System.out.println();
    }
    
    public String[] getMove() {
        String sender = getCurrentPlayer();
        String move = input.nextLine().trim().toUpperCase();
        return new String[]{sender, move};
    }
    
    private String getCurrentPlayer() {
        String current = firstPlayer ? player1 : player2;
        firstPlayer = !firstPlayer;
        return current;
    }
}