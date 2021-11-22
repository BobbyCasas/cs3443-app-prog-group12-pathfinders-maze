package application.model;

import java.util.ArrayList;

public class Room {
	
	private int key;
	private int numExits;
	private int correctExit;
	private ArrayList<String> exits = new ArrayList<String>();	// set by MazeController when room is visited
	private String entrance;									// north, east, south, or west. set by MazeController
	private boolean visited;
	
	/******************************** public Room(int key) *****************************************
	 * - Constructor for a Room object that sets key and initializes visited to false.
	 * 
	 * Parameters:
	 * 		int key	- Room's two-digit key
	 * 
	 * Keys:
	 * 		- There are 8 possible two-digit keys that a room could have.
	 * 		The first digit(1, 3, or 4) represents how many exits the room has. The second 
	 * 		digit(1, 2, or 3) represents the correct exit relative to the entrance of the 
	 * 		room (1 = straight, 2 = left, 3 = right).
	 * 		- 11 is the key for the Maze start room and 14 is the key for dead ends. All other 
	 * 		rooms have at least 1 entrance, 1 correct exit, and 1 wrong exit.
	 * 
	 * 		|_key_|_FXML_displays_|_correct_exit_|
	 * 		  11	1 exit			straight
	 * 		  14	1 exit			entrance
	 * 		  31	3 exits			straight
	 *        32	3 exits			left
	 *		  33	3 exits			right
	 * 		  41	4 exits			straight
	 * 		  42	4 exits			left
	 * 		  43	4 exits			right
	 ***********************************************************************************************/
	public Room(int key) {
		this.key = key;
		numExits = key / 10;
		correctExit = key % 10;
		visited = false;
	}

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}
	
	public int getNumExits() {
		return numExits;
	}

	public void setNumExits(int numExits) {
		this.numExits = numExits;
	}

	public int getCorrectExit() {
		return correctExit;
	}

	public void setCorrectExit(int correctExit) {
		this.correctExit = correctExit;
	}
	
	public ArrayList<String> getExits() {
		return exits;
	}

	public void setExits(ArrayList<String> exits) {
		this.exits = exits;
	}
	
	public void addExit(String newExit) {
		exits.add(newExit);
	}

	public String getEntrance() {
		return entrance;
	}

	public void setEntrance(String entrance) {
		this.entrance = entrance;
	}

	public boolean isVisited() {
		return visited;
	}

	public void setVisited(boolean visited) {
		this.visited = visited;
	}
}
