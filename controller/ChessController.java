package controller;

import model.ChessModel;
import view.ChessView;

public class ChessController {
    
    ChessModel model;
    ChessView view;
    
    public ChessController(String player1, String player2) throws Exception {
        
        
        this.model = new ChessModel(player1, player2);
        this.view = new ChessView(player1, player2);
    }
    
    private void run() throws Exception {
        do {
            view.updateView(model.getBoard());
            String []message = view.getMove();
            model.move(message[0], message[1].toUpperCase());
        } while(!model.getState().contains("checkmate"));
        view.updateView(model.getBoard());
        System.out.println(model.getState());
    }
    
    public static void main(String []args) throws Exception {
        ChessController controller = new ChessController("patrick", "thursday");
        
        controller.run();
    }
    
}