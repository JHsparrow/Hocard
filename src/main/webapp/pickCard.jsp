<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>카드 관리시스템 - 카드뽑기</title>
<link rel="stylesheet" href="style.css">
</head>
<body>
	<%@include file="topmenu.jsp" %>
	<section class="index_sec">
		<div class="pick_con">
			<ul>
				<li class="border_L"><p class="pick_cno c1 ">레전드 2%</p></li>
				<li class="border_U"><p class="pick_cno c2">유니크 8%</p></li>
				<li class="border_R"><p class="pick_cno c3">레어 35%</p></li>
				<li class="border_N"><p class="pick_cno c4">노말 55%</p></li>
			</ul>
			<div class="con_pickbtn">
				<button id="pick1" class="pick_btn">카드 1장뽑기</button>
				<button id="pick3" class="pick_btn">카드 3장뽑기</button>
			</div>
			<ul>
			<c:forEach var="i" items="${pickList}" varStatus="status">
				<c:set var="rarity" value="${i.rarity}"></c:set>
				<li class="border_<c:out value="${rarity}" />">
					<img class="List_attack" src="./images/attack3.png">
					<img class="List_hp" src="./images/hp.png">
					<span class="list_attack_point">${i.attack}</span>
					<span class="list_hp_point">${i.hp}</span>
					<span class="list_name">${i.card_name}</span>
				</li>
			</c:forEach>
			</ul>
			
			
			<form id="frm" method="post" action="pickcard">
				<input id="pick_no" type="hidden" name="pick_no" />
			</form>
		</div>
	</section>
	<script>
		$('#pick1').click(function(){
			$('#pick_no').val('1');
			$('#frm').submit();
			
		})
		$('#pick3').click(function(){
			$('#pick_no').val('3');
			$('#frm').submit();
		})
	</script>
	<%@include file="footer.jsp" %>
</body>
</html>