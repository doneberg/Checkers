package core;

import ui.CheckersGUI;
import ui.Piece;
import ui.PieceType;

/**
 * 
 * @author donaldbergeson
 * @version 1.0
 */
public class CheckersLogic {
	private boolean playerOneTurn;
	private boolean computerPlayer = false;
	private boolean guiEnabled = false;
	private CheckersComputerPlayer compy;
	private CheckersGUI gui;
	private char[][] brain;
	private boolean noComputerMoves = false;
	BoardType boardType;
	private final char part = '|';
	private final int standard = 8;
	private final char[] letters = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'};
	private final char[] numbers = {'1', '2', '3', '4', '5', '6', '7', '8'};
	/**
	 * constructor for Logic unit of Checkers game
	 */
	public CheckersLogic() {
		playerOneTurn = true;
		gameBoardMaker();
	}
	
	private void gameBoardMaker() {
		brain = new char[standard][standard];
		for (int row = 0; row < brain.length; row++) { //fill in everything with floors
			for (int col = 0; col < brain[0].length; col++) {
				brain[row][col] = BoardType.FLOOR.type;
			}
		}
		
		for (int row = 7; row > 4; row--) { //x's
			for (int col = 0; col < brain[0].length; col++) {
				if (row%2 == 0) {
					if (col%2 == 0) {
						brain[row][col] = BoardType.FLOOR.type;
					}
					else {
						brain[row][col] = BoardType.X.type;
					}	
				}
				else {
					if (col%2 == 0) {
						brain[row][col] = BoardType.X.type;
					}
					else {
						brain[row][col] = BoardType.FLOOR.type;
					}	
				}
			}
		}
		
		for (int row = 0; row < 3; row++) { //x's
			for (int col = 0; col < brain[0].length; col++) {
				if (row%2 == 0) {
					if (col%2 == 0) {
						brain[row][col] = BoardType.FLOOR.type;
					}
					else {
						brain[row][col] = BoardType.O.type;
					}	
				}
				else {
					if (col%2 == 0) {
						brain[row][col] = BoardType.O.type;
					}
					else {
						brain[row][col] = BoardType.FLOOR.type;
					}	
				}
			}
		}	
	}
	
	/**
	 * Constructor for gui version of Checkers Logic
	 * @param square an array of square objects
	 * @param GUI the gui object
	 * @param group group of pieces
	 * @throws IllegalArgumentException throws an exception if it receives an improper argument
	 */
	public CheckersLogic(CheckersGUI GUI) throws IllegalArgumentException{ 
		playerOneTurn = true;
		guiEnabled = true;
		gui = GUI;
		gameBoardMaker();
	}
	
	/**
	 * Converts data from square[][] in the gui to the char[][] in the logic module
	 */
	public void SquareConverter() {
		for(int i = 0; i < standard; i++) {
			for (int j = 0; j < standard; j++) {			
				if(gui.getSquare(i, j).hasPiece()) {
					brain[i][j] = SquareConverter(gui.getSquare(i, j).getPiece().getType());
				}
				else {
					brain[i][j] = BoardType.FLOOR.type;
				}
			}
		}
	}
	private char SquareConverter(PieceType type) {
		switch (type) {
		case BLACK:
			return BoardType.O.type;
		case RED:
			return BoardType.X.type;
			default:
				return  BoardType.FLOOR.type;
		}
		
	}
	/**
	 * Converts data from char[][] in the logic module to the square[][] in the gui
	 */
		public void ComputerMoveConverter() {
			
			
			for(int i = 0; i < standard; i++) {
				for (int j = 0; j < standard; j++) {
					
					if(brain[i][j] == BoardType.FLOOR.type) {
						
						if (gui.getSquare(i, j).hasPiece()) {
							gui.getPieceGroup().getChildren().remove(gui.getSquare(i, j).getPiece());
							gui.getSquare(i, j).setPiece(null); 
						}
						
					
					}

					else if(brain[i][j] == BoardType.O.type) {
						
						if(!gui.getSquare(i, j).hasPiece()) {
							gui.getSquare(i, j).setPiece(new Piece(PieceType.BLACK, i, j));
							gui.getPieceGroup().getChildren().add(gui.getSquare(i, j).getPiece());
						}
					}
				}
			}
			SquareConverter();
	}
	/**
	 * returns char signifying whose turn it is
	 * @return X or O depending on whose turn 
	 */
	public char turn() {	
		if (playerOneTurn) {
			return 'X';
		}
		else
			return 'O';
	}
	/**
	 * returns char of the pieces of the player whose turn it currently is
	 * @return 'x' or 'o'
	 */
	public char player() {	
		if (playerOneTurn) {
			return BoardType.X.type;
		}
		else
			return BoardType.O.type;
	}
	/**
	 * returns char of the pieces of the player whose turn it currently isn't
	 * @return 'x' or 'o'
	 */
	public char opponent() {	
		if (playerOneTurn) {
			return BoardType.O.type;
		}
		else
			return BoardType.X.type;
	}
	
