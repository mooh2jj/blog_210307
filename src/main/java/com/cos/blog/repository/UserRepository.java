package com.cos.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cos.blog.model.User;

// DAO
// 자동으로 bean등록이 된다. @Repository 안해도 됨!
public interface UserRepository extends JpaRepository<User, Integer> {

	// JPA Naming 쿼리
	// SELECT * FROM user WHERE username = ? AND password = ?;
	User findByUsernameAndPassword(String username, String password);
}
