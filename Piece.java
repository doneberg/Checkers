package ui;

import javafx.scene.layout.StackPane;
import javafx.scene.shape.Ellipse;
import core.CheckersLogic;
public class Piece extends StackPane {

	private PieceType type;
	
	private double mouseX, mouseY;
	private double oldX, oldY;
	
	public PieceType getType() {
		return type;
	}
	
	public double getOldY() {
		return oldY;
	}
	
	public double getOldX() {
		return oldX;
	}
	
	public void reset() {
		relocate(oldX, oldY);
	}
	

	public Piece(PieceType Type, int y, int x) {
		this.type = Type;
		
		move(y,x);
		
		Ellipse top = new Ellipse(CheckersGUI.SIZE * 0.3000, 
				CheckersGUI.SIZE * 0.25);
		Ellipse bottom = new Ellipse(CheckersGUI.SIZE * 0.3000, 
				CheckersGUI.SIZE * 0.25);		
		top(top);
		bottom(bottom);
		
		this.getChildren().addAll(bottom, top);
		
		setOnMousePressed( e -> {
			mouseX = e.getSceneX();
			mouseY = e.getSceneY();
		});
		
		setOnMouseDragged(e -> {
			relocate(e.getSceneX() - mouseX + oldX, 
					e.getSceneY() - mouseY + oldY);
		});
	}
	
	private void top(Ellipse ellipse) {
		ellipse.setFill(type == PieceType.RED ?
				CheckersGUI.red[0] : CheckersGUI.black[1]);
		ellipse.setStroke(type == PieceType.RED ?
				CheckersGUI.black[0] : CheckersGUI.black[1]);
		ellipse.setTranslateX((CheckersGUI.SIZE - CheckersGUI.SIZE*0.3000*2)/2);
		ellipse.setTranslateY((CheckersGUI.SIZE - CheckersGUI.SIZE*0.25*2)/2 -  CheckersGUI.SIZE * .05);
	}
	
	private void bottom(Ellipse ellipse) {
		ellipse.setFill(type == PieceType.RED ?
				CheckersGUI.black[0] : CheckersGUI.black[0]);
		ellipse.setStroke(type == PieceType.RED ?
				CheckersGUI.red[1] : CheckersGUI.black[1]);
		ellipse.setStrokeWidth(CheckersGUI.SIZE * 0.03);
		ellipse.setTranslateX((CheckersGUI.SIZE - CheckersGUI.SIZE * 0.3000 * 2)/2);
		ellipse.setTranslateY((CheckersGUI.SIZE - CheckersGUI.SIZE * 0.25 * 2)/2 );
		
	}
	
	public void move(int y, int x)
	{
		oldX = x * CheckersGUI.SIZE;
		oldY = y * CheckersGUI.SIZE;
		relocate(oldX, oldY);
	}
	
}
