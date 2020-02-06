/**
* Class that will deal with the input from the files and decide what needs to be done with the 
* pages and jobs
* 
* Name: Main.java
*  
* Written by: Niall Quinn - October 2018 
* 
* Purpose: Reads in the jobs and pages from file and process them 
* 
* usage:See main.java  
* 
* Subroutines/Libraries: See import statements below 
* 
*/



import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Scanner;

public class SharedMemory {
	//An arraylist of all the active jobs
	ArrayList<Job> activeJobList = new ArrayList<Job>();
	//an arraylist of all the active page list
	ArrayList<Page> activePageList = new ArrayList<Page>();
	//a list of all the completed job numbers 
	ArrayList<Integer> completedJobNumbers = new ArrayList<Integer>();
	//a new instance of main memory
	MainMemory m = new MainMemory();
	//a new instance of swap space
	SwapSpace s = new SwapSpace();
	//this is the choice for LRU or Random 
	//1 for LRU or 2 for Random 
	//i tried to have user input for this but kept giving null pointer exceptions
	int choice=1;
	
	/**
	 * Constructor for the class
	 * @param filename- the name of the file to be opened and read in
	 */
	public SharedMemory(String filename) {
		fileReader(filename);	
	}
	
	/**
	 * The method to open the file and read it in line by line 
	 * @param filename - the name of the file passed from main 
	 */
	public void fileReader(String filename) {
		//create a new file from the input file
		File inputFile = new File(filename);
		//create a new scanner
		Scanner in = null;
		
		try {
			//try to create a scanner from the input file 
			in = new Scanner(inputFile);
		}
		//if the file is not found in the directory
		catch(FileNotFoundException e){
			//print that the file cannot be opened 
			System.out.println("Cannot open " + filename);
			//close the system
			System.exit(0);
		}
		//use a delimeter of "," to read in CSV files
		in.useDelimiter(",");
		
		//while there is another line in the file 
		while(in.hasNextLine()) {
			//create a new scanner based on the next line of the file 
			Scanner curLine = new Scanner(in.nextLine());
			//while there is input in the next line
			while(curLine.hasNext()) {
				curLine.useDelimiter(",");
				//the job number is the next int
				int jobNumber = curLine.nextInt();
				//the page number is the next int
				int pageNumber = curLine.nextInt();
				//calls the process input method for the job and page number
				processInput(jobNumber,pageNumber);	
			}
			System.out.println("Main Memory: "+m.printAllPages());
			System.out.println("Swap Space: "+s.printAllPages());
			m.incrementTimer();
		}
		in.close();	
		String algorithm ="";
		if(choice==1) {
			algorithm="LRU";
		}
		else {
			algorithm ="Random";
		}
		System.out.println("\n" +"File " + filename+ " read complete: \n");
		System.out.println("Algorithm used: " + algorithm);
		System.out.println("Main Memory: " +m.printAllPages());
		System.out.println("Swap Space : " +s.printAllPages());
		System.out.println("Number of first time loads: "+ m.getNoFirstTimeLoads());
		System.out.println("Number of hits: "+ m.getHitCounter());
		System.out.println("Number of faults: " +m.getNoFaults());
		System.out.println("Number of errors: "+ m.getNoErrors());
	}

	/**
	 * Processes the input from the file
	 * @param jobNumber - the job number
	 * @param pageNumber - the page number
	 */
	private void processInput(int jobNumber,int pageNumber) {
		
		//if the job is already done, print the job is done 
		if(completedJobNumbers.contains(jobNumber)) {
			System.out.println("Job has finished");
		}
		
		//if the page number is -999
		else if(pageNumber==-999) {
			//get the job with the job number from input 
			Job job=getJob(jobNumber);
			//print out the job is done
			System.out.println("Job done");
			//goes through all the pages associated with the job 
			for(int i=0;i<job.getPageList().size();i++) {
				//if the page is in main memory, remove it 
				if(m.isInMainMemory(job.getPageList().get(i))) {
					m.removeFromMainMemory(job.getPageList().get(i));
				}
				//if the page is swap space, remove it
				else if(s.isInSwapSpace(job.getPageList().get(i))) {
					s.removeFromSwapSpace(job.getPageList().get(i));
				}
			}
			//remove the job from active job 
			removeJob(job);
			//add the job number to the completed job list 
			completedJobNumbers.add(job.getJobNumber());
		}
		
		//if the job number hasnt already been created 
		else if(!(jobExists(jobNumber))) {
			//create a new job
			Job newJob = new Job(jobNumber);
			//create a new page
			Page newPage = new Page(pageNumber,newJob);
			//add the new page to the new job
			newJob.addPage(newPage);
			//add the job to job list
			activeJobList.add(newJob);
			//add the page to the page list
			activePageList.add(newPage);
			//test print statements for creating jobs and pages
			//System.out.println("New Job " + newJob.getJobNumber() + " has been created");
			//System.out.println("New Page "+ newPage.getPageNumber() + " has been created");
			
			//process the page 
			processPage(newJob,newPage);
		}
		
		//if the job already exists but the page doesnt
		else if(jobExists(jobNumber)&& !pageExists(pageNumber)) {
			//get the job
			Job currentJob = getJob(jobNumber);
			//create a new page and add it to the job
			Page newPage = new Page(pageNumber,currentJob);
			currentJob.addPage(newPage);
			activePageList.add(newPage);
			//System.out.println("New Page "+ newPage.getPageNumber() + " has been created");
			
			//process the page 
			processPage(currentJob,newPage);
		}
		
		//if the page and job already exist 
		else if(jobExists(jobNumber)&& pageExists(pageNumber)) {
			//get job and page and process the page request 
			Job job = getJob(jobNumber);
			Page page = getPage(pageNumber);
			processPage(job,page);	
		}
	}
	
