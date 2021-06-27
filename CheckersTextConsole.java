package ui;
import java.util.Scanner;
import core.CheckersLogic;
import javafx.application.Application;
/**
 * 
 * @author donaldbergeson
 * @version 1.0
 */
public class CheckersTextConsole {

	public static void main(String[] args) throws Exception {
		try {
		String choice;
		String input;
		String player = "";
		Scanner in = new Scanner(System.in);
		
		
		Screen("Welcome to Checkers. \nEnter 'C' to play in the Console or enter 'G' to play with a GUI");
		choice = in.next();
		while (!(choice.equals("G") || choice.equals("g") || choice.equals("C") || choice.equals("c"))){
			Screen("Incorrect input, do try again.");
			choice = in.next();
		}
		if (choice.equals("G") || choice.equals("g")) {
			Application.launch(CheckersGUI.class, args);
		}
		else {
		CheckersLogic game = new CheckersLogic();
		Screen(game.toString());		
		Screen("Begin Game. Enter 'P' if you want to play against another player;\nenter 'C' to play against computer.");
		choice = in.next();
		while (!(choice.equals("P") || choice.equals("p") || choice.equals("C") || choice.equals("c"))){
			Screen("Wrong input, do try again.");
			choice = in.next();
		}
		
		if(choice.equals("C") || choice.equals("c")) {
			game.setComputerPlayer();
		}
		
		
		if (game.ComputerGame()) {
			player = " against computer";
		}
		Screen("\nStart game" + player + ".");
		
		while (!game.isGameOver()) 
		{
		Screen(game.toString());
		Screen("Player" + game.Turn() + " - your turn." );
		Screen("Choose a cell position of piece to be moved and the new position. e.g. 3a-4b");
		
		input = in.next();
		while (!(isValid(input))) {
			Screen("Improper input, Player" + game.Turn() + ", do try again.");
			input = in.next();
		}
		boolean valid = game.readMove(input);	
			while(!valid) {
			Screen("illegal move, but do try again old chap");
			valid = game.readMove(in.next());
			}
			
		game.nextTurn();
		}
		Screen(game.toString());
		Screen("Player " + game.Winner() + " Won the Game");
		
		in.close();
		}
		}
		catch (IllegalArgumentException exception) {
			// Catch some IllegalArgumentExceptions
			System.out.println("Please try again, my friend.");
		}
	}
	
	/**
	 * Prints the string to the console followed by two new lines.
	 * @param str String to be printed to console
	 */
	public static void Screen(String str) { 
		System.out.println(str);
		System.out.println();
	}
	
	/**
	 * boolean method that returns whether string contains a number from 0-8
	 * @param str String under consideration
	 * @return if given String is a number 0-8
	 */
	public static boolean isNumber(String str) {
		if (str.equals("0") || str.equals("1") || str.equals("2") || str.equals("3") || str.equals("4") || str.equals("5") 
				|| str.equals("6") || str.equals("7") || str.equals("8")){
			return true;
		}
		return false;
	}
	
	/**
	 * method that tells if a given String is a letter (a-h v A-H)
	 * @param str String under consideration
	 * @return if a letter (a-h v A-H)
	 */
	public static boolean isLetter(String str) { //specifically. a letter a-h
		if (str.equals("a") || str.equals("b") || str.equals("c") || str.equals("d") || str.equals("e") || str.equals("f") 
				|| str.equals("g") || str.equals("h")){
			return true;
		}
		if (str.equals("A") || str.equals("B") || str.equals("C") || str.equals("D") || str.equals("E") || str.equals("F") 
				|| str.equals("G") || str.equals("H")){
			return true;
		}
		return false;
	}
	
	/**
	 * method for determining if a given String is of the correct basic format for the Checkers game
	 * @param input the String being considered
	 * @return if the answer is a valid Checkers move (Note: does not return whether the Checkers move
	 * is legal in current game, only if the basic format is correct.)
	 */
	public static boolean isValid(String input) {
		if (input.length() > 4 && input.length() < 10) {
		if (isLetter(input.substring(1,2)) && isLetter(input.substring(4)) && isNumber(input.substring(0,1)) && isNumber(input.substring(3,4))){
			
				return true;
			}
		}
		return false;
	}	
}
