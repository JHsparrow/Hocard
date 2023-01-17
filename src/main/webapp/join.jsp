<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>카드 관리시스템 - 회원가입</title>
<link rel="stylesheet" href="style.css">
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Jua&display=swap" rel="stylesheet">
</head>
<body>
	<%@include file="topmenu.jsp" %>
	<section class="hg100">
		<form id="frm" action="joinResult" method="post">
			<div class="join_container">
				<div class="input-group mb-3">
				  <span class="input-group-text col-3" id="basic-addon1">아이디</span>
				  <input type="text" class="form-control" id="Userid" name="user_id" placeholder="아이디를 입력하세요" aria-label="Userid" aria-describedby="basic-addon1" maxlength="15">
				</div>
				<div class="input-group mb-3">
				  <span class="input-group-text col-3" id="basic-addon1">이름</span>
				  <input type="text" class="form-control" name="UName" placeholder="이름을 입력하세요" aria-label="Username" aria-describedby="basic-addon1">
				</div>
				<div class="input-group mb-3">
				  <span class="input-group-text col-3" id="basic-addon1">비밀번호</span>
				  <input type="password" class="form-control" id="pwd_O" name="UPassword" placeholder="비밀번호를 입력하세요" aria-label="password" aria-describedby="basic-addon1">
				</div>
				<div class="input-group mb-3">
				  <span class="input-group-text col-3" id="basic-addon1">비밀번호 확인</span>
				  <input type="password" class="form-control" id="pwd_C" onchange="check_pwd()" placeholder="동일한 비밀번호를 입력하세요" aria-label="passwordCheck" aria-describedby="basic-addon1">
				  <span id="pwd_Y" class=" input-group-text pwd_Y">비밀번호 일치</span>
				  <span id="pwd_N" class="input-group-text pwd_N">비밀번호 불일치</span>
				</div>
				<button class="btn col-3 btn-outline-secondary" onclick="frm_submit()" type="button">가입</button>
				<button class="btn col-3 btn-outline-danger" onclick="history.back()" type="button">취소</button>
			</div>
			
			
		</form>
	</section>
	<script>
		function frm_submit(){
			var eng = /^[a-zA-Z]*$/; 
			if ($('[name=Userid]').val()=="") {
				alert("아이디를 입력해주세요.");
				$('[name=Userid]').focus();
				return false;
			}
			
			if ($('[name=UName]').val()=="") {
				alert("이름을 입력해주세요.");
				$('[name=UName]').focus();
				return false;
			}
			if ($('[name=UPassword]').val()=="") {
				alert("비밀번호를 입력해주세요.");
				$('[name=UPassword]').focus();
				return false;
			}
			if ($('[name=UPassword]').val()!=$('#pwd_C').val()) {
				alert("비밀번호를 확인 부탁드립니다.");
				$('#pwd_C').focus();
				return false;
			}
			$('#frm').submit();
		}
		
		function nameCheck(){
			var Nlength = $('.mod_name').val().length;
			if (Nlength >= 7) {
				alert('이름은 6글자 이내로 작성해주십시오.');
				$('.mod_name').val().slice(0,-1);
			}
		}
		$(function(){
			$('#pwd_Y').hide();
			$('#pwd_N').hide();
		})
		function check_pwd(){
			var pwdO = $('#pwd_O').val();
			var pwdC = $('#pwd_C').val();
			
			if(pwdO==pwdC){
				$('#pwd_Y').show();
				$('#pwd_N').hide();
			}else {
				$('#pwd_Y').hide();
				$('#pwd_N').show();
			}
		}
	</script>
	<%@include file="footer.jsp" %>
</body>
</html>