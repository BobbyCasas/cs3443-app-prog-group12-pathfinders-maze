package application.controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/****************************************************************************************************
 * @author Robert Casas (sgk782)
 * UTSA CS 3443 - Group project - Pathfinders - Maze
 * Fall 2021
 * 
 * EndController is a controller class for Escaped.fxml and Minotaur.fxml which 
 * contains FXML variables and event handler methods handleHome() and handleNew().
 ***************************************************************************************************/
public class EndController {
	
	/************************* public void handleHome(ActionEvent event) *************************************
	 * - FXML event handler method that is fired when the "Home" button is clicked, which changes the scene 
	 * to Home.fxml in the current window.
	 * 
	 * Parameters:
	 * 		ActionEvent event	- ActionEvent object that contains event source, event target, and event type.
	 *********************************************************************************************************/
	@FXML public void handleHome(ActionEvent event) throws IOException {
		AnchorPane homePane = FXMLLoader.load(getClass().getResource("/application/view/Home.fxml"));	// homePane loads Login.fxml
		Scene scene = new Scene(homePane);											// scene = homePane
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();	// window = current window
        window.setScene(scene);														// set scene in window
        window.show();																// show window
	}
	
	/************************* public void handleNew(ActionEvent event) *************************************
	 * - FXML event handler method that is fired when the "New Maze" button is clicked, which changes the 
	 * scene to a new Maze.fxml in the current window.
	 * 
	 * Parameters:
	 * 		ActionEvent event	- ActionEvent object that contains event source, event target, and event type.
	 *********************************************************************************************************/
	@FXML public void handleNew(ActionEvent event) throws IOException {
		GridPane mazePane = FXMLLoader.load(getClass().getResource("/application/view/Maze.fxml"));	// mazePane loads Maze.fxml
		Scene scene = new Scene(mazePane);											// scene = mazePane
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();	// window = current window
        window.setScene(scene);														// set scene in window
        window.show();																// show window
	}
}
