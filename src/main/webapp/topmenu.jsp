<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
 <%
 Object setest =  session.getAttribute("userId");
 String Userid = (String)setest; 
 String login_flag = (String)session.getAttribute("loginFlag");
 
 
 String test = request.getServletPath();
 String Page_active = null;
 switch(test){
 case "/addCard.jsp" : Page_active = "add"; break;
 case "/cardList.jsp" : Page_active = "list"; break;
 case "/cardmodify.jsp" : Page_active = "list"; break;
 case "/cardview.jsp" : Page_active = "list"; break;
 case "/cardLog.jsp" : Page_active = "log"; break;
 case "/join.jsp" : Page_active = "join"; break;
 case "/mycardlist.jsp" : Page_active = "mycard"; break;
 case "/mycardview.jsp" : Page_active = "mycard"; break;
 case "/pickCard.jsp" : Page_active = "pick"; break;
 case "/userInfoView.jsp" : Page_active = "userinfo"; break;
 case "/userInfoModify.jsp" : Page_active = "userinfo"; break;
 } 
 	
 %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js" integrity="sha384-oBqDVmMz9ATKxIep9tiCxS/Z9fNfEXiDAYTujMAeBAsjFuCZSmKbSSUnQlmh/jp3" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.min.js" integrity="sha384-cuYeSxntonz0PPNlHhBs68uyIAVpIIOZZ5JqeqvYYIcEL727kskC66kF92t6Xl2V" crossorigin="anonymous"></script>
<script src="https://code.jquery.com/jquery-3.6.3.min.js" integrity="sha256-pvPw+upLPUjgMXY0G+8O0xUf+/Im1MZjXxxgOcBQBXU=" crossorigin="anonymous"></script>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.2.1/css/all.min.css" integrity="sha512-MV7K8+y+gLIBoVD59lQIYicR65iaqukzvf/nwasF0nqhPay5w/9lJmVM2hMDcnK1OnMGCdVK+iQrJ7lzPJQd1w==" crossorigin="anonymous" referrerpolicy="no-referrer" />
</head>
<body>
<div class="fixed-top">
	<header>
		???????????? ????????? ver 2023
	</header>
	<nav class="navbar navbar-dark bg-dark">
		<ul class="nav nav-pills col-10">
			<%if (Userid != null){ %>
				<%if (Userid.equals("admin")) {%>
				<li class="nav-item"><a class="nav-link <%if (Page_active=="add") out.print("active");%>" href="addcard">????????????</a></li>
				<li class="nav-item"><a class="nav-link <%if (Page_active=="list") out.print("active");%>" href="cardlist">????????????</a></li>
				<li class="nav-item"><a class="nav-link <%if (Page_active=="log") out.print("active");%>" href="cardlog?pageno=1">????????????</a></li>
				<%} %>
			<%} %>
			<li class="nav-item"><a class="nav-link <%if (Page_active=="pick") out.print("active");%>" href="pickpage">????????????(??????)</a></li>
			<li class="nav-item"><a class="nav-link <%if (Page_active=="mycard") out.print("active");%>" href="mycard">????????????(??????)</a></li>
			<li class="nav-item"><a class="nav-link <%if (Page_active==null) out.print("active");%>" href="index">?????????</a></li>
		</ul>
		<ul class="nav nav-pills ">
			<%if (Userid==null)  {%>
			<li class="nav-item"><a class="nav-link btnpointer" data-bs-toggle="modal" data-bs-target="#exampleModal" data-bs-whatever="@getbootstrap">?????????</a></li>
			<%}else{ %>
			<li class="nav-item"><a class="nav-link" href="logout" class="btnpointer">????????????</a></li>
			<%} %>
			
			
			<%if (Userid == null) { %>
			<li class="nav-item"><a class="nav-link <%if (Page_active=="join") out.print("active");%>" href="join">????????????</a></li>
			<%}else{%>
			<li class="nav-item"><a class="nav-link <%if (Page_active=="userinfo") out.print("active");%>" href="viewUserinfo">????????????</a></li>
			<%} %>
		</ul>
	</nav>
</div>
		<!-- Vertically centered modal -->
	<div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
	  <div class="modal-dialog modal-dialog-centered">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h1 class="modal-title fs-5" id="exampleModalLabel">?????????</h1>
	        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
	      </div>
	      <div class="modal-body">
	        <form id="frm_login" method="post" action="login">
	          <div class="mb-3">
	            <label for="userid" class="col-form-label">?????????</label>
	            <input type="text" class="form-control" id="userid" name="userid">
	          </div>
	          <div class="mb-3">
	            <label for="pwd" class="col-form-label">????????????</label>
	            <input type="password" class="form-control" id="pwd" name="password">
	          </div>
	        </form>
	      </div>
	      <div class="modal-footer modal-footer-centered">
	        <button type="button" class="btn btn-primary" onclick="send_login()">?????????</button>
	        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">??????</button>
	      </div>
	    </div>
	  </div>
	</div>
		<script>
			function modalControll(){
				$('.modal_container').toggle();
			}	
			function send_login(){
				if ($('#userid').val()==""){
					alert('???????????? ???????????????');
					$('#userid').focus();
					return false
				}
				if ($('#pwd').val()==""){
					alert('??????????????? ???????????????');
					$('#pwd').focus();
					return false
				}
				$('#frm_login').submit();
			}
			
			$(document).click(function(e){
		    	if($(e.target).hasClass('red')){
		        	$('.modal_container').hide();
		        }
		    });
			
			$(function(){
				var login_flag = '<%=login_flag%>'
				if (login_flag == "N"){
					$('.modal_container').show();
				}
			})
		</script>
		<%
       		session.setAttribute("loginFlag", "None");
       	%>
</body>
</html>