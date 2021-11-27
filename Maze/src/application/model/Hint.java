package application.model;
/****************************************************************************************************
 * @author Dylan Johnson (gug903)
 * UTSA CS 3443 - Group project - Pathfinders - Maze
 * Fall 2021
 *  
 * Hint class to store hints and their steps
 * 
 * 
 ***************************************************************************************************/
public class Hint {
	private String hint;
	private String dir;
	/**
	 * @return the hint
	 */
	public String getHint() {
		return hint;
	}
	/**
	 * @param hint the hint to set
	 */
	public void setHint(String hint) {
		this.hint = hint;
	}
	/**
	 * @return the dir
	 */
	public String getDir() {
		return dir;
	}
	/**
	 * @param dir the dir to set
	 */
	public void setDir(String dir) {
		this.dir = dir;
	}
	public Hint(String dir, String hint) {//constructor for Hint class
		this.dir = dir;
		this.hint = hint;
	}
	
	
}
