package application.controller;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/****************************************************************************************************
 * @author Robert Casas (sgk782)
 * UTSA CS 3443 - Group project - Pathfinders - Maze
 * Fall 2021
 * 
 * HomeController is a controller class for Home.fxml which 
 * contains event handler methods handleStart() and handleExit().
 ***************************************************************************************************/
public class HomeController {
	
	/************************* public void handleStart(ActionEvent event) ************************************
	 * - FXML event handler method that is fired when the "Start" button is clicked, which changes the scene 
	 * to Maze.fxml in the current window.
	 * 
	 * Parameters:
	 * 		ActionEvent event	- ActionEvent object that contains event source, event target, and event type.
	 *********************************************************************************************************/
	@FXML public void handleStart(ActionEvent event) throws IOException {
		GridPane mazePane = FXMLLoader.load(getClass().getResource("/application/view/Maze.fxml"));	// mazePane loads Maze.fxml
		Scene scene = new Scene(mazePane);											// scene = mazePane
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();	// window = current window
        window.setScene(scene);														// set scene in window
        window.show();																// show window
	}
	
	/************************* public void handleExit(ActionEvent event) ************************************
	 * - FXML event handler method that is fired when the "Exit" button is clicked, which closes the 
	 * current window.
	 * 
	 * Parameters:
	 * 		ActionEvent event	- ActionEvent object that contains event source, event target, and event type.
	 *********************************************************************************************************/
	@FXML public void handleExit(ActionEvent event) throws IOException {
		Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();	// window = current window
		window.close();																// close window
	}
}
