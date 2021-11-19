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
	private LinkedList<Integer> mazeSolution = new LinkedList<Integer>();
	private Random random = new Random();
	
	public Maze() {
		minotaurChance = 0;
		
		for(int i=0; i<10; i++)
			mazeSolution.add((random.nextInt(4)+1)*10 + (random.nextInt(3)+1));
	}
	
	boolean addWrongTurn() {
		minotaurChance += random.nextInt(10)+1;
		if (minotaurChance >= 100)
			return true;
		
		return false;
	}
}