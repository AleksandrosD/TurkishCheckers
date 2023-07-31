package application;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Tile extends Rectangle{
	
    private String content;
    private Piece piece;
    /**
     * This is the tile that the board is build from.
     * @param x
     * @param y
     */
    public Tile( int x, int y) {
    	
        setWidth(Board.tileSize);
        setHeight(Board.tileSize);
        
        relocate(x * Board.tileSize, y * Board.tileSize);
        
        if((y + x) % 2 == 0)
            setFill(Color.valueOf("#eeeed2"));
        else
            setFill(Color.valueOf("#769656"));
    }
    /**
     * This function returns null if the tile of the board is empty!
     * @return
     */
    public boolean hasPiece() {
        return piece != null;
    }
    /**
     * This function gets the piece in the tile we have chosen.
     * @return
     */
    public Piece getPiece() {
        return piece;
    }
    /**
     * This tile puts the piece in the tile we have chosen.
     * @param p
     */
    public void setPiece(Piece p) {
        piece = p;
    }
    /**
     * Returns what is in the tile if we have a black piece(B), white piece(W), or null(0,1).
     * @return
     */
	public String getContent() {
		return content;
	}
	/**
	 * Sets what is contained in the tile black piece(B), white piece(W), or null(0,1).
	 * @param content
	 */
	public void setContent(String content) {
		this.content = content;
	}
}