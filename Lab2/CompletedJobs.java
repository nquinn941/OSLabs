/**
* Class for storing all the completed jobs
 * 
 * Name: CompletedJobs.java
 *  
 * Written by: Niall Quinn - October 2018 
 * 
 * Purpose: Create a completed job and add it to an arraylist of completed jobs 
 * 
 * usage: put Main.java and Job.java in the same file. Open command prompt in that location. 
 * Use command javac *.java to compile the code. Run using java Main.   
 * 
 * Subroutines/Libraries: See import statements below 
 * 
 */

import java.util.ArrayList;

public class CompletedJobs {
	
	//String for name of scheduler
	private String scheduler;
	//String for name of job type
	private String jobFamily;
	//average response time of the job
	private int avgResponseTime;
	//average turnaround time of job
	private int avgTurnaroundTime;
	//number of context swithces of job
	private int noContextSwitches;
	//static arraylist of all completed jobs
	private static ArrayList<CompletedJobs>completedJobs= new ArrayList<CompletedJobs>();
	
	public CompletedJobs(String scheduler, String jobFamily, int avgResponseTime, int avgTurnaroundTime,int noContextSwitches) {
		setScheduler(scheduler);
		setJobFamily(jobFamily);
		setAvgResponseTime(avgResponseTime);
		setAvgTurnaroundTime(avgTurnaroundTime);
		setContextSwitches(noContextSwitches);
		//adds the newly created job to the arraylist 
		completedJobs.add(this);
	}
	/**
	 * get name of scheduler
	 * @return scheduler- the name of the scheduler 
	 */
	public String getScheduler() {
		return scheduler;
	}
	
	/**
	 * set the name of scheduler
	 * @param scheduler
	 */
	public void setScheduler(String scheduler) {
		this.scheduler = scheduler;
	}

	/**
	 * get the name of job family
	 * @return- job family
	 */
	public String getJobFamily() {
		return jobFamily;
	}

	/**
	 * set name of job family 
	 * @param jobFamily
	 */
	public void setJobFamily(String jobFamily) {
		this.jobFamily = jobFamily;
	}

	/**
	 * get average response time for job
	 * @return avgResponseTime
	 */
	public int getAvgResponseTime() {
		return avgResponseTime;
	}

	/**
	 * set average response time
	 * @param avgResponseTime
	 */
	public void setAvgResponseTime(int avgResponseTime) {
		this.avgResponseTime = avgResponseTime;
	}

	/**
	 * get average turnaround time for job 
	 * @return avgTurnaroundTime
	 */
	public int getAvgTurnaroundTime() {
		return avgTurnaroundTime;
	}

	/**
	 * set average response time 
	 * @param avgTurnaroundTime
	 */
	public void setAvgTurnaroundTime(int avgTurnaroundTime) {
		this.avgTurnaroundTime = avgTurnaroundTime;
	}

	/**
	 * get the number of context switches the job performs
	 * @return noContextSwitches
	 */
	public int getContextSwitches() {
		return noContextSwitches;
	}
	
	/**
	 * set number of context switches in job
	 * @param contextSwitches
	 */
	public void setContextSwitches(int contextSwitches) {
		this.noContextSwitches = contextSwitches;
	}
	
	/**
	 * A toString method to print out information about job
	 */
	public String toString() {
		//string is formatted so can print in a table 
		String output = String.format("%1s %20s %20d %20d %20d", getScheduler(),getJobFamily(),getAvgResponseTime(),getAvgTurnaroundTime(),getContextSwitches());
		return output;
	}
	
	public static void printAllJobs() {
		//prints out table headers formatted 
		System.out.printf("%1s %16s %20s %20s %20s", "Scheduler", "Job Family", "AvgResponseTime", "AvgTurnaroundTime", "NoContextSwitches");
		System.out.println("\n");
		//loop through all jobs in list and print them out 
		for(int i=0;i<completedJobs.size();i++) {
			System.out.println(completedJobs.get(i).toString());
		}
	}

}
