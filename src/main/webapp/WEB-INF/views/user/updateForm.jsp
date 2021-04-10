<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>
<div class="container">
	<form>
	<!-- <form action="/user/join" method="post"> 옛날 방식 -> json으로 바꿈 -->
		<input type="hidden" id="id" value="${principal.user.id}" />
		<div class="form-group">
			<label for="username">Username:</label>
			<input type="text" value="${principal.user.username}" class="form-control" placeholder="Enter username" id="username" readonly="readonly">
		</div>
		
		<c:if test="${empty principal.user.oauth}">
			<div class="form-group">
				<label for="password">Password</label> 
				<input type="password" class="form-control" placeholder="Enter password" id="password">
			</div>
		</c:if>
		
		<div class="form-group">
			<label for="password">Password:</label> 
			<input type="password" class="form-control" placeholder="Enter password" id="password">
		</div>
		<div class="form-group">
			<label for="email">Email:</label> 
			<input type="email" value="${principal.user.email}" class="form-control" placeholder="Enter email" id="email">
		</div>
	</form>
		<button id ="btn-update" class="btn btn-primary">회원수정완료</button>

</div>

<!-- <script src="/blog/js/user.js"></script> -->
<script type="text/javascript">
let index = {
	init : function(){
		$("#btn-update").on("click", ()=>{	// function(){}, ()=> 하는 이유 : this를 바인딩하기 위해서
			this.update();
		});
	},
	
	update : function(){
		//alert("user의 update함수 호출됨");
		let data = {
			id: $("#id").val(),
			username: $("#username").val(),
			password: $("#password").val(),
			email: $("#email").val()
			
		}

		$.ajax({
			type: "put",
			url: "/user",
			data: JSON.stringify(data),
			contentType: "application/json; charset=utf-8"
			//dataType: "json",	// 요새 안 적어줘도 자동으로 json리턴해줌! 

		}).done(function(resp){
			//alert(resp);
			console.log(resp);
			alert("회원수정이 완료되었습니다!!");
			location.href="/";
		}).fail(function(error){
			alert(JSON.stringify(error));
		});	// ajax 통신을 이용해서 3개의 데이터를 json으로 변경하여 insert 요청
	}
}

index.init();

</script>

<%@ include file="../layout/footer.jsp"%>

