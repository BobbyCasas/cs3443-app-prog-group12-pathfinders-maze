package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/****************************************************************************************************
 * @author 
 * UTSA CS 3443 - Group project - Pathfinders - Maze
 * Fall 2021
 * 
 * EndController is a controller class for Escaped.fxml and Minotaur.fxml which 
 * contains FXML variables and event handler methods handleHome() and handleNew().
 ***************************************************************************************************/
public class EndController {
	
	@FXML public void handleHome(ActionEvent event) throws IOException {
		AnchorPane homePane = FXMLLoader.load(getClass().getResource("Home.fxml"));	// homePane loads Login.fxml
		Scene scene = new Scene(homePane);											// scene = homePane
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();	// window = current window
        window.setScene(scene);														// set scene in window
        window.show();																// show window
	}
	
	@FXML public void handleNew(ActionEvent event) throws IOException {
		
	}
}
