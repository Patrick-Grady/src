package model;

public class ChessPlayer {
    private String name;
    private ChessModel.Color color;
    
    public ChessPlayer(String name, ChessModel.Color color) {
        this.name = name;
        this.color = color;
    }
    
    public String getName() {
        return this.name;
    }
    
    public ChessModel.Color getColor() {
        return this.color;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public void setColor(ChessModel.Color color) {
        this.color = color;
    }
}