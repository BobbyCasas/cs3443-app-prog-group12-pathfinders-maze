package application;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MazeController {
	@FXML private Label hint;
	
	@FXML private Pane topPane;
	@FXML private Pane bottomPane;
	@FXML private Pane leftPane;
	@FXML private Pane rightPane;
	
	@FXML public void handleHome(ActionEvent event) throws IOException {
		AnchorPane homePane = FXMLLoader.load(getClass().getResource("Home.fxml"));	// homePane loads Login.fxml
		Scene scene = new Scene(homePane);											// scene = homePane
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();	// window = current window
        window.setScene(scene);														// set scene in window
        window.show();																// show window
	}
	
	@FXML public void handleNew(ActionEvent event) throws IOException {
		leftPane.setStyle("-fx-background-color: black");
		rightPane.setStyle("-fx-background-color: black");
		bottomPane.setStyle("-fx-background-color: black");
	}
	
	@FXML public void handleTop(ActionEvent event) throws IOException {
		
	}
	
	@FXML public void handleBottom(ActionEvent event) throws IOException {
		
	}
	
	@FXML public void handleLeft(ActionEvent event) throws IOException {
		
	}
	
	@FXML public void handleRight(ActionEvent event) throws IOException {
		
	}
}
