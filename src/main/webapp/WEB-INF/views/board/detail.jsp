<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp" %>

<div class="container">
	<button class="btn btn-secondary" onclick="history.back();">돌아가기</button>
	<c:if test="${board.user.id == principal.user.id}">
		<!-- <button id="btn-update" class="btn btn-warning">수정</button> -->
		<a href="/board/${board.id}/updateForm" class="btn btn-warning">수정</a>
		<button id="btn-delete" class="btn btn-danger">삭제</button>
	</c:if>
	<br/><br/>
	<div>
	
		글번호: <span id="id"><i>${board.id}</i></span>
		작성자: <span><i>${board.user.username}</i></span>
	</div>
	<br/>
	<div>
		<h3>${board.title}</h3>
	</div>
	<hr>
	<div>
		<div>${board.content}</div>
	</div>

</div>

<script type="text/javascript">
let index = {
	init : function(){
		$("#btn-delete").on("click", ()=>{	
			this.deleteById();
		});
	},
	
	
	deleteById : function(){
		// var id = $("#id").val(); (x)
		var id = $("#id").text();
		
		$.ajax({
			type: "delete",
			url: "/api/board/"+id,
			contentType: "application/json; charset=utf-8",
			dataType: "json"
		}).done(function(resp){
			console.log(resp);
			alert("삭제가 완료되었습니다!!");
			location.href="/";
		}).fail(function(error){
			alert(JSON.stringify(error));
		});	
	}
}

index.init();

</script>
<%@ include file="../layout/footer.jsp" %>

