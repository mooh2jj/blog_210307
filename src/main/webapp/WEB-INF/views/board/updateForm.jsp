<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp" %>

<div class="container">
	<form>
	<input type="hidden" id="id" value="${board.id}"/>
		<div class="form-group">
			<input value="${board.title}" type="text" name="title" class="form-control" placeholder="Enter title" id="title">
		</div>

		<div class="form-group">
		  <textarea class="form-control summernote" rows="5" id="content">value="${board.content}"</textarea>
		</div>

	</form>
	<button id="btn-update" class="btn btn-primary">수정</button>
</div>
<script>
  $('.summernote').summernote({
    tabsize: 2,
    height: 300
  });
</script>
<script type="text/javascript">
let index = {
	init : function(){
		$("#btn-update").on("click", ()=>{	// function(){}, ()=> 하는 이유 : this를 바인딩하기 위해서
			this.update();
		});
	},
	
	update : function(){
		let id = $("#id").val();
		
		let data = {
			title: $("#title").val(),
			content: $("#content").val()
		};

		$.ajax({
			type: "put",
			url: "/api/board/"+id,
			data: JSON.stringify(data),
			contentType: "application/json; charset=utf-8",
			dataType: "json"

		}).done(function(resp){
			console.log(resp);
			alert("글수정이 완료되었습니다!!");
			location.href="/";
		}).fail(function(error){
			alert(JSON.stringify(error));
		});	
	}
}

index.init();

</script>
<%@ include file="../layout/footer.jsp" %>

