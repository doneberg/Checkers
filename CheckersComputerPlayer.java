package core;

import java.util.Random;
import ui.CheckersGUI;
import ui.MoveResult;
import ui.MoveType;
import ui.Piece;
import ui.Square;
/**
 * Class for computer control of Player O (Player 2) in a game of Checkers.
 * @author donaldbergeson
 *
 */
public class CheckersComputerPlayer {
	final int standard = 8;
	char[][] brain;
	Square[][] mind;
	CheckersGUI gui;
	CheckersLogic logic;
	MoveType moveType;
	MoveResult move;
	Random rand = new Random();
	Piece piece;

	/**
	 * Constructor for text based game
	 * @param Brain char[][] board
	 * @param Logic checkers logic object
	 */
	public CheckersComputerPlayer(char[][] Brain, CheckersLogic Logic) {
	this.brain = Brain;	
	this.logic = Logic;
	}
	
	/**
	 * function for finding a legal move for the computer operated player 2
	 * @return if operation was successful
	 */
	public boolean move() {
		for(int i = 0; i <= standard - 1; i++) {
			for(int j = 0; j <= standard-1; j++) {
				if(brain[i][j] == 'o') {

			if((j < (standard-1)) && logic.parseMove(i, j, i + 1, j + 1)) {
				//System.out.println("right");
				return true;
			}
			
			if(j > 0 && logic.parseMove(i, j, i + 1, j - 1)) {
				//System.out.println("left");
				return true;
			}
			
			if((i < 6 && j < 6) && logic.parseMove(i, j, i + 2, j + 2)) {
				//System.out.println(" jump right");
				return true;
			}
			
			if((i < 6 && j > 1) && logic.parseMove(i, j, i + 2, j - 2)) {
				//System.out.println(" jump left");
				return true;
			}
				}
		
		}
		}
		return false;
	}

}
