<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.sql.*"%>
<%@ page import="DTO.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>카드 관리시스템 - 메인페이지</title>
<link rel="stylesheet" href="style.css">
</head>
<body>
	<%@include file="topmenu.jsp" %>
	<section class="index_sec">
		<div class="index_con">
		<%if(Userid != null) { %>
			<%if (Userid.equals("admin") ){ %>
				<h1>현재 관리중인 카드</h1>
			<%} else { %>
				<h1>현재 소유중인 카드</h1>
			<% } %>
		<%} else{ %>
			<h1>현재 관리중인 카드</h1>
		<%} %>
		<%
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@//localhost:1521/xe", "hsparrow", "hsparrow1234");
			IndexView iv = new IndexView();
			Statement stmt = con.createStatement();
			String sql;
			
			if(Userid != null) {
			
				if (Userid.equals("admin")){
					sql = "select count(case when rarity = 'L' then 1 end), count(case when rarity = 'U' then 1 end), count(case when rarity = 'R' then 1 end),count(case when rarity = 'N' then 1 end) from card_list";
				} else {
					sql = "select count(case when rarity = 'L' then 1 end), count(case when rarity = 'U' then 1 end), count(case when rarity = 'R' then 1 end),count(case when rarity = 'N' then 1 end) from PICKCARD A inner join card_list B on a.card_no = b.card_no where a.userid = '"+Userid+"'";
				}
			} else {
				sql = "select count(case when rarity = 'L' then 1 end), count(case when rarity = 'U' then 1 end), count(case when rarity = 'R' then 1 end),count(case when rarity = 'N' then 1 end) from card_list";				
			}
			
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) {
		%>
			<ul>
				<li class="border_L"><span class="c1"></span><p class="idx_cno c1">레전드 <%=rs.getString(1) %>장</p></li>
				<li class="border_U"><span class="c2"></span><p class="idx_cno c2">유니크 <%=rs.getString(2) %>장</p></li>
				<li class="border_R"><span class="c3"></span><p class="idx_cno c3">레어 <%=rs.getString(3) %>장</p></li>
				<li class="border_N"><span class="c4"></span><p class="idx_cno c4">노말 <%=rs.getString(4) %>장</p></li>
			</ul>
		</div>
		<%
			}
			con.close();
			stmt.close();
		} catch (Exception e) {
		}
		%>
	</section>
	<%@include file="footer.jsp" %>
</body>
</html>