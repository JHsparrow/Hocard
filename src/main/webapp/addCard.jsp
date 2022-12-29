<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>호희왕 - 메인페이지</title>
<link rel="stylesheet" href="style.css">
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Jua&display=swap" rel="stylesheet">
</head>
<body>
	<%@include file="topmenu.jsp" %>
	<section>
		<form name="frm" action="modify" method="post">
			<div class="view_con">
				<div >
				<input type="hidden" name="card_no" />
					<img class="view_attack" src="./images/attack3.png">
					<img class="view_hp" src="./images/hp.png">
					<span class="view_attack_point"><input class="mod_input" name="attack" type="text" value="${card.attack}" size=1 maxlength='1' /></span>
					<span class="view_hp_point"><input class="mod_input" name="hp" type="text" value="${card.defense}" size=1 maxlength='1' /></span>
					<p class="view_name"><input class="mod_name" name="card_name" type="text" value="${card.card_name}" maxlength='10'  /></p>
					<div class="view_desc">
						<span><input class="mod_desc" name="desc" type="text"></span>
					</div>	
					<select class="view_rarity" name="rarity" >
						<option value="L" >레전드</option>
						<option value="U">유니크</option>
						<option value="R">레어</option>
						<option value="N">노말</option>
					</select>
				</div>
				<div>
					<p class="view_btn" onclick="mod_sub()">등&nbsp;&nbsp;&nbsp;록</p>
					<p class="view_btn" onclick="location.href='index.jsp'">돌아가기</p>
				</div>
			</div>
		</form>
	</section>
	<script>
		function frm_submit(){
			var f = document.frm;
			if (f.rarity.value == '') {
				alert("희귀도를 선택해주세요.");
				return false;
			}
			if (f.card_name.value == '') {
				alert("카드이름을 입력해주세요.");
				return false;
			}
			
			f.submit();
		}
	</script>
	<%@include file="footer.jsp" %>
</body>
</html>