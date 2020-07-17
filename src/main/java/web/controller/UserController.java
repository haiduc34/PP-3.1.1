package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import web.model.Role;
import web.model.User;
import web.service.UserService;

import java.util.*;

@Controller
@RequestMapping("/")
public class UserController {

	@Autowired
	UserService userDetailsServiceImpl;

	@GetMapping(value = "/")
	public String printWelcome(ModelMap model) {
		List<String> messages = new ArrayList<>();
		messages.add("Hello!");
		messages.add("I'm Spring MVC-SECURITY application");
		messages.add("5.2.0 version by sep'19 ");
		model.addAttribute("messages", messages);
		return "hello";
	}

    @GetMapping(value = "login")
    public String loginPage() {
        return "login";
    }

    @GetMapping (value = "admin")
	public String adminPage(Model model) {
		model.addAttribute("user", new User());
		model.addAttribute("listUsers", userDetailsServiceImpl.listUsers());
		return  "admin/admin";
	}

	@PostMapping(value = "/admin/add")
	public String addUser(@RequestParam("username") String name, @RequestParam("password") String password){
		Set<Role> roles = new HashSet<>();
		roles.add(userDetailsServiceImpl.getRoleByName("ROLE_USER"));
		userDetailsServiceImpl.addUser(new User(name, roles, password));
		return "redirect:/admin";
	}

	@PostMapping("/admin/remove")
	public String removeUser(@RequestParam("id") Long id, Model model){

		userDetailsServiceImpl.removeUser(id);

		return "redirect:/admin";
	}

	@GetMapping("/admin/update")
	public String userData(Model model, @RequestParam("id") Long id){
		model.addAttribute("user", userDetailsServiceImpl.getUserById(id));
		return "admin/updateUser";
	}

	@PostMapping("/admin/update")
	public String userData(Model model, @RequestParam("id") Long id, @RequestParam("username")
			String name, @RequestParam("password") String password, @RequestParam("role") String role){
		HashSet<Role> roles = new HashSet<>();
		roles.add(userDetailsServiceImpl.getRoleByName(role));
		userDetailsServiceImpl.updateUser(new User(id, name, roles, password));
		return "redirect:/admin";
	}

	@GetMapping("/user")
	public String userPage(Model model){

		model.addAttribute("user", (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
		return "user/userpage";
	}

	@PostMapping("/logout")
	public String logoutPage () {
		return "redirect:/admin";
	}



}