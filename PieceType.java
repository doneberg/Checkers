package ui;

/**
 * 
 * @author donaldbergeson
 *
 */
public enum PieceType {
	BLACK(1), RED(-1);
	
	final int direction;
	
	/**
	 * the two types of pieces in checkers
	 * @param direction specifies which direction can a given piece move
	 */
	PieceType(int direction) {
		this.direction = direction;
	}
	
	
	}