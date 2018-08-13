package model;

import java.util.ArrayList;

public class ChessBoard implements Cloneable {
    
    ChessPiece [][]board;
    
    public ChessBoard() {
        this.board = new ChessPiece[8][8];
        setBoard();
    }
    
    private void setBoard() {        
        setSide(ChessModel.Color.white);
        setSide(ChessModel.Color.black);
    }
    
    private void setSide(ChessModel.Color color) {
        int pawnRow = color == ChessModel.Color.white ? 2 : 7;
        int kingRow = color == ChessModel.Color.white ? 1 : 8;
        
        for(char col = 'A'; col <= 'H'; col++) {
            setSpot(new Position(pawnRow, col), new ChessPiece(color, ChessModel.PieceType.pawn));
        }
        
        setSpot(new Position(kingRow, 'A'), new ChessPiece(color, ChessModel.PieceType.rook));
        setSpot(new Position(kingRow, 'B'), new ChessPiece(color, ChessModel.PieceType.knight));
        setSpot(new Position(kingRow, 'C'), new ChessPiece(color, ChessModel.PieceType.bishop));
        setSpot(new Position(kingRow, 'D'), new ChessPiece(color, ChessModel.PieceType.queen));
        setSpot(new Position(kingRow, 'E'), new ChessPiece(color, ChessModel.PieceType.king));
        setSpot(new Position(kingRow, 'F'), new ChessPiece(color, ChessModel.PieceType.bishop));
        setSpot(new Position(kingRow, 'G'), new ChessPiece(color, ChessModel.PieceType.knight));
        setSpot(new Position(kingRow, 'H'), new ChessPiece(color, ChessModel.PieceType.rook));
        
    }
    
    public Position getKingPosition(ChessModel.Color color) {
        for(int row = 1; row <= 8; row++) {
            for(char col = 'A'; col <= 'H'; col++) {
                Position p = new Position(row, col);
                ChessPiece piece = getPieceAt(p);
                if(!isOpen(p) && piece.getColor() == color && piece.getType() == ChessModel.PieceType.king) {
                    return p;
                }
            }
        }
        
        return null;
    }
    
    public ArrayList<Position> getAllPieces(ChessModel.Color color) {
        ArrayList<Position> piecePositions = new ArrayList<>();
        
        for(int row = 1; row <= 8; row++) {
            for(char col = 'A'; col <= 'H'; col++) {
                Position p = new Position(row, col);
                if(!isOpen(p) && getPieceAt(p).getColor() == color) {
                    piecePositions.add(p);
                }
            }
        }
        
        return piecePositions;
    }
    
    public void move(Position start, Position end) {
        ChessPiece piece = getPieceAt(start);
        setSpot(end, piece);
        clearSpot(start);
    }
        
    public boolean pathIsClear(Position start, Position end) {
        ChessPiece piece = getPieceAt(start);
        if(piece == null || !spotFreeToTake(piece, end)) {
            return false;
        }
        
        switch(piece.getType()) {
            case pawn:
                return pawnCanMove(start, end);
            case knight:
                return knightCanMove(start, end);
            case bishop:
                return canMoveDiagonally(start, end);
            case rook:
                return canMoveHorizontally(start, end) || canMoveVertically(start, end);
            case queen:
                return canMoveDiagonally(start, end) || canMoveHorizontally(start, end) || canMoveVertically(start, end);
            case king:
                return kingCanMove(start, end);
            default:
                return false;
        }
    }
    
    private boolean pawnCanMove(Position start, Position end) {
        ChessPiece pawn = getPieceAt(start);
        ChessModel.Color color = pawn.getColor();
        
        int direction = color == ChessModel.Color.white ? 1 : -1;

        boolean singleStep = end.getColumn() == start.getColumn() && end.getRow() - start.getRow() == 1 * direction;
        boolean attack = Math.abs(end.getColumn() - start.getColumn()) == 1 && end.getRow() - start.getRow() == 1 * direction && getPieceAt(end).getColor() != color;
        boolean doubleStep = !pawn.hasMoved() && end.getColumn() == start.getColumn() && end.getRow() - start.getRow() == 2 * direction;
        
        return singleStep || attack || doubleStep;
    }
    
