package com.cos.blog.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Reply {

	@Id			// pk
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;	
	
	@Column(nullable = false, length = 200)
	private String content;
	
	@ManyToOne	// 여러개의 답변은 하나의 게시글에 달릴 수 있다.
	@JoinColumn(name="boardId")
	private Board board;
	
	@ManyToOne	// 여러개의 답변은 한명이 단다.
	@JoinColumn(name="userId")
	private User user;
	
	@CreationTimestamp
	private Timestamp createDate;
	
}
