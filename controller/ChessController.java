package controller;

import model.ChessModel;
import view.*;

public class ChessController {
    
    ChessModel model;
    ChessView view;
    
    public ChessController(String player1, String player2, String ...args) throws Exception {
        this.model = new ChessModel(player1, player2);
        this.view = new TextView(player1, player2);
    }
    
    private void run() throws Exception {
        do {
            view.updateView(model.getBoardState());
            String []message = view.getMove();
            model.move(message[0], message[1].toUpperCase());
        } while(!model.getState().contains("checkmate"));
        view.updateView(model.getBoardState());
        System.out.println(model.getState());
    }
    
    public static void main(String []args) throws Exception {
        ChessController controller = new ChessController("patrick", "thursday");
        
        controller.run();
    }
}