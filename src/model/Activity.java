package model;

import java.time.ZonedDateTime;

public class Activity {
	private static int IDActivitycount = 0; 
	private int IDActivity;
	private String Title;
	private String Description;
	private ZonedDateTime StartDate;
	private ZonedDateTime EndDate;
	
	public Activity() {
		
	}
	
	public Activity(int IDActivity, String Title, String Description, ZonedDateTime StartDate, ZonedDateTime EndDate) {
		this.IDActivity = IDActivitycount;
		IDActivitycount++;
		this.Title = Title;
		this.Description = Description;
		this.StartDate = StartDate;
		this.EndDate = EndDate;
	}
}
