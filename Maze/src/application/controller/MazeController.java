package application.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
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
 * @author Robert Casas (sgk782)
 * UTSA CS 3443 - Group project - Pathfinders - Maze
 * Fall 2021
 * 
 * MazeController is a controller class for Maze.fxml which contains FXML variables and event 
 * handler methods to dynamically change the FXML elements on screen to simulate navigating 
 * through a Maze.
 ***************************************************************************************************/
public class MazeController {
	
	@FXML private Label tutorial;								// tutorial Label for maze start room
	@FXML private Label hint;									// hint Label populated with a hint to the correct exit of every room
	
	@FXML private Pane northPane;								// Panes used to hide/reveal exits
	@FXML private Pane eastPane;
	@FXML private Pane westPane;
	@FXML private Pane southPane;
	
	@FXML private Button northButton;							// Buttons for exiting a room, enabled/disabled with Panes
	@FXML private Button eastButton;
	@FXML private Button westButton;
	@FXML private Button southButton;
	
	private Maze maze = new Maze();									// Maze object
	private LinkedList<Room> mazeRooms = maze.getMazeRooms();	// mazeRooms LinkedList from maze
	private int mazeIndex = 0;									// index used to move forward and backward through mazeRooms
	private Room currentRoom = mazeRooms.get(mazeIndex);		// the current room being displayed
	
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
				|| (currentRoom.getEntrance().equals("west") && currentRoom.getCorrectExit() == 2)
				|| (currentRoom.getEntrance().equals("east") 
						&& currentRoom.getCorrectExit() == 3)) {	// if correct exit was taken...
			mazeIndex++;												// increment mazeIndex
			if (mazeIndex >= mazeRooms.size())							// if no more rooms left...
				showEscaped(event);											// show win screen Escaped.fxml
			else {														// else...
				currentRoom = mazeRooms.get(mazeIndex);						// set up next room
				currentRoom.setEntrance("south");							// set entrance to "south"
				showRoom();													// show room
			}
		}
		else if (currentRoom.getEntrance().equals("north")) {		// else if exiting back through room entrance...
			if (currentRoom.getCorrectExit() == 4)						// if current room is a dead end...
				currentRoom = mazeRooms.get(mazeIndex);						// set up room at current index
			else {														// else, player is going backwards...
				mazeIndex--;												// decrement mazeIndex
				currentRoom = mazeRooms.get(mazeIndex);						// set up previous room
			}
			showRoom();													// show room
		}
		else {														// else, wrong exit was taken...
			if (maze.increaseMinotaurChance()) 							// if increased Minotaur chance is 100 or more...
				showMinotaur(event);										// show lose screen Minotaur.fxml
			else {														// else...
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
			mazeIndex++;												// increment mazeIndex
			if (mazeIndex >= mazeRooms.size())							// if no more rooms left...
				showEscaped(event);											// show win screen Escaped.fxml
			else {														// else...
				currentRoom = mazeRooms.get(mazeIndex);						// set up next room
				currentRoom.setEntrance("east");							// set entrance to "east"
				showRoom();													// show room
			}
		}
		else if (currentRoom.getEntrance().equals("west")) {		// else if exiting back through room entrance...			
			if (currentRoom.getCorrectExit() == 4)						// if current room is a dead end...
				currentRoom = mazeRooms.get(mazeIndex);						// set up room at current index
			else {														// else, player is going backwards...
				mazeIndex--;												// decrement mazeIndex
				currentRoom = mazeRooms.get(mazeIndex);						// set up previous room
			}
			showRoom();													// show room
		}
		else {														// else, wrong exit was taken...
			if (maze.increaseMinotaurChance()) 							// if increased Minotaur chance is 100 or more...
				showMinotaur(event);										// show lose screen Minotaur.fxml
			else {														// else...
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
			mazeIndex++;												// increment mazeIndex
			if (mazeIndex >= mazeRooms.size())							// if no more rooms left...
				showEscaped(event);											// show win screen Escaped.fxml
			else {														// else...
				currentRoom = mazeRooms.get(mazeIndex);						// set up next room
				currentRoom.setEntrance("west");							// set entrance to "west"
				showRoom();													// show room
			}
		}
		else if (currentRoom.getEntrance().equals("east")) {		// else if exiting back through room entrance...			
			if (currentRoom.getCorrectExit() == 4)						// if current room is a dead end...
				currentRoom = mazeRooms.get(mazeIndex);						// set up room at current index
			else {														// else, player is going backwards...
				mazeIndex--;												// decrement mazeIndex
				currentRoom = mazeRooms.get(mazeIndex);						// set up previous room
			}
			showRoom();													// show room
		}
		else {														// else, wrong exit was taken...
			if (maze.increaseMinotaurChance()) 							// if increased Minotaur chance is 100 or more...
				showMinotaur(event);										// show lose screen Minotaur.fxml
			else {														// else...
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
			mazeIndex++;												// increment mazeIndex
			if (mazeIndex >= mazeRooms.size())							// if no more rooms left...
				showEscaped(event);											// show win screen Escaped.fxml
			else {														// else...
				currentRoom = mazeRooms.get(mazeIndex);						// set up next room
				currentRoom.setEntrance("north");							// set entrance to "west"
				showRoom();													// show room
			}
		}
		else if (currentRoom.getEntrance().equals("south")) {		// else if exiting back through room entrance...			
			if (currentRoom.getCorrectExit() == 4)						// if current room is a dead end...
				currentRoom = mazeRooms.get(mazeIndex);						// set up room at current index
			else {														// else, player is going backwards...
				mazeIndex--;												// decrement mazeIndex
				currentRoom = mazeRooms.get(mazeIndex);						// set up previous room
			}
			showRoom();													// show room
		}
		else {														// else, wrong exit was taken...
			if (maze.increaseMinotaurChance()) 							// if increased Minotaur chance is 100 or more...
				showMinotaur(event);										// show lose screen Minotaur.fxml
			else {														// else...
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

	/************************************* public void showNorth() ********************************************
	 * - Method adds north to currentRoom exits ArrayList if not visited, enables northButton and sets 
	 * northPane background color to null.
	 *********************************************************************************************************/
	public void showNorth() {
		if (!currentRoom.isVisited())						// if room is not visited...
			currentRoom.addExit("north");						// add "north" to room's exits ArrayList
		northButton.setDisable(false);						// enable north Button
		northPane.setStyle("-fx-background-color: null");	// show north exit
	}
	// public void hideNorth(): disables northButton and sets northPane background color to black
	public void hideNorth() {
		northButton.setDisable(true);
		northPane.setStyle("-fx-background-color: black");
	}
	/************************************* public void showWest() ********************************************
	 * - Method adds west to currentRoom exits ArrayList if not visited, enables westButton and sets 
	 * westPane background color to null.
	 *********************************************************************************************************/
	public void showWest() {
		if (!currentRoom.isVisited())						// if room is not visited...
			currentRoom.addExit("west");						// add "west" to room's exits ArrayList
		westButton.setDisable(false);
		westPane.setStyle("-fx-background-color: null");
	}
	// public void hideWest(): disables westButton and sets westPane background color to black
	public void hideWest() {
		westButton.setDisable(true);
		westPane.setStyle("-fx-background-color: black");
	}
	/************************************* public void showEast() ********************************************
	 * - Method adds east to currentRoom exits ArrayList if not visited, enables eastButton and sets 
	 * eastPane background color to null.
	 *********************************************************************************************************/
	public void showEast() {
		if (!currentRoom.isVisited())						// if room is not visited...
			currentRoom.addExit("east");						// add "east" to room's exits ArrayList
		eastButton.setDisable(false);
		eastPane.setStyle("-fx-background-color: null");	
	}
	// public void hideEast(): disables eastButton and sets eastPane background color to black
	public void hideEast() {
		eastButton.setDisable(true);
		eastPane.setStyle("-fx-background-color: black");	
	}	
	/************************************* public void showSouth() ********************************************
	 * - Method adds south to currentRoom exits ArrayList if not visited, enables southButton and sets 
	 * southPane background color to null.
	 *********************************************************************************************************/
	public void showSouth() {
		if (!currentRoom.isVisited())						// if room is not visited...
			currentRoom.addExit("south");						// add "south" to room's exits ArrayList
		southButton.setDisable(false);
		southPane.setStyle("-fx-background-color: null");
	}
	// public void hideSouth(): disables southButton and sets southPane background color to black
	public void hideSouth() {
		southButton.setDisable(true);
		southPane.setStyle("-fx-background-color: black");
	}
	
	/************************************* public void showRoom() ********************************************
	 * - showRoom() is a method called to display the currentRoom with currentRoom's entrance variable 
	 * previously set. Method first hides all exits and clears the hint Label from the previous room. Then, 
	 * displays the exits and the hint depending on whether currentRoom has been visited or not.
	 * 		- if visited, the currentRoom's exits are retrieved from its exits ArrayList and displayed.
	 * 		- if not visited:
	 * 			- first, method displays currentRoom's entrance set previously.
	 * 				- second, method displays currentRoom's correct exit based on the room's entrance and 
	 * 				populates currentRoom's hint variable with maze.popHintDirection("direction").
	 * 					-third, method displays 1 or 2 wrong exits depending on currentRoom's numExits variable
	 * 				- fourth, method sets currentRoom as visited.
	 * 		- method populates hint Label with currentRoom's hint variable
	 *********************************************************************************************************/
	public void showRoom() {
		hideNorth();											// hide all exits
		hideWest();
		hideEast();
		hideSouth();
		hint.setText("");										// clear hint Label
		
		if (currentRoom.isVisited()) {							// if currentRoom is visited...
			ArrayList<String> exits = currentRoom.getExits();		// get exits ArrayList from currentRoom
			for (String s : exits) {								// show every exit in exits ArrayList
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
		else {													// else, currentRoom not visited
			if (currentRoom.getEntrance().equals("north")) {		// if entrance = north...
				showNorth();											// show entrance
				if (currentRoom.getCorrectExit() == 1) {				// if correct exit = straight...
					currentRoom.setHint(maze.popHintDirection("south"));	// set south hint for room
					showSouth();											// show correct south exit
					showEast();												// show wrong east exit
					if (currentRoom.getNumExits() == 4)						// if room has 4 exits...
						showWest();												// show 2nd wrong exit
				}
				else if (currentRoom.getCorrectExit() == 2) {			// if correct exit = left...
					currentRoom.setHint(maze.popHintDirection("east"));		// set east hint for room
					showEast();												// show correct east exit
					showWest();												// show wrong west exit
					if (currentRoom.getNumExits() == 4)						// if room has 4 exits...
						showSouth();											// show 2nd wrong exit
				}
				else if (currentRoom.getCorrectExit() == 3)	{			// if correct exit = right...
					currentRoom.setHint(maze.popHintDirection("west"));		// set west hint for room
					showWest();												// show correct west exit
					showSouth();											// show wrong south exit
					if (currentRoom.getNumExits() == 4)						// if room has 4 exits...
						showEast();												// show 2nd wrong exit
				}
			}
			else if (currentRoom.getEntrance().equals("west")) {	// else if entrance = west...
				showWest();												// show entrance
				if (currentRoom.getCorrectExit() == 1) {				// if correct exit = straight...
					currentRoom.setHint(maze.popHintDirection("east"));		// set east hint for room
					showEast();												// show correct east exit
					showNorth();											// show wrong north exit
					if (currentRoom.getNumExits() == 4)						// if room has 4 exits...
						showSouth();											// show 2nd wrong exit
				}
				else if (currentRoom.getCorrectExit() == 2) {			// if correct exit = left...
					currentRoom.setHint(maze.popHintDirection("north"));	// set north hint for room
					showNorth();											// show correct north exit
					showSouth();											// show wrong south exit
					if (currentRoom.getNumExits() == 4)						// if room has 4 exits...
						showEast();												// show 2nd wrong exit
				}
				else if (currentRoom.getCorrectExit() == 3)	{			// if correct exit = right...
					currentRoom.setHint(maze.popHintDirection("south"));	// set south hint for room
					showSouth();											// show correct south exit
					showEast();												// show wrong east exit
					if (currentRoom.getNumExits() == 4)						// if room has 4 exits...
						showNorth();											// show 2nd wrong exit
				}
			}
			else if (currentRoom.getEntrance().equals("east")) {	// else if entrance = east...
				showEast();												// show entrance
				if (currentRoom.getCorrectExit() == 1) {				// if correct exit = straight...
					currentRoom.setHint(maze.popHintDirection("west"));		// set west hint for room
					showWest();												// show correct west exit
					showSouth();											// show wrong south exit
					if (currentRoom.getNumExits() == 4)						// if room has 4 exits...
						showNorth();											// show 2nd wrong exit
				}
				else if (currentRoom.getCorrectExit() == 2) {			// if correct exit = left...
					currentRoom.setHint(maze.popHintDirection("south"));	// set south hint for room
					showSouth();											// show correct south exit
					showNorth();											// show wrong north exit
					if (currentRoom.getNumExits() == 4)						// if room has 4 exits...
						showWest();												// show 2nd wrong exit
				}
				else if (currentRoom.getCorrectExit() == 3)	{			// if correct exit = right...
					currentRoom.setHint(maze.popHintDirection("north"));	// set north hint for room
					showNorth();											// show correct north exit
					showWest();												// show wrong west exit
					if (currentRoom.getNumExits() == 4)						// if room has 4 exits...
						showSouth();											// show 2nd wrong exit
				}
			}
			else if (currentRoom.getEntrance().equals("south")) {	// else if entrance = south...
				showSouth();											// show entrance
				if (currentRoom.getCorrectExit() == 1) {				// if correct exit = straight...
					currentRoom.setHint(maze.popHintDirection("north"));	// set north hint for room
					showNorth();											// show correct north exit
					showWest();												// show wrong west exit
					if (currentRoom.getNumExits() == 4)						// if room has 4 exits...
						showEast();												// show 2nd wrong exit
				}
				else if (currentRoom.getCorrectExit() == 2) {			// if correct exit = left...
					currentRoom.setHint(maze.popHintDirection("west"));		// set west hint for room
					showWest();												// show correct west exit
					showEast();												// show wrong east exit
					if (currentRoom.getNumExits() == 4)						// if room has 4 exits...
						showNorth();											// show 2nd wrong exit
				}
				else if (currentRoom.getCorrectExit() == 3)	{			// if correct exit = right...
					currentRoom.setHint(maze.popHintDirection("east"));		// set east hint for room
					showEast();												// show correct east exit
					showNorth();											// show wrong north exit
					if (currentRoom.getNumExits() == 4)						// if room has 4 exits...
						showWest();												// show 2nd wrong exit
				}
			}
			currentRoom.setVisited(true);							// set currentRoom as visited
		}
		hint.setText(currentRoom.getHint());					// show currentRoom's hint
	}
	
	/************************* public void showMinotaur(ActionEvent event) ************************************
	 * - Method changes the scene to the losing screen Minotaur.fxml in the current window.
	 * 
	 * Parameters:
	 * 		ActionEvent event	- ActionEvent object that contains event source, event target, and event type.
	 *********************************************************************************************************/
	public void showMinotaur(ActionEvent event) throws IOException {
		AnchorPane minoPane = FXMLLoader.load(getClass().getResource("/application/view/Minotaur.fxml"));	// minoPane loads Minotaur.fxml
		Scene scene = new Scene(minoPane);											// scene = minoPane
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();	// window = current window
        window.setScene(scene);														// set scene in window
        window.show();																// show window
	}
	
	/************************* public void showEscaped(ActionEvent event) *************************************
	 * - Method changes the scene to the wining screen Escaped.fxml in the current window.
	 * 
	 * Parameters:
	 * 		ActionEvent event	- ActionEvent object that contains event source, event target, and event type.
	 *********************************************************************************************************/
	public void showEscaped(ActionEvent event) throws IOException {
		AnchorPane escPane = FXMLLoader.load(getClass().getResource("/application/view/Escaped.fxml"));	// escPane loads Escaped.fxml
		Scene scene = new Scene(escPane);											// scene = escPane
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();	// window = current window
        window.setScene(scene);														// set scene in window
        window.show();																// show window
	}
}
