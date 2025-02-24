package model;

import java.io.Serializable;
import java.time.ZonedDateTime;

public class Activity implements Serializable{
	private static int IDActivitycount = 0; 
	private int IDActivity;
	private String Title;
	private String Description;
	private ZonedDateTime StartDate;
	private ZonedDateTime EndDate;
	
	public Activity() {
		
	}
	
	public Activity( String Title, String Description, ZonedDateTime StartDate, ZonedDateTime EndDate) {
		this.IDActivity = IDActivitycount;
		IDActivitycount++;
		this.Title = Title;
		this.Description = Description;
		this.StartDate = StartDate;
		this.EndDate = EndDate;
	}

	public static int getIDActivitycount() {
		return IDActivitycount;
	}

	public static void setIDActivitycount(int iDActivitycount) {
		IDActivitycount = iDActivitycount;
	}

	public int getIDActivity() {
		return IDActivity;
	}

	public void setIDActivity(int iDActivity) {
		IDActivity = iDActivity;
	}

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public ZonedDateTime getStartDate() {
		return StartDate;
	}

	public void setStartDate(ZonedDateTime startDate) {
		StartDate = startDate;
	}

	public ZonedDateTime getEndDate() {
		return EndDate;
	}

	public void setEndDate(ZonedDateTime endDate) {
		EndDate = endDate;
	}
	
	
}
