package ca.sheridancollege.ozcelikh.controllers;

import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestParam;

import ca.sheridancollege.ozcelikh.beans.UserSchedule;
import ca.sheridancollege.ozcelikh.database.DatabaseAccess;
import ca.sheridancollege.ozcelikh.repository.WeeklyScheduleList;

/**
 * 
 * @author Hizir Ozcelik, November 2021
 */

@Controller
public class HomeController {

	@Autowired
	@Lazy
	private DatabaseAccess da;

	private WeeklyScheduleList weeklyScheduleList;
	


	public HomeController(WeeklyScheduleList weeklyScheduleList) {

		super();
		weeklyScheduleList.getWeeklyScheduleList().add(new UserSchedule(1, "Simon", "Hood", "simon.hood@sheridancollege.ca", "Wednesday",
				"Morning", "Friday", "Lunch Time"));
		weeklyScheduleList.getWeeklyScheduleList().add(new UserSchedule(2, "Frank", "Smith", "frank@sheridancollege.ca", "Tuesday", "Morning",
				"Thursday", "Afternoon"));
		weeklyScheduleList.getWeeklyScheduleList().add(new UserSchedule(3, "John", "Doe", "frank@sheridancollege.ca", "Tuesday", "Morning",
				"Thursday", "Afternoon"));

		this.weeklyScheduleList = weeklyScheduleList;
	}

//	Return if weeklyScheduleList has any record for specific userName
//	Purpose is to prevent duplication in weeklyScheduleList for any user
//	This method found and adapted from https://stackoverflow.com/questions/54511107/how-to-get-the-index-of-object-by-its-property-in-java-list
	public boolean isEmailFound(WeeklyScheduleList weeklyScheduleList, String email) {

		return weeklyScheduleList.getWeeklyScheduleList().stream().filter(o -> o.getEmail().equals(email)).findFirst().isPresent();
	}

// 	Return index number of specific email which is already in the weeklyScheduleList
// 	This method found and adapted from https://stackoverflow.com/questions/54511107/how-to-get-the-index-of-object-by-its-property-in-java-list
	public int getIndexOfUserSchedule(WeeklyScheduleList weeklyScheduleList, String email) {

		return IntStream.range(0, weeklyScheduleList.getWeeklyScheduleList().size())
				.filter(i -> weeklyScheduleList.getWeeklyScheduleList().get(i).getEmail().equals(email)).findFirst().orElse(-1);
	}

	@GetMapping("/")
	public String index() {
		return "index";
	}

	@GetMapping("/secure")
	public String secureIndex(Model model, Authentication authentication) {
		String email = authentication.getName();
		String name = da.findUserAccount(email).getName();
		String lastName = da.findUserAccount(email).getLastName();
		model.addAttribute("name", name);
		model.addAttribute("lastName", lastName);

		return "/secure/index";
	}

	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@GetMapping("/secure/userSchedule")
	public String userSchedule(Model model) {

		model.addAttribute("userSchedule", new UserSchedule());

		return "/secure/userSchedule";
	}

	@PostMapping("/secure/addUserSchedule")
	public String addUserSchedule(Model model, Authentication authentication, UserSchedule userSchedule) {

		String email = authentication.getName();
		String name = da.findUserAccount(email).getName();
		String lastName = da.findUserAccount(email).getLastName();

// Checking if user already in the weeklyScheduleList
		if (isEmailFound(weeklyScheduleList, email)) {

			int index = getIndexOfUserSchedule(weeklyScheduleList, email);

			System.out.println("User found " + email);
			
			userSchedule.setId(index);
			userSchedule.setName(name);
			userSchedule.setLastName(lastName);
			userSchedule.setEmail(email);
			
// Updating the user meeting preference on the List
			weeklyScheduleList.getWeeklyScheduleList().set(index, userSchedule);
			model.addAttribute("weeklyScheduleList", weeklyScheduleList.getWeeklyScheduleList());

			System.out.println("User schedule succcesfully UPDATED!");

		} else {

			System.out.println("Record not found for " + email);

// Since user will be the last object in the list, size of the List sets as userSchedule's id
			userSchedule.setId(weeklyScheduleList.getWeeklyScheduleList().size());

			userSchedule.setName(name);
			userSchedule.setLastName(lastName);
			userSchedule.setEmail(email);

			weeklyScheduleList.getWeeklyScheduleList().add(userSchedule);

			model.addAttribute("weeklyScheduleList", weeklyScheduleList.getWeeklyScheduleList());
			System.out.println("User schedule succesfully STORED!");
		}
		;

		return "/secure/weeklySchedule";
	}

	@GetMapping("/secure/weeklySchedule")
	public String weeklySchedule(Model model) {

		model.addAttribute("weeklyScheduleList", weeklyScheduleList.getWeeklyScheduleList());

		return "/secure/weeklySchedule";
	}

	@GetMapping("/permission-denied")
	public String permissionDenied() {
		return "/error/permission-denied";
	}

	@GetMapping("/register")
	public String getRegister() {
		return "register";
	}
	
	@GetMapping("/about")
	public String getAbout() {
		return "about";
	}

	@PostMapping("/register")
	public String postRegister(@RequestParam String name, @RequestParam String lastName, @RequestParam String username,
			@RequestParam String password) {

// Checking if user already has an account before new account creation.
		if (da.findUserAccount(username) == null) {
			da.addUser(name, lastName, username, password);

			Long userId = da.findUserAccount(username).getUserId();

			da.addRole(userId, Long.valueOf(1));

			return "accountCreated";
		} else
			return "userFound";
	}

}
