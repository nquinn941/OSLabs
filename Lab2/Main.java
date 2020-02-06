/**
* Class that contains subroutines for creating 3 different types of job distributions 
 * Also has methods for scheduling these newly created jobs in 4 different algorithms 
 * A main method for creating jobs and running them is provided with a command line UI.
 * 
 * Name: Main.java
 *  
 * Written by: Niall Quinn - October 2018 
 * 
 * Purpose: Create and process jobs in multiple algorithms 
 * 
 * usage: put Main.java and Job.java in the same file. Open command prompt in that location. 
 * Use command javac *.java to compile the code. Run using java Main.   
 * 
 * Subroutines/Libraries: See import statements below 
 * 
 */

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Main {
	static int noOfJobs = 1000;
	public static void main(String[]args) {
		//runs the test method 
		//runPSJF();
		
		//Creates the 3 different types of jobs
		ArrayList<Job> gaussianJobs = createGaussianJobs();
		ArrayList<Job> randomJobs = createRandomJobs();
		ArrayList<Job> reverseRandomJobs = createReverseRandom();
		
		while(true) {
			//Asks user to select which scheduling algorithm to use 
			System.out.println("Select a number:");
			System.out.println("1.FIFO");
			System.out.println("2.SJF");
			System.out.println("3.RR");
			System.out.println("4.PSJF");
			System.out.println("0.EXIT");
			Scanner in = new Scanner(System.in);
			int choice = in.nextInt();
			
			//If the user inputs 0, the program closes
			if(choice==0) {
				CompletedJobs.printAllJobs();
				System.exit(0);
			}
			//If choice is 1 
			if(choice==1) {
				//print out FIFO
				System.out.println("---FIFO---" +"\n");
				System.out.println("---Gaussian---");
				//runs FIFO with gaussian 
				firstInFirstOut(gaussianJobs,"Gaussian");
				//runs FIFO with random
				System.out.println("---Random---");
				firstInFirstOut(randomJobs,"Random");
				System.out.println("---Inverse Random---");
				//runs FIFO with reverse random jobs
				firstInFirstOut(reverseRandomJobs,"Inverse Random");
			}
			
			//if choice is 2 
			if(choice==2) {
				//print out SJF 
				System.out.println("---SJF---" +"\n");
				System.out.println("---Gaussian---");
				//runs shortest job first with gaussian jobs
				shortestJobFirst(gaussianJobs,"Gaussian");
				System.out.println("---Random---");
				//runs shortest job first with random jobs
				shortestJobFirst(randomJobs,"Random");
				System.out.println("---Inverse Random---");
				//runs shortest job first with reverse random jobs
				shortestJobFirst(reverseRandomJobs,"Inverse Random");
			}
			//if choice is 3 
			if(choice==3) {
				//asks the user to input the cpu time
				System.out.println("Input CPU time");
				int timeSlice = in.nextInt();
				
				System.out.println("---RR---" +"\n");
				System.out.println("---Gaussian---");
				//runs round robin with gaussian jobs and time slice
				roundRobin(gaussianJobs,timeSlice,"Gaussian");
				System.out.println("---Random---");
				//runs round robin with random jobs and time slice
				roundRobin(randomJobs,timeSlice,"Random");
				System.out.println("---Inverse Random---");
				//runs round robin with reverse random jobs and time slice
				roundRobin(reverseRandomJobs,timeSlice,"Inverse Random");
			}
			//if user inputs 4 
			if(choice==4) {
				//prints out PSJF
				System.out.println("---PSJF---" +"\n");
				System.out.println("---Gaussian---");
				//runs pre-emptive shortest job first with gaussian jobs
				preEmptiveSJF(gaussianJobs,"Gaussian");
				System.out.println("---Random---");
				//runs pre-emptive shortest job first with random jobs
				preEmptiveSJF(randomJobs,"Random");
				System.out.println("---Inverse Random---");
				//runs pre-emptive shortest job first with reverse random jobs
				preEmptiveSJF(reverseRandomJobs,"Inverse Random");
			}		
		}	
	}

	/**
	 * Method that runs a test Pre-Emptive Shortest job first 
	 */
	public static void runPSJF() {
		//Creates 3 random jobs
		Job jobA = new Job(100,0);
		Job jobB = new Job(40,10);
		Job jobC = new Job(20,21);
		Job jobD = new Job(10,30);
		//creates an arrayList for jobs
		ArrayList<Job> testJobList = new ArrayList<Job>();
		testJobList.add(jobA);
		testJobList.add(jobB);
		testJobList.add(jobC);
		testJobList.add(jobD);
		//runs the preEmptiveSJF algorithm with the test job
		preEmptiveSJF(testJobList,"PSJF");
	}
	
	/**
	 * A method to create a guassian distribution of runtimes 
	 * @return a list of jobs
	 */
	public static ArrayList<Job> createGaussianJobs() {
		//creates an arraylist for the jobs 
		ArrayList<Job> jobList = new ArrayList<Job>();
		Random rand = new Random();
		
		//creates a thousand jobs 
		for(int i=0; i<noOfJobs;i++) {
			//makes a random arrival time with a mean of 70 and sd of 15
			int arrivalTime= (int) (160+ rand.nextGaussian()*15);
			//makes a random run time with a mean of 150 and sd of 20
			int runTime =(int)(150+ rand.nextGaussian()*20);
			//creates a new job with the run and arrival times
			Job newJob = new Job(runTime,arrivalTime);
			//adds the new job to the list of jobs
			jobList.add(newJob);
		}
		
		return jobList;
	}
	
	/**
	 * Creates a random distribution of jobs 
	 * @return the list of jobs
	 */
	public static ArrayList<Job> createRandomJobs(){
		ArrayList<Job> jobList = new ArrayList<Job>();
		Random rand = new Random();
		Job newJob;
		
		//creates 1000 jobs
		for(int i=0;i<noOfJobs;i++) {
			//creates random arrival time with mean of 70 and sd of 15
			int arrivalTime= (int) (160+ rand.nextGaussian()*15);
			//creates a random double 
			double random = rand.nextDouble();
			//if the random number is greater than or equal to 0.8
			if(random>=0.8) {
				//creates a run time with a mean of 250 and sd of 15
				int runTime = (int)(250+ random * 15);
				//creates a new job with runtime, arrival time and categorised as large
				newJob = new Job(runTime,arrivalTime,"Large");
				//increments the no of large
				
			}
			
			//if the random double is less than 0.8
			else{
				//creates a run time with mean of 50 and sd of 5 
				int runTime = (int)(50+ random*5);
				//creates a new job with run and arrival time and categorised as small
				newJob = new Job(runTime,arrivalTime,"Small");
				//increments amount of small numbers 
			}
			//adds job to list 
			jobList.add(newJob);	
		}
		return jobList;
	}
	
	/**
	 * Creates random jobs in the opposite distribution to the above method 
	 * @return the list of jobs 
	 */
	public static ArrayList<Job> createReverseRandom(){
		ArrayList<Job> jobList = new ArrayList<Job>();
		Random rand = new Random();
		Job newJob;
		
		
		//creates a thousand jobs 
		for(int i=0;i<noOfJobs;i++) {
			//creates arrival times with the mean of 70 and sd of 15 
			int arrivalTime= (int) (160+ rand.nextGaussian()*15);
			double random = rand.nextDouble();
			
			//if the random number is less than 0.8
			if(random<0.8) {
				//creates a run time with mean of 250 and sd of 15 
				int runTime = (int)(250+ random * 15);
				newJob = new Job(runTime,arrivalTime,"Large");
			}
			//if random number is larger than 0.8
			else{
				//creates a run time with mean of 50 and sd of 5 
				int runTime = (int)(50+ random*5);
				newJob = new Job(runTime,arrivalTime,"Small");	
			}
			jobList.add(newJob);	
		}
		
		return jobList;
	}
	
	/**
	 * A method that implements the first in first out scheduling algorithm 
	 * @param jobList the jobs to be passed into the the algorithm 
	 */
	public static void firstInFirstOut(ArrayList<Job> jobList,String jobFamily) {
		// the queue for numbers that have arrived and ready to be processed
		ArrayList<Job> queue = new ArrayList<Job>();
		//the job being processed 
		ArrayList<Job> process = new ArrayList<Job>();
		//the timer for the scheduling 
		int timer=0;
		//the response and turn around times for the entire algorithm 
		int totalResponseTime = 0;
		int totalTurnaroundTime = 0;
		int noContextSwitches =0;
		
		//while there is a number waiting to arrive, be processed or is currently being processed 
		while(!allArrived(jobList)||!queue.isEmpty()||!process.isEmpty()) {
			
			//gets all the jobs that have reached the arrival time from the job list 
			//and adds it to a new arrived list 
			ArrayList<Job> arrivedList = getArrivedJobs(jobList,timer);
			//adds the arrived jobs to the queue
			queue.addAll(arrivedList);
			
			//if queue contains at least one number and the process list is empty 
			if(!queue.isEmpty() && process.isEmpty()) {
				//gets the job from the front of the queue
				Job nextJob = queue.get(0);
				//adds it to the processing list
				process.add(nextJob);
				//removes it from the queue 
				queue.remove(0);
				
				
				//set the time the job is first run to the current timer value 
				nextJob.setTimeFirstRun(timer);
				//adds the response time of the current job to the total for the algorithm 
				totalResponseTime+=nextJob.getResponseTime();
				noContextSwitches++;
			}
			
			//if the process list contains a value 
			else if(!process.isEmpty()) {
				//decrease the time left on the job by 1 
				process.get(0).decrementTimeLeft();
			}
			
			//if the process list isn't empty and the current process has no time left 
			if(!process.isEmpty()&&process.get(0).getTimeLeft()==0) {
				//sets the time finished on the job equal to the timer 
				process.get(0).setTimeFinished(timer);
				//adds the jobs turn around time to the total turn around time 
				totalTurnaroundTime+=process.get(0).getTurnaroundTime();
				//sets the jobs status to 2 - finished 
				process.get(0).setStatus(2);
				//removes the job from process list 
				process.remove(0);
				//prints out the job is done at timer- for testing purposes 
				//System.out.println("Job done at "+ timer);
			}	
			//increments the timer 
			timer++;
		}
		//prints out the average response and turn around times of the jobs 
		System.out.println("Average response time is " + totalResponseTime/jobList.size());
		System.out.println("Average turnaround time is "+ totalTurnaroundTime/jobList.size());
		System.out.println("Number of context switches is " +noContextSwitches +"\n");
		CompletedJobs completedJob = new CompletedJobs("FIFO",jobFamily,totalResponseTime/jobList.size(),totalTurnaroundTime/jobList.size(),noContextSwitches);
		reset(jobList);
		
	}
	
	/**
	 * A method that implements the shortest job first algorithm 
	 * @param jobList the jobs to be passed to the algorithm
	 */
	public static void shortestJobFirst(ArrayList<Job> jobList,String jobFamily) {
		ArrayList<Job> jobsList = new ArrayList<Job>();
		jobsList.addAll(jobList);
		// the queue for numbers that have arrived and ready to be processed
		ArrayList<Job> queue = new ArrayList<Job>();
		//the job being processed 
		ArrayList<Job> process = new ArrayList<Job>();
		//the timer for the scheduling 
		int timer=0;
		//the response and turn around times for the entire algorithm 
		int totalResponseTime = 0;
		int totalTurnaroundTime = 0;
		int noContextSwitches =0;
		
		//while there is a number waiting to arrive, be processed or is currently being processed
		while(!allArrived(jobList)||!queue.isEmpty()||!process.isEmpty()) {
			
			//gets all the jobs that have reached the arrival time from the job list 
			//and adds it to a new arrived list 
			ArrayList<Job> arrivedList = getArrivedJobs(jobList,timer);
			//adds the arrived jobs to the queue
			queue.addAll(arrivedList);
			
			//if the process queue is empty and the queue isn't empty 
			if(process.isEmpty()&& !queue.isEmpty()) {
				//the shortest job method is called and set equal to a new job
				Job shortestJob = getShortestJob(queue);
				//adds the shortest job to the process list
				process.add(shortestJob);
				//set the time first run to current timer
				shortestJob.setTimeFirstRun(timer);
				//removes the shortest job from the queue 
				queue.remove(shortestJob);
				totalResponseTime+=shortestJob.getResponseTime();
				noContextSwitches++;
				//prints out shortest job for testing purposes 
				//System.out.println(shortestJob);
			}	
			//if process list isn't empty 
			else if(!process.isEmpty()) {
				//decrement time remaining of the current job 
				process.get(0).decrementTimeLeft();
			}
			//if the process queue isn't empty and the job being processed has no time left 
			if(!process.isEmpty()&&process.get(0).getTimeLeft()==0) {
				//set job's time finished to the current timer
				process.get(0).setTimeFinished(timer);
				//add the finished jobs turn around time to the total turn around time
				totalTurnaroundTime+=process.get(0).getTurnaroundTime();
				//set the jobs status to 2 - finished 
				process.get(0).setStatus(2);
				//remove the job from the process queue 
				process.remove(0);
				//System.out.println("Job done at "+ timer);
			}
			//increments timer 
			timer++;
		}
		//prints the average response and turn around times for the algorithm 
		System.out.println("Average response time is " + totalResponseTime/jobList.size());
		System.out.println("Average turn around time is "+ totalTurnaroundTime/jobList.size());	
		System.out.println("Number of context switches is " + noContextSwitches + "\n");
		CompletedJobs newJob = new CompletedJobs("SJF", jobFamily,totalResponseTime/jobList.size(),totalTurnaroundTime/jobList.size(),noContextSwitches);
		reset(jobList);
	}

	/**
	 * A method that implements the round robin scheduling algorithm 
	 * @param jobList - the list of jobs to be scheduled 
	 * @param timeSlice - the CPU time each job is allowed at once 
	 */
	public static void roundRobin(ArrayList<Job> jobList, int timeSlice,String jobFamily) {
		// the queue for numbers that have arrived and ready to be processed
		ArrayList<Job> queue = new ArrayList<Job>();
		//the job being processed 
		ArrayList<Job> process = new ArrayList<Job>();
		//the timer for the scheduling 
		int timer=0;
		//the response and turn around times for the entire algorithm 
		int totalResponseTime = 0;
		int totalTurnaroundTime = 0;
		//the number of times the context of the job switches 
		int noContextSwitches =0;
		
		//while there is a number waiting to arrive, be processed or is currently being processed
		while(!allArrived(jobList)||!queue.isEmpty()||!process.isEmpty()) {
			
			//gets all the jobs that have reached the arrival time from the job list 
			//and adds it to a new arrived list 
			ArrayList<Job> arrivedList = getArrivedJobs(jobList,timer);
			//adds the arrived jobs to the queue
			queue.addAll(arrivedList);
			
			//if the queue isn't empty and the process list is empty 
			if(!queue.isEmpty() && process.isEmpty()) {
				//the next job is set to the first job in the queue
				Job nextJob = queue.get(0);
				//prints the next job for testing purposes 
				//System.out.println(nextJob);
				//adds the next job to the process list 
				process.add(nextJob);
				//if the next job hasn't been run before 
				if(nextJob.isFirstRun()) {
					//set the jobs first run time to current timer 
					nextJob.setTimeFirstRun(timer);
					//adds the response time of the job to the total response time 
					totalResponseTime+=nextJob.getResponseTime();
				}
				//removes the next job from the queue 
				queue.remove(nextJob);
				//sets the job status to 1 - ready for processing
				nextJob.setStatus(1);
				//set the first run boolean of the job to false 
				nextJob.setFirstRun(false);
				//increments the no of context switches
				noContextSwitches++;
			}
			//if process list isn't empty
			else if(!process.isEmpty()) {
				//decrement the time remaining on the job
				process.get(0).decrementTimeLeft();
			}
			//if process isn't empty and the time on the job is 0 
			if(!process.isEmpty()&&process.get(0).getTimeLeft()==0) {
				//finished job is set to the first job in the process list 
				Job finishedJob = process.get(0);
				//set the time finished of the job to the timer
				finishedJob.setTimeFinished(timer);
				//adds the turn around time of the job to the total turn around time
				totalTurnaroundTime += finishedJob.getTurnaroundTime();
				//sets the status of the job to 2- finished 
				finishedJob.setStatus(2);
				//removes the job from the procces list 
				process.remove(finishedJob);
				
				//print out the finished job and time for testing purposes 
				//System.out.println(finishedJob + " has finished at "+ timer);
			}
			//increments the timer
			timer++;
			
			//calculates if time slice is over 
			//if the remainder of timer/ time slice is 0 and process list isnt empty
			if(timer%timeSlice==0 && !process.isEmpty()) {
				//adds to the process job to the queue
				queue.add(process.get(0));
				//removes the job from the process list 
				process.remove(0);
				//prints time up for testing purposes 
				//System.out.println("Time Up");
			}
		}
		//prints out average response and turn around times as well as context switches 
		System.out.println("Average response time is " + totalResponseTime/jobList.size());
		System.out.println("Average turn around time is "+ totalTurnaroundTime/jobList.size());
		System.out.println("Number of context switches is " + noContextSwitches + "\n");
		CompletedJobs newJob = new CompletedJobs("RR"+timeSlice, jobFamily,totalResponseTime/jobList.size(),totalTurnaroundTime/jobList.size(),noContextSwitches);
		reset(jobList);
	}

	/**
	 * A method that implements the preemptive shortest job first scheduling algorithm 
	 * @param jobList the jobs that need to be scheduled 
	 */
	public static void preEmptiveSJF(ArrayList<Job>jobList,String jobFamily) {
		// the queue for numbers that have arrived and ready to be processed
		ArrayList<Job> queue = new ArrayList<Job>();
		//the job being processed 
		ArrayList<Job> process = new ArrayList<Job>();
		//the timer for the scheduling 
		int timer=0;
		//the response and turn around times for the entire algorithm 
		int totalResponseTime = 0;
		int totalTurnaroundTime = 0;
		//the number of times the context of the job switches 
		int noContextSwitches =0;
		//the number of preemptive switches made by the algorithm 
		int noPreEmptiveSwitches=0;
		
		//while there is a number waiting to arrive, be processed or is currently being processed
		while(!allArrived(jobList)||!queue.isEmpty()||!process.isEmpty()) {
			
			//gets all the jobs that have reached the arrival time from the job list 
			//and adds it to a new arrived list 
			ArrayList<Job> arrivedList = getArrivedJobs(jobList,timer);
			//adds the arrived jobs to the queue
			queue.addAll(arrivedList);
			
			
			//if process list is empty and there are jobs in the queue
			if(process.isEmpty() && !queue.isEmpty()) {
				//get the shortest job
				Job nextJob = getShortestJob(queue);
				//add the shortest job to the process list
				process.add(nextJob);
				//remove the same job from the queue
				queue.remove(nextJob);
				//if its the jobs first run
				if(nextJob.isFirstRun()) {
					//set the time of first run to the timer
					nextJob.setTimeFirstRun(timer);
					//add the response time of the job to the total response time
					totalResponseTime+=nextJob.getResponseTime();
				}
				//set the first run boolean to false
				nextJob.setFirstRun(false);
				//increment the number of context switches
				noContextSwitches++;
			}
			
			//if the process list isn't empty and the queue is empty 
			 if(!process.isEmpty() && queue.isEmpty()) {
				//decrement the time left on the job
				process.get(0).decrementTimeLeft();
			}
			
			//if the process list has a job and the queue isn't empty
			if(!process.isEmpty()&& !queue.isEmpty()&&timer%20==0) {
				//get the shortest job from the queue and set it to compare job
				Job compareJob = getShortestJob(queue);
				//get the job currently being processed 
				Job currentJob = process.get(0);
				
				//if the time left on the compare job is shorter than the current job 
				if(compareJob.getTimeLeft()<currentJob.getTimeLeft()) {
					//remove the current job from process list
					process.remove(currentJob);
					//add it to the queue
					queue.add(currentJob);
					//add the compare job to the process list
					process.add(compareJob);
					//remove the compare job from the queue
					queue.remove(compareJob);
					//if its the compare jobs first run
					if(compareJob.isFirstRun()) {
						//set the time of first run to timer
						compareJob.setTimeFirstRun(timer);
						//add the response time of the compare job to the total response time 
						totalResponseTime+=compareJob.getResponseTime();
					}
					//set the compare job first run boolean to false 
					compareJob.setFirstRun(false);
					//print out job switched for testing purposes 
					//System.out.println("Job Switched");
					//increment the no of preemptive switches 
					noPreEmptiveSwitches++;
					//increment the no of context switches 
					noContextSwitches++;	
				}
				else {
					//Decrement the time remaining on current job if it is shorter than compare job
					currentJob.decrementTimeLeft();
				}
			}
			//if timer isnt a multiple of 20 it decreases time left 
			else if(timer%20!=0 && !process.isEmpty() && !queue.isEmpty()) {
				process.get(0).decrementTimeLeft();
			}
			//if process isn't empty and the job in it has no time remaining 
			if(!process.isEmpty() && process.get(0).getTimeLeft()==0) {
				Job finishedJob = process.get(0);
				//set time of job finished to the timer
				finishedJob.setTimeFinished(timer);
				//add the jobs turn around time to total turn around time 
				totalTurnaroundTime += finishedJob.getTurnaroundTime();
				//set status of job to 2- finished 
				finishedJob.setStatus(2);
				//remove the job from process list
				process.remove(finishedJob);
				//print out finished job and time for testing purposes 
				//System.out.println(finishedJob + " has finished at "+ timer);
			}
			//increment timer
			timer++;
		}
		//print out response and turn around times as well as no of switches (context and preemptive)
		System.out.println("Average response time is " + totalResponseTime/jobList.size());
		System.out.println("Average turn around time is "+ totalTurnaroundTime/jobList.size());
		System.out.println("Number of context switches is " + noContextSwitches);
		System.out.println("Number of preemptive switches " + noPreEmptiveSwitches + "\n");
		CompletedJobs newJob = new CompletedJobs("PSJF", jobFamily,totalResponseTime/jobList.size(),totalTurnaroundTime/jobList.size(),noContextSwitches);
		reset(jobList);
	}
	
	/**
	 * Method for returning the shortest job in a list
	 * @param jobList the list of jobs 
	 * @return the job with the shortest remaining time
	 */
	public static Job getShortestJob(ArrayList<Job> jobList) {
		//the shortest job in list
		Job shortestJob=null;
		//the shortest time 
		//set high so the shortest time will be smaller than it 
		int shortestTime = 1000;
			
		//for all jobs in job list
		for(int i=0;i<jobList.size();i++) {
			//if the current item in lists time is shorter than the shortest time
			if(jobList.get(i).getTimeLeft()<shortestTime) {
				//set the shortest time to the current jobs time left 
				shortestTime = jobList.get(i).getTimeLeft();
				//set the shortest job to the current job
				shortestJob = jobList.get(i);
			}
		}
		//return the shortest job from the job list
		return shortestJob;
	}
	
	/**
	 * A method to find all the jobs that have arrived 
	 * @param jobList the list of all jobs
	 * @param timer the current time that jobs should have arrived by 
	 * @return a list of all the jobs that have arrived by the current time
	 */
	public static ArrayList<Job> getArrivedJobs(ArrayList<Job>jobList, int timer){
		ArrayList<Job> arrivedList = new ArrayList<Job>();
		//loops through the all the jobs in the job list  
		for(int i=0;i<jobList.size();i++) {
			//if the job has an arrival time equal to the current timer 
			if((jobList.get(i).getArrivalTime()==timer||jobList.get(i).getArrivalTime()<timer)&&jobList.get(i).getStatus()==0) {
				//adds the current job to the queue 
				arrivedList.add(jobList.get(i));
				//sets the status of the job to 1- ready to be processed 
				jobList.get(i).setStatus(1);	
			}
		}
		//return all the jobs that have arrived by current time
		return arrivedList;
	}
	
	/**
	 * A method to test if all the jobs in a list have arrived 
	 * @param jobList the list of jobs
	 * @return a boolean, true if all have arrived, false otherwise
	 */
	public static boolean allArrived(ArrayList<Job> jobList) {
		//boolean that will be returned 
		boolean allArrived;
		//number of jobs arrived
		int arrivedNumber = 0;
		//loops through all the jobs in the list 
		for(int i=0;i<jobList.size();i++) {
			//if the jobs status is either a 1 or 2 
			if(jobList.get(i).getStatus()>=1) {
				//increment the number arrived
				arrivedNumber++;
			}
		}
		//if the number arrived is equal to the number of jobs
		if(arrivedNumber==jobList.size()) {
			//set all arrived boolean to true
			allArrived=true;
		}
		else {
			//if the arrived number isnt the same as job number, set all arrived to false
			allArrived = false;
		}
		//return all arrived
		return allArrived;
	}
	
	/**
	 * A method that resets the jobs 
	 * @param jobList the list of jobs that need resetting 
	 */
	public static void reset(ArrayList<Job>jobList) {
		//loops through the job list
		for(int i=0;i<jobList.size();i++) {
			//set job to the current job in list
			Job job = jobList.get(i);
			//get the run time of the job
			int runTime = job.getRunTime();
			//set the first run boolean to true
			job.setFirstRun(true);
			//sets status to 0- not arrived
			job.setStatus(0);
			//set the time left in the job 
			job.setTimeLeft(runTime);
		}		
	}
}