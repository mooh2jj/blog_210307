package com.cos.blog.test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

//스프링이 com.cos.blog 패키지 이하를 스캔해서 모든 파일을 메모리에 new하는 것은 아니다.
//특정 어노체이샨이 붙어 있는 클래스파일들을 new해서 (Ioc)스프링 컨테이너에 관리해준다.
@RestController	
public class BlogControllerTest {

	// http://localhost:8000/blog/test/hello
	@GetMapping("/test/hello")
	public String hello() {
		return "<h1>hello Work!</h1>";
	}
}
