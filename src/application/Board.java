package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

public class Board extends Application implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int tileSize = 80;
    public static final int tileX = 8;
    public static final int tileY = 8;
    
    private int bPieces = 16;
    private int wPieces = 16;
	
    private Tile[][] board = new Tile[tileX][tileY];
    
    private Group tileGroup = new Group();
    private Group pieceGroup = new Group();
    
    private Stage primaryStage;
    
    private Player player1 = new Player(-1, true);
    private Player player2 = new Player(1, false);
    
    /**
     * 
     * @return
     * @throws FileNotFoundException
     */
    private Parent createContent() throws FileNotFoundException {
    	
        Pane root = new Pane();
        
        root.setPrefSize(tileX * tileSize, tileY * tileSize);
        root.getChildren().addAll(tileGroup, pieceGroup);
                
        File start = new File("C:\\Users\\aleks\\eclipse-workspace\\game\\start.mp3");
        Media media3 = new Media(start.toURI().toString());
        MediaPlayer playerSound3 = new MediaPlayer(media3);
        playerSound3.setAutoPlay(true);
        
        for(int i = 0; i < tileY; i++) {
            for(int j = 0; j < tileX; j++) {
            	
            	Tile t = new Tile(j, i);
                
                board[j][i] = t;

                tileGroup.getChildren().add(t);
                
                Piece piece = null;
               
                if (i > 0 && i < 3) {
                    piece = addP(1,j, i);
                    board[j][i].setContent("1");
                    System.out.print("B ");
                }
                
                if (i > 4 && i < 7) {
                   piece = addP(2,j, i);
                   board[j][i].setContent("-1");
                   System.out.print("W ");
                }
                
                if (piece != null) {
                   t.setPiece(piece);
                   pieceGroup.getChildren().add(piece);
                   continue;
               }
                
               if((i + j) % 2 == 0)
            	   System.out.print("0 ");
               else
                	System.out.print("1 ");
            }
            System.out.println(" ");
        }
        
        System.out.println(" ");
        
        return root;
    }
    
   
    /**
     * creates a piece based on the type that it is (black or white)
     */
    private Piece addP(int type,int x, int y) {
    	
    	Piece piece;
    	
    	if(type == 1) {
    		Piece pb = new Black(x, y);
    		piece = pb;
    		
    	}else {
    		Piece pw = new White(x, y);
    		piece = pw;
    	}
    	
    	piece.setOnMouseReleased(e -> {
    		
    		int newX = toBoard(piece.getLayoutX());
    		int newY = toBoard(piece.getLayoutY());

    		MoveResult result;

    		if (newX < 0 || newY < 0 || newX >= tileX || newY >= tileY) 
    			result = new MoveResult(MoveType.NONE);
            
    		else
    			result = tryMove(piece, newX, newY);
            
    		int x0 = toBoard(piece.getPrevX());
    		int y0 = toBoard(piece.getPrevY());
            
    		
            File move = new File("C:\\Users\\aleks\\eclipse-workspace\\game\\move2.mp3");
            File kill = new File("C:\\Users\\aleks\\eclipse-workspace\\game\\capture.mp3");
            Media media = new Media(move.toURI().toString());
            Media media2 = new Media(kill.toURI().toString());
            MediaPlayer playerSound1 = new MediaPlayer(media);
            MediaPlayer playerSound2 = new MediaPlayer(media2);
            
    		switch (result.getType()) {
    			case NONE:
    				
    				piece.abortMove();
    				System.out.println("");
    				break;
                    
    			case NORMAL:
    				
    				
    				//changing turns
    			    player1.changeTurn();
    			    player2.changeTurn();
    			    
    				piece.move(newX, newY);
    				
    				//actually changing the coordinates on the board
    				board[x0][y0].setPiece(null);
    				board[newX][newY].setPiece(piece);
    				
    				//printing
    				board[x0][y0].setContent(null);
    				board[newX][newY].setContent(piece.toString(piece.getTeam()));
    				
    				playerSound1.setAutoPlay(true);
    				
    				System.out.println("");
    				break;
                    
    			case KILL:
    				
    				if(bPieces == 0 || wPieces == 0)
    					result = new MoveResult(MoveType.NONE);
    				
    				piece.move(newX, newY);
    				
    				board[x0][y0].setPiece(null);
    				board[newX][newY].setPiece(piece);
    				
    				Piece otherPiece = result.getPiece();
    				board[toBoard(otherPiece.getPrevX())][toBoard(otherPiece.getPrevY())].setPiece(null);
    				
    				pieceGroup.getChildren().remove(otherPiece);
    				
                    if(piece.getTeam() == 1) {
                    	wPieces--;
                    	if(wPieces == 0)
                    		endGame();
                    	
                    }else { 
                    	bPieces--;
                    	if(bPieces == 0)
                    		endGame();
                    }
                    
                    System.out.println("");
                    playerSound2.setAutoPlay(true);
    				break;
    			}
    		
    			
    		/**
    		 * printing the updated board after every move
    		 */
            	for(int i = 0; i < tileY; i++) {
            		for(int j = 0; j < tileX; j++) {
                	
            			if (board[j][i].hasPiece() && board[j][i].getContent() == "-1") {
            				System.out.print("W ");
            				continue;
            			}
            			
            			if(board[j][i].hasPiece() && board[j][i].getContent() == "1") {
            				System.out.print("B ");
            				continue;
            			}
                   
            			if((i + j) % 2 == 0) {
            				if(board[j][i].hasPiece()) {
            					System.out.print("B ");
            					continue;
            				}
            				System.out.print("0 ");
            			}
            				
            			else {
            				if(board[j][i].hasPiece()) {
            					System.out.print("W ");
            					continue;
            				}
            				System.out.print("1 ");
            			}
                    
            		}
            		System.out.println("");
            	}
            	System.out.println("");
            	System.out.println(wPieces);
            	System.out.println(bPieces);
    		});
    	
    	return piece;
    }
    
    
    /**
     * changes the image to king
     * @param pi
     * @param y
     */
    private void Change(Piece pi,int y){
    	
    	if(pi.getDirection() == 1 && y == 7)
      		pi.setNewImage();
    	
     	else if(pi.getDirection() == -1 && y == 0)
     		pi.setNewImage();
    }
    
    
    /**
     * moves the piece based on turns
     * @param piece
     * @param newX
     * @param newY
     * @return
     */
    private MoveResult tryMove(Piece piece, int newX, int newY) {
    	
    	if (board[newX][newY].hasPiece())
            return new MoveResult(MoveType.NONE);       
        
        if(player1.getTurn() && piece.getTeam() == player1.getTeam()) {
        	return getMoveResult(piece, newX, newY);
        	
        }else if(player2.getTurn() && piece.getTeam() == player2.getTeam()) {
        	return getMoveResult(piece, newX, newY);
        }
           
        return new MoveResult(MoveType.NONE);
     }
    
    
    /**
     * is the logic of the movements
     * @param piece
     * @param newX
     * @param newY
     * @return
     */
    private MoveResult getMoveResult(Piece piece, int newX, int newY) {
    	
        int x0 = toBoard(piece.getPrevX());
        int y0 = toBoard(piece.getPrevY());
        
        if(piece.getDirection() == 1 || piece.getDirection() == -1) {
    		if ((newY == y0 && Math.abs(newX - x0) < 2) || newX == x0 && (newY - y0) == piece.getDirection()) {
    		
    			Change(piece,newY);
    			return new MoveResult(MoveType.NORMAL);
    		
    		}else if (newY == y0 && Math.abs(newX - x0) < 3 || newX == x0 && (newY - y0) == piece.getDirection() * 2) {
    
    			Change(piece,newY);
    			int x1 = x0 + (newX - x0) / 2;
    			int y1 = y0 + (newY - y0) / 2;

    			if (board[x1][y1].hasPiece() && board[x1][y1].getPiece().getTeam() != piece.getTeam())
    				return new MoveResult(MoveType.KILL, board[x1][y1].getPiece());
    		}
    
        }else {
        	if (Math.abs(newX - x0) < 2 && Math.abs(newY - y0) < 2 ) {
        		return new MoveResult(MoveType.NORMAL);
	 
        	}else if (Math.abs(newX - x0) < 3 && Math.abs(newY - y0) < 3) {
	        
     	
        		int x1 = x0 + (newX - x0) / 2;
        		int y1 = y0 + (newY - y0) / 2;

        		if (board[x1][y1].hasPiece() && board[x1][y1].getPiece().getTeam() != piece.getTeam())
        			return new MoveResult(MoveType.KILL, board[x1][y1].getPiece());
        		else
        			return new MoveResult(MoveType.NONE);
         		}
	 		}
        return new MoveResult(MoveType.NONE);
    }
    /**
     * This function take the cordinates form the mouse and translates them to 2-dimentional cordinates.
     * @param pixel
     * @return
     */
    private int toBoard(double pixel) {
        return (int)(pixel + tileSize / 2) / tileSize;
    }
    /**
     * Ends the game when one of the player loses all its pieces.
     */
    private void endGame() {
    	
    	
    	
    	if(bPieces == 0) {
    		primaryStage = new Stage();
    		
    		    
    		     Label outputLabel = new Label("White Wins!");
    		     outputLabel.setStyle("-fx-font-size: 24pt; -fx-padding: 30px;");
    		     //outputLabel.setStyle("-fx-text-align: center");
    		     Scene scene = new Scene(outputLabel, 230, 100);
    		     primaryStage.setTitle("RESULT");
    		     primaryStage.setScene(scene);
    		     primaryStage.show();
    	}
		
    	else if(wPieces == 0){
    		primaryStage = new Stage();
    		
		     
		     Label outputLabel = new Label("Black Wins!");
		     outputLabel.setStyle("-fx-font-size: 24pt; -fx-padding: 30px;");
		     //outputLabel.setStyle("-fx-text-align: center");
		     Scene scene = new Scene(outputLabel, 230, 100);
		     primaryStage.setTitle("RESULT");
		     primaryStage.setScene(scene);
		     primaryStage.show();
    	}
    	
    	//imageViewGif.setFitWidth(800);
    	//imageViewGif.setFitHeight(800);
        
        //Scene scene = new Scene(new Pane(imageViewGif));
        //primaryStage.setScene(scene);
        //primaryStage.show();
        
    }
    public static void WriteToFile(Tile[][] player) throws FileNotFoundException, IOException{
        ObjectOutputStream write= new ObjectOutputStream(new FileOutputStream("Game.bin"));
        write.writeObject(player);
        write.close();
    }
    
    public static void ReadFile(Tile[][] player) throws FileNotFoundException, IOException, ClassNotFoundException{
        ObjectInputStream read= new ObjectInputStream(new FileInputStream("Game.bin"));
        Tile[][] savedGame=(Tile[][]) read.readObject();
        read.close();
        player=savedGame;
    }
    
   
    /**
     * this will be the game after the menu
     */
	public void start(Stage pStage) throws Exception{
		
		primaryStage = pStage;
		
        
        
        pStage.setTitle("TurkishCheckers 2.0");
        Menu menus = new Menu("Menu");
        Menu m = new Menu("Creators");
        MenuItem m1 = new MenuItem("Aleksandros Doci");
        MenuItem m2 = new MenuItem("Erlis Ibrahimi");
        MenuItem m3 = new MenuItem("Yoosuf Siddiqui");
        
        MenuItem exit=new MenuItem("Exit Game");
        MenuItem saveexit=new MenuItem("Save and Exit");
        MenuItem saved=new MenuItem("Last Game");
        menus.getItems().add(exit);
        menus.getItems().add(saveexit);
        menus.getItems().add(saved);
      
        
        // add menu items to menu
        m.getItems().add(m1);
        m.getItems().add(m2);
        m.getItems().add(m3);
  
        // create a menubar
        MenuBar mb = new MenuBar();
        
        saved.setOnAction(mouseEvent->{
        	try {
				ReadFile(board);
			} catch (ClassNotFoundException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        });
        
        exit.setOnAction(mouseEvent->{pStage.close();});
        saveexit.setOnAction(mouseEvent->{
        	try {
				WriteToFile(board);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	pStage.close();
        });
        
        // add menu to menubar
        mb.getMenus().addAll(menus,m);
  
        // create a VBox
        VBox vb = new VBox(mb,createContent());
        Scene scene = new Scene(vb);
        pStage.setScene(scene);
        pStage.show();
    }
	
	//here will go the starting menu
//	    public void start1(Stage pStage) throws Exception{	
//		pStage = new Stage();
		
  //     Scene scene = new Scene(new Pane(), 800, 800);
    //   pStage.setTitle("TurkishCheckers 2.0");
     //  pStage.setScene(scene);
      // pStage.show();
  //  }
	
	public static void main(String args[]) { launch(args); } 
}
