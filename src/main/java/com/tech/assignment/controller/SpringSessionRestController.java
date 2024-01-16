package com.tech.assignment.controller;

import com.tech.assignment.dto.RestaurantDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.HashSet;
import java.util.Set;

@RestController
@Slf4j
public class SpringSessionRestController {

    @GetMapping("/")
    String uid(HttpSession session) {
        return session.getId();
    }


    @PostMapping("/persistMessageRest")
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
}
