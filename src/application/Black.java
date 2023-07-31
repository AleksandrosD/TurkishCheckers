package application;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Black extends Piece {
	
	private int direction = 1;
	private static int team = 1;
/**
 * Constructor of the black piece.
 * @param x
 * @param y
 */
	public Black(int x, int y) {
        super(x, y);
        setImage();
    }

	/**
	 * mutators
	 * @param image
	 */
    public void setPiece(Image image){
    	this.setImage(image);
    }
     
    /**
     * basic black pawn
     */
    public void setImage(){
    	
    	Image img = new Image("C:\\Users\\aleks\\eclipse-workspace\\game\\black.png");
    	
    	ImageView imageView = new ImageView(img);
    	imageView.setPreserveRatio(true);  
    	this.setPiece(img);
    }
    
    /**
     * changes the pawn to a black king when reaching the end of the enemy board
     */
    public void setNewImage() {
    	
    	Image img = new Image("C:\\Users\\aleks\\eclipse-workspace\\game\\blackKing.png");
    	
    	ImageView imageView = new ImageView(img);
    	imageView.setPreserveRatio(true);  
    	this.setPiece(img);
    	setDirection(0);
    }
    /**
     * The direction that the piece moves.
     * @param dir
     */
	public void setDirection(int dir) {
		direction = dir;
	}
	


	/**
	 * returns the direction that the piece moves.
	 */
	@Override
	public int getDirection() {
		return direction;
	}

	/**
	 * returns the team that the piece has.
	 */
	public int getTeam() {
		return team;
	}
	
}