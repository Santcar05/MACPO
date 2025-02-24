package model;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Comparator;

public class ActivityModelController implements Serializable {

	private ArrayList<Activity> Activities = new ArrayList<Activity>();
	
	public Activity createActivity(String Title,String Description,ZonedDateTime StartDate,ZonedDateTime EndDate) {
		return new Activity(Title,Description,StartDate,EndDate);
	}
	public void addActivity(Activity activity) {
		Activities.add(activity);
	}
	
	public void deleteActivity(Activity activity) {
		Activities.remove(activity);
	}
	
	public void sortActivities() {
		Activities.sort(Comparator.comparing(Activity::getStartDate)
                .thenComparing(Activity::getEndDate));
	}
	
	
	public ArrayList<Activity> getActivities() {
		return Activities;
	}
	public void setActivities(ArrayList<Activity> activities) {
		Activities = activities;
	}
	
	
	
}
