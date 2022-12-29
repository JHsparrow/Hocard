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
				<div class="view_border_<c:out value="${card.rarity}" />">
				<input type="hidden" name="card_no" value="${card.card_no}" />
					<img class="view_attack" src="./images/attack3.png">
					<img class="view_hp" src="./images/hp.png">
					<span class="view_attack_point"><input class="mod_input" name="attack" type="text" value="${card.attack}" size=1 maxlength='1' /></span>
					<span class="view_hp_point"><input class="mod_input" name="hp" type="text" value="${card.defense}" size=1 maxlength='1' /></span>
					<p class="view_name"><input class="mod_name" name="card_name" type="text" value="${card.card_name}" maxlength='10'  /></p>
					<div class="view_desc">
						<span><input class="mod_desc" name="desc" type="text"></span>
					</div>	
					<select class="view_rarity" name="rarity" >
						<option <c:if test="${card.rarity eq 'L'}">selected</c:if> value="L" >레전드</option>
						<option <c:if test="${card.rarity eq 'U'}">selected</c:if> value="U">유니크</option>
						<option <c:if test="${card.rarity eq 'R'}">selected</c:if> value="R">레어</option>
						<option <c:if test="${card.rarity eq 'N'}">selected</c:if> value="N">노말</option>
					</select>
				</div>
				<div>
					<p class="view_btn" onclick="mod_sub()">수&nbsp;&nbsp;&nbsp;정</p>
					<p class="view_btn" onclick="location.href='viewcard?card_no=${card.card_no}'">돌아가기</p>
				</div>
			</div>
		</form>
	</section>
	<script>
	function mod_sub(){
		alert('수정완료하였습니다.');
		document.frm.submit();
	}
		
	</script>
	<%@include file="footer.jsp" %>
</body>
</html>