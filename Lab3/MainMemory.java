/**
* Class that acts as main memory containing  pages
* 
* Name: MainMemory.java
*  
* Written by: Niall Quinn - October 2018 
* 
* Purpose: has an arraylist of pages  
* 
* usage:See main.java  
* 
* Subroutines/Libraries: See import statements below 
* 
*/

import java.util.ArrayList;
import java.util.Random;

public class MainMemory {
	
	//an arraylist of active pages
	private ArrayList<Page> activePages = new ArrayList<Page>();
	//capacity of main memory
	private final int CAPACITY=10;
	private int timer=0;
	private int hitCounter =0;
	private int noFaults=0;
	private int noFirstTimeLoads=0;
	private int noErrors=0;
	 
	/**
	 * check if main memory is full
	 * @return
	 */
	public  boolean isFull() {
		//check if the size of the active page list is equal to capacity 
		if (activePages.size()==CAPACITY) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * Increment the hit counter
	 */
	public void incrementHitCounter() {
		setHitCounter(getHitCounter() + 1);
	}
	
	/**
	 * increment the number of faults 
	 */
	public void incrementNoFaults() {
		setNoFaults(getNoFaults() + 1);
	}
	
	/**
	 * increment the number of first time loads
	 */
	public void incrementNoFirstTimeLoads() {
		setNoFirstTimeLoads(getNoFirstTimeLoads() + 1);
	}
	
	/**
	 * increment the number of errors 
	 */
	public void incrementNoErrors() {
		setNoErrors(getNoErrors() + 1);
	}
	
	/**
	 * add a page to memory and set the time it is used
	 * @param page
	 */
	public void addToMemory(Page page) {
		activePages.add(page);
		setTimeUsed(page);
	}
	
	/**
	 * check if a page is in memory 
	 * @param page - the page to be checked
	 * @return true if page is in memory, else false
	 */
	public  boolean isInMainMemory(Page page) {
		if(activePages.contains(page)) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * remove an arraylist of pages from memory
	 * @param pageList the pages to be removed
	 */
	public void removeFromMainMemory(ArrayList<Page> pageList) {
		//iterates through page list and checks if it is in memory 
		for(int i=0;i<pageList.size();i++) {
			if(activePages.contains(pageList.get(i))){
				activePages.remove(pageList.get(i));
			}
		}
	}
	
	/**
	 * remove a single page from memory
	 * @param page the page to be removed 
	 */
	public void removeFromMainMemory(Page page) {
		activePages.remove(page);
	}
	
	/**
	 * The method to check which page was least recently used 
	 * @return the least recently used page
	 */
	public Page leastRecentlyUsed() {
		//sets the a compartive time to one more than current time
		int timeToCompare = timer++;
		Page page=null;
		//iterates through pages and checks if the time used is less than current time to compare
		for(int i=0;i<activePages.size();i++) {
			if(activePages.get(i).getTimeStamp()<timeToCompare) {
				timeToCompare=activePages.get(i).getTimeStamp();
				page=activePages.get(i);
			}
		}
		return page;
	}
	
	/**
	 * method to pick a random page 
	 * @return
	 */
	public Page random() {
		Page page = null;
		Random rand = new Random();
		//pick a random number between 0 and the capacity of the memory 
		int pageIndex = rand.nextInt(CAPACITY);
		page=activePages.get(pageIndex);
		return page;
	}

	/**
	 * return number of hits 
	 * @return hit counter
	 */
	public int getHitCounter() {
		return hitCounter;
	}

	/**
	 * set number of hits
	 * @param hitCounter
	 */
	public void setHitCounter(int hitCounter) {
		this.hitCounter = hitCounter;
	}

	/**
	 * return number of page faults
	 * @return number of faults 
	 */
	public int getNoFaults() {
		return noFaults;
	}

	/**
	 * 
	 * @param noFaults
	 */
	public void setNoFaults(int noFaults) {
		this.noFaults = noFaults;
	}

	/**
	 * 
	 * @return noFirstTimeLoads 
	 */
	public int getNoFirstTimeLoads() {
		return noFirstTimeLoads;
	}

	/**
	 * set the number of first time loads 
	 * @param noFirstTimeLoads
	 */
	public void setNoFirstTimeLoads(int noFirstTimeLoads) {
		this.noFirstTimeLoads = noFirstTimeLoads;
	}
	
	/**
	 * increment the timer
	 */
	public void incrementTimer() {
		timer++;
	}
	
	/**
	 * set the time a page is used at 
	 * @param page
	 */
	public void setTimeUsed(Page page) {
		page.setTimeStamp(timer);
	}
	
	/**
	 * print all the pages in memory 
	 * @return a textual output of all pages in memory 
	 */
	public String printAllPages() {
		String output="";
		for(int i=0;i<activePages.size();i++) {
			output+=activePages.get(i).getPageNumber() + " ";
		}
		return output;
	}

	/**
	 * 
	 * @return no of errors 
	 */
	public int getNoErrors() {
		return noErrors;
	}

	/**
	 * set no of errors 
	 * @param noErrors
	 */
	public void setNoErrors(int noErrors) {
		this.noErrors = noErrors;
	}
}
