<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>카드 관리시스템 - 카드수정</title>
<link rel="stylesheet" href="style.css">
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Jua&display=swap" rel="stylesheet">
</head>
<body>
	<%@include file="topmenu.jsp" %>
	<section>
		<form name="frm" action="modify" method="post">
			<div class="view_con">
				<div id="view_border" class="view_border_<c:out value="${card.rarity}" />">
				<input type="hidden" name="card_no" value="${card.card_no}" />
					<img class="view_attack" src="./images/attack3.png">
					<img class="view_hp" src="./images/hp.png">
					<span class="view_attack_point"><input id="mod_attack" class="mod_input" name="attack" type="text" value="${card.attack}" onkeyup="AHCheck()" maxlength="1"/></span>
					<span class="view_hp_point"><input id="mod_hp" class="mod_input" name="hp" type="text" min='1' max='9' value="${card.hp}" onkeyup="AHCheck()" maxlength="1"/></span>
					<p class="view_name"><input class="mod_name" name="card_name" type="text" onkeyup="nameCheck()" value="${card.card_name}" maxlength='6' placeholder="카드이름을 입력하세요(6자)" /></p>
					<div class="view_desc">
						<textarea class="mod_desc" name="description" placeholder="설명을 입력하세요&#13;&#10;(생략가능)">${card.description}</textarea>
					</div>	
					<select class="view_rarity" name="rarity" onchange="borderChange()" >
						<option <c:if test="${card.rarity eq 'L'}">selected</c:if> value="L" >레전드</option>
						<option <c:if test="${card.rarity eq 'U'}">selected</c:if> value="U">유니크</option>
						<option <c:if test="${card.rarity eq 'R'}">selected</c:if> value="R">레어</option>
						<option <c:if test="${card.rarity eq 'N'}">selected</c:if> value="N">노말</option>
					</select>
				</div>
				<div>
					<p class="view_btn" onclick="mod_sub()">수&nbsp;&nbsp;&nbsp;정</p>
					<p class="view_btn" onclick="location.href='viewcard?card_no=${card.card_no}'">돌아가기</p>
				</div>
			</div>
		</form>
	</section>
	<script>
	function mod_sub(){
		document.frm.submit();
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
		if($('.view_rarity').val()=='L'){
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