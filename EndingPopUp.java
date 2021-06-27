package ui;

import core.BoardType;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class EndingPopUp {
	
	private static boolean status;
	private final static String title = "Checkers 2.0";
	private final static Font font = new Font(25);
	private String paragraph = "\nGame Over! Winner: Player ";
	
public boolean display(char winner) {
	Stage popup = new Stage();
	popup.initModality(Modality.APPLICATION_MODAL);
	
	popup.setTitle(title);
	popup.setMinWidth(25);
	
	Label label = new Label();
	label.setText(paragraph + winner);
	label.setFont(font);

	VBox box = new VBox(CheckersGUI.SIZE);
	box.getChildren().addAll(label);
	box.setAlignment(Pos.CENTER);
	
	Scene scene = new Scene(box);
	popup.setScene(scene);
	popup.showAndWait();
	System.out.println(status);
	return status;
}
}
