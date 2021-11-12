package application;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/****************************************************************************************************
 * @author Robert Casas
 * UTSA CS 3443 - Group project - Pathfinders - Maze
 * Fall 2021
 * 
 * HomeController is a controller class for Home.fxml which 
 * contains event handler methods handleStart() and handleExit().
 ***************************************************************************************************/
public class HomeController {
	
	@FXML public void handleStart(ActionEvent event) throws IOException {
		GridPane mazePane = FXMLLoader.load(getClass().getResource("Maze.fxml"));	// mazePane loads Maze.fxml
		Scene scene = new Scene(mazePane);											// scene = mazePane
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();	// window = current window
        window.setScene(scene);														// set scene in window
        window.show();																// show window
	}
	
	@FXML public void handleExit(ActionEvent event) throws IOException {
		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();	// window = current window
		window.close();																// close window
	}
}
