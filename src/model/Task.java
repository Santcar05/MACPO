package model;

public class Task {
	private static int IDTaskCount = 0;
	private int IDTask;
	private String Title;
	private String Description;
	private int Status;
	private int Priority;
	
	public Task(){
		
	}
	
	public Task(int IDTask, String Title, String Description, int Status, int Priority) {
		this.IDTask = IDTaskCount;
		IDTaskCount++;
		this.Description = Description;
		this.Priority = Priority;
		this.Title = Title;
		this.Status = Status;
	}
	
	
}
