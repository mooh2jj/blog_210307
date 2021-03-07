package com.cos.blog.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TempControllerTest {

	@GetMapping("/temp/home")
	public String tempHome() {
		System.out.println("tempHome()");
		// 파일리턴 기본경로: src/main/resources/static, but jsp는 안돼!
		// 리턴명 : /home.html
		// => 풀경로 : src/main/resources/static/home.html
		// static은 브라우저가 인식할 수 있는 파일만 넣는 거! html, img, css
//		There was an unexpected error (type=Not Found, status=404).
//		JSP file [/WEB-INF/views/home.html.jsp] not found // 이거 안됨;;;
		return "/home.html";
	}
	
	@GetMapping("/temp/jsp")
	public String tempJsp() {
		System.out.println("tempJsp()");
		// prefix : /WEB-INF/views/
		// suffix : jsp
		// 풀네임 : /WEB-INF/views/test.jsp.jsp // 그전에 main 아래에 webapp폴더 만들어줘!
		return "/test";
	}
}
