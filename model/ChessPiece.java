package model;

public class ChessPiece implements Cloneable {
    private ChessModel.Color color;
    private ChessModel.PieceType type;
    private int timesMoved;
    
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
        
        return colorPrefix + pieceSuffix;
    }
}