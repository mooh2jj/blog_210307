package com.cos.blog.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
//ORM이란? -> java(다른 언어)Object -> DB 테이블로 매핑하는 기술 (jpa) 
@Entity		// User 클래스가 Mysql에 테이블이 생성이 된다.
//@DynamicInsert	// insert시에 null인 필드를 제외시켜준다. 
public class User {
	
	@Id	// Pk
	@GeneratedValue(strategy = GenerationType.IDENTITY)	// 프로젝트에서 연결된 해당 DB의 넘버링 전략을 따라간다. 
	private int id;	// 시퀀스(오라클) or auto_increment(Mysql)
	
	@Column(nullable = false, length = 30)
	private String username;	// 아이디
	
	@Column(nullable = false, length = 100)	// 123456 => 해쉬(비밀번호 암호화)
	private String password;
	
	@Column(nullable = false, length = 50)
	private String email;
	
//	@ColumnDefault("'user'")
//	private String role;	// String -> Enum을 쓰는 게 좋다. 도메인(범위)설정을 해줄 수 있어서//범위 ex. admin, user, manager
	@Enumerated(EnumType.STRING)
	private RoleType role;
	
	@CreationTimestamp	// 시간이 자동 입력
	private Timestamp createDate;
	
}
