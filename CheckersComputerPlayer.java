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

		for (int i = rand.nextInt(standard); i >= 0; i = rand.nextInt(standard)) {
			for (int j = 0; j < standard; j++) {
				for (int k = standard-1; k > 0; k--) {
					for (int l = standard-1; l > 0; l--) {
					//System.out.println("move() = " + j + " "+ i + " " + l + " " + k);
					  if(logic.parseMove(i, j, k, l)) { 
						  return true;
					  }	
					}
				}	
			}
		}return false;	
	}
}
