package model;

import java.util.ArrayList;

public class ChessState implements Cloneable {
    
    private final static boolean KING_SIDE = true, QUEEN_SIDE = false;
    
    ChessBoard board;
    ChessModel.Color color;    
    
    private ChessState(ChessModel.Color color) {
        this.color = color;
        board = new ChessBoard();
    }
    
    // API
    public ChessModel.Color getCurrentPlayerColor() {
        return this.color;
    }
    
    public String[] getAllMovesFrom(String start) throws Exception {
        if(!validRef(start)) {
            throw new Exception("Invalid reference");
        }
        
        Position position = mapStringToPosition(start);
        ArrayList<Position> moves = getAllMovesFrom(position);
        String []stringMoves = new String[moves.size()];
        
        for(int i = 0; i < moves.size(); i++) {
            Position p = moves.get(i);
            ChessModel.PieceType type = board.getPieceAt(p).getType();
            char typeLetter = mapTypeToLetter(type);
            stringMoves[i] = typeLetter + p.toString();
        }
        
        return stringMoves;
    }
    
    public boolean checkmated() throws Exception {
        return inCheck() && !canMoveAny(color);
    }
    
    public boolean stalemated() throws Exception {
        return !inCheck() && !canMoveAny(color);
    }
    
    public boolean inCheck() throws Exception {
        return inCheck(color);
    }
    
    public boolean canExecute(String move) throws Exception {
        if(move.equals("0-0") || move.equals("O-O")) {
            return canCastle(KING_SIDE);
        }
        else if(move.equals("0-0-0") || move.equals("O-O-O")) {
            return canCastle(QUEEN_SIDE);
        }
        
        Position []positions = getStartAndEnd(move);
        Position start = positions[0], end = positions[1];
        
        if(!validRef(move)) {
            throw new Exception("Invalid ref");
        }
        else if(!board.pathIsClear(start, end)) {
            throw new Exception("path unclear");
        }
        else if(movePutsPlayerInCheck(start, end)) {
            throw new Exception("puts in check");
        }
        
        return true;
//        return validRef(move) && board.pathIsClear(start, end) && !movePutsPlayerInCheck(start, end);
    }
    
    public void execute(String move) {
        if(move.equals("0-0") || move.equals("O-O")) {
            castle(KING_SIDE);
        }
        else if(move.equals("0-0-0") || move.equals("O-O-O")) {
            castle(QUEEN_SIDE);
        }
        else {
            Position []positions = getStartAndEnd(move);
            board.move(positions[0], positions[1]);
        }
        color = getOpponent(color);
    }
    
    public String[][] getBoard() {
        return board.getBoard();
    }
    
    // helper
    private boolean inCheck(ChessModel.Color color) throws Exception {
        ChessModel.Color opponent = getOpponent(color);    
        Position kingPosition = board.getKingPosition(color);
        
        // check if any pieces have a clear path to the opponent king
        for(Position p : board.getAllPieces(opponent)) {
            if(board.pathIsClear(p, kingPosition)) {
                return true;
            }
        }
        
        return false;
    }
    
    public boolean canMoveAny(ChessModel.Color color) throws Exception {
        ArrayList<Position> pieces = board.getAllPieces(color);
        
        for(Position p : pieces) {
            if(!getAllMovesFrom(p).isEmpty()) {
                return true;
            }
        }
        
        return false;
    }
    
    private ArrayList<Position> getAllMovesFrom(Position start) throws Exception {
        // ChessPiece piece = board.getPieceAt(start);
        ArrayList<Position> canMoveTo = new ArrayList<>();
        
        for(int row = 1; row <= 8; row++) {
            for(char col = 'A'; col <= 'H'; col++) {
                Position end = new Position(row, col);
                if(board.pathIsClear(start, end) && !movePutsPlayerInCheck(start, end)) {
                    canMoveTo.add(end);
                }
            }
        }
        
        return canMoveTo;
    }
    
