package com.supra.sso.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.supra.sso.model.Role;
import com.supra.sso.model.User;
import com.supra.sso.model.UserForm;
import com.supra.sso.model.UserToken;
import com.supra.sso.repository.RoleRepository;
import com.supra.sso.service.SecurityService;
import com.supra.sso.service.UserService;
import com.supra.sso.utiities.ApplicationConstants;
import com.supra.sso.validators.UserValidator;

@Controller
public class UserController {
	
	@Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private RoleRepository roleRepository;
    
    //Welcome
    @RequestMapping(value = {"/", "/welcome"}, method = RequestMethod.GET)
    public String welcome(Model model, HttpSession httpSession) {
    	User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	if(null != loggedInUser) {
    		model.addAttribute("user", loggedInUser);
    		return "welcome";
    	}
    	else {
    		return "redirect:/logout";    		
    	}
    }
//...
    
    //Login
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model, String error, String logout) {
        if (error != null)
            model.addAttribute("error", "Your username and password is invalid.");
        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");
        return "login";
    }

    
    //Registration
    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration(Model model) {
        model.addAttribute("userForm", new UserForm());
        return "registration";
    }

    
    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registration(@ModelAttribute("userForm") UserForm userForm, BindingResult bindingResult, Model model) {
        userValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return "registration";
        }
        List<Role> roles = roleRepository.findAll();
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        for (Role role : roles){
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getAuthority()));
        }
        User user = new User(userForm.getUsername(), userForm.getPassword(), grantedAuthorities);
        userService.save(user);
        securityService.autologin(userForm.getUsername(), userForm.getPasswordConfirm());
        return "redirect:/welcome";
    }
    

    @RequestMapping(value="/openPageFor/{moduleName}")
    public String openPageForModule(@PathVariable("moduleName") String moduleName) {
    	String viewName=null;
    	if(moduleName.equals(ApplicationConstants.ATTENDANCE_MODULE)) {
    		viewName = "http://localhost:8081/timesheet/welcometimesheet";
    	}
    	else if(moduleName.equals(ApplicationConstants.TIMESHEET_MODULE)) {
    		viewName = "timesheetWelcome";
    	}
    	else {
    		viewName = "errorModuleWelcome";
    	}
    		
    	return "redirect://"+viewName;
    }
    
}