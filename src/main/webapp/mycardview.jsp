<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>카드 관리시스템 - 내 카드 상세보기</title>
<link rel="stylesheet" href="style.css">
</head>
<body>
	<%@include file="topmenu.jsp" %>
	<section>
		<div class="view_con">
			<div class="view_border_<c:out value="${card.rarity}" />">
				<img class="view_attack" src="./images/attack3.png">
				<img class="view_hp" src="./images/hp.png">
				<span class="view_attack_point">${card.attack}</span>
				<span class="view_hp_point">${card.hp}</span>
				<p class="view_name">${card.card_name}</p>
				<div class="view_desc">
					<span>${card.description}</span>
				</div>	
			</div>
			<div>
				<p class="view_btn" onclick="DelCheck()">삭&nbsp;&nbsp;&nbsp;제</p>
				<p class="view_btn" onclick="location.href='mycard'">목&nbsp;&nbsp;&nbsp;록</p>
			</div>
			
		</div>
		
	</section>
	<%@include file="footer.jsp" %>
	<script>
		function DelCheck(){
			if(confirm('삭제하시겠습니까?')){
				location.href='delmycard?pick_no=${card.pick_no}';
			}
		}
	</script>
</body>
</html>