    // helper
    private void castle(boolean kingSide) {
        // locate pieces based on color and side
        int row = color == ChessModel.Color.white ? 1 : 8;
        int direction = kingSide ? 1 : -1;
        
        // calculate start and end positions
        Position kingStart = new Position(row, 'E');
        Position kingEnd = new Position(row, (char) ('E' + 2 * direction));
        Position rookStart = new Position(row, (kingSide ? 'H' : 'A'));
        Position rookEnd = new Position(row, (char) ('E' + direction));
        
        // move pieces
        board.move(kingStart, kingEnd);
        board.move(rookStart, rookEnd);
    }
    
    // validation
    private ChessModel.PieceType mapLetterToType(char letter) {
        switch(letter) {
            case 'N':
                return ChessModel.PieceType.knight;
            case 'B':
                return ChessModel.PieceType.bishop;
            case 'R':
                return ChessModel.PieceType.rook;
            case 'Q':
                return ChessModel.PieceType.queen;
            case 'K':
                return ChessModel.PieceType.king;
            default:
                return ChessModel.PieceType.pawn;
        }
    }
    
    private char mapTypeToLetter(ChessModel.PieceType type) {
        switch(type) {
            case knight:
                return 'N';
            case bishop:
                return 'B';
            case rook:
                return 'R';
            case queen:
                return 'Q';
            case king:
                return 'K';
            default:
                return 'P';
        }
    }
    
    private Position mapStringToPosition(String pos) {
        pos = pos.substring(pos.length()-2, pos.length());
        char col = pos.charAt(0);
        int row = pos.charAt(1) - '0';
                
        return new Position(row, col);
    }
    
    private Position[] getStartAndEnd(String move) {
        String []split = move.split(" ");
        Position start = mapStringToPosition(split[0]);
        Position end = mapStringToPosition(split[1]);
        return new Position[]{start, end};
    }
    
    private boolean movePutsPlayerInCheck(Position start, Position end) throws Exception {
        ChessPiece piece = board.getPieceAt(end);
        board.move(start, end);
        boolean checked = inCheck();
        board.move(end, start);
        board.setSpot(end, piece);
        
        return checked;
    }
    
    private boolean validRef(String move) {
        Position []positions = getStartAndEnd(move);
        String []pos = move.split(" ");
        ChessPiece piece = board.getPieceAt(positions[0]);
        ChessModel.PieceType moveType = ChessModel.PieceType.pawn;
        if(pos[0].length() > 2) {
            moveType = mapLetterToType(pos[0].charAt(0));
        }
        
        return piece != null && piece.getType() == moveType;
    }
    
    private boolean canCastle(boolean kingSide) throws Exception {
        Position kingPosition = board.getKingPosition(color);
        ChessPiece king = board.getPieceAt(kingPosition);
        if(king.hasMoved()) {
            return false;
        }
        
        int row = color == ChessModel.Color.white ? 1 : 8;
        ChessPiece rook = kingSide ? board.getPieceAt(row, 'H') : board.getPieceAt(row, 'A');
        if(rook == null) {
            return false;
        }
        
        int direction = kingSide ? 1 : -1;
        for(int i = 1; i <= 2; i++) {
            Position position = new Position(row, (char) ('E' + i * direction));
            if(!board.isOpen(position) || movePutsPlayerInCheck(kingPosition, position)) {
                return false;
            }
        }
        
        return true;
    }
    
    
    
    @Override
    public ChessState clone() throws CloneNotSupportedException {
        ChessState clone = (ChessState) super.clone();
        clone.board = board.clone();
        
        return clone;
    }
    
    public static ChessState getNextState(ChessState state, String move) throws CloneNotSupportedException {
        if(state == null)
            return new ChessState(ChessModel.Color.white);
        ChessState next = state.clone();
        next.execute(move);
        return next;
    }
    
    // convenience method
    private ChessModel.Color getOpponent(ChessModel.Color color) {
        return color == ChessModel.Color.white ? ChessModel.Color.black : ChessModel.Color.white;
    }
}