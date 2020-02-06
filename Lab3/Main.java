/**
 * Class that takes an input from a file and opens the file reader method in another class 
 * 
 * Name: Main.java
 *  
 * Written by: Niall Quinn - October 2018 
 * 
 * Purpose: Get the input file from the user and read jobs from that file 
 * 
 * usage: Put Main, SharedMemory, SwapSpace and MainMemory in the same folder. 
 * Open a command window there and use javac *.java, followed by jave Main.  
 * 
 * Subroutines/Libraries: See import statements below 
 * 
 */

import java.util.Scanner;

public class Main {
	
	public static void main (String[]args) {
		//Asks the user to enter the file name
		System.out.print("Enter file to read: ");
		//Opens a scanner
		Scanner in = new Scanner(System.in);
		//gets the filename from user
		String filename = in.nextLine();
		in.close();
		//Opens a new shared memory class with the filename as parameter 
		SharedMemory sM = new SharedMemory(filename);
	}

}
