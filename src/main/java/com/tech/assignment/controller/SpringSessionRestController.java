package com.tech.assignment.controller;

import com.tech.assignment.dto.RestaurantDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashSet;
import java.util.Set;

@RestController
@Slf4j
public class SpringSessionRestController {

    @GetMapping("/")
    String uid(HttpSession session) {
        return session.getId();
    }


    @GetMapping(value = "/submitMsg")
    public boolean submitMsg(@RequestParam("msg") String msg, HttpServletRequest request,HttpSession session) {
        @SuppressWarnings("unchecked")

        String authorization = request.getHeader("Authorization");
        String base64Credentials = authorization.substring("Basic".length()).trim();
        byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
        String credentials = new String(credDecoded, StandardCharsets.UTF_8);
        final String[] values = credentials.split(":", 2);
        // credentials = username:password

        Set<String> msgs = (Set<String>) session.getAttribute("MY_SESSION_MESSAGES");
        if (msgs == null) {
            msgs = new HashSet<>();
            session.setAttribute("MY_SESSION_MESSAGES", msgs);
        }
        msgs.add(values[0]+":"+msg);
        session.setAttribute("MY_SESSION_MESSAGES", msgs);

        //model.addAttribute("restaurantDetail",new RestaurantDetails());


        return true;

    }

    @GetMapping(value = "/viewSessionMsgs")
    public Set<String> viewSessionMsgs(HttpSession session) {
        @SuppressWarnings("unchecked")

       // org.springframework.security.core.userdetails.User	 principal = (org.springframework.security.core.userdetails.User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
       // Set<String> msgs = (Set<String>) request.getSession().getAttribute("MY_SESSION_MESSAGES");
        Set<String> msgs = (Set<String>) session.getAttribute("MY_SESSION_MESSAGES");
        if (msgs == null) {
            msgs = new HashSet<>();
            session.setAttribute("MY_SESSION_MESSAGES", msgs);
        }


        return msgs;

    }


    @GetMapping(value = "/adminfinalizedrestaurant")
    public Set<String> adminfinalizedrestaurant(HttpSession session,HttpServletRequest request) {
        @SuppressWarnings("unchecked")

      //  org.springframework.security.core.userdetails.User	 principal = (org.springframework.security.core.userdetails.User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        // Set<String> msgs = (Set<String>) request.getSession().getAttribute("MY_SESSION_MESSAGES");
        Set<String> msgs = (Set<String>) session.getAttribute("MY_SESSION_MESSAGES");
        Set<String> finalMsgs=new HashSet<>();
        for (String name : msgs) {
            final String[] values = name.split(":", 2);
            if(!values[1].equalsIgnoreCase("BOMBAY_RESATRUNT"))
            finalMsgs.add(name);
        }

        session.invalidate();
        session=request.getSession(true);
        session.setAttribute("MY_SESSION_MESSAGES", finalMsgs);

        return msgs;

    }
}
