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
	
	/************************************************************************************************
	 * public Maze():
	 * 		- Constructor for a Maze object that initializes minotaurChance to 0 and generates the 
	 * 		Rooms for mazeSolution LinkedList. 
	 * 
	 ***********************************************************************************************/
	public Maze() {
		minotaurChance = 0;
		Room newRoom = new Room(11);	// Room 11 is Maze's default starting room
		mazeSolution.add(newRoom);		// add starting room to beginning of maze solution
		for(int i=0; i<10; i++) {		// add 10 random rooms after starting room
			newRoom = new Room((random.nextInt(4)+1)*10 + (random.nextInt(3)+1));
			mazeSolution.add(newRoom);
		}
	}
	
	boolean addWrongTurn() {
		minotaurChance += random.nextInt(10)+1;
		if (minotaurChance >= 100)
			return true;		
		return false;
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