package ca.sheridancollege.ozcelikh.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author Hizir Ozcelik, November 2021
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSchedule {
	
	private final String[] DAYS = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
	private final String[] TIMES = {"Morning", "Lunch Time", "Afternoon"};
	
	private int id;
	private String name;
	private String lastName;
	private String email;
	private String prefDay1;
	private String prefTime1;
	private String prefDay2;
	private String prefTime2;
	

}
