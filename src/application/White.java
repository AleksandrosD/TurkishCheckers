package application;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class White extends Piece {
	
	private int direction = -1;
	private static int team = -1;
	
	/**
	 * Constuctor
	 * @param x
	 * @param y
	 */
	public White(int x, int y) {
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
     * basic white pawn
     */
    public void setImage(){
    	
    	Image img = new Image("C:\\Users\\aleks\\eclipse-workspace\\game\\white.png");
    	
    	ImageView imageView = new ImageView(img);
    	imageView.setPreserveRatio(true);  
    	this.setPiece(img);
    }

    /**
     * changes the pawn to a white king when reaching the end of the enemy board
     */
	protected void setNewImage() {
		Image img = new Image("C:\\Users\\aleks\\eclipse-workspace\\game\\whiteKing.png");
    	
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
		direction=dir;
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