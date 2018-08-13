package model;

public class Position {
    private int row;
    private char col;
    
    public Position(int row, char col) {
        this.row = row;
        this.col = col;
    }
    
    public int getRow() {
        return this.row;
    }
    
    public char getColumn() {
        return this.col;
    }
    
    public void setRow(int row) {
        this.row = row;
    } 
    
    public void setColumn(char col) {
        this.col = col;
    }
    
    @Override
    public String toString()  {
        return col + "" + row;
    }
}