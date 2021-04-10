package com.cos.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.cos.blog.model.KakaoProfile;
import com.cos.blog.model.OAuthToken;
import com.cos.blog.model.User;
import com.cos.blog.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

// 인증이 안된 사용자들이 출입할 수 있는 경로를 /auth/** 허용
// 그냥 주소가 '/'이면 index.jsp 허용
// static 이하에 있는 /js/**, /css/**, /image/**

@Controller
public class UserController {

	@Value("${cos.key}")
	private String cosKey;		// cosKey 노출되면 바로 로그인되서 망함!
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserService userService;
	
//	http://localhost:8000/user/joinForm
	@GetMapping("/auth/joinForm")
	public String joinForm() {
		System.out.println("joinForm start..");
		return "user/joinForm";
		
	}
	
//	http://localhost:8000/user/joinForm
	@GetMapping("/auth/loginForm")
	public String loginForm() {
		
		return "user/loginForm";
		
	}
	
	// 카카오인증 응답코드(callback)처리 메서드
	@GetMapping("/auth/kakao/callback")
	public String kakaCallback(String code) {	
		// String code : 카카오가 응답하는 code -> 엑세스토큰을 받을 것임.
		// POST방식으로 key=value 데이터를 요청, 카카오쪽으로 4가지 정보(grant_type, client_id, redirect_id, code) 보낼것임 
		
		// Restrofit2(안드로이드쪽)
		// OkHttp
		// RestTemplate // http요청을 간편하게 쓸 수 있는 라이브러리
		
		RestTemplate rt = new RestTemplate();
		
		// HttpHeader 오브젝트 MultiValueMap params 생성
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type", "authorization_code");
		params.add("client_id", "ad0cf1bc3edf1384b75ff662ff6a0b84");
		params.add("redirect_id", "http://localhost:8000/auth/kakao/callback");
		params.add("code", code);	// code는 동적임
		
		// HttpHeader와 HttpBody를 하나의 오브젝트 HttpEntity에 담기
		HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = 
				new HttpEntity<>(params, headers);
		// Http 요청하기 - Post방식으로 - 그리고 response 변수의 응답 받음.
		ResponseEntity<String> response = rt.exchange(
				"https://kauth.kakao.com/oauth/token",		// 토큰발급 요청 주소(post)
				HttpMethod.POST,							// 메서드 방식
				kakaoTokenRequest,							// Header와 body정보
				String.class								// 응답을 받을 타입
			);
		
		// Gson, Json Simple, ObjectMapper 사용
		ObjectMapper objectMapper = new ObjectMapper();
		OAuthToken oauthToken = null;
		try {
			oauthToken = objectMapper.readValue(response.getBody(), OAuthToken.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		System.out.println("카카오 엑세스 토큰 : "+oauthToken.getAccess_token());
		
		
		RestTemplate rt2 = new RestTemplate();
		
		// HttpHeader 오브젝트 생성
		HttpHeaders headers2 = new HttpHeaders();
		headers2.add("Authorization", "Bearer "+oauthToken.getAccess_token());
		headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		
		// HttpHeader와 HttpBody를 하나의 오브젝트에 담기
		HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest2 = 
				new HttpEntity<>(headers2);
		
		// Http 요청하기 - Post방식으로 - 그리고 response 변수의 응답 받음.
		ResponseEntity<String> response2 = rt2.exchange(
				"https://kapi.kakao.com/v2/user/me",
				HttpMethod.POST,
				kakaoProfileRequest2,
				String.class
		);
		System.out.println(response2.getBody());
		
		ObjectMapper objectMapper2 = new ObjectMapper();
		KakaoProfile kakaoProfile = null;
		try {
			kakaoProfile = objectMapper2.readValue(response2.getBody(), KakaoProfile.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		// User 오브젝트 : username(규칙정함 = email_id), password, email
		System.out.println("카카오 id(번호) : "+kakaoProfile.getId());
		System.out.println("카카오 email : "+kakaoProfile.getKakao_account().getEmail());
		
		System.out.println("블로그서버 username : "+kakaoProfile.getKakao_account().getEmail()+"_"+kakaoProfile.getId());
		System.out.println("블로그서버 email : "+kakaoProfile.getKakao_account().getEmail());
		System.out.println("블로그서버 패스워드 : "+cosKey);
		
		User kakaoUser = User.builder()
				.username(kakaoProfile.getKakao_account().getEmail()+"_"+kakaoProfile.getId())
				.password(cosKey)
				.email(kakaoProfile.getKakao_account().getEmail())
				.oauth("kakao")		// 카카오 로그인한 사람인지 구분자
				.build();
		
		// 가입자 혹은 비가입자 체크 처리
		User originUser = userService.회원찾기(kakaoUser.getUsername());
		
		if(originUser.getUsername() == null) {
			System.out.println("기존회원이 아닙니다. 회원가입을 해야합니다.");
			userService.회원가입(kakaoUser);
		}
		System.out.println("자동 로그인을 진행합니다.");
		// 로그인 처리
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(kakaoUser.getUsername(), cosKey));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		
//		test용 return
//		return response2.getBody();		// getBody or getHeader 둘을 분리해서 쓸 수 있음.
//		return response.getBody();
		return "redirect:/";	// 회원가입 및 로그인 처리완료
	}
	
	
	@GetMapping("/user/updateForm")
	public String updateForm() {	
	  return "user/updateForm";
	}
	
}
