package com.tech.assignment.controller;

import com.tech.assignment.dto.RestaurantDetails;
import com.tech.assignment.dto.User;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
public class SpringSessionController {

	private final String hashReference= "Restaurant";
	@Resource(name="redisTemplate")          // 'redisTemplate' is defined as a Bean in AppConfig.java
	private RedisTemplate template;
	@GetMapping("/admin/login")
	public String home(Model model, HttpSession session) {
		return "adminindex";
	}
	@RequestMapping(value={"/user1/login", "/user2/login","/user3/login", "/user4/login","/user5/login", "/user6/login"},method = RequestMethod.GET)
	public String user(Model model,HttpSession session) {

		return "index";
	}



	@PostMapping("/persistMessage")
	public String persistMessage(@RequestParam("msg") String msg, HttpServletRequest request,Model model) {
		@SuppressWarnings("unchecked")

		org.springframework.security.core.userdetails.User	 principal = (org.springframework.security.core.userdetails.User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Set<String> msgs = (Set<String>) request.getSession().getAttribute("MY_SESSION_MESSAGES");
		if (msgs == null) {
			msgs = new HashSet<>();
			request.getSession().setAttribute("MY_SESSION_MESSAGES", msgs);
		}
		msgs.add(msg);
		request.getSession().setAttribute("MY_SESSION_MESSAGES", msgs);
		model.addAttribute("sessionMessages", msgs);
		//model.addAttribute("restaurantDetail",new RestaurantDetails());

		return "redirect:/viewAdminSubmit" ;

	}

	@GetMapping("/destroy")
	public String destroySession(HttpServletRequest request,Model model, @RequestParam(value = "selected", required = false) String[] selected) {
		RestaurantDetails restaurantDetails=new RestaurantDetails();
		restaurantDetails.setSessionId(request.getSession().getId());
		restaurantDetails.setHotelNames(selected);
		restaurantDetails.setSubmtTme(new Date());
	//	request.getSession().setAttribute("MY_SESSION_MESSAGES",new ArrayList<>());
	//	request.getSession().setAttribute("MY_INVITATION",new ArrayList<>());
		request.getSession().invalidate();
		model.addAttribute("submittedRestaurants",selected);
       template.opsForHash().put(hashReference,request.getSession().getId(),restaurantDetails);

		return "adminSubmit";
	}

	@GetMapping("/viewAdminSubmit")
	public String viewAdminSubmit(Model model,HttpSession session) {

		/*List<RestaurantDetails> restaurantDetails=template.opsForHash().values(hashReference);
		RestaurantDetails restaurantDetail =restaurantDetails.get(0);
		model.addAttribute("restaurantDetail",restaurantDetail);
		template.opsForHash().delete(hashReference,restaurantDetail.getSessionId());*/
		Set<String> messages = (Set<String>) session.getAttribute("MY_SESSION_MESSAGES");
		List<RestaurantDetails> restaurantDetails=template.opsForHash().values(hashReference);
		RestaurantDetails restaurantDetail=new RestaurantDetails();
		List<String> adminConfirmedMsg=new ArrayList<>();
		if(!CollectionUtils.isEmpty(restaurantDetails)) {
			restaurantDetail = restaurantDetails.get(0);
			template.opsForHash().delete(hashReference, restaurantDetail.getSessionId());
			if(null != restaurantDetail.getHotelNames())
			adminConfirmedMsg=Arrays.asList(restaurantDetail.getHotelNames());
		}
		if (messages == null) {
			messages = new HashSet<>();

		}
		model.addAttribute("sessionMessages", messages);
		model.addAttribute("confirmedMessages", new HashSet(adminConfirmedMsg));
		return "viewMessage";
	}
	@PostMapping("/createSession")
	public String createSession(Model model, HttpSession session) {
		@SuppressWarnings("unchecked")
		String sessionId = session.getId();
        session.setAttribute("sessionId", sessionId);
		model.addAttribute("sessionId", sessionId);
		model.addAttribute("user",new User());
        @SuppressWarnings("unchecked")
		Set<String> messages = (Set<String>) session.getAttribute("MY_SESSION_MESSAGES");
		if (messages == null) {
			messages = new HashSet<>();
		}
		session.setAttribute("sessionMessages", messages);
        return "SessionInvite";

	}

	@GetMapping("/join")
	public String join(HttpSession session, Model model) {
		// Store session identifier in the current session
		// request.getSession().setAttribute("sessionId", sessionId);

		// Add session information to the model for rendering
		Set<String> messages = (Set<String>) session.getAttribute("MY_SESSION_MESSAGES");

		if (messages == null) {
			messages = new HashSet<>();
		}
		model.addAttribute("sessionMessages", messages);
		return "joinsession";
	}

	@PostMapping("/invite")
	public String invite(@ModelAttribute("user") User user,Model model,HttpSession session,HttpServletRequest request) {
		List<String> users = (List<String>) session.getAttribute("MY_INVITATION");
		if (users == null) {
			users = new ArrayList<>();
			request.getSession().setAttribute("MY_INVITATION", users);
		}
		users.add(user.getUserId());
		request.getSession().setAttribute("MY_INVITATION", users);
		model.addAttribute("users", users);
		return "SessionInvite";
	}
}