package application.controller;

import java.io.IOException;
import java.util.ArrayList;
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
import javafx.scene.layout.GridPane;
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
	private Room currentRoom = mazeItr.next();
	private Room returnRoom;
	
	@FXML public void handleHome(ActionEvent event) throws IOException {
		AnchorPane homePane = FXMLLoader.load(getClass().getResource("/application/view/Home.fxml"));	// homePane loads Login.fxml
		Scene scene = new Scene(homePane);											// scene = homePane
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();	// window = current window
        window.setScene(scene);														// set scene in window
        window.show();																// show window
	}
	
	@FXML public void handleNew(ActionEvent event) throws IOException {
		GridPane mazePane = FXMLLoader.load(getClass().getResource("/application/view/Maze.fxml"));	// mazePane loads Maze.fxml
		Scene scene = new Scene(mazePane);											// scene = mazePane
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();	// window = current window
        window.setScene(scene);														// set scene in window
        window.show();																// show window
	}
	
	/************************* public void handleNorth(ActionEvent event) *************************************
	 * - FXML event handler method that is fired when the "North" button is clicked. Method uses 
	 * 	currentRoom to find out if "north" was the correct exit, neutral exit/entrance, or wrong exit of 
	 * 	the current room:
	 * 		- if correct, currentRoom is changed to maseItr.next() and the scene shows the next room with 
	 * 		a south entrance.
	 * 		- if neutral, currentRoom is changed to either returnRoom or mazeItr.previous() and the scene 
	 * 		shows the previous visited room.
	 * 		- if wrong, currentRoom saved in returnRoom then changed to maze.getDeadend() and the scene 
	 * 		shows a dead end with a south entrance/exit.
	 * 
	 * Parameters:
	 * 		ActionEvent event	- ActionEvent object that contains event source, event target, and event type.
	 *********************************************************************************************************/
	@FXML public void handleNorth(ActionEvent event) throws IOException {
		if (currentRoom.getKey() == 11)					// if exiting the start room...
			tutorial.setVisible(false);						// make tutorial Label invisible
		
		hint.setText("");								// clear hint Label
		if ((currentRoom.getEntrance().equals("south") && currentRoom.getCorrectExit() == 1)
				|| (currentRoom.getEntrance().equals("east") && currentRoom.getCorrectExit() == 2)
				|| (currentRoom.getEntrance().equals("west") 
						&& currentRoom.getCorrectExit() == 3)) {	// if correct exit was taken...
			if (!mazeItr.hasNext())										// if no more rooms left...
				showEscaped(event);											// show win screen Escaped.fxml
			else {														// else...
				currentRoom = mazeItr.next();								// set up next room
				currentRoom.setEntrance("south");							// set entrance to "south"
				showSouth();												// show south exit/entrance
				hideNorth();												// hide all other exits
				hideWest();
				hideEast();
				if (currentRoom.getNumExits() == 4) {						// if room has 4 exits...
					showNorth();												// show all exits
					showWest();
					showEast();
				}
				else {														// else, show correct exit + a wrong exit
					if (currentRoom.getCorrectExit() == 1) {					// if correct exit = straight...
						showNorth();												// show correct north exit
						showWest();													// show wrong west exit
					}
					else if (currentRoom.getCorrectExit() == 2) {				// if correct exit = left...
						showWest();													// show correct west exit
						showEast();													// show wrong east exit
					}
					else if (currentRoom.getCorrectExit() == 3)	{				// if correct exit = right...
						showEast();													// show correct east exit
						showNorth();												// show wrong north exit
					}
				}
				currentRoom.setVisited(true);								// set room as visited
				hint.setText("");											// set hint
			}
		}
		else if (currentRoom.getEntrance().equals("north")) {		// else if exiting back through room entrance...			
			if (currentRoom.getCorrectExit() == 4)						// if current room is a dead end...
				currentRoom = returnRoom;									// set up returnRoom
			else														// else, player is going backwards...
				currentRoom = mazeItr.previous();							// set up previous room
			showVisitedRoom();											// show room
		}
		else {														// else, wrong exit was taken...
			maze.increaseMinotaurChance();								// increase Minotaur chance
			if (maze.getMinotaurChance() >= 100) 						// if Minotaur chance is 100 or more...
				showMinotaur(event);										// show lose screen Minotaur.fxml
			else {														// else...
				returnRoom = currentRoom;									// save current room in returnRoom
				currentRoom = maze.getDeadend();							// set up dead end
				currentRoom.setEntrance("south");							// set entrance to "south"
				showSouth();												// show south exit/entrance
				hideNorth();												// hide all other exits
				hideWest();
				hideEast();
				hint.setText("Dead end. The Minotaur grows nearer!");
			}
		}
	}
	
	/************************* public void handleWest(ActionEvent event) *************************************
	 * - FXML event handler method that is fired when the "West" button is clicked. Method uses 
	 * 	currentRoom to find out if "west" was the correct exit, neutral exit/entrance, or wrong exit of 
	 * 	the current room:
	 * 		- if correct, currentRoom is changed to maseItr.next() and the scene shows the next room with 
	 * 		a east entrance.
	 * 		- if neutral, currentRoom is changed to either returnRoom or mazeItr.previous() and the scene 
	 * 		shows the previous visited room.
	 * 		- if wrong, currentRoom saved in returnRoom then changed to maze.getDeadend() and the scene 
	 * 		shows a dead end with a east entrance/exit.
	 * 
	 * Parameters:
	 * 		ActionEvent event	- ActionEvent object that contains event source, event target, and event type.
	 *********************************************************************************************************/
	@FXML public void handleWest(ActionEvent event) throws IOException {
		hint.setText("");								// clear hint Label
		if ((currentRoom.getEntrance().equals("east") && currentRoom.getCorrectExit() == 1)
				|| (currentRoom.getEntrance().equals("south") && currentRoom.getCorrectExit() == 2)
				|| (currentRoom.getEntrance().equals("north") 
						&& currentRoom.getCorrectExit() == 3)) {	// if correct exit was taken...
			if (!mazeItr.hasNext())										// if no more rooms left...
				showEscaped(event);											// show win screen Escaped.fxml
			else {														// else...
				currentRoom = mazeItr.next();								// set up next room
				currentRoom.setEntrance("east");							// set entrance to "east"
				showEast();													// show east exit/entrance
				hideNorth();												// hide all other exits
				hideWest();
				hideSouth();
				if (currentRoom.getNumExits() == 4) {						// if room has 4 exits...
					showNorth();												// show all exits
					showWest();
					showSouth();
				}
				else {														// else, show correct exit + a wrong exit
					if (currentRoom.getCorrectExit() == 1) {					// if correct exit = straight...
						showWest();												// show correct west exit
						showSouth();											// show wrong south exit
					}
					else if (currentRoom.getCorrectExit() == 2) {				// if correct exit = left...
						showSouth();												// show correct south exit
						showNorth();												// show wrong north exit
					}
					else if (currentRoom.getCorrectExit() == 3)	{				// if correct exit = right...
						showNorth();												// show correct north exit
						showWest();													// show wrong west exit
					}
				}
				currentRoom.setVisited(true);								// set room as visited
				hint.setText("");											// set hint
			}
		}
		else if (currentRoom.getEntrance().equals("west")) {		// else if exiting back through room entrance...			
			if (currentRoom.getCorrectExit() == 4)						// if current room is a dead end...
				currentRoom = returnRoom;									// set up returnRoom
			else														// else, player is going backwards...
				currentRoom = mazeItr.previous();							// set up previous room
			showVisitedRoom();											// show room
		}
		else {														// else, wrong exit was taken...
			maze.increaseMinotaurChance();								// increase Minotaur chance
			if (maze.getMinotaurChance() >= 100) 						// if Minotaur chance is 100 or more...
				showMinotaur(event);										// show lose screen Minotaur.fxml
			else {														// else...
				returnRoom = currentRoom;									// save current room in returnRoom
				currentRoom = maze.getDeadend();							// set up dead end
				currentRoom.setEntrance("east");							// set entrance to "east"
				showEast();													// show east exit/entrance
				hideNorth();												// hide all other exits
				hideWest();
				hideSouth();
				hint.setText("Dead end. The Minotaur grows nearer!");
			}
		}
	}
	
	/************************* public void handleEast(ActionEvent event) *************************************
	 * - FXML event handler method that is fired when the "East" button is clicked. Method uses 
	 * 	currentRoom to find out if "east" was the correct exit, neutral exit/entrance, or wrong exit of 
	 * 	the current room:
	 * 		- if correct, currentRoom is changed to maseItr.next() and the scene shows the next room with 
	 * 		a west entrance.
	 * 		- if neutral, currentRoom is changed to either returnRoom or mazeItr.previous() and the scene 
	 * 		shows the previous visited room.
	 * 		- if wrong, currentRoom saved in returnRoom then changed to maze.getDeadend() and the scene 
	 * 		shows a dead end with a west entrance/exit.
	 * 
	 * Parameters:
	 * 		ActionEvent event	- ActionEvent object that contains event source, event target, and event type.
	 *********************************************************************************************************/
	@FXML public void handleEast(ActionEvent event) throws IOException {
		hint.setText("");								// clear hint Label
		if ((currentRoom.getEntrance().equals("west") && currentRoom.getCorrectExit() == 1)
				|| (currentRoom.getEntrance().equals("north") && currentRoom.getCorrectExit() == 2)
				|| (currentRoom.getEntrance().equals("south") 
						&& currentRoom.getCorrectExit() == 3)) {	// if correct exit was taken...
			if (!mazeItr.hasNext())										// if no more rooms left...
				showEscaped(event);											// show win screen Escaped.fxml
			else {														// else...
				currentRoom = mazeItr.next();								// set up next room
				currentRoom.setEntrance("west");							// set entrance to "west"
				showWest();													// show west exit/entrance
				hideNorth();												// hide all other exits
				hideEast();
				hideSouth();
				if (currentRoom.getNumExits() == 4) {						// if room has 4 exits...
					showNorth();												// show all exits
					showEast();
					showSouth();
				}
				else {														// else, show correct exit + or - a wrong exit
					if (currentRoom.getCorrectExit() == 1) {					// if correct exit = straight...
						showEast();													// show correct east exit
						showNorth();												// show wrong north exit
					}
					else if (currentRoom.getCorrectExit() == 2) {				// if correct exit = left...
						showNorth();												// show correct north exit
						showSouth();												// show wrong south exit
					}
					else if (currentRoom.getCorrectExit() == 3)	{				// if correct exit = right...
						showSouth();												// show correct south exit
						showEast();													// show wrong east exit
					}
				}
				currentRoom.setVisited(true);								// set room as visited
				hint.setText("");											// set hint
			}
		}
		else if (currentRoom.getEntrance().equals("east")) {		// else if exiting back through room entrance...			
			if (currentRoom.getCorrectExit() == 4)						// if current room is a dead end...
				currentRoom = returnRoom;									// set up returnRoom
			else														// else, player is going backwards...
				currentRoom = mazeItr.previous();							// set up previous room
			showVisitedRoom();											// show room
		}
		else {														// else, wrong exit was taken...
			maze.increaseMinotaurChance();								// increase Minotaur chance
			if (maze.getMinotaurChance() >= 100) 						// if Minotaur chance is 100 or more...
				showMinotaur(event);										// show lose screen Minotaur.fxml
			else {														// else...
				returnRoom = currentRoom;									// save current room in returnRoom
				currentRoom = maze.getDeadend();							// set up dead end
				currentRoom.setEntrance("west");							// set entrance to "west"
				showWest();													// show west exit/entrance
				hideNorth();												// hide all other exits
				hideEast();
				hideSouth();
				hint.setText("Dead end. The Minotaur grows nearer!");
			}
		}
	}
	
	/************************* public void handleSouth(ActionEvent event) *************************************
	 * - FXML event handler method that is fired when the "South" button is clicked. Method uses 
	 * 	currentRoom to find out if "south" was the correct exit, neutral exit/entrance, or wrong exit of 
	 * 	the current room:
	 * 		- if correct, currentRoom is changed to maseItr.next() and the scene shows the next room with 
	 * 		a north entrance.
	 * 		- if neutral, currentRoom is changed to either returnRoom or mazeItr.previous() and the scene 
	 * 		shows the previous visited room.
	 * 		- if wrong, currentRoom saved in returnRoom then changed to maze.getDeadend() and the scene 
	 * 		shows a dead end with a north entrance/exit.
	 * 
	 * Parameters:
	 * 		ActionEvent event	- ActionEvent object that contains event source, event target, and event type.
	 *********************************************************************************************************/
	@FXML public void handleSouth(ActionEvent event) throws IOException {
		hint.setText("");								// clear hint Label
		if ((currentRoom.getEntrance().equals("north") && currentRoom.getCorrectExit() == 1)
				|| (currentRoom.getEntrance().equals("east") && currentRoom.getCorrectExit() == 2)
				|| (currentRoom.getEntrance().equals("west") 
						&& currentRoom.getCorrectExit() == 3)) {	// if correct exit was taken...
			if (!mazeItr.hasNext())										// if no more rooms left...
				showEscaped(event);											// show win screen Escaped.fxml
			else {														// else...
				currentRoom = mazeItr.next();								// set up next room
				currentRoom.setEntrance("north");							// set entrance to "west"
				showNorth();												// show north exit/entrance
				hideWest();													// hide all other exits
				hideEast();
				hideSouth();
				if (currentRoom.getNumExits() == 4) {						// if room has 4 exits...
					showWest();													// show all exits
					showEast();
					showSouth();
				}
				else {														// else, show correct exit + a wrong exit
					if (currentRoom.getCorrectExit() == 1) {					// if correct exit = straight...
						showSouth();												// show correct south exit
						showEast();													// show wrong east exit
					}
					else if (currentRoom.getCorrectExit() == 2) {				// if correct exit = left...
						showEast();													// show correct east exit
						showWest();													// show wrong west exit
					}
					else if (currentRoom.getCorrectExit() == 3)	{				// if correct exit = right...
						showWest();													// show correct west exit
						showSouth();												// show wrong south exit
					}
				}
				currentRoom.setVisited(true);								// set room as visited
				hint.setText("");											// set hint
			}
		}
		else if (currentRoom.getEntrance().equals("south")) {		// else if exiting back through room entrance...			
			if (currentRoom.getCorrectExit() == 4)						// if current room is a dead end...
				currentRoom = returnRoom;									// set up returnRoom
			else														// else, player is going backwards...
				currentRoom = mazeItr.previous();							// set up previous room
			showVisitedRoom();											// show room
		}
		else {														// else, wrong exit was taken...
			maze.increaseMinotaurChance();								// increase Minotaur chance
			if (maze.getMinotaurChance() >= 100) 						// if Minotaur chance is 100 or more...
				showMinotaur(event);										// show lose screen Minotaur.fxml
			else {														// else...
				returnRoom = currentRoom;									// save current room in returnRoom
				currentRoom = maze.getDeadend();							// set up dead end
				currentRoom.setEntrance("north");							// set entrance to "north"
				showNorth();												// show north exit/entrance
				hideWest();													// hide all other exits
				hideEast();
				hideSouth();
				hint.setText("Dead end. The Minotaur grows nearer!");
			}
		}
	}
	
	public void showNorth() {
		if (!currentRoom.isVisited())						// if room is not visited
			currentRoom.addExit("north");						// add "north" to room's exits ArrayList
		northButton.setDisable(false);						// show north Button
		northPane.setStyle("-fx-background-color: null");	// show north exit
	}
	public void hideNorth() {
		northButton.setDisable(true);
		northPane.setStyle("-fx-background-color: black");
	}
	public void showWest() {
		if (!currentRoom.isVisited())						// if room is not visited
			currentRoom.addExit("west");						// add "west" to room's exits ArrayList
		westButton.setDisable(false);
		westPane.setStyle("-fx-background-color: null");
	}
	public void hideWest() {
		westButton.setDisable(true);
		westPane.setStyle("-fx-background-color: black");
	}
	public void showEast() {
		if (!currentRoom.isVisited())						// if room is not visited
			currentRoom.addExit("east");						// add "east" to room's exits ArrayList
		eastButton.setDisable(false);
		eastPane.setStyle("-fx-background-color: null");	
	}
	public void hideEast() {
		eastButton.setDisable(true);
		eastPane.setStyle("-fx-background-color: black");	
	}
	public void showSouth() {
		if (!currentRoom.isVisited())						// if room is not visited
			currentRoom.addExit("south");						// add "south" to room's exits ArrayList
		southButton.setDisable(false);
		southPane.setStyle("-fx-background-color: null");
	}
	public void hideSouth() {
		southButton.setDisable(true);
		southPane.setStyle("-fx-background-color: black");
	}
	
	public void showVisitedRoom() {
		hideNorth();										// hide all exits
		hideWest();
		hideEast();
		hideSouth();
		ArrayList<String> exits = currentRoom.getExits();	// get exits ArrayList from currentRoom
		for (String s : exits) {							// show every exit in exits ArrayList
			if (s.equals("north"))
				showNorth();
			else if (s.equals("west"))
				showWest();
			else if (s.equals("east"))
				showEast();
			else if (s.equals("south"))
				showSouth();
		}
	}
	
	public void showMinotaur(ActionEvent event) throws IOException {
		AnchorPane minoPane = FXMLLoader.load(getClass().getResource("/application/view/Minotaur.fxml"));	// minoPane loads Minotaur.fxml
		Scene scene = new Scene(minoPane);											// scene = minoPane
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();	// window = current window
        window.setScene(scene);														// set scene in window
        window.show();																// show window
	}
	
	public void showEscaped(ActionEvent event) throws IOException {
		AnchorPane escPane = FXMLLoader.load(getClass().getResource("/application/view/Escaped.fxml"));	// escPane loads Escaped.fxml
		Scene scene = new Scene(escPane);											// scene = escPane
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();	// window = current window
        window.setScene(scene);														// set scene in window
        window.show();																// show window
	}
}
