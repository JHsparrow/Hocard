<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<% 
	String fl_rarity = request.getParameter("fl_rarity");
	String fl_pickno = request.getParameter("fl_pickno");
	String srt_rarity = request.getParameter("srt_rarity");
	if (fl_rarity == null) fl_rarity = "none";
	if (fl_pickno == null) fl_pickno = "none";
	if (srt_rarity == null) srt_rarity = "none";
 %>
 <c:set var="Clist" value="${fn:length(cardList)}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>카드 관리시스템 - 내 카드목록</title>
<link rel="stylesheet" href="style.css">
</head>
<body>
	<%@include file="topmenu.jsp" %>
	<section <c:if test="${Clist <= 10}">class="hg100"</c:if>> 
		<div class="list_con">
			<c:choose>
				<c:when test="${Clist eq 0}">
					<p class="norow">현재 뽑은 카드가 없습니다.</p>
				</c:when>
				<c:otherwise>
				<ul>
					<c:forEach var="i" items="${cardList}" varStatus="status">
					<c:set var="rarity" value="${i.rarity}"></c:set>
					<li class="border_<c:out value="${rarity}" />" onclick="location.href='viewmycard?pick_no=${i.pick_no}'">
						<img class="List_attack" src="./images/attack3.png">
						<img class="List_hp" src="./images/hp.png">
						<span class="list_attack_point">${i.attack}</span>
						<span class="list_hp_point">${i.hp}</span>
						<span class="list_name">${i.card_name}</span>
					</li>
					</c:forEach>
				</ul>
				</c:otherwise>
			</c:choose>
		</div>
		
		<div class="flex_div">
			<div>
				<select id="fl_sel" class="srt_select" onchange="srt_rarity()">
					<option hidden>레어도</option>
					<option value="none" <%if (srt_rarity.equals("none")) out.print("selected");%>>필터 없음</option>
					<option value="L" <%if (srt_rarity.equals("L")) out.print("selected");%>>레전드</option>
					<option value="U" <%if (srt_rarity.equals("U")) out.print("selected");%>>유니크</option>
					<option value="R" <%if (srt_rarity.equals("R")) out.print("selected");%>>레어</option>
					<option value="N" <%if (srt_rarity.equals("N")) out.print("selected");%>>노말</option>
				</select>
			</div>
			<div>
				<% if(fl_rarity.equals("up")){ %>
				<button class="fl_btn blockbtn" onclick="fl_rarity('down')">레어도순 <i class="fa-solid fa-sort-up"></i></button>
				<%} else if(fl_rarity.equals("down")){ %>
				<button class="fl_btn blockbtn" onclick="fl_rarity('none')">레어도순 <i class="fa-solid fa-sort-down"></i></button>
				<%}else{ %>
				<button class="fl_btn blockbtn" onclick="fl_rarity('up')">레어도순 <i class="fa-solid fa-sort"></i></button>
				<%} %>
				
				<% if(fl_pickno.equals("up")){ %>
				<button class="fl_btn blockbtn" onclick="fl_pickno('down')">등록순 <i class="fa-solid fa-sort-up"></i></button>
				<%} else if(fl_pickno.equals("down")){ %>
				<button class="fl_btn blockbtn" onclick="fl_pickno('none')">등록순 <i class="fa-solid fa-sort-down"></i></button>
				<%}else{ %>
				<button class="fl_btn blockbtn" onclick="fl_pickno('up')">등록순 <i class="fa-solid fa-sort"></i></button>
				<%} %>
			</div>
		</div>
		
		<form id="frm" method="get" action="mycard">
			<input id="fl_rarity" type="hidden" name="fl_rarity" value="<%=fl_rarity %>" />
			<input id="fl_pickno" type="hidden" name="fl_pickno" value="<%=fl_pickno %>" />
			<input id="srt_rarity" type="hidden" name="srt_rarity" value="<%=srt_rarity %>" />
		</form>
	</section>
	<script>
		function fl_rarity(val){
			$('#fl_rarity').val(val);
			$('#frm').submit();
		}
		
		function fl_pickno(val){
			$('#fl_pickno').val(val);
			$('#frm').submit();
		}
		
		function srt_rarity(val){
			$('#srt_rarity').val($('#fl_sel').val());
			$('#frm').submit();
		}
	</script>
	<%@include file="footer.jsp" %>
</body>
</html>