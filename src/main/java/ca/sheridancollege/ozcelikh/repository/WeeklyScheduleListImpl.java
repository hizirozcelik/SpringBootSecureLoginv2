package ca.sheridancollege.ozcelikh.repository;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.stereotype.Component;

import ca.sheridancollege.ozcelikh.beans.UserSchedule;



@Component
public class WeeklyScheduleListImpl implements WeeklyScheduleList {
	
	public List<UserSchedule> weeklyScheduleList = new CopyOnWriteArrayList<UserSchedule>();

	public List<UserSchedule> getWeeklyScheduleList() {
		return weeklyScheduleList;
	};

	public void setWeeklyScheduleList(List<UserSchedule> weeklyScheduleList) {
		this.weeklyScheduleList = weeklyScheduleList;
	};
	
	public void emptyList() {
		weeklyScheduleList.clear();
	}

}