    public boolean knightCanMove(Position start, Position end) {
        int rowDiff = Math.abs(end.getRow() - start.getRow());
        int columnDiff = Math.abs(end.getColumn() - start.getColumn());
        
        if(rowDiff == 0 || columnDiff == 0) {
            return false;
        }
        else if(rowDiff + columnDiff != 3) {
            return false;
        }
        return true;
    }
    
    private boolean canMoveDiagonally(Position start, Position end) {
        int hOffset = Math.abs(end.getColumn() - start.getColumn());    // horizonal offset
        int vOffset = Math.abs(end.getRow() - start.getRow());    // vertical offset
        
        if(hOffset != vOffset) {
            return false;
        }
        
        ChessPiece piece = getPieceAt(start);
        int hDirection = end.getColumn() > start.getColumn() ? 1 : -1;  // horizontal direction
        int vDirection = end.getRow() > start.getRow() ? 1 : -1;  // vertical direction
        int dOffset = hOffset;                          // diagonal offset
        
        // check all spots diagonally between start and end are free
        for(int i = 1; i < dOffset; i++) {
            Position p = new Position(start.getRow() + i * hDirection, (char) (start.getColumn() + i * vDirection));
            if(!spotFreeToTake(piece, p)) {
                return false;
            }
        }
        
        return true;
    }
    
    private boolean canMoveHorizontally(Position start, Position end) {
        if(end.getRow() != start.getRow()) {
            return false;
        }
        
        ChessPiece piece = getPieceAt(start);
        int direction = end.getColumn() > start.getColumn() ? 1 : -1;
        
        for(int i = 1; i < Math.abs(end.getColumn() - start.getColumn()); i++) {
            Position p = new Position(start.getRow(), (char) (start.getColumn() + i * direction));
            if(!spotFreeToTake(piece, p)) {
                return false;
            }
        }
        
        return true;
    }
    
    private boolean canMoveVertically(Position start, Position end) {
        if(end.getColumn() != start.getColumn()) {
            return false;
        }
        
        ChessPiece piece = getPieceAt(start);
        int direction = end.getRow() > start.getRow() ? 1 : -1;
        
        for(int i = 1; i < Math.abs(end.getRow() - start.getRow()); i++) {
            Position p = new Position(start.getRow() + i * direction, start.getColumn());
            if(!spotFreeToTake(piece, p)) {
                return false;
            }
        }
        
        return true;
    }
    
    private boolean kingCanMove(Position start, Position end) {
        return Math.abs(end.getRow() - start.getRow()) <= 1 && Math.abs(end.getColumn() - start.getColumn()) <= 1;
    }
    
    private boolean spotFreeToTake(ChessPiece piece, Position spot) {
        if(piece == null) {
            return false;
        }
        else if(spot.getRow() < 1 || spot.getRow() > 8 || spot.getColumn() < 'A' || spot.getColumn() > 'H') {
            return false;
        }
        else if(!isOpen(spot) || getPieceAt(spot).getColor() == piece.getColor()) {
            return false;
        }
        return true;
    }
    
    private void clearSpot(Position spot) {
        setSpot(spot, null);
    }
    
    public void setSpot(Position spot, ChessPiece piece) {
        if(spot != null) {
            board[row(spot.getRow())][column(spot.getColumn())] = piece;
        }
    }
    
    public boolean isOpen(Position spot) {
        return getPieceAt(spot) != null;
    }
    
    public boolean isOpen(int row, char col) {
        return getPieceAt(row, col) != null;
    }
    
    public ChessPiece getPieceAt(Position spot) {
        return spot != null ? getPieceAt(spot.getRow(), spot.getColumn()) : null;
    }
    
    public ChessPiece getPieceAt(int row, char col) {
        return board[row(row)][column(col)];
    }
    
    private int row(int row) {
        return 8 - row;
    }
    
    private int column(char column) {
        return column - 'A' - 1;
    }
    
    @Override
    public ChessBoard clone() throws CloneNotSupportedException {
        ChessBoard cboard = (ChessBoard) super.clone();
        cboard.board = copyBoard();
        
        return cboard;
    }
    
    private ChessPiece[][] copyBoard() throws CloneNotSupportedException {        
        ChessPiece [][]newBoard = new ChessPiece[board.length][board[0].length];
        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board[i].length; j++) {
                if(board[i][j] != null) {
                    newBoard[i][j] = board[i][j].clone();
                }
                else {
                    newBoard[i][j] = null;
                }
            }
        }
        return newBoard;
    }
}