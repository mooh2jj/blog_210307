package com.cos.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

//	http://localhost:8000/user/joinForm
	@GetMapping("user/joinForm")
	public String joinForm() {
		
		return "user/joinForm";
		
	}
	
//	http://localhost:8000/user/joinForm
	@GetMapping("user/loginForm")
	public String loginForm() {
		
		return "user/loginForm";
		
	}
}
