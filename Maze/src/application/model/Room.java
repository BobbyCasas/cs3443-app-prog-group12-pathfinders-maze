package application.model;

public class Room {
	
	private int key;
	private boolean visited;
	
	/************************************************************************************************
	 * public Room(int key):
	 * 		- Constructor for a Room object that sets key and initializes visited to false.
	 * 
	 * Parameters:
	 * 		int key	- Room's two-digit key(1 of 10)
	 * 
	 * Keys:
	 * 		- There are 10 possible keys a room could have that contain information about the room.
	 * 		The first digit(1, 2, 3, or 4) represents how many exits the room has. The second 
	 * 		digit(1, 2, or 3) represents the correct exit relative to the entrance of the 
	 * 		room (1 = straight, 2 = left, 3 = right).
	 * 		- 11 is the default key for the Maze start room. All other rooms have at least 1 entrance 
	 * 		and 1 correct exit.
	 * 
	 * 		|_key_|_FXML_displays_|_correct_exit_|
	 * 		  11	1 exit			straight
	 * 		  21	2 exits			straight
	 * 		  22	2 exits			left
	 * 		  23	2 exits			right
	 * 		  31	3 exits			straight
	 *        32	3 exits			left
	 *		  33	3 exits			right
	 * 		  41	4 exits			straight
	 * 		  42	4 exits			left
	 * 		  43	4 exits			right
	 ***********************************************************************************************/
	public Room(int key) {
		this.key = key;
		visited = false;
	}

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}

	public boolean isVisited() {
		return visited;
	}

	public void setVisited(boolean visited) {
		this.visited = visited;
	}
}
