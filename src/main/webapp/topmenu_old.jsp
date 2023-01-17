<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
 <%
 Object setest =  session.getAttribute("userId");
 String wSession = (String)setest; 
 String login_flag = (String)session.getAttribute("loginFlag");
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
	<header>
		카드관리 시스템 ver 2023 <%if (wSession != null) out.print(wSession); %>
	</header>
	<nav>
		<ul>
			<li><a href="addcard">카드추가</a></li>
			<li><a href="cardlist">카드목록</a></li>
			<li><a href="cardlog?pageno=1">카드로그</a></li>
			<li><a href="pickpage">카드뽑기(유저)</a></li>
			<li><a href="mycard">뽑은카드(유저)</a></li>
			<li><a href="index">홈으로</a></li>
			<%if (wSession==null)  {%>
			<li><a class="btnpointer" onclick="modalControll()"><button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#exampleModal" data-bs-whatever="@getbootstrap">로그인</button></a></li>
			<%}else{ %>
			<li><a href="logout" class="btnpointer">로그아웃</a></li>
			<%} %>
			<li><a href="join">회원가입</a></li>
			
		</ul>
	</nav>
		<!-- <div class="modal_container">
			<div class="modal_login">
				<form id="frm_login" method="post" action="login">
					<table class="modal_table nomodal">
						<tr class="nomodal">
							<th class="nomodal" ><label for="txt_id" >아이디</label></th>
							<td class="nomodal"><input type="text" id="txt_id" name="userid" /></td>
						</tr>
						<tr class="nomodal">
							<th class="nomodal"><label for="txt_pwd" >비밀번호</label></th>
							<td class="nomodal"><input type="password" id="txt_pwd" name="password" /></td>
						</tr>
					</table>
					<button class="modal_btn nomodal" type="button" onclick="send_login()">로그인</button>
					<button class="modal_btn red" type="button">취소</button>
				</form>
			</div>
		</div>-->
		
		<!-- Vertically centered modal -->
	<div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
	  <div class="modal-dialog">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h1 class="modal-title fs-5" id="exampleModalLabel">로그인</h1>
	        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
	      </div>
	      <div class="modal-body">
	        <form id="frm_login" method="post" action="login">
	          <div class="mb-3">
	            <label for="userid" class="col-form-label">아이디</label>
	            <input type="text" class="form-control" id="userid" name="userid">
	          </div>
	          <div class="mb-3">
	            <label for="pwd" class="col-form-label">비밀번호</label>
	            <input type="text" class="form-control" id="pwd" name="password">
	          </div>
	        </form>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
	        <button type="button" class="btn btn-primary">Send message</button>
	      </div>
	    </div>
	  </div>
	</div>
		<script>
			function modalControll(){
				$('.modal_container').toggle();
			}	
			function send_login(){
				if ($('#txt_id').val()==""){
					alert('아이디를 입력하시오');
					$('#txt_id').focus();
					return false
				}
				if ($('#txt_pwd').val()==""){
					alert('비밀번호를 입력하시오');
					$('#txt_pwd').focus();
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