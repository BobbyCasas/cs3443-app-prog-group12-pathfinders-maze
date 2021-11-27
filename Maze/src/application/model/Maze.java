package application.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;

/****************************************************************************************************
 * @author Robert Casas (sgk782)
 * UTSA CS 3443 - Group project - Pathfinders - Maze
 * Fall 2021
 * 
 * Maze is a constructor class with methods that load, store, and update a Maze object.
 * 
 * Variables:
 * 		int minotaurChance			- Maze's chance of losing to the Minotaur.
 * 		LinkedList<Room> mazeRooms	- Maze's LinkedList of randomly generated rooms.
 * 		LinkedList<Hint> hints		- Maze's LinkedList of hints populated by loadHints().
 * 		Random random				- Random used for random nuber geration.
 ***************************************************************************************************/
public class Maze {
	
	private int minotaurChance;
	private LinkedList<Room> mazeRooms = new LinkedList<Room>();
	private LinkedList<Hint> hints = new LinkedList<Hint>();
	private Random random = new Random();
	
	/********************************** public Maze() ***********************************************
	 * - Constructor for a Maze object that initializes minotaurChance to 0, generates random 
	 * Room objects for mazeRooms LinkedList, and calls loadHints().
	 ***********************************************************************************************/
	public Maze() {
		minotaurChance = 0;
		Room newRoom = new Room(11);	// key 11 is Maze's start room
		newRoom.setVisited(true);		// set start room as visited
		newRoom.setEntrance("south");	// set start room entrance to "south" so correct exit = straight
		newRoom.addExit("north");		// add "north" to start room's exits ArrayList
		mazeRooms.add(newRoom);			// add start room to mazeRooms
		
		for(int i=0; i<10; i++) {		// add 10 random rooms after starting room
			newRoom = new Room((random.nextInt(2)+3)*10 + (random.nextInt(3)+1));	// every key = (30 or 40) + (1, 2, or 3)
			mazeRooms.add(newRoom);
		}
		loadHints();
	}
	
	/********************************* public void loadHints() **************************************
	 * - Method creates Hint objects from scanning a data file and stores them in hints LinkedList.
	 ***********************************************************************************************/
	public void loadHints() {
		String direction = "";							// stores direction for newHint
		String hint = "";								// stores hint for newHint
		Hint newHint;									// Hint object added to hints LinkedList in loop
		try {
			File file = new File("hints.txt");			// file = "hints.txt"
			Scanner scnr = new Scanner(file);			// Scanner to scan file
			scnr.useDelimiter(",|\n");					// Scanner delimits with commas or newlines
			
			while (scnr.hasNextLine()) {				// while file has next line...
				direction = scnr.next();					// first token = direction/answer
				hint = scnr.next();							// second token = hint
				
				newHint = new Hint(direction, hint);		// create newHint with tokens
				addHint(newHint);							// add newHint to hints LinkedList
			}
			scnr.close();								// close Scanner
		} catch ( FileNotFoundException e ) {
			e.printStackTrace();
		}
	}
	
	/************************* public boolean increaseMinotaurChance() ******************************
	 * - Method increments Minotaur's chance of finding player by 34.
	 * 
	 * Returns:
	 * 		true	- if minotaurChance >= 100
	 * 		false	- otherwise
	 ***********************************************************************************************/
	public boolean increaseMinotaurChance() {
		minotaurChance += 34;
		if (minotaurChance >= 100)
			return true;
		return false;
	}
	
	/********************* public String popHintDirection(String direction) *************************
	 * - Method removes a hint off of Maze's hints LinkedList and returns it as a string based on 
	 * a given direction.
	 * 
	 * Returns:
	 * 		hint	- removed Hint's hint string variable.
	 ***********************************************************************************************/
	public String popHintDirection(String direction) {
		String hint = "...";
		for (int i = 0; i < hints.size(); i++) {
			if (hints.get(i).getDir().equals(direction))
				hint = hints.remove(i).getHint();
		}
		return hint;
	}
	
	// public Room getDeadend(): returns a new Room created with the dead end key
	public Room getDeadend() {
		Room deadendRoom = new Room(14);
		return deadendRoom;
	}

	// public int getMinotaurChance(): returns Maze's minotaurChance
	public int getMinotaurChance() {
		return minotaurChance;
	}

	// public void setMinotaurChance(int minotaurChance): sets Maze's minotaurChance
	public void setMinotaurChance(int minotaurChance) {
		this.minotaurChance = minotaurChance;
	}

	// public LinkedList<Room> getMazeRooms(): returns Maze's mazeRooms LinkedList
	public LinkedList<Room> getMazeRooms() {
		return mazeRooms;
	}

	// public LinkedList<Hint> getHints(): returns Maze's hints LinkedList
	public LinkedList<Hint> getHints() {
		return hints;
	}

	// public void addHint(Hint newHint): adds hint to Maze's hints LinkedList
	public void addHint(Hint newHint) {
		hints.add(newHint);
	}
}