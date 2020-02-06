
public class Job {
	//the run time of the job
	private int runTime;
	//the arrival time of the job
	private int arrivalTime;
	//status of the job
	private int status;
	//type of job (big/small)
	private String type;
	//time of first run
	private int timeFirstRun;
	//time left for the job
	private int timeLeft;
	//time the job was finished 
	private int timeFinished;
	//boolean to see if current run is the first run
	private boolean firstRun;
	
	/**
	 * Constructor of job
	 * @param runTime run time of the job
	 * @param arrivalTime arrival time of the job
	 */
	public Job(int runTime,int arrivalTime) {
		//set run time
		setRunTime(runTime);
		//set arrival time
		setArrivalTime(arrivalTime);
		//set status to 0 
		setStatus(0);
		//set time left to the run time
		setTimeLeft(runTime);
		//set the first run boolean to true
		setFirstRun(true);
	}
	
	/**
	 * Constructors for jobs that are large/small
	 * @param runTime run time of job
	 * @param arrivalTime arrival time of job
	 * @param type type of job
	 */
	public Job(int runTime,int arrivalTime,String type) {
		//set run time
		setRunTime(runTime);
		//set arrival time
		setArrivalTime(arrivalTime);
		//set type of job
		setType(type);
		//set status of job to 0
		setStatus(0);
		//set time left to runtime 
		setTimeLeft(runTime);
		//set first run boolean to true
		setFirstRun(true);
	}

	/**
	 * @return the runTime
	 */
	public int getRunTime() {
		return runTime;
	}

	/**
	 * @param runTime the runTime to set
	 */
	public void setRunTime(int runTime) {
		this.runTime = runTime;
	}

	/**
	 * @return the arrivalTime
	 */
	public int getArrivalTime() {
		return arrivalTime;
	}

	/**
	 * @param arrivalTime the arrivalTime to set
	 */
	public void setArrivalTime(int arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	/**
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(int status) {
		this.status = status;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	
	public String toString() {
		return "Run time: " + runTime + " Arrival time: " + arrivalTime + "\n";
	}

	/**
	 * @return the timeFirstRun
	 */
	public int getTimeFirstRun() {
		return timeFirstRun;
	}

	/**
	 * @param timeFirstRun the timeFirstRun to set
	 */
	public void setTimeFirstRun(int timeFirstRun) {
		this.timeFirstRun = timeFirstRun;
	}

	/**
	 * @return the timeLeft
	 */
	public int getTimeLeft() {
		return timeLeft;
	}

	/**
	 * @param timeLeft the timeLeft to set
	 */
	public void setTimeLeft(int timeLeft) {
		this.timeLeft = timeLeft;
	}
	
	public void decrementTimeLeft() {
		this.timeLeft--;
	}
	
	public int getResponseTime() {
		return (timeFirstRun-arrivalTime);
	}

	/**
	 * @return the timeFinished
	 */
	public int getTimeFinished() {
		return timeFinished;
	}

	/**
	 * @param timeFinished the timeFinished to set
	 */
	public void setTimeFinished(int timeFinished) {
		this.timeFinished = timeFinished;
	}
	
	public int getTurnaroundTime() {
		return getTimeFinished()-getArrivalTime();
	}

	/**
	 * @return the firstRun
	 */
	public boolean isFirstRun() {
		return firstRun;
	}

	/**
	 * @param firstRun the firstRun to set
	 */
	public void setFirstRun(boolean firstRun) {
		this.firstRun = firstRun;
	}

}
