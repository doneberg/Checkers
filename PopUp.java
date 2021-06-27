package ui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class PopUp {
	
	private static boolean status;
	private final static String title = "Checkers 2.0";
	private final static Font font = new Font(25);
	private final static int SIZE = 10;
/**
 * displays a question with a yes or no answer	
 * @param question string to be displayed (the question)
 * @return boolean
 */
public boolean display(String question) {
	Stage popup = new Stage();
	popup.initModality(Modality.APPLICATION_MODAL);
	
	popup.setTitle(title);
	popup.setMinWidth(25);
	
	Label label = new Label();
	label.setText(question);
	label.setFont(font);
	Button yes = new Button("yes");
	yes.setMinSize(SIZE, SIZE);
	yes.setFont(font);
	
	yes.setOnAction(e ->{
		status = true;
		popup.close();
	});
	Button no = new Button("no");
	no.setMinSize(SIZE, SIZE);
	no.setFont(font);
	no.setOnAction(e ->{
		status = false;
		popup.close();
	});
	
	HBox answer = new HBox();
	answer.getChildren().addAll(yes, no);
	answer.setAlignment(Pos.CENTER);
	answer.setSpacing(CheckersGUI.SIZE);
	
	VBox box = new VBox(CheckersGUI.SIZE);
	box.getChildren().addAll(label, answer);
	box.setAlignment(Pos.CENTER);
	
	Scene scene = new Scene(box);
	popup.setScene(scene);
	popup.showAndWait();
	System.out.println(status);
	return status;
}
}
