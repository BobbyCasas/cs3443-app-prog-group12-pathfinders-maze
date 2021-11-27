package application.model;

import java.util.ArrayList;

/****************************************************************************************************
 * @author Robert Casas (sgk782)
 * UTSA CS 3443 - Group project - Pathfinders - Maze
 * Fall 2021
 * 
 * Room is a constructor class with methods that load, store, and update a Maze object.
 * 
 * Variables:
 * 		int key					- Room's two-digit key.
 * 		int numExits			- Room's number of exits (1, 3, or 4).
 * 		int correctExit			- Room's correct exit relative to entrance (1, 2, 3, or 4).
 * 		ArrayList<String> exits	- Room's ArrayList of exits.
 * 		String entrance			- Room's entrance
 * 		String hint				- Room's hint toward the correct exit.
 * 		boolean visited			- true if Room has been visited by the player.
 ***************************************************************************************************/
public class Room {
	
	private int key;
	private int numExits;
	private int correctExit;
	private ArrayList<String> exits = new ArrayList<String>();	// set by MazeController when room is visited
	private String entrance;									// north, east, south, or west. set by MazeController
	private String hint;
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
	 * 		digit(1, 2, 3, or 4) represents the correct exit relative to the entrance of the 
	 * 		room (1 = straight, 2 = left, 3 = right, 4 = back).
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

	// public int getKey(): returns Room's key
	public int getKey() {
		return key;
	}

	// public void setKey(int key): sets Room's key
	public void setKey(int key) {
		this.key = key;
	}
	
	// public int getNumExits(): returns Room's number of exits
	public int getNumExits() {
		return numExits;
	}

	// public void setNumExits(int numExits): sets Room's number of exits
	public void setNumExits(int numExits) {
		this.numExits = numExits;
	}

	// public int getCorrectExit(): returns Room's correct exit
	public int getCorrectExit() {
		return correctExit;
	}

	// public void setCorrectExit(int correctExit): sets Room's correct exit
	public void setCorrectExit(int correctExit) {
		this.correctExit = correctExit;
	}
	
	// public ArrayList<String> getExits(): returns Room's exits ArrayList
	public ArrayList<String> getExits() {
		return exits;
	}
	
	// public void addExit(String newExit): adds exit to Room's exits ArrayList
	public void addExit(String newExit) {
		exits.add(newExit);
	}

	// public String getEntrance(): returns Room's entrance
	public String getEntrance() {
		return entrance;
	}

	// public void setEntrance(String entrance): sets Room's entrance
	public void setEntrance(String entrance) {
		this.entrance = entrance;
	}

	// public String getHint(): returns Room's hint
	public String getHint() {
		return hint;
	}

	// public void setHint(String hint): sets Room's hint
	public void setHint(String hint) {
		this.hint = hint;
	}

	// public boolean isVisited(): returns true if Room is visited
	public boolean isVisited() {
		return visited;
	}

	// public void setVisited(boolean visited): sets Room's visited boolean
	public void setVisited(boolean visited) {
		this.visited = visited;
	}
}
