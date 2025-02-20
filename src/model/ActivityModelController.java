package model;

import java.util.ArrayList;

public class ActivityModelController {

	private ArrayList<Activity> Activities = new ArrayList<Activity>();
	
	
	public void addActivity(Activity activity) {
		Activities.add(activity);
	}
	
	public void deleteActivity(Activity activity) {
		Activities.remove(activity);
	}
}
