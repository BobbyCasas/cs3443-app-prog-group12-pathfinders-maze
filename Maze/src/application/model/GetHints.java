package application.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
/****************************************************************************************************
 * @author Dylan Johnson (gug903)
 * UTSA CS 3443 - Group project - Pathfinders - Maze
 * Fall 2021
 * 
 * GetHints reads the hints text file and returns the desired output including a hint object array list
 * IMPORTANT NOTE
 * ANY CHANGE TO hint.txt's LOCATION WILL REQUIRE THIS CLASS TO CHANGE AS WELL
 * 
 * hint.txt is expected to be in the format:
 * 
 * Direction,Hint
 * NextDirection,NextHint
 * .
 * .
 * .
 * 
 ***************************************************************************************************/
public class GetHints {
	
	public int getHintSize() throws FileNotFoundException {//returns size of hint text file in lines
		File f= new File("hints.txt");
		Scanner s = new Scanner(f);
		int i=0;
		while(s.hasNextLine()) {
			s.nextLine();
			i++;
		}
		s.close();
		return i;
	}
	public String getHint(int n) throws FileNotFoundException {//returns a specified hint
		File f= new File("hints.txt");
		Scanner s = new Scanner(f);
		for(int i=0;i<n;i++) {
			s.nextLine();
		}
		String hintLine = s.nextLine();
		String[] tok=hintLine.split(",");
		s.close();
		return tok[1];
	}
	public String getDir(int n) throws FileNotFoundException {//returns a specified direction
		File f= new File("hints.txt");
		Scanner s = new Scanner(f);
		for(int i=0;i<n;i++) {
			s.nextLine();
		}
		String hintLine = s.nextLine();
		String[] tok=hintLine.split(",");
		s.close();
		return tok[0];
	}
	
	public ArrayList<Hint> getHintarr() throws FileNotFoundException {//returns a hint object array lsit
		ArrayList<Hint> h=new ArrayList<Hint>();
		for(int i=0;i<getHintSize();i++) {
			h.add(new Hint(getHint(i),getDir(i)));
		}
		h=randomize(h);
		return h;	
	}
	
	public ArrayList<Hint> randomize(ArrayList<Hint> h){
		Hint first=h.remove(0);
		Collections.shuffle(h);
		h.add(0,first);
		return h;
	}
}
