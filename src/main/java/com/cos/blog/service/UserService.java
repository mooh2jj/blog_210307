package com.cos.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
//	@Transactional
//	public int 회원가입(User user) {
//		try {
//			userRepository.save(user);
//			return 1;
//		} catch(Exception e) {
//			e.printStackTrace();
//			System.out.println("UserService : 회원가입():" + e.getMessage());
//		}
//		return -1;
//	}
	@Transactional
	public void 회원가입(User user) {
		System.out.println("회원가입 해쉬진입");
		String rawPassword = user.getPassword(); //1234 원문
		String ecnPassword = encoder.encode(rawPassword); // 해쉬화
		user.setPassword(ecnPassword);
		user.setRole(RoleType.USER);
		userRepository.save(user);
	
	}

//	@Transactional(readOnly = true)		// select 할 때 트랜잭션 시작, 서비스 종료시에 트랜잭션 종료 (정합성)
//	public User 로그인(User user) {
//		
//		return userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
//	}
}