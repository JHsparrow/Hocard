<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.sql.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="style.css">
</head>
<body>
	<%@ include file="topmenu.jsp"%>
	<section>
		<div class="title">홈쇼핑 회원관리 프로그램</div>
		<div class="wrapper">
			<table>
				<tr>
					<th>회원번호</th>
					<th>회원성명</th>
					<th>전화번호</th>
					<th>주소</th>
					<th>가입일자</th>
					<th>고객등급</th>
					<th>거주지역</th>
				</tr>
				<%
				try {
					Class.forName("oracle.jdbc.OracleDriver");
					Connection con = DriverManager.getConnection("jdbc:oracle:thin:@//localhost:1521/xe", "system", "sys1234");
					Statement stmt = con.createStatement();
					
					String sql = "SELECT custno, custname, phone, address, TO_CHAR(joindate,'YYYY-MM-DD') joindate,";
					sql += "DECODE(grade,'A','VIP','B','일반','직원') grade, city FROM member_tbl_02 ORDER BY custno";
					
					ResultSet rs = stmt.executeQuery(sql);
					while (rs.next()) {
				%>
				<tr>
					<td><a href="modify.jsp?custno=<%=rs.getInt(1)%>"><%=rs.getInt(1)%></a></td>
					<td><%=rs.getString(2)%></td>
					<td><%=rs.getString(3)%></td>
					<td><%=rs.getString(4)%></td>
					<td><%=rs.getString(5)%></td>
					<td><%=rs.getString(6)%></td>
					<td><%=rs.getString(7)%></td>
				</tr>
				<%
					} //end of while()
						
					con.close();
					stmt.close();
					
				} catch (Exception e) {

				}
				%>
			</table>
		</div>
	</section>
	<%@ include file="footer.jsp"%>
</body>
</html>