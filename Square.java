package ui;

import javafx.scene.shape.Rectangle;

public class Square extends Rectangle {

	private Piece piece;

	public Square(boolean light, int y, int x) {
		this.setWidth(CheckersGUI.SIZE);
		this.setHeight(CheckersGUI.SIZE);
		this.setTranslateX(x *  CheckersGUI.SIZE); 
		this.setTranslateY(y *  CheckersGUI.SIZE);
		setFill(light ? CheckersGUI.green[1] : 
			CheckersGUI.green[0]);
	}
	
	public Piece getPiece() {
		return piece;
	}
	
	public void setPiece(Piece Piece) {
		this.piece = Piece;
	}
	public boolean hasPiece() {
		return piece != null;
	}
	
	
}
