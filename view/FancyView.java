package view;

public class FancyView extends BasicView {
        
    public FancyView(String player1, String player2) {
        super(player1, player2);
    }
    
    @Override
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
    
    @Override
    String getUserInput() {
        return "";
    }
    
    @Override
    void displayHistory() {
        
    }
}