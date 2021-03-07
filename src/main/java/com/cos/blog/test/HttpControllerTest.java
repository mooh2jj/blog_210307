package com.cos.blog.test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

//사용자가 요청 -> 응답(Data)
@RestController
public class HttpControllerTest {

	// 브라우저 요청은 GET방식밖에 되지 않는다. -> 그래서 postman을 사용한 것! 
//	@GetMapping("/http/get")
//	public String getTest(@RequestParam int id) {
//		return "get 요청"+id;
//	}
	
	@GetMapping("/http/get")
	public String getTest() {
		return "get 요청";
//		return "get 요청"+m.getId() + m.getPassword();
	}
	
//	@PostMapping("/http/post")	// text/plain
//	public String postTest() {
//		return "post 요청";
//	}
	
//	@PostMapping("/http/post")	// 'x-www-form-urlencoded' 
//	public String postTest(Member m) {	//post 보낼 때는 그냥 DTO !	
//		return "post 요청"+m.getId() + m.getUsername() + m.getPassword() +m.getEmail();
//	}
	
//	@PostMapping("/http/post")	// postman 'raw'->text => text/plain
//	public String postTest(@RequestBody String text) {	// 다른 건 @RequestBody 붙여줘야 가능!
//		System.out.println("text:"+text);
//		return "post 요청"+text;
//	}
	
	@PostMapping("/http/post")	// aplication/json
	public String postTest(@RequestBody Member m) {	//post json보낼 때는 DTO라도 @RequestBody 붙여줘!	// MessageConvertor(스프링부트)
		return "post 요청"+m.getId() + m.getUsername() + m.getPassword() +m.getEmail();
	}
	
	
	@PutMapping("/http/put")	
	public String putTest(@RequestBody Member m) {
		return "put 요청"+m.getId() + m.getUsername() + m.getPassword() +m.getEmail();
	}
	
	@GetMapping("/http/lombokTest")
	public String lombokTest() {
		Member m1 = new Member(1, "sar", "1234", "email");
		System.out.println("getter:"+m1.getId());
		m1.setId(5000);
		System.out.println("setter:"+m1.getId());
		return "lombok 요청";
	}
}