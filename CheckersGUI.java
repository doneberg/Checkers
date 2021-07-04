package ui;
import java.io.IOException;

import core.CheckersLogic;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class CheckersGUI extends Application {
	public static final int standard = 8; //standard checker board
	public static final int SIZE = 100;
	public static final int BIG = standard * SIZE;
    public static final Color black[] = {Color.BLACK, Color.DARKSLATEGRAY};
    public static final Color red[] = {Color.RED, Color.CRIMSON};
    public static final Color green[] = {Color.GREEN, Color.LAWNGREEN};
    private Pane gameboard;
    private Square[][] board = new Square[standard][standard];
    private CheckersLogic logic;
    private Group squareGroup = new Group();
    private Group pieceGroup = new Group();
	private PopUp pop;
	private final String question = "Would you like to play a computer opponent?";
	private final String doubleJump = "Would you like to double jump?";//not implemented yet
    
	 public void start(Stage primaryStage) throws IOException
	  {
		 Scene scene = new Scene(createGame());
		    primaryStage.setTitle("Checkers"); 
		    primaryStage.setScene(scene);
		    primaryStage.show();
		    this.logic = new CheckersLogic(this);
			pop = new PopUp();
			
		    if (pop.display(question)){
		    	logic.setComputerPlayer();
			    }
	  
	  }
	 
	 public void setSquare(int i, int j, Piece piece) {
		 board[i][j].setPiece(piece);
	 }
	 
	 public Square getSquare(int i, int j) {
		return board[i][j];
	 }
   /**
    * Creates game board with light and dark squares as well as pieces
    * @return gameboard Pane
    */
	 public Pane createGame() 
 	{
		 gameboard = new Pane();
		 gameboard.setPrefSize(SIZE*standard, SIZE*standard);
		 gameboard.getChildren().addAll(squareGroup, pieceGroup);
		 gameboard.setPadding(new Insets(10, 10, 10, 10));
		
 		 for (int i = 0; i < standard; i++) 
 	        {
 	        	for (int j = 0; j < standard; j++ ) { 		
 	        		Square square = new Square(isLightSquare(i, j), i, j);
 	        		board[i][j] = square; //change
 	        		
 	        		squareGroup.getChildren().add(square);
 	        		
 	        		Piece piece = null;
 	        		
 	        		if( i <= 2 && !(isLightSquare(i, j))) {
 	        			piece = createPiece(PieceType.BLACK, i, j);
 	        		}
 	        		
 	        		if( i >= 5 && !(isLightSquare(i, j))) {
 	        			piece = createPiece(PieceType.RED, i, j);
 	        		}
 	        		if(piece != null) {
 	        			square.setPiece(piece);
 	 	        		pieceGroup.getChildren().add(piece); 
 	        		}
 	        	}
 	        }
 		 
 		 return gameboard;
 		 
 	}
	 public Group getPieceGroup() {
		 return pieceGroup;
	 }
	/**
	 * returns a boolean signifying whether a square is light or dark
	 * @param j x location
	 * @param i y location
	 * @return is light square 
	 */
	 public boolean isLightSquare(int i, int j) {
		 return (j + i) % 2 == 0;
	 }
	 public Piece createPiece(PieceType type, int y, int x) 
	 {
		Piece piece = new Piece(type, y, x); 
		
		piece.setOnMouseReleased(e -> {
			int newX = toBoard(piece.getLayoutX());
			int newY = toBoard(piece.getLayoutY());	
			MoveResult result = tryMove(piece, newY, newX);

			mover(result, piece, newY, newX);
			if(result.getType() == MoveType.REGULAR || result.getType() == MoveType.JUMP) {
				this.logic.nextTurn();
			}
			
		});
		
 		 return piece;
 	}
/**
 * The moved piece is given along with the newY and newX coordinates that generated the MoveResult.
 * This method then takes the appropriate action depending on the MoveResult. 
 * @param result
 * @param piece
 * @param newY
 * @param newX
 */
	public void mover(MoveResult result, Piece piece, int newY, int newX) {
		int x0 = toBoard(piece.getOldX());
		int y0 = toBoard(piece.getOldY());
		
		switch(result.getType()) {
		
		case NONE:
			piece.reset();
			break;
			
		case REGULAR:
			piece.move(newY, newX); //move piece
			board[y0][x0].setPiece(null); //move representation of piece
			board[newY][newX].setPiece(piece);	
			break;
			
		case JUMP:
			piece.move(newY, newX);
			board[y0][x0].setPiece(null);
			board[newY][newX].setPiece(piece);
			
			Piece otherPiece = result.getPiece();
			board[toBoard(otherPiece.getOldY())][toBoard(otherPiece.getOldX())].setPiece(null);
			pieceGroup.getChildren().remove(otherPiece);
			
			if (logic.getPlayerOneTurn()) {	
					pop = new PopUp();
					if(newY > 1 && newX > 1) {
						
						result = tryMove(piece, newY - 2, newX -  2); //try jumping left
					
					if (result.getType() == MoveType.JUMP) {
						 if (pop.display(doubleJump)){
							mover(result, piece, newY - 2, newX - 2);
							
							    }		
						}	
					}
					else if(newY > 1 && newX < standard - 2) {
						
						result = tryMove(piece, newY - 2, newX +  2); //try jumping right
						
						if (result.getType() == MoveType.JUMP) {
							 if (pop.display(doubleJump)){
							mover(result, piece, newY - 2, newX + 2);
							
							}		
						}
				}
			}
				else if (!logic.ComputerGame()) {
					if (logic.numberCheck(newY) && logic.numberCheck(newX)) {
						if(newY < standard - 1 && newX > 1) {
						result = tryMove(piece, newY + 2, newX - 2);
						if (result.getType() == MoveType.JUMP && pop.display(doubleJump)) {
							mover(result, piece, newY + 2, newX - 2);
							
						}
						if(newY < standard - 1 && newX < standard - 2) {
							result = tryMove(piece, newY + 2, newX + 2);
							if (result.getType() == MoveType.JUMP && pop.display(doubleJump)) {
								mover(result, piece, newY + 2, newX + 2);	
							}		
					}
			}
			}
			}
			break;
		}			
	}
	
	/**
	 * Gives the results of a move
	 * @param piece the piece being moved
	 * @param newX new X location on board (0-8)
	 * @param newY new Y location (0-8)
	 * @return a move result which contains a movetype and perhaps a piece if the movetype was a jump
	 */
	public MoveResult tryMove(Piece piece, int newY, int newX) {
		
		int x0 = toBoard(piece.getOldX());
		int y0 = toBoard(piece.getOldY());
		
		if (newY > (standard-1) || newX > (standard-1) || newY < 0 || newX < 0
				|| board[newY][newX].hasPiece() || isLightSquare(newY, newX) 
				|| (piece.getType() != PieceType.RED && logic.getPlayerOneTurn())
				|| (piece.getType() != PieceType.BLACK && !logic.getPlayerOneTurn()) ) {
			return new MoveResult(MoveType.NONE);
		}
		
		if (Math.abs(newX - x0) == 1 
				&& newY - y0 == piece.getType().direction) {
			return new MoveResult(MoveType.REGULAR);
		}
		
		else if (Math.abs(newX - x0) == 2 && newY - y0 == piece.getType().direction*2) {
			int x1 = x0 + (newX - x0) / 2;
			int y1 = y0 + (newY - y0) / 2;
			
			if(board[y1][x1].hasPiece() 
					&& !board[y1][x1].getPiece().getType().equals(piece.getType())) {
				return new MoveResult(MoveType.JUMP, board[y1][x1].getPiece());
			}
		}
		return new MoveResult(MoveType.NONE);
	}
	
	/**
	 * Converts between javaFX x and y values and the 0-8 checkers board values
	 * @param pixel
	 * @return
	 */
	private int toBoard(double pixel) {
		return (int) (pixel + SIZE/2) / SIZE;
	}
	
	/**
	 * determines if the game is over
	 */
	 public void gameOver() {
		   if(this.logic.isGameOver()) {
			   EndingPopUp end = new EndingPopUp();
				end.display(logic.Winner());
		   }
	   }
	 
	 /**
	  * launches program
	  * @param args from main
	  */
	  public static void main(String[] args) {
		  launch(args);
	  }
	 
  
}
