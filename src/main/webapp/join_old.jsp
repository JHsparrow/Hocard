<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>카드 관리시스템 - 회원가입</title>
<link rel="stylesheet" href="style.css">
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Jua&display=swap" rel="stylesheet">
</head>
<body>
	<%@include file="topmenu.jsp" %>
	<section class="vh-100 bg-image">
  		<div class="mask d-flex align-items-center h-100 gradient-custom-3">
    		<div class="container h-100">
      			<div class="row d-flex justify-content-center align-items-center h-100">
        			<div class="col-12 col-md-9 col-lg-7 col-xl-6">
         				<div class="card" style="border-radius: 15px;">
            				<div class="card-body p-5">
              					<h2 class="text-uppercase text-center mb-5">회원가입</h2>
              					<form>
					                <div class="form-outline mb-4">
                  						<span class="input-group-text col-3" id="basic-addon1">아이디</span>
					 					<input type="text" class="form-control col-4" id="userid" name="user_id" placeholder="아이디를 입력하세요" aria-label="Userid" aria-describedby="basic-addon1" maxlength="15">
                					</div>

					                <div class="form-outline mb-4">
					                  <input type="email" id="form3Example3cg" class="form-control form-control-lg" />
					                  <label class="form-label" for="form3Example3cg">Your Email</label>
					                </div>
		
					                <div class="form-outline mb-4">
					                  <input type="password" id="form3Example4cg" class="form-control form-control-lg" />
					                  <label class="form-label" for="form3Example4cg">Password</label>
					                </div>
		
					                <div class="form-outline mb-4">
					                  <input type="password" id="form3Example4cdg" class="form-control form-control-lg" />
					                  <label class="form-label" for="form3Example4cdg">Repeat your password</label>
					                </div>
					
					                <div class="form-check d-flex justify-content-center mb-5">
					                  <input class="form-check-input me-2" type="checkbox" value="" id="form2Example3cg" />
					                  <label class="form-check-label" for="form2Example3g">
					                    I agree all statements in <a href="#!" class="text-body"><u>Terms of service</u></a>
					                  </label>
					                </div>
					
					                <div class="d-flex justify-content-center">
					                  <button type="button"
					                    class="btn btn-success btn-block btn-lg gradient-custom-4 text-body">Register</button>
					                </div>
					
					                <p class="text-center text-muted mt-5 mb-0">Have already an account? <a href="#!"
					                    class="fw-bold text-body"><u>Login here</u></a></p>

				             </form>
				            </div>
				          </div>
				        </div>
				      </div>
				    </div>
				  </div>
</section>
	<script>
		function frm_submit(){
			var eng = /^[a-zA-Z]*$/; 
			if ($('[name=Userid]').val()=="") {
				alert("아이디를 입력해주세요.");
				$('[name=Userid]').focus();
				return false;
			}
			
			if ($('[name=UName]').val()=="") {
				alert("이름을 입력해주세요.");
				$('[name=UName]').focus();
				return false;
			}
			if ($('[name=UPassword]').val()=="") {
				alert("비밀번호를 입력해주세요.");
				$('[name=UPassword]').focus();
				return false;
			}
			if ($('[name=UPassword]').val()!=$('#pwd_C').val()) {
				alert("비밀번호를 확인 부탁드립니다.");
				$('#pwd_C').focus();
				return false;
			}
			$('#frm').submit();
		}
		
		function nameCheck(){
			var Nlength = $('.mod_name').val().length;
			if (Nlength >= 7) {
				alert('이름은 6글자 이내로 작성해주십시오.');
				$('.mod_name').val().slice(0,-1);
			}
		}
		$(function(){
			$('#pwd_Y').hide();
			$('#pwd_N').hide();
		})
		function check_pwd(){
			var pwdO = $('#pwd_O').val();
			var pwdC = $('#pwd_C').val();
			
			if(pwdO==pwdC){
				$('#pwd_Y').show();
				$('#pwd_N').hide();
			}else {
				$('#pwd_Y').hide();
				$('#pwd_N').show();
			}
		}
	</script>
	<%@include file="footer.jsp" %>
</body>
</html>