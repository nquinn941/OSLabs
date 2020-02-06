
public class Page {

	private int pageNumber;
	private Job job;
	private int timeStamp;
	
	public Page(int pageNumber, Job job) {
		this.setPageNumber(pageNumber);
		this.setJob(job);
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
	
	public Job getJob() {
		return job;
	}

	public void setJob(Job job) {
		this.job = job;
	}

	public void setTimeStamp(int timeStamp) {
		this.timeStamp=timeStamp;
	}
	
	public int getTimeStamp() {
		return timeStamp;
	}
	
	
}