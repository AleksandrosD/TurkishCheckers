package application;

public class MoveResult {
	
	private MoveType type;
    private Piece piece;
    
    
    /**
     * move result if the move is not legal.
     * @param type
     */
    public MoveResult(MoveType type) {
        this(type, null);
    }
    /**
     * move result when the move is legal.
     * @param type
     * @param piece
     */
    public MoveResult(MoveType type, Piece piece) {
        this.type = type;
        this.piece = piece;
    }
    
    
    /**
     * Gets the type of the move(NONE,MOVE,KILL)
     * @return
     */
    public MoveType getType() {
        return type;
    }
    /**
     * Gets the piece that is chosen to move.
     * @return
     */
    public Piece getPiece() {
        return piece;
    }
}