	/**
	 * Switches whose turn it is
	 */
	public void nextTurn() {
		
		changeTurn();
		
		if(guiEnabled) {
			SquareConverter();	
			gui.gameOver();
		}	
		
			if(!isGameOver()) {
			if (!playerOneTurn && computerPlayer) {
				
				if(!compy.move()) {
					this.ComputerLoses();
				}
				if (guiEnabled) {
					ComputerMoveConverter();
					SquareConverter();	
					}
				
				
				gui.gameOver();
				changeTurn();
				//System.out.println(this.toString());
			}
			}
	}
	/**
	 * returns a String representation of the current board state for the game
	 */
	public String toString() {
		int index = standard -1; // 8 or 0-7 numbers and letters flanking the board. For counting purposes
		int count = 0;
		int pos = 0;
		String str = "";
		int size = 19;
		
		for (int i = 0; i < size/2; i++) {
			
			for(int j = 0; j < size-1; j++) {
				str += " ";
				if(j%2 != 0 && i < standard) { //if odd -> partition
					str += part;						
				}
				else if(j%2 != 0 && i >= standard) { //if odd add -> space (between letters)
					str += " ";								
				}
			else { //if even
				if (j == 0 && index >= 0) {
					 //numbers count down on left
					str += numbers[index];
					index--;
				}
				else {
					if (i >= standard && j == 0) { //space between bottom letters
						
						str += " ";
					}
					else if(i >= standard) { //bottom letters 
						str += letters[count];
						count++;
					
					}
					else {
					
						str += brain[i][pos];
						pos++;
						if (pos == 8) {
							pos = 0;
						}
					}				
				}
				}
				}
		str += "\n\n";
		}
		return str;
	}
	
