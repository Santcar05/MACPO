package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.ArrayList;

public class Macpo implements Serializable{
	private  TaskModelController tasksModelController = new TaskModelController();
	private  ActivityModelController activitiesModelController= new ActivityModelController();
	
	public Macpo() {
		
	}
	
	public void saveSystem() throws IOException {
		ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream("data.mpo"));
		output.writeObject(this);
	}
	
	public Macpo loadSystem() throws IOException, ClassNotFoundException{
		Macpo macpo = new Macpo();
		try {
			ObjectInputStream input = new ObjectInputStream(new FileInputStream("data.mpo"));
			macpo = (Macpo) input.readObject();
		}catch(Exception e) {
			//Create a empty file
			File file = new File("data.mpo");
		}
		return macpo;
	}
	
	public float findRateToDo() {
		return tasksModelController.findRateToDo();
	}
	public float findRateInProgress() {
		return tasksModelController.findRateInProgress();
	}
	
	public float findRateCompleted() {
		return tasksModelController.findRateCompleted();
	}
	public boolean addTaskToModelController(String title,String description, int status,int priority )throws Exception {
		Task newTask = tasksModelController.createTask(title, description, status, priority);
		tasksModelController.addTask(newTask);
		return true;
	}
	
	public boolean addActivityToModelController(String Title,String Description,ZonedDateTime StartDate,ZonedDateTime EndDate)throws Exception {
		Activity newActivity = activitiesModelController.createActivity(Title,Description,StartDate,EndDate);
		activitiesModelController.addActivity(newActivity);
		return true;
	}
	public void sortTasks() {
		tasksModelController.sortTasks();
	}
	
	public void deleteTask(Task task) {
		tasksModelController.deleteTask(task);
	}

	public void modifyTask(Task task) {
		
	}
	
	
	public void sortActivities() {
		activitiesModelController.sortActivities();
	}

	
	public void deleteActivity(Activity activity) {
		activitiesModelController.deleteActivity(activity);
	
	}
	public ActivityModelController getActivitiesModelController() {
		return activitiesModelController;
	}


	public void setActivitiesModelController(ActivityModelController activitiesModelController) {
		this.activitiesModelController = activitiesModelController;
	}


	public TaskModelController getTasksModelController() {
		return tasksModelController;
	}


	public void setTasksModelController(TaskModelController tasksModelController) {
		this.tasksModelController = tasksModelController;
	}
	
	
}
