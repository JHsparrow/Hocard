<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>호희왕 - 메인페이지</title>
<link rel="stylesheet" href="style.css">
</head>
<body>
	<%@include file="topmenu.jsp" %>
	<section>
		<form name="" action="">
			<div class="view_con">
				<div class="view_border_<c:out value="${card.rarity}" />">
				<input type="hidden" name="card_no" value="${card.card_no}" />
					<img class="view_attack" src="./images/attack3.png">
					<img class="view_hp" src="./images/hp.png">
					<span class="view_attack_point"><input class="mod_input" name="attack" type="text" value="${card.attack}" size=1 maxlength='1' /></span>
					<span class="view_hp_point"><input class="mod_input" name="hp" type="text" value="${card.defense}" size=1 maxlength='1' /></span>
					<p class="view_name"><input class="mod_name" name="card_name" type="text" value="${card.card_name}"  /></p>
					<div class="view_desc">
						<span><input class="mod_desc" name="desc" type="text"></span>
					</div>	
					<select class="view_rarity" name="rarity" >
						<option>레전드</option>
						<option>유니크</option>
						<option>레어</option>
						<option>노말</option>
					</select>
				</div>
				<div>
					<p class="view_btn" onclick="location.href='modifycard?card_no=${card.card_no}'">수&nbsp;&nbsp;&nbsp;정</p>
					<p class="view_btn" onclick="location.href='modify?${card.card_no}'">목&nbsp;&nbsp;&nbsp;록</p>
				</div>
			</div>
		</form>
		
	</section>
	<%@include file="footer.jsp" %>
</body>
</html>