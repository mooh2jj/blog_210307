<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>
<div class="container">

	<form action="/auth/loginProc" method="post">
		<div class="form-group">
			<label for="username">Username:</label> 
			<input type="text" name="username" class="form-control" placeholder="Enter username" id="username">
		</div>

		<div class="form-group">
			<label for="password">Password:</label> 
			<input type="password" name="password" class="form-control" placeholder="Enter password" id="password">
		</div>

		<button id="btn-login" class="btn btn-primary">로그인</button>
		<a href="https://kauth.kakao.com/oauth/authorize?client_id=ad0cf1bc3edf1384b75ff662ff6a0b84&redirect_uri=http://localhost:8000/auth/kakao/callback&response_type=code"><img height="39px;" src="/image/kakao_login_button.png" /></a>
	</form>

</div>
<script>
	let index = {
	init : function(){
		$("#btn-login").on("click", ()=>{	// function(){}, ()=> 하는 이유 : this를 바인딩하기 위해서
			this.save();
		});
	},
	
	save : function(){
		
		let data = {
			username: $("#username").val(),
			password: $("#password").val(),
			
		}

		$.ajax({
			type: "post",
			url: "/api/user/login",
			data: JSON.stringify(data),
			contentType: "application/json; charset=utf-8"
			//dataType: "json",	// 요새 안 적어줘도 자동으로 json리턴해줌! 

		}).done(function(resp){
			
			console.log(resp);
			alert("로그인이 완료되었습니다.");
			location.href="/";
		}).fail(function(error){
			alert(JSON.stringify(error));
		});	// ajax 통신을 이용해서 3개의 데이터를 json으로 변경하여 insert 요청
	}
}

index.init();
</script>

<%@ include file="../layout/footer.jsp"%>

