<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>호희왕 - 카드뽑기</title>
<link rel="stylesheet" href="style.css">
</head>
<body>
	<%@include file="topmenu.jsp" %>
	<section>
		<form name="frm" action="insert">
			<p class="cardForm">
				<input class="attack" type="text" name="attack" size="2"> 
				<input class="defense" type="text" name="defense" size="2">
				<label class="L_attact">공격력</label>
				<label class="L_defense">방어력</label>
				<select class="card_sel" name="rarity">
					<option>희귀도 선택</option>
					<option value="L">전설</option>
					<option value="U">유니크</option>
					<option value="R">희귀</option>
					<option value="N">일반</option>
				</select> 
				<label class="L_name">카드이름</label>
				<input class="name" type="text" name="card_name">
				<input type="button" onclick="frm_submit()" value="카드등록" > 
			</p>
		</form>
	</section>
	<%@include file="footer.jsp" %>
	<script>
		function frm_submit(){
			var f = document.frm;
			if (f.rarity.value == '') {
				alert("희귀도를 선택해주세요.");
				return false;
			}
			if (f.card_name.value == '') {
				alert("카드이름을 입력해주세요.");
				return false;
			}
			
			f.submit();
		}
	</script>
</body>
</html>