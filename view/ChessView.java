package view;

import model.ChessState;

public interface ChessView {
    
    public void updateView(ChessState board);
    public void printBoard();
    public String[] getMove();
}