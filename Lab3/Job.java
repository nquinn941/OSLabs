import java.util.ArrayList;

public class Job {
	
	private int jobNumber;
	private ArrayList<Page> pageList = new ArrayList<Page>();
	
	
	public Job(int jobNumber) {
		this.setJobNumber(jobNumber);
	}

	public int getJobNumber() {
		return jobNumber;
	}

	public void setJobNumber(int jobNumber) {
		this.jobNumber = jobNumber;
	}
	
	public ArrayList<Page> getPageList(){
		return pageList;
	}
	
	public void addPage(Page page) {
		pageList.add(page);
	}
	
	public String printAllPages() {
		String output="";
		for(int i=0;i<pageList.size();i++) {
			output+=pageList.get(i).getPageNumber();
			output +=" ";
		}
		return output;
	}
	
	public String toString() {
		return "Job Number: " + getJobNumber() + " Page Numbers: " + printAllPages(); 
	}
	
	
	
	

}
