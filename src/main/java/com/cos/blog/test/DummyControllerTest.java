package com.cos.blog.test;

import java.util.List;
import java.util.function.Supplier;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;


@RestController
public class DummyControllerTest {

	@Autowired	// 의존성 주입
	private UserRepository userRepository;
	
	
	@DeleteMapping("/dummy/user/{id}")
	public String delete(@PathVariable int id) {
		
		try {
			userRepository.deleteById(id);
			
		} catch (EmptyResultDataAccessException e) {		// Exception으로 해도 돼!
			return "삭제에 실패하였습니다. 해당 id는 없습니다.";
		}
		
		return "삭제되었습니다. id"+id;
	}
	
	@Transactional
	@PutMapping("/dummy/user/{id}")
	public User updateUser(@PathVariable int id, @RequestBody User requestUser) {
		//json 데이터 요청=>  MessageConverter의 Jackson라이브러리가 변환해서 받아준다!
		System.out.println("id: "+id);
		System.out.println("password: "+requestUser.getPassword());
		System.out.println("password: "+requestUser.getEmail());
		
		User user = userRepository.findById(id).orElseThrow(()->{
			return new IllegalArgumentException("수정에 실패하였습니다.");
		});
		user.setPassword(requestUser.getPassword());
		user.setEmail(requestUser.getEmail());
//		save함수는 id를 전달하지 않으면 insert를 해주고
//		save함수는 id를 전달하면 해당 id에 대한 데이터가 있으면 update를 해줌!
//		userRepository.save(requestUser);	
//		더티 채킹
		
		return user;
		
	}
	
	
	// 전체 select
	@GetMapping("/dummy/users")
	public List<User> list(){
		return userRepository.findAll();
	}
	
	
	// 한페이지당 2건에 데이터를 리턴받아 볼 예정
	@GetMapping("/dummy/user")
	public List<User> pageList(@PageableDefault(size=2, sort="id", direction = Sort.Direction.DESC) Pageable pageable){
		
//		Page<User> users = userRepository.findAll(pageable);
		Page<User> pagingUser = userRepository.findAll(pageable);
		List<User> users = pagingUser.getContent();
		return users;
	}
	
	
	// {id}주소로 파라미터를 전달 받을 수 있음
	@GetMapping("/dummy/user/{id}")
	public User detail(@PathVariable int id) {
		// user/7같으거 던지면 user가 null되서 반환되니! orElseThrow 사용!
		User user = userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>() {
			@Override
			public IllegalArgumentException get() {
				return new IllegalArgumentException("해당 유저는 없습니다. id:"+id);
			}
		});
		
		// 람다식
//		User user = userRepository.findById(id).orElseThrow(()->{
//				return new IllegalArgumentException("해당 유저는 없습니다. id:"+id);
//		});
		
		// 변환 (웹브라우저가 이해할 수 있는 데이터) -> json (Gson 라이브러리)
		// 스프링부트 = MessageConverter라는 애가 응답시에 자동 작동
		// user 오브젝트를 json으로 변환해서 브라우저에 응답해준다!
		return user;
	};
	
	@PostMapping("/dummy/join")
	public String join(User user) {		// key=value
		System.out.println("User:"+ user);
		
//		user.setRole("user");	// 실수할 수 있어 Enum으로 대체하자!
		user.setRole(RoleType.USER);
		userRepository.save(user);
		return "회원가입이 완료되었습니다.";
	}
	
}
