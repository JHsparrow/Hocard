<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>카드 관리시스템 - 카드추가</title>
<link rel="stylesheet" href="style.css">
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Jua&display=swap" rel="stylesheet">
</head>
<body>
	<%@include file="topmenu.jsp" %>
	<section class="hg100">
		<form name="frm" action="insert" method="post">
			<div class="view_con">
				<div id="view_border" class="view_border_M">
				<input type="hidden" name="card_no" />
					<img class="view_attack" src="./images/attack3.png">
					<img class="view_hp" src="./images/hp.png">
					<span class="view_attack_point"><input id="mod_attack" class="mod_input" name="attack" type="text" onkeyup="AHCheck()" size=1 maxlength='1' /></span>
					<span class="view_hp_point"><input id="mod_hp" class="mod_input" name="hp" type="text" onkeyup="AHCheck()" size=1 maxlength='1' /></span>
					<p class="view_name"><input class="mod_name" name="card_name" type="text" onkeyup="nameCheck()" maxlength='6' placeholder="카드이름을 입력하세요(6자)" /></p>
					<div class="view_desc">
						<textarea style="overflow:hidden" class="mod_desc" name="description" placeholder="설명을 입력하세요&#13;&#10;(생략가능)"></textarea>
					</div>	
					<select class="view_rarity" name="rarity" onchange="borderChange()" >
						<option value="M">희귀도 선택</option>
						<option value="L" >레전드</option>
						<option value="U">유니크</option>
						<option value="R">레어</option>
						<option value="N">노말</option>
					</select>
				</div>
				<div class="con_btn">
					<p class="view_btn" onclick="frm_submit()">등&nbsp;&nbsp;&nbsp;록</p>
					<p class="view_btn" onclick="location.href='index.jsp'">돌아가기</p>
				</div>
			</div>
		</form>
	</section>
	<script>
		function frm_submit(){
			var f = document.frm;
			if (f.rarity.value == 'M') {
				alert("희귀도를 선택해주세요.");
				f.rarity.focus();
				return false;
			}
			if (f.card_name.value == '') {
				alert("카드이름을 입력해주세요.");
				f.card_name.focus();
				return false;
			}
			if (f.attack.value == '') {
				alert("공격력을 입력해주세요(1~9)");
				f.attack.focus();
				return false;
			}
			if (f.hp.value == '') {
				alert("체력을 입력해주세요(1~9)");
				f.hp.focus();
				return false;
			}
			
			f.submit();
		}
		function nameCheck(){
			var Nlength = $('.mod_name').val().length;
			if (Nlength >= 7) {
				alert('이름은 6글자 이내로 작성해주십시오.');
				$('.mod_name').val().slice(0,-1);
			}
		}
		function AHCheck(){
			var Alength = $('#mod_attack').val().length;
			var Hlength = $('#mod_hp').val().length;
			$('#mod_attack').val($('#mod_attack').val().replace(/[^0-9]/g,""));
			$('#mod_hp').val($('#mod_hp').val().replace(/[^0-9]/g,""));
			if (Alength >= 2) {
				alert('한자리만 가능합니다');
				$('#mod_attack').val($('#mod_attack').val().slice(0,-1));
			}
			if (Hlength >= 2) {
				alert('한자리만 가능합니다');
				$('#mod_hp').val($('#mod_hp').val().slice(0,-1));
				$(this).val($(this).val().replace(/[^0-9]/g,""));
			}
		}	
		function borderChange(){
			if($('.view_rarity').val()=='M'){
				$('#view_border').attr('class','view_border_M');
			}
			else if($('.view_rarity').val()=='L'){
				$('#view_border').attr('class','view_border_L');
			}
			else if($('.view_rarity').val()=='U'){
				$('#view_border').attr('class','view_border_U');
			}
			else if($('.view_rarity').val()=='R'){
				$('#view_border').attr('class','view_border_R');
			}
			else if($('.view_rarity').val()=='N'){
				$('#view_border').attr('class','view_border_N');
			}
		}
	</script>
	<%@include file="footer.jsp" %>
</body>
</html>