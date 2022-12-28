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
		<div class="list_con">
			<ul>
				<c:forEach var="i" items="${cardList}" varStatus="status">
				<c:set var="rarity" value="${i.rarity}"></c:set>
				<li class="border_<c:out value="${rarity}" />" onclick="location.href='viewcard?card_no=${i.card_no}'">
					<img class="List_attack" src="./images/attack3.png">
					<img class="List_hp" src="./images/hp.png">
					<span class="list_attack_point">${i.attack}</span>
					<span class="list_hp_point">${i.defense}</span>
					<span class="list_name">${i.card_name}</span>
				</li>
				</c:forEach>
			</ul>
		</div>
	</section>
	<%@include file="footer.jsp" %>
</body>
</html>