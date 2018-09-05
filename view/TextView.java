package view;

public class TextView extends BasicView {
    
    public TextView(String player1, String player2) {
        super(player1, player2);
    }
    
    @Override
    public void printBoard() {
        for(int i = 0; i < 8; i++) {
            System.out.print("|");
            for(int j = 0; j < 8; j++) {
                System.out.printf("%s|", board[i][j]);
            }
            System.out.println();
        }
    }
    
    @Override
    String getUserInput() {
        return input.nextLine().trim().toUpperCase();
    }
    
    @Override
    void displayHistory() {
        for(String move : getHistory()) {
            System.out.println(move);
        }
    }
}