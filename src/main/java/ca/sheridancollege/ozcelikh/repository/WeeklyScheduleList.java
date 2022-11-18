package ca.sheridancollege.ozcelikh.repository;

import java.util.List;

import ca.sheridancollege.ozcelikh.beans.UserSchedule;



public interface WeeklyScheduleList {
	
	public List<UserSchedule> getWeeklyScheduleList();

	public void setWeeklyScheduleList(List<UserSchedule> movieList);
	
	public void emptyList();


}
