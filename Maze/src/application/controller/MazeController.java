package application.controller;

import java.io.IOException;
import java.util.LinkedList;
import java.util.ListIterator;
import application.model.Maze;
import application.model.Room;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/****************************************************************************************************
 * @author 
 * UTSA CS 3443 - Group project - Pathfinders - Maze
 * Fall 2021
 * 
 * MazeController is a controller class for Maze.fxml which 
 * contains FXML variables and various event handler methods.
 ***************************************************************************************************/
public class MazeController {
	
	@FXML private Label hint;
	@FXML private Label tutorial;
	
	@FXML private Pane northPane;
	@FXML private Pane eastPane;
	@FXML private Pane westPane;
	@FXML private Pane southPane;
	
	@FXML private Button northButton;
	@FXML private Button eastButton;
	@FXML private Button westButton;
	@FXML private Button southButton;
	
	Maze maze = new Maze();
	private LinkedList<Room> mazeSolution = maze.getMazeSolution();
	private ListIterator<Room> mazeItr = mazeSolution.listIterator();
	private Room currentRoom = mazeSolution.getFirst();
	
	private int currentRoomKey;
	private int numExits;
	private int correctExit;
	
	public void initialize() {
		currentRoomKey = currentRoom.getKey();
		numExits = currentRoomKey / 10;
		correctExit = currentRoomKey % 10;
	}
	
	@FXML public void handleHome(ActionEvent event) throws IOException {
		AnchorPane homePane = FXMLLoader.load(getClass().getResource("/application/view/Home.fxml"));	// homePane loads Login.fxml
		Scene scene = new Scene(homePane);											// scene = homePane
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();	// window = current window
        window.setScene(scene);														// set scene in window
        window.show();																// show window
	}
	
	@FXML public void handleNew(ActionEvent event) throws IOException {
		AnchorPane mazePane = FXMLLoader.load(getClass().getResource("/application/view/Maze.fxml"));	// mazePane loads Maze.fxml
		Scene scene = new Scene(mazePane);											// scene = mazePane
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();	// window = current window
        window.setScene(scene);														// set scene in window
        window.show();																// show window
	}
	
	@FXML public void handleTop(ActionEvent event) throws IOException {		
		if (!tutorial.isDisabled())						// if tutorial is showing...
			tutorial.setDisable(true);						// disable it
		
		if (correctExit == 1) {							// if correct exit was taken...
			currentRoom = mazeItr.next();					// set up next room
			currentRoomKey = currentRoom.getKey();
			numExits = currentRoomKey / 10;
			correctExit = currentRoomKey % 10;
		}
		else {											// else, wrong exit was taken...
			maze.minotaurChanceUp();							// increase minotaur chance
			
			northButton.setDisable(true);						// close all exits except south
			northPane.setStyle("-fx-background-color: black");
			westButton.setDisable(true);
			westPane.setStyle("-fx-background-color: black");
			eastButton.setDisable(true);
			eastPane.setStyle("-fx-background-color: black");			
			southButton.setDisable(false);
			southPane.setStyle("-fx-background-color: null");
		}
	}
	
	@FXML public void handleBottom(ActionEvent event) throws IOException {
		
	}
	
	@FXML public void handleLeft(ActionEvent event) throws IOException {
		
	}
	
	@FXML public void handleRight(ActionEvent event) throws IOException {
		
	}
}
