package com.cos.blog.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.cos.blog.config.auth.PrincipalDetail;

@Controller
public class BoardController {

//	@AuthenticationPrincipal PrincipalDetail principal
	@GetMapping("/")
	public String index() {		// 컨트롤러에서 세션을 어떻게 찾는가?
//		System.out.println("로그인 사용자 아이디:"+principal.getUsername());
		// WEF-INF/views/index.jsp
		return "index";
	}
	// USER 권한이 필요
	@GetMapping("/board/saveForm")
	public String saveForm() {		// 컨트롤러에서 세션을 어떻게 찾는가?
		return "board/saveForm";
	}
}
