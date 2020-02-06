/**
* Class that is used as swap space for jobs being held in memory
* 
* Name: Main.java
*  
* Written by: Niall Quinn - October 2018 
* 
* Purpose: Used for storing pages in swap space  
* 
* usage:See main.java  
* 
* Subroutines/Libraries: See import statements below 
* 
*/

import java.util.ArrayList;

public class SwapSpace {

	//An array of pages used to store the pages that are in swap 
	private  ArrayList<Page> activePages = new ArrayList<Page>();
	//a final int of the capacity of the swap space 
	private final int CAPACITY =15;
	 
	 /**
	  * Checks if swap space is full
	  * @return true if swap space is full, else false
	  */
	public boolean isFull() {
		//if the size of the active pages is equal to capacity 
		if (activePages.size()==CAPACITY) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * Check if the page is in swap space 
	 * @param page - the page to check 
	 * @return true if page is in swap, else false
	 */
	public boolean isInSwapSpace(Page page) {
		//if page is in swap 
		if(activePages.contains(page)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * Remove a list of pages from memory 
	 * @param pageList
	 */
	public void removeFromSwapSpace(ArrayList<Page> pageList) {
		//iterates through page list
		for(int i=0;i<pageList.size();i++) {
			//checks if active pages contains the specific page
			if(activePages.contains(pageList.get(i))) {
				//remove the page 
				activePages.remove(pageList.get(i));
			}
		}
	}
	
	/**
	 * remove a single page from swap space 
	 * @param page
	 */
	public void removeFromSwapSpace(Page page) {
		activePages.remove(page);
	}

	/**
	 * add a page to swap space
	 * @param page
	 */
	public void addToSwapSpace(Page page) {
		activePages.add(page);		
	}
	
	/**
	 * print all pages in swap space 
	 * @return a textual output of swap space 
	 */
	public String printAllPages() {
		String output="";
		for(int i=0;i<activePages.size();i++) {
			output+=activePages.get(i).getPageNumber()+ " ";
		}
		return output;
	}
}
