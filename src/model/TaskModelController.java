package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;

public class TaskModelController implements Serializable{

	private ArrayList<Task> Tasks = new ArrayList<Task>();
	
	public Task createTask( String Title, String Description, int Status, int Priority) {
		return new Task(Title, Description, Status, Priority);
	}
	
	public void addTask(Task task) {
		Tasks.add(task);
	}
	
	public void deleteTask(Task task) {
		Tasks.remove(task);
	}
	
	public float findRateToDo() {
		float rate = 0;
		float to_do_count = 0;
		for(Task task: Tasks) {
			if(task.getStatus() == Constants.TO_DO)
				to_do_count++;
		}
		
		System.out.println("To-Do Count: " + to_do_count);
	    System.out.println("Total Tasks: " + Tasks.size());
	    
		if(Tasks.size() > 0)
			rate = (to_do_count/(float)Tasks.size())* 100;
		return rate;
	}
	public float findRateInProgress() {
		float rate = 0;
		float in_progress_count = 0;
		for(Task task: Tasks) {
			if(task.getStatus() == Constants.IN_PROGRESS)
				in_progress_count++;
		}
		if(Tasks.size() > 0)
			rate = (in_progress_count/(float)Tasks.size())* 100;
		return rate;
	}
	
	public float findRateCompleted() {
		float rate = 0;
		float completed_count = 0;
		for(Task task: Tasks) {
			if(task.getStatus() == Constants.DONE)
				completed_count++;
		}
		if(Tasks.size() > 0)
			rate =( completed_count/(float)Tasks.size())* 100;;
		return rate;
	}
	

	public ArrayList<Task> getTasks() {
		return Tasks;
	}

	public void setTasks(ArrayList<Task> tasks) {
		Tasks = tasks;
	}
	
	
	public void sortTasks() {
		//sort by status, Priority and then date
        Tasks.sort(Comparator.comparing(Task::getStatus)
                .thenComparing(Task::getPriority));
	}
}