	/**
	 * A method that processes the page requests 
	 * @param job - the job request 
	 * @param page - the page request 
	 */
	private void processPage(Job job,Page page) {
		
		//if the page is already in main memory 
		if(m.isInMainMemory(page)) {
			//increment hit counter
			m.incrementHitCounter();
			//System.out.println("Hit counter incremented");
			
			//set the time the page was last used 
			m.setTimeUsed(page);
		}
		//if the page is in swap space 
		else if(s.isInSwapSpace(page)) {
			//System.out.println("Page Fault");
			
			//call the swap into main method 
			swapToMain(page);
		}
		
		else {
			//if the page isnt in memory yet, put it there 
			moveIntoMemory(job,page);
		}
	}
	
	/**
	 * a method to add a page into main memory 
	 * @param job
	 * @param page
	 */
	private void moveIntoMemory(Job job,Page page) {
		//if memory isnt full
		if(!m.isFull()) {
			//add page to main memory
			m.addToMemory(page);
			//increment number of first time loads
			m.incrementNoFirstTimeLoads();
		}
		
		//if swap isnt full
		else if(m.isFull()&&!s.isFull()) {
			//take a page out of swap 
			swapPageOut();
			//add the page to memory
			m.addToMemory(page);
			//increment first time loads 
			m.incrementNoFirstTimeLoads();
		}
		
		//if main and swap is full
		else if(m.isFull()&&s.isFull()) {
			//remove all pages associated with job from Main memory and swap space
			System.out.println("Memory full - job error");
			//get all the pages from the job
			ArrayList<Page> pageList = job.getPageList();
			//remove those pages from main and swap
			m.removeFromMainMemory(pageList);
			s.removeFromSwapSpace(pageList);
			//remove the job and pages from active list 
			activeJobList.remove(job);
			activePageList.removeAll(pageList);
			//add job number to completed job numbers
			completedJobNumbers.add(job.getJobNumber());
			//increment the number of errors
			m.incrementNoErrors();
		}	
	}
	
	/**
	 * Put a page from swap into main
	 * @param page
	 */
	private void swapToMain(Page page) {
		//if memory isn't full, remove the page from swap and add it to main
		if(!m.isFull()) {
			s.removeFromSwapSpace(page);
			m.addToMemory(page);
		}
		else {
			//decide which page to remove from main
			Page pageToRemove = null;
			//if choice is 1, use least recently used 
			if(choice==1) {
				pageToRemove = m.leastRecentlyUsed();
			}
			//if choice is 2, use random 
			if(choice==2) {
				pageToRemove = m.random();
			}
			
			//remove the page from main and add it to swap 
			m.removeFromMainMemory(pageToRemove);
			s.addToSwapSpace(pageToRemove);
			
			//remove the requested page from swap and add it to main 
			s.removeFromSwapSpace(page);
			m.addToMemory(page);
		}
		m.incrementNoFaults();	
	}
	
	/**
	 * A method to remove a page from memory 
	 */
	private void swapPageOut() {
		Page pageToRemove = null;
		if(choice==1) {
			pageToRemove = m.leastRecentlyUsed();
		}
		if(choice==2) {
			pageToRemove = m.random();
		}
		//System.out.println(pageToRemove.getPageNumber()+" has been removed ");
		m.removeFromMainMemory(pageToRemove);
		s.addToSwapSpace(pageToRemove);
	}
	
	/**
	 * Check if a job exists based on its number 
	 * @param jobNumber
	 * @return true if page exists, else false
	 */
	private boolean jobExists(int jobNumber) {
		boolean exists=false;
		//increments through the active job list
		for(int i=0;i<activeJobList.size();i++) {
			//if the current job on the active job list has the job number 
			if(activeJobList.get(i).getJobNumber()==jobNumber) {
				//return true
				exists=true;
				return exists;
			}
		}
		return exists;
	}
	
	/**
	 * check if page exists
	 * @param pageNumber
	 * @return true if page exists, else false
	 */
	private boolean pageExists(int pageNumber) {
		//increment through the page list and check if it exists 
		boolean exists=false;
		for(int i=0;i<activePageList.size();i++) {
			if(activePageList.get(i).getPageNumber()==pageNumber) {
				exists=true;
				return exists;
			}
		}
		return exists;
	}
	/**
	 * get a job based on job number
	 * @param jobNumber
	 * @return the job
	 */
	private Job getJob(int jobNumber) {
		Job job=null;
		//increment through active job list 
		for(int i=0;i<activeJobList.size();i++) {
			//check if the job number is equal to job in list 
			if(activeJobList.get(i).getJobNumber()==jobNumber) {
				job= activeJobList.get(i);
				return job;
			}
		}
		return job;
	}
	
	/**
	 * Return the page from page number 
	 * @param pageNumber
	 * @return page from page number
	 */
	private Page getPage(int pageNumber) {
		Page page=null;
		//increment through the page list 
		for(int i=0;i<activePageList.size();i++) {
			//check if the current page has the same job number 
			if(activePageList.get(i).getPageNumber()==pageNumber) {
				page= activePageList.get(i);
				return page;
			}
		}
		return page;
	}
	
	/**
	 * Remove the job when it is complete
	 * @param job
	 */
	public void removeJob(Job job) {
		//remove the job from active page list
		activeJobList.remove(job);
		//remove all the pages from the page list 
		activePageList.removeAll(job.getPageList());
		//add the job number to the complete job number 
		completedJobNumbers.add(job.getJobNumber());
	}
}