	/**
	 * reads in a String, turns it into numbers the MoveParser can understand, 
	 * and finally returns a boolean signifying whether or not input was successful.
	 * @param str a move e.g. 3a-4b
	 * @return boolean representing whether or not move was legal and successfully implemented 
	 */
	public boolean readMove(String str) { 
		try {
		int startRow = Integer.parseInt(str.substring(0, 1));
		String startCol = str.substring(1,2);
		int startColumn = ColumnTranslator(startCol);
		int endRow = Integer.parseInt(str.substring(str.length()-2, str.length()-1));
		String endCol = str.substring(str.length()-1, str.length());
		int endColumn = ColumnTranslator(endCol);
		startRow = 8 - startRow; //translates to numbers for array
		endRow = 8 - endRow;
		
		return parseMove(startRow, startColumn, endRow, endColumn);	
		}
		catch (IllegalArgumentException exception) {
			// Catch some IllegalArgumentExceptions
			System.out.println("Please try again, my friend.");
			return false;
		}
	}
	/**
	 * Method for checking if a given checkers move is legal
	 * @param startRow starting Y
	 * @param startColumn starting X
	 * @param endRow ending Y
	 * @param endColumn ending X
	 * @return if move legal
	 */
	public boolean parseMove(int startRow, int startColumn, int endRow, int endColumn) throws IllegalArgumentException { 
		if(!(numberCheck(startRow) || numberCheck(startColumn) 
				||numberCheck(endRow) 
				||numberCheck(endColumn))) {
			//System.out.println("test failed!!!000");
			return false;
		}
		
		if (LegalMove(startRow, startColumn, endRow, endColumn)) {
			Move(startRow, startColumn, endRow, endColumn);
			//System.out.println("legal move ------");
			return true;
			}
		
		
		if(LegalJump(startRow, startColumn, endRow, endColumn)) {
			Jump(startRow, startColumn, endRow, endColumn);
			//System.out.println("legal jump ------" + "\n" + 
			//startRow  + "\n" + startColumn  + "\n" + endRow  + "\n" + endColumn);
			
			if (computerPlayer) {
				if(endRow < standard - 1) {
					
				if(endColumn > 1)
				if(LegalJump(endRow, endColumn, endRow + 2, endColumn - 2)) {
					Jump(endRow, endColumn, endRow + 2, endColumn - 2);
					//System.out.println("DOUBLE jump WOW------");
					
				}
				
				else if(endColumn < standard - 1) 
				if(LegalJump(endRow, endColumn, endRow + 2, endColumn + 2)) {
					Jump(endRow, endColumn, endRow + 2, endColumn + 2);
					//System.out.println("DOUBLE jump WOW------");
			
				}	
				}
			}
			return true;
			
			}
	if(!computerPlayer && !guiEnabled) {
		if(LegalDoubleJump(startRow, startColumn, endRow, endColumn)) {
			Move(startRow, startColumn, endRow, endColumn);
			//System.out.println("legal dble ------");
			return true;	
		}
	}
		
		return false;
	}
	
	/**
	 * Translates the letter name of the column into a number
	 * @param str String letter representation of column name
	 * @return int column number or -100 if wrong input passed as argument
	 */
	public int ColumnTranslator(String str) {
		String[] columns = {"a", "b", "c", "d", "e", "f", "g", "h"};
		for (int i = 0; i < columns.length; i++) {
			if (str.equals(columns[i])) {
				return i;
			}
		}
		return -100;
	}
	
	/**
	 * Moves a piece from one square to another of the board
	 * @param fromY starting Y coordinate
	 * @param fromX starting X coordinate
	 * @param toY ending Y coordinate
	 * @param toX ending X coordinate
	 */
	public void Move(int fromY, int fromX, int toY, int toX) {
		 this.brain[fromY][fromX] = BoardType.FLOOR.type;
		 this.brain[toY][toX] = player();
	}
	
	/**
	 * A player jumps over an opponents piece, after which the opponent's piece is taken from the board. 
	 * @param fromY starting Y coordinate
	 * @param fromX starting X coordinate
	 * @param toY ending Y coordinate
	 * @param toX ending X coordinate
	 */
	public void Jump(int fromY, int fromX, int toY, int toX) {
		 boolean left = fromX > toX; 
		 this.brain[fromY][fromX] = BoardType.FLOOR.type;
		 this.brain[toY][toX] = player();
		 
		 if(playerOneTurn) {
			if (left) {
			this.brain[fromY-1][fromX-1] = BoardType.FLOOR.type;
			}
			else {
			this.brain[fromY-1][fromX+1] = BoardType.FLOOR.type;
			}
		 }
		 else {
				if (left) {
				this.brain[fromY+1][fromX-1] = BoardType.FLOOR.type;
				}
				else {
				this.brain[fromY+1][fromX+1] = BoardType.FLOOR.type;
				}
		 }
	}
	
