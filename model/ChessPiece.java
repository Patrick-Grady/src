package model;

import java.util.HashMap;

public class ChessPiece implements Cloneable {
    private ChessModel.Color color;
    private ChessModel.PieceType type;
    private int timesMoved;
    private static HashMap<String, String> iconMap;
    
    public ChessPiece(ChessModel.Color color, ChessModel.PieceType type) {
        this.color = color;
        this.type = type;
        this.timesMoved = 0;
    }
    
    public ChessModel.Color getColor() {
        return this.color;
    }
    
    public ChessModel.PieceType getType() {
        return this.type;
    }
    
    public void setColor(ChessModel.Color color) {
        this.color = color;
    }
    
    public void setType(ChessModel.PieceType type) {
        this.type = type;
    }
    
    public void incrementMoved() {
        this.timesMoved++;
    }
    
    public void decrementMoved() {
        this.timesMoved--;
    }
    
    public boolean hasMoved() {
        return this.timesMoved > 0;
    }
    
    @Override
    public ChessPiece clone() throws CloneNotSupportedException {
        return (ChessPiece) super.clone();
    }
    
    @Override
    public String toString() {
        String colorPrefix = color == ChessModel.Color.white ? "W" : "B";
        String pieceSuffix = "P";
        
        switch(type) {
            case knight:
                pieceSuffix = "N";
                break;
            case bishop:
                pieceSuffix = "B";
                break;
            case rook:
                pieceSuffix = "R";
                break;
            case queen:
                pieceSuffix = "Q";
                break;
            case king:
                pieceSuffix = "K";
                break;
        }
        
        return getIcon(colorPrefix + pieceSuffix);
    }
    
    private static String getIcon(String piece) {
        if(iconMap == null) {
            iconMap = new HashMap<>();
            iconMap.put("WK", "" + '♔');    // U+2654 white chess king	
            iconMap.put("WQ", "" + '♕');    // U+2655 white chess queen	
            iconMap.put("WR", "" + '♖');    // U+2656 white chess rook	
            iconMap.put("WB", "" + '♗');    // U+2657 white chess bishop	
            iconMap.put("WN", "" + '♘');    // U+2658 white chess knight	
            iconMap.put("WP", "" + '♙');    // U+2659 white chess pawn	
            iconMap.put("BK", "" + '♚');    // U+265A black chess king	
            iconMap.put("BQ", "" + '♛');    // U+265B black chess queen	
            iconMap.put("BR", "" + '♜');    // U+265C black chess rook	
            iconMap.put("BB", "" + '♝');    // U+265D black chess bishop	
            iconMap.put("BN", "" + '♞');    // U+265E black chess knight	
            iconMap.put("BP", "" + '♟');    // U+265F black chess pawn
        }
        return iconMap.get(piece);
    }
}