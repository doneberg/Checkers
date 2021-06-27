package core;

/**
 * 
 * @author donaldbergeson
 *
 */
public enum BoardType {

	FLOOR('_'), X('x'), O('o');
	
	final char type;
	
	/**
	 * public enum for the associated char of each board type
	 * @param type
	 */
	BoardType(char type) {
		this.type = type;
	}
}
