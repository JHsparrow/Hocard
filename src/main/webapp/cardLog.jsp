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
		<table>
			<tr>
				<th>카드넘버</th>
				<th>카드이름</th>
				<th>구분</th>
				<th>일자</th>
			</tr>
			
			<c:forEach var="i" items="${cardLog}" varStatus="status">
			<tr>
				<td>${i.card_no}</td>
				<td>${i.card_name}</td>
				<td>${i.gubun }</td>
				<td>${i.reg_date}</td>
			</tr>
			</c:forEach>
			
			
		</table>
	</section>
	<%@include file="footer.jsp" %>
</body>
</html>