	/**
	 * Returns a boolean regarding the legality of a certain move within the game of Checkers
	 * @param fromY starting Y coordinate
	 * @param fromX starting X coordinate
	 * @param toY ending Y coordinate
	 * @param toX ending X coordinate
	 */
	public boolean LegalMove(int fromY, int fromX, int toY, int toX) {	
		if(Math.abs(fromY - toY) == 1) {
			if(Math.abs(fromX - toX) == 1 && brain[toY][toX] == BoardType.FLOOR.type){
				if(brain[fromY][fromX] == player()) {
					if ((playerOneTurn && fromY > toY)
							||(!playerOneTurn && fromY < toY)) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	/**
	 * Returns a boolean regarding the legality of a jump within the game of Checkers
	 * @param fromY starting Y coordinate
	 * @param fromX starting X coordinate
	 * @param toY ending Y coordinate
	 * @param toX ending X coordinate
	 */
	public boolean LegalJump(int fromY, int fromX, int toY, int toX) {
		boolean zero = fromX == 0; //(x-1) DNE
		boolean max = fromX == standard-1; //(x+1) DNE
		boolean left = fromX > toX;
		//for 2 - player 1
		if ((fromY < 2 && playerOneTurn) 
				|| (fromY > 5 && !playerOneTurn) 
				|| !(brain[toY][toX] == BoardType.FLOOR.type)
				|| !(brain[fromY][fromX] == player())){
			return false;
		}
		if (Math.abs(fromY - toY) == 2 && Math.abs(fromX - toX) == 2) { //single jump and erase opponents piece
			if (playerOneTurn) {
				if(!max) {
					if (brain[fromY-1][fromX+1] == opponent()) {
						return true;
					}	
				}
				if(!zero) { //in case of wall to left
					if (brain[fromY-1][fromX-1] == opponent()) {
						return true;
					}
				}
			}
			else {
				if (!max && !left) {
					if(brain[fromY+1][fromX+1] == opponent()) {
						return true;
				}
				}
				if(!zero && left) { //in case of wall to left
					if (brain[fromY+1][fromX-1] == opponent()) {
						return true;
					}
				}
			}
		}
		return false;	
	}
	

	/**
	 * Returns a boolean regarding the legality of a double jump within the game of Checkers.
	 * IMPORTANT-- Unlike the other Legality checkers, this one also changes the char[][], 
	 * by turning the jumped over squares back into blank tiles directly.
	 * @param fromY starting Y coordinate
	 * @param fromX starting X coordinate
	 * @param toY ending Y coordinate
	 * @param toX ending X coordinate
	 */
	public boolean LegalDoubleJump(int fromY, int fromX, int toY, int toX) {
		if ((fromY < 3 && playerOneTurn) || (fromY > 4 && !playerOneTurn ) 
				|| !(brain[toY][toX] == BoardType.FLOOR.type)
				|| !(brain[fromY][fromX] == player())) {
			return false;
		}
		
		if(Math.abs(fromY - toY) == 4 && (Math.abs(fromX - toX) == 4 || fromX == toX)) {
		if (fromX - toX == 4) { //jump far to the left
			if(playerOneTurn) {
				if(brain[fromY-1][fromX-1] == opponent() 
						&& brain[fromY-3][fromX-3] == opponent() 
						&& brain[fromY-2][fromX-2] == BoardType.FLOOR.type) {
					brain[fromY-1][fromX-1] = BoardType.FLOOR.type;
					brain[fromY-3][fromX-3] = BoardType.FLOOR.type;
					return true;
				}
			}
			else {
				if(brain[fromY+1][fromX-1] == opponent() 
						&& brain[fromY+3][fromX-3] == opponent() 
						&& brain[fromY+2][fromX-2] == BoardType.FLOOR.type) {
					brain[fromY+1][fromX-1] = BoardType.FLOOR.type;
					brain[fromY+3][fromX-3] = BoardType.FLOOR.type;
					return true;
				}
			}
		}
		if (fromX - toX == -4) { //jump far to the right
			if(playerOneTurn) {
				if(brain[fromY-1][fromX+1] == opponent() 
						&& brain[fromY-3][fromX+3] == opponent() 
						&& brain[fromY-2][fromX+2] == BoardType.FLOOR.type) {	
					brain[fromY-1][fromX+1] = BoardType.FLOOR.type;
					brain[fromY-3][fromX+3] = BoardType.FLOOR.type;
					return true;
				}
			}
			else {
				if(brain[fromY+1][fromX+1] == opponent() 
						&& brain[fromY+3][fromX+3] == opponent() 
						&& brain[fromY+2][fromX+2] == BoardType.FLOOR.type) {
					brain[fromY+1][fromX+1] = BoardType.FLOOR.type;
					brain[fromY+3][fromX+3] = BoardType.FLOOR.type;
					return true;
				}
			}
		}
		if (fromX == toX) { //end up same column
			
			if (playerOneTurn) {
				if(fromX < 2) {
					if(brain[fromY-1][fromX+1] == opponent() 
							&& brain[fromY-3][fromX+1] == opponent() 
							&& brain[fromY-2][fromX+2] == BoardType.FLOOR.type) {
						brain[fromY-1][fromX+1] = BoardType.FLOOR.type;
						brain[fromY-3][fromX+1] = BoardType.FLOOR.type;
						return true;
					}
				}
				if(fromX > 5) {
					if(brain[fromY-1][fromX-1] == opponent() &&
							brain[fromY-3][fromX-1] == opponent()
							&& brain[fromY-2][fromX-2] == BoardType.FLOOR.type) {
						brain[fromY-1][fromX-1] = BoardType.FLOOR.type;
						brain[fromY-3][fromX-1] = BoardType.FLOOR.type;
						return true;
					}
				}
				else {
					if(fromX > 1 && fromX < 4) { //aka 2 and 3
						if(brain[fromY-1][fromX-1] == opponent() && //swerve left back to middle
								brain[fromY-3][fromX-1] == opponent()
								&& brain[fromY-2][fromX-2] == BoardType.FLOOR.type) {
							if(LegalJump(fromY, fromX, fromY-2, fromX-2)) {
								brain[fromY-1][fromX-1] = BoardType.FLOOR.type;
								brain[fromY-3][fromX-1] = BoardType.FLOOR.type;
								return true;
							}
							if(brain[fromY-1][fromX+1] == opponent() && //swerve right back to middle
									brain[fromY-3][fromX+1] == opponent()
									&& brain[fromY-2][fromX+2] == BoardType.FLOOR.type) {	
								brain[fromY-1][fromX+1] = BoardType.FLOOR.type;
								brain[fromY-3][fromX+1] = BoardType.FLOOR.type;
								return true;
							}
							if(brain[fromY-1][fromX+1] == opponent() && //swerve far right
									brain[fromY-3][fromX+3] == opponent()
									&& brain[fromY-2][fromX+2] == BoardType.FLOOR.type) {
								brain[fromY-1][fromX+1] = BoardType.FLOOR.type;
								brain[fromY-3][fromX+3] = BoardType.FLOOR.type;
							}
								
							}
					}
						if(fromX > 3 && fromX < 6) { //aka 4 and 5	
						
							if(brain[fromY-1][fromX-1] == opponent() && //swerve left and back to middle
								brain[fromY-3][fromX-1] == opponent()
								&& brain[fromY-2][fromX-2] == BoardType.FLOOR.type) {
								if(LegalJump(fromY, fromX, fromY-2, fromX-2)) {
									brain[fromY-1][fromX-1] = BoardType.FLOOR.type;
									brain[fromY-3][fromX-1] = BoardType.FLOOR.type;
									return true;
						}
					}
						if(brain[fromY-1][fromX+1] == opponent() && //swerve right and back to middle
								brain[fromY-3][fromX+1] == opponent()
								&& brain[fromY-2][fromX+2] == BoardType.FLOOR.type) {
								if(LegalJump(fromY, fromX, fromY-2, fromX+2)) {
									brain[fromY-1][fromX+1] = BoardType.FLOOR.type;
									brain[fromY-3][fromX+1] = BoardType.FLOOR.type;
									return true;
							
						}
						}
						
						if(brain[fromY-1][fromX+1] == opponent() && //swerve far right
								brain[fromY-3][fromX+3] == opponent()
								&& brain[fromY-2][fromX+2] == BoardType.FLOOR.type) {
								if(LegalJump(fromY, fromX, fromY-2, fromX+2)) {
									brain[fromY-1][fromX+1] = BoardType.FLOOR.type;
									brain[fromY-3][fromX+3] = BoardType.FLOOR.type;
									return true;
						}
							
						}
						
						}
				}
			}
			else {
				if(fromX > 5) {
					if(brain[fromY+1][fromX-1] == opponent() && //swerve left
							brain[fromY+3][fromX-1] == opponent()
							&& brain[fromY+2][fromX-2] == BoardType.FLOOR.type) {
						brain[fromY+1][fromX-1] = BoardType.FLOOR.type;
						brain[fromY+3][fromX-1] = BoardType.FLOOR.type;
						return true;
					}	
				}
				if(fromX < 2) {
					if(brain[fromY+1][fromX+1] == opponent() && //swerve right
							brain[fromY+3][fromX+1] == opponent()
							&& brain[fromY+2][fromX+2] == BoardType.FLOOR.type) {
					brain[fromY+1][fromX+1] = BoardType.FLOOR.type;
					brain[fromY+3][fromX+1] = BoardType.FLOOR.type;
					return true;
				}
				}
				else {
					if(fromX > 1 && fromX < 4) { //aka 2 and 3
						if(brain[fromY+1][fromX-1] == opponent() && //swerve left back to middle
								brain[fromY+3][fromX-1] == opponent()
								&& brain[fromY+2][fromX-2] == BoardType.FLOOR.type) {
							if(LegalJump(fromY, fromX, fromY+2, fromX-2)) {
								brain[fromY+1][fromX-1] = BoardType.FLOOR.type;
								brain[fromY+3][fromX-1] = BoardType.FLOOR.type;
								return true;
							}
							if(brain[fromY+1][fromX+1] == opponent() && //swerve right back to middle
									brain[fromY+3][fromX+1] == opponent()
									&& brain[fromY+2][fromX+2] == BoardType.FLOOR.type) {	
								brain[fromY+1][fromX+1] = BoardType.FLOOR.type;
								brain[fromY+3][fromX+1] = BoardType.FLOOR.type;
								return true;
							}
							if(brain[fromY+1][fromX+1] == opponent() && //swerve far right
									brain[fromY+3][fromX+3] == opponent()
									&& brain[fromY+2][fromX+2] == BoardType.FLOOR.type) {
								brain[fromY+1][fromX+1] = BoardType.FLOOR.type;
								brain[fromY+3][fromX+3] = BoardType.FLOOR.type;
							}
								
							}
					}
						if(fromX > 3 && fromX < 6 && fromY < standard-3) { //aka 4 and 5	
						
							if(brain[fromY+1][fromX-1] == opponent() && //swerve left and back to middle
								brain[fromY+3][fromX-1] == opponent()
								&& brain[fromY+2][fromX-2] == BoardType.FLOOR.type) {
								if(LegalJump(fromY, fromX, fromY+2, fromX-2)) {
									brain[fromY+1][fromX-1] = BoardType.FLOOR.type;
									brain[fromY+3][fromX-1] = BoardType.FLOOR.type;
									return true;
						}
					}
						if(brain[fromY+1][fromX+1] == opponent() && //swerve right and back to middle
								brain[fromY+3][fromX+1] == opponent()
								&& brain[fromY+2][fromX+2] == BoardType.FLOOR.type) {
								if(LegalJump(fromY, fromX, fromY+2, fromX+2)) {
									brain[fromY+1][fromX+1] = BoardType.FLOOR.type;
									brain[fromY+3][fromX+1] = BoardType.FLOOR.type;
									return true;
							
						}
						}
						
						if(brain[fromY+1][fromX+1] == opponent() && //swerve far right
								brain[fromY+3][fromX+3] == opponent()
								&& brain[fromY+2][fromX+2] == BoardType.FLOOR.type) {
								if(LegalJump(fromY, fromX, fromY+2, fromX+2)) {
									brain[fromY+1][fromX+1] = BoardType.FLOOR.type;
									brain[fromY+3][fromX+3] = BoardType.FLOOR.type;
									return true;
						}
							
						}
						
						}
				}
			}
		}
		}
		return false;	
	}
	
	/**
	 * Returns a boolean stating whether a given square has legal moves available to it.
	 * @param y Y or row coordinate of starting square
	 * @param x X or column coordinate of starting square
	 * @return true if legal, false otherwise
	 */
	public boolean HasLegalMove(int y, int x) {
		for(int i = 0; i < brain.length; i++) {
			for(int j = 0; j < brain[0].length;j++) {
				if (LegalMove(y, x, i, j) || LegalJump(y,x,i,j)) {
					return true;
				}	
				}
				}
		return false;
	}
	
	/**
	 * Returns a boolean stating whether a given square has legal jumps available to it.
	 * @param y Y or row coordinate of starting square
	 * @param x X or column coordinate of starting square
	 * @return true if legal, false otherwise
	 */
	public boolean HasLegalJump(int y, int x) {
		if (playerOneTurn) {
			if (brain[y][x] == BoardType.X.type) {
				if (x > 1 && y > 1) {
					if(LegalJump(y, x, y-2, x-2)){
						return true;
					}
					if (x < 6) {
					if(LegalJump(y, x, y-2, x+2)){
						return true;
					}
					}
				}
			}
		}
		else {
			if(brain[y][x] == BoardType.O.type) {
				if (x > 1 &&  y > 1 && y < 6) {
					if(LegalJump(y, x, y+2, x-2)){
						return true;
					}
					if(x < 6) {
					if(LegalJump(y, x, y+2, x+2)){
						return true;
					}
					}
				}
			}
		}
		return false;
	}
	
	/**
	 * Method for determining if there are legal moves left to play. 
	 * In effect, checking for end of game conditions.
	 * @return boolean true if there are indeed moves left and false otherwise.
	 */
	public boolean MovesLeft() {
		for(int i = 0; i < brain.length; i++) {
			for(int j = 0; j < brain[0].length;j++) {	
					if (HasLegalMove(i, j)) {
						return true;
					}
			}
		}
		return false;
	}
public boolean getPlayerOneTurn() {
	return playerOneTurn;
}
/**
 * returns a boolean of whether or not the game is over
 * @return boolean of whether the game continues or not
 */
public boolean isGameOver() {
	if(guiEnabled) {
		SquareConverter();
	}
	if(!MovesLeft() || noComputerMoves) {
		return true;
	}
	
	return false;
	}

/**
 * indicates if the game features a computer player for player O
 * @return if playing computer
 */
public boolean ComputerGame() {
	if(computerPlayer) {
		return true;
	}
	return false;
	}

/**
 * creates a computer player to play as player O
 */
public void setComputerPlayer() {
computerPlayer = true;
compy = new CheckersComputerPlayer(brain, this);
}

public boolean numberCheck(int number) {
	if (number > standard-1 || number < 0) {
		return false;
	}
	return true;
}
public void ComputerLoses() {
	noComputerMoves = true;
}
/**
 * returns a char signifying whether player X or player O was the winner of the game
 * @return
 */
public char Winner(){
	if (playerOneTurn) {
		return 'O';
	}
	else return 'X';
	}

/**
 * flips whose turn it is
 */
public void changeTurn() {
	if (playerOneTurn) {
		playerOneTurn = false;
	}
	else {
		playerOneTurn = true;
	}
	}


	}
