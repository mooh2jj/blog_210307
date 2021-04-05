package com.cos.blog.controller.api;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.dto.ResponseDto;
import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.service.UserService;

@RestController
public class UserApiController {
	
	@Autowired
	private UserService userSerive;
	
	
	@PostMapping("/auth/joinProc")
	public ResponseDto<Integer> save(@RequestBody User user) {	// username, password, email
		System.out.println("User: " + user);
		System.out.println("UserApiController: save 호출됨");
		
//		int result = userSerive.회원가입(user);
		userSerive.회원가입(user);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}
	
//	/blog/api/user/login
	// 전통적 방식의 로그인 방법
//	@PostMapping("/api/user/login")
//	public ResponseDto<Integer> login(@RequestBody User user, HttpSession session) {	// username, password, email
//		System.out.println("User: " + user);
//		System.out.println("UserApiController: login 호출됨");
//		
//		User principal = userSerive.로그인(user); 	//principal 접근주체
//		
//		if(principal != null) {
//			session.setAttribute("principal", principal);
//		}
//		
//		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
//	}
}
