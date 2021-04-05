<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp" %>

<div class="container">
	<form>
		<div class="form-group">
			<label for="title">Title:</label> 
			<input type="text" name="title" class="form-control" placeholder="Enter title" id="title">
		</div>

		<div class="form-group">
		  <label for="content">Content</label>
		  <textarea class="form-control summernote" rows="5" id="content"></textarea>
		</div>

	</form>
	<button id="btn-save" class="btn btn-primary">등록</button>
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
		$("#btn-save").on("click", ()=>{	// function(){}, ()=> 하는 이유 : this를 바인딩하기 위해서
			this.save();
		});
	},
	
	save : function(){
		let data = {
			title: $("#title").val(),
			content: $("#content").val()
			
		}

		$.ajax({
			type: "post",
			url: "/api/board",
			data: JSON.stringify(data),
			contentType: "application/json; charset=utf-8",
			dataType: "json",	// 요새 안 적어줘도 자동으로 json리턴해줌! 

		}).done(function(resp){
			//alert(resp);
			console.log(resp);
			alert("글쓰기가 완료되었습니다!!");
			location.href="/";
		}).fail(function(error){
			alert(JSON.stringify(error));
		});	// ajax 통신을 이용해서 3개의 데이터를 json으로 변경하여 insert 요청
	}
}

index.init();

</script>
<%@ include file="../layout/footer.jsp" %>

