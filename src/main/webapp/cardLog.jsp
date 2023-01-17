<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="totalpage" value="${totalpage}"></c:set>
<c:set var="page" value="10"></c:set>
<%
	int totalpage = (int)(pageContext.getAttribute("totalpage"));
	int nowPage = Integer.parseInt(request.getParameter("pageno"));
%>
<c:set var="Clog" value="${fn:length(cardLog)}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>카드 관리시스템 - 카드로그</title>
<link rel="stylesheet" href="style.css">
</head>
<body class="log_body">
	<%@include file="topmenu.jsp" %>
	<section class="log_sec">
		<div class="log_con">
			<h2>카드 로그 목록</h2>
			<table class="table table-dark  table-striped-columns table-hover">
				<c:if test="${Clog > 0}">
				<tr>
					<th>순서</th>
					<th>카드넘버</th>
					<th>카드이름</th>
					<th>구분</th>
					<th>일자</th>
				</tr>
				</c:if>
				<c:choose>
					<c:when test="${Clog eq 0}">
						<p class="norow">현재 등록된 로그가 없습니다.</p>
					</c:when>
					<c:otherwise>
						<c:forEach var="i" items="${cardLog}" begin="${((param.pageno-1)*10)}" end="${((param.pageno-1)*10)+page-1}" varStatus="status">
						<tr>
							<td>${status.index+1}</td>
							<td>${i.card_no}</td>
							<td>${i.card_name}</td>
							<td>${i.gubun }</td>
							<td>${i.reg_date}</td>
						</tr>
						</c:forEach>
					</c:otherwise>
				</c:choose>
			</table>
			<c:if test="${Clog > 0}">
			<div class="pageing">
				<nav aria-label="Page navigation example">
					<ul class="pagination pagination-dark justify-content-center">
						<c:choose>
							<c:when test="${param.pageno!=1}">
								<li class="page-item page-item-success "><a class="page-link" href="cardlog?pageno=${param.pageno-1}" aria-label="Previous">&lt;</a></li>
							</c:when>
							<c:otherwise>
								<li class="page-item page-item-success"><a class="page-link disabled"  aria-label="Previous">&lt;</a></li>
							</c:otherwise>
						</c:choose>
				        <%for(int i=1; i<=totalpage; i++) {%>
				        	<li class="page-item"><a class="page-link <%if(nowPage==i) out.print("active"); %> " href="cardlog?pageno=<%=i%>" class="num"><%=i%></a></li>
				        <%} %>
				        <c:choose>
							<c:when test="${param.pageno!=totalpage}">
								<li class="page-item"><a class="page-link" href="cardlog?pageno=${param.pageno+1}" class="bt next">&gt;</a></li>
							</c:when>
							<c:otherwise>
								<li class="page-item"><a class="page-link disabled" href="#" class="bt next">&gt;</a></li>
							</c:otherwise>
						</c:choose>
					</ul>
				</nav>
	      	</div>
	        </c:if>
      	</div>
	</section>
	<%@include file="footer.jsp" %>
</body>
</html>