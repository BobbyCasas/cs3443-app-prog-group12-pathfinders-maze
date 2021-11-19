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
	
	@FXML private Pane topPane;
	@FXML private Pane leftPane;
	@FXML private Pane rightPane;
	@FXML private Pane bottomPane;
	
	@FXML private Button topButton;
	@FXML private Button leftButton;
	@FXML private Button rightButton;
	@FXML private Button bottomButton;
	
	private LinkedList<Room> mazeSolution = new LinkedList<Room>();
	private ListIterator<Room> mazeItr;
	private Room currentRoom;
	
	public void initialize() {
		Maze maze = new Maze();
		mazeSolution = maze.getMazeSolution();
		mazeItr = mazeSolution.listIterator();
		currentRoom = mazeSolution.getFirst();
	}
	
	@FXML public void handleHome(ActionEvent event) throws IOException {
		AnchorPane homePane = FXMLLoader.load(getClass().getResource("/application/view/Home.fxml"));	// homePane loads Login.fxml
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
		if (currentRoom.getKey() == 11 
				|| currentRoom.getKey() == 21 
				|| currentRoom.getKey() == 31 
				|| currentRoom.getKey() == 41) {
			currentRoom = mazeItr.next();
			//more code here
		}
		else {
			
		}
	}
	
	@FXML public void handleBottom(ActionEvent event) throws IOException {
		
	}
	
	@FXML public void handleLeft(ActionEvent event) throws IOException {
		
	}
	
	@FXML public void handleRight(ActionEvent event) throws IOException {
		
	}
}
