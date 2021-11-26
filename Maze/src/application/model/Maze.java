package application.model;

import java.util.LinkedList;
import java.util.Random;

/****************************************************************************************************
 * @author Robert Casas (sgk782)
 * UTSA CS 3443 - Group project - Pathfinders - Maze
 * Fall 2021
 * 
 * Maze is a constructor class with methods that load, store, and update a Maze object.
 * 
 * Variables:
 * 		Example ex	- This is an example variable for comments
 ***************************************************************************************************/
public class Maze {
	
	private int minotaurChance;
	private LinkedList<Room> mazeSolution = new LinkedList<Room>();
	private Random random = new Random();
	
	/********************************** public Maze() ***********************************************
	 * - Constructor for a Maze object that initializes minotaurChance to 0 and generates the 
	 * Rooms for mazeSolution LinkedList.
	 ***********************************************************************************************/
	public Maze() {
		minotaurChance = 0;
		Room newRoom = new Room(11);	// Room key 11 is Maze's starting room
		newRoom.setVisited(true);		// set room as visited
		newRoom.setEntrance("south");	// set room entrance to "south" for correct exit = straight to be true
		mazeSolution.add(newRoom);		// add starting room to maze solution
		
		for(int i=0; i<10; i++) {		// add 10 random rooms after starting room
			//ADD HINT CALLS HERE WILL NEED TO REPLACE 10 WITH SIZE OF HINT
			newRoom = new Room((random.nextInt(2)+3)*10 + (random.nextInt(3)+1));
			mazeSolution.add(newRoom);
		}
	}
	
	public void increaseMinotaurChance() {
		minotaurChance += random.nextInt(10)+11;
	}
	
	public Room getDeadend() {
		Room deadendRoom = new Room(14);
		return deadendRoom;
	}

	public int getMinotaurChance() {
		return minotaurChance;
	}

	public void setMinotaurChance(int minotaurChance) {
		this.minotaurChance = minotaurChance;
	}

	public LinkedList<Room> getMazeSolution() {
		return mazeSolution;
	}	
}