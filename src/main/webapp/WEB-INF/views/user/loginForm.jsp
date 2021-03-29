<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>
<div class="container">

	<form>
		<div class="form-group">
			<label for="username">Username:</label> <input type="text" class="form-control" placeholder="Enter username" id="username">
		</div>

		<div class="form-group">
			<label for="password">Password:</label> <input type="password" class="form-control" placeholder="Enter password" id="password">
		</div>
		<div class="form-group form-check">
			<label class="form-check-label"> <input class="form-check-input" type="checkbox"> Remember me
			</label>
		</div>
	</form>
		<button id="btn-login" class="btn btn-primary">로그인</button>

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
			url: "/blog/api/user/login",
			data: JSON.stringify(data),
			contentType: "application/json; charset=utf-8"
			//dataType: "json",	// 요새 안 적어줘도 자동으로 json리턴해줌! 

		}).done(function(resp){
			
			console.log(resp);
			alert("로그인이 완료되었습니다.");
			location.href="/blog";
		}).fail(function(error){
			alert(JSON.stringify(error));
		});	// ajax 통신을 이용해서 3개의 데이터를 json으로 변경하여 insert 요청
	}
}

index.init();
</script>

<%@ include file="../layout/footer.jsp"%>

