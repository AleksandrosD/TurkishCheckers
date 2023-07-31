package application;

import javafx.scene.image.ImageView;

public abstract class Piece extends ImageView {
	
    private double currX, currY, prevX, prevY;
   
    /**
     * constructor
     * @param x
     * @param y
     */
    public Piece(int x,int y) {
    	
    	//placing the piece (also used to place it when moved)
        move(x , y);
        
        setFitHeight(Board.tileSize);
        setFitWidth(Board.tileSize);
        
//        System.out.println("I has here!");
        
        //on mouse commands
        setOnMousePressed(e -> {
            currX = e.getSceneX();
            currY = e.getSceneY();
        });
        
        setOnMouseDragged(e -> {
            relocate(e.getSceneX() - currX + prevX, e.getSceneY() - currY + prevY);
        });
    }
    
    
    /**
     * Gets the previous coordinate Y of the piece.
     * @return
     */
    public double getPrevY() {
        return prevY;
    }
    /**
     * Gets the current coordinate Y of the piece.
     * @return
     */
    public double getCurrY() {
        return currY;
    }
    /**
     * Gets the current coordinate X of the piece
     * @return
     */
    public double getCurrX() {
        return currX;
    }
 
    /**
     * Gets the previous coordinate X of the piece.
     * @return
     */
    public double getPrevX() {
        return prevX;
    }
   
    /**
     * aborting the move if its illegal
     */
    public void abortMove() {
    	relocate(prevX, prevY);
    }
    
    /**
     * moves a piece to another position
     * @param x
     * @param y
     */
    public void move(int x,int y) {
    	
    	prevX = x * Board.tileSize;
    	prevY = y * Board.tileSize;
    	relocate(prevX, prevY);
	}
    /**
     * Turns the int from the team into a string.
     * @param t
     * @return
     */
    public String toString(int t) {
    	String s = "";
    	
    	s += t;
    	
    	return s;
    }
    
    //abstract classes
    public abstract int getDirection();
	protected abstract void setNewImage();
	protected abstract int getTeam();
	
}