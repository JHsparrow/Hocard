<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
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
				  <span class="input-group-text col-3" >아이디</span>
				  <span class="form-control"><%=Userid %></span>
				</div>
				<div class="input-group mb-3">
				  <span class="input-group-text col-3">이름</span>
				  <span class="form-control">${userinfo.username}</span>
				</div>
				
				<button class="btn col-3 btn-outline-primary" onclick="location.href='modifyUserinfo'" type="button">정보 수정하기</button>
				<button class="btn col-3 btn-outline-danger" onclick="location.href='index.jsp'" type="button">돌아가기</button>
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