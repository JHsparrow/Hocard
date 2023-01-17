package DAO;

import java.sql.*;
import java.util.ArrayList;

import DTO.*;

public class HocardDAO {
	Connection conn = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	
	final String JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";
	final String JDBC_URL = "jdbc:oracle:thin:@localhost:1521:xe";
	
	public Connection open() {
		Connection conn = null;
		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(JDBC_URL, "Hsparrow", "hsparrow1234");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	public ArrayList<PickCard> PickCardTemp(String userid) throws Exception {
		Connection conn = open();
		ArrayList<PickCard> PickList = new ArrayList<>();
		
		String sql = "select a.card_no,b.card_name,b.rarity,b.attack,b.hp FROM pickcard_temp A inner join card_list B on a.card_no = b.card_no where userid = '"+userid+"'";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();		
		
		try (conn; pstmt; rs) {
			
			while (rs.next()) {
				PickCard pc = new PickCard();
				pc.setCard_no(rs.getInt(1));
				pc.setCard_name(rs.getString(2));
				pc.setRarity(rs.getString(3));
				pc.setAttack(rs.getInt(4));
				pc.setHp(rs.getInt(5));
				PickList.add(pc);
			}
			
			return PickList;
		}
	}
	
	public ArrayList<CardList> PickCardList() throws Exception {
		Connection conn = open();
		ArrayList<CardList> CardList = new ArrayList<>();
		
		String sql = "select card_no,card_name,rarity,attack,hp from card_list";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();		
		
		try (conn; pstmt; rs) {
			while (rs.next()) {
				CardList cl = new CardList();
				cl.setCard_no(rs.getInt(1));
				cl.setCard_name(rs.getString(2));
				cl.setRarity(rs.getString(3));
				cl.setAttack(rs.getInt(4));
				cl.setHp(rs.getInt(5));
				CardList.add(cl);
			}
			return CardList;
		}
	}
	
	public String PickCard(String pickno, String userid) throws Exception {
		
		int percentage = (int)(Math.random()*100);
		int card_no = 0;
		String sql;
		Connection conn = open();
		
		if (percentage <= 2) {
			sql = "select card_no from( select * from card_list order by DBMS_RANDOM.RANDOM) where rownum = 1 and rarity = 'L'";
		}
		else if(percentage <= 8) {
			sql = "select card_no from( select * from card_list order by DBMS_RANDOM.RANDOM) where rownum = 1 and rarity = 'U'";
		}
		else if(percentage <= 35) {
			sql = "select card_no from( select * from card_list order by DBMS_RANDOM.RANDOM) where rownum = 1 and rarity = 'R'";
		}
		else{
			sql = "select card_no from( select * from card_list order by DBMS_RANDOM.RANDOM) where rownum = 1 and rarity = 'N'";
		}
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		
		try (pstmt; rs) {
			if (rs.next()) {
				card_no = rs.getInt(1);
			}
		}
		
		String sql2 = "insert into pickcard_temp (PICK_NO,USERID,CARD_NO) values (pickcard_seq.nextval, ?, ?)";
		PreparedStatement ps1 = conn.prepareStatement(sql2);
		try (ps1;){
			ps1.setString(1, userid);
			ps1.setInt(2, card_no);
			ps1.executeUpdate();
		}
		
		String sql3 = "insert into pickcard (PICK_NO,USERID,CARD_NO) values (pickcard_seq.nextval, ?, ?)";
		PreparedStatement ps2 = conn.prepareStatement(sql3);
		try (ps2;){
			ps2.setString(1, userid);
			ps2.setInt(2, card_no);
			ps2.executeUpdate();
		}
		
		return "pickCard.jsp";
	}
	
	
	public void deleteTemp() throws Exception {
		Connection conn = open();
		String sql = "delete from pickcard_temp";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		try (conn; pstmt) {
			pstmt.executeUpdate();
		}
	}
	
	public int insertCard(AddCard ac) throws Exception {
		Connection conn = open();

		String sql = "insert into card_list (card_no, card_name, rarity, attack, hp, description, reg_date) values(CARDLIST_SEQ.nextval, ?, ?, ?, ?, ?, sysdate)";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		int result = 0;
		try (conn; pstmt) {
			pstmt.setString(1, ac.getCard_name());
			pstmt.setString(2, ac.getRarity());
			pstmt.setInt(3, ac.getAttack());
			pstmt.setInt(4, ac.getHp());
			pstmt.setString(5, ac.getDescription());
			result = pstmt.executeUpdate();
		}
		return result;
	}
	
	public void insertCardLog(int card_no, String Gubun) throws Exception {
		Connection conn = open();
		if (Gubun == "C") {
			String sql = "select max(card_no) from card_list";
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			try (pstmt; rs) {
				if (rs.next()) {				
					card_no = rs.getInt(1);
				}
			}
		}
		String sql = "insert into card_log values (CARDLOG_SEQ.nextval,?,?,sysdate)";
		PreparedStatement pstmt = conn.prepareStatement(sql);

		try (conn; pstmt) {
			pstmt.setInt(1, card_no);
			pstmt.setString(2, Gubun);
			pstmt.executeUpdate();
		}
	}
	
	public ArrayList<CardList> getCardList(String fl_rt, String fl_cardno ,String srt_rarity) throws Exception {
		Connection conn = open();
		ArrayList<CardList> CardList = new ArrayList<>();
		
		if (fl_rt == null) fl_rt = "none";
		if (fl_cardno == null) fl_cardno = "none";
		if (srt_rarity == null) srt_rarity = "none";
		
		String sql = "select card_no,card_name,rarity,attack,hp from card_list";
		
		if (!srt_rarity.equals("none")) {
			sql += " where rarity = '"+srt_rarity+"' ";
		}
		
		if(!fl_rt.equals("none")&&!fl_cardno.equals("none")) {
			sql += " order by ";
			if (fl_rt.equals("up")) {
				sql += " decode(rarity,'L',1,'U',2,'R',3,'N',4) asc";
			}
			else if (fl_rt.equals("down")) {
				sql += " decode(rarity,'L',1,'U',2,'R',3,'N',4) desc";
			}
			
			if (fl_cardno.equals("up")) {
				sql += " ,card_no asc";
			}
			else if (fl_cardno.equals("down")) {
				sql += " ,card_no desc";
			}
			
		}
		else {
			if (fl_rt.equals("up")) {
				sql += " order by decode(rarity,'L',1,'U',2,'R',3,'N',4) asc";
			}
			else if (fl_rt.equals("down")) {
				sql += " order by decode(rarity,'L',1,'U',2,'R',3,'N',4) desc";
			}
			
			if (fl_cardno.equals("up")) {
				sql += " order by card_no asc";
			}
			else if (fl_cardno.equals("down")) {
				sql += " order by card_no desc";
			}
		}
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();		
		
		try (conn; pstmt; rs) {
			while (rs.next()) {
				CardList cl = new CardList();
				cl.setCard_no(rs.getInt(1));
				cl.setCard_name(rs.getString(2));
				cl.setRarity(rs.getString(3));
				cl.setAttack(rs.getInt(4));
				cl.setHp(rs.getInt(5));
				CardList.add(cl);
			}
			return CardList;
		}
		
	}
	
	public CardList ViewCard(int card_no) throws Exception {
		CardList cl = new CardList();
		Connection conn = open();
		
		String sql = "select card_no,card_name,rarity,attack,hp,description from card_list where card_no = "+card_no;
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		try (conn; pstmt; rs) {
			if (rs.next()) {				
				cl.setCard_no(rs.getInt(1));
				cl.setCard_name(rs.getString(2));
				cl.setRarity(rs.getString(3));
				cl.setAttack(rs.getInt(4));
				cl.setHp(rs.getInt(5));
				cl.setDescription(rs.getString(6));
			}
			return cl;
		}
	}
	
	public CardView ViewforModi(int card_no) throws Exception {
		CardView cl = new CardView();
		Connection conn = open();
		
		String sql = "select card_no,card_name,rarity,attack,hp,description from card_list where card_no = "+card_no;
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();		
		
		try (conn; pstmt; rs) {
			if (rs.next()) {				
				cl.setCard_no(rs.getInt(1));
				cl.setCard_name(rs.getString(2));
				cl.setRarity(rs.getString(3));
				cl.setAttack(rs.getInt(4));
				cl.setHp(rs.getInt(5));
				cl.setDescription(rs.getString(6));
			}
			return cl;
		}
	}
	
	public String CheckPickCard(String userid, int cardno) throws Exception {
		
		Connection conn = open();
		String result;
		String sql = "select pick_no from pickcard where card_no = " + cardno;
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		
		try (conn; pstmt;) {
			if (rs.next()) {				
				result = "Y";
			}
			else result = "N";
			System.out.println(sql);
			System.out.println("유저아이디 : "+userid);
			System.out.println("카드넘버 : "+cardno);
			System.out.println("결과 : "+result);
			
			return result;
		}
	}
	
	public int ModifyCard(ModifyCard mc) throws Exception {
		Connection conn = open();
		int result = 0;
		String sql = "update card_list set card_name = ?, rarity = ?, attack = ? , hp = ? ";
		if (mc.getDescription() != null ) {
			sql += " ,description = ? ";
		}
		sql += " where card_no = ? ";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		try (conn; pstmt) {
			pstmt.setString(1, mc.getCard_name());
			pstmt.setString(2, mc.getRarity());
			pstmt.setInt(3, mc.getAttack());
			pstmt.setInt(4, mc.getHp());
			if (mc.getDescription() != null ) {
				pstmt.setString(5, mc.getDescription());
				pstmt.setInt(6, mc.getCard_no());
			}
			else {
				pstmt.setInt(5, mc.getCard_no());
			}
			result = pstmt.executeUpdate();	
		}	
		return result;
	}
	public int DeleteCard(int card_no) throws Exception {
		Connection conn = open();
		int result = 0;
		String sql = "Delete from card_list where card_no = "+card_no ;
		String sql2 = "Delete from pickcard where card_no = "+card_no ;
		String sql3 = "Delete from pickcard_temp where card_no = "+card_no ;
		PreparedStatement pstmt = conn.prepareStatement(sql);
		PreparedStatement pstmt2 = conn.prepareStatement(sql2);
		PreparedStatement pstmt3 = conn.prepareStatement(sql3);
		
		try (conn; pstmt; pstmt2; pstmt3;) {
			pstmt2.executeUpdate();
			pstmt3.executeUpdate();
			result = pstmt.executeUpdate();
		}
		return result; 
	}	
	
	public int DelMyCard(int pick_no) throws Exception {
		Connection conn = open();
		int result = 0;
		String sql = "Delete from pickcard where pick_no = "+pick_no ;
		PreparedStatement pstmt = conn.prepareStatement(sql);
		try (conn; pstmt ) {
			result = pstmt.executeUpdate();
		}
		return result; 
	}
	
	public void DeleteMyCard(int card_no) throws Exception {
		Connection conn = open();
		String sql2 = "Delete from pickcard where card_no = "+card_no ;
		String sql3 = "Delete from pickcard_temp where card_no = "+card_no ;
		
		PreparedStatement pstmt2 = conn.prepareStatement(sql2);
		PreparedStatement pstmt3 = conn.prepareStatement(sql3);
		
		try (conn; pstmt2; pstmt3) {
			pstmt2.executeUpdate();
			pstmt3.executeUpdate();
		}
	}
	
	public void insertDelCard(AddCard ac, int card_no) throws Exception {
		Connection conn = open();
		String card_name = null;
		String rarity = null;
		int attack = 0;
		int hp = 0;
		String sql = "select card_name, rarity, attack,hp from card_list where card_no = "+card_no;
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		try (pstmt; rs) {
			if (rs.next()) {				
				card_name = rs.getString(1);
				rarity = rs.getString(2);
				attack = rs.getInt(3);
				hp = rs.getInt(4);
			}
		}

		String sql2 = "insert into card_del_list (card_no, card_name, rarity, attack, hp, reg_date) values(?, ?, ?, ?, ?, sysdate)";
		PreparedStatement ps = conn.prepareStatement(sql2);

		try (conn; ps) {
			ps.setInt(1, card_no);
			ps.setString(2, card_name);
			ps.setString(3, rarity);
			ps.setInt(4, attack);
			ps.setInt(5, hp);
			ps.executeUpdate();
		}
	}
	
	
	public ArrayList<CardLog> getCardLog() throws Exception {
		Connection conn = open();
		ArrayList<CardLog> CardList = new ArrayList<>();

		String sql = "select A.log_no,A.card_no, "
				+ "    case when b.card_name is null then (select card_name from card_del_list where card_no = a.card_no) else b.card_name end "
				+ "    ,decode(A.gubun,'C','추가','U','수정','D','삭제'),to_char(A.reg_date,'yyyy.mm.dd') from card_log A "
				+ "    left outer join card_list B on a.card_no = b.card_no order by a.reg_date desc";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();		
		try (conn; pstmt; rs) {
			while (rs.next()) {
				CardLog cl = new CardLog();
				cl.setLog_no(rs.getInt(1));
				cl.setCard_no(rs.getInt(2));
				cl.setCard_name(rs.getString(3));
				cl.setGubun(rs.getString(4));
				cl.setReg_date(rs.getString(5));
				CardList.add(cl);
			}
			return CardList;
		}
	}
	public int CountLoglist() throws Exception {
		Connection conn = open();
		String sql = "select count(*) from card_log A left outer join card_list B on a.card_no = b.card_no order by a.log_no";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();		
		try (conn; pstmt; rs) {
			if(rs.next()) {
				return rs.getInt(1);
			}
			else {return rs.getInt(1);}
		}
	}
	
	public IndexView ViewIndex() throws Exception {
		IndexView iv = new IndexView();
		Connection conn = open();
		
		String sql = "select count(case when rarity = 'L' then 1 end), count(case when rarity = 'U' then 1 end), count(case when rarity = 'R' then 1 end),count(case when rarity = 'N' then 1 end) from card_list";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		try (conn; pstmt; rs) {
			if (rs.next()) {	
				iv.setcLegend(rs.getInt(1));
				iv.setcUnique(rs.getInt(2));
				iv.setcRare(rs.getInt(3));
				iv.setcNomal(rs.getInt(4));
			}
			return iv;
		}
	}
	
	public ArrayList<CardList> MyCardList(String fl_rt, String fl_pickno ,String srt_rarity, String userid) throws Exception {
		if (fl_rt == null) fl_rt = "none";
		if (fl_pickno == null) fl_pickno = "none";
		if (srt_rarity == null) srt_rarity = "none";
		
		Connection conn = open();
		ArrayList<CardList> CardList = new ArrayList<>();
		
		String sql = "select a.card_no,b.card_name,b.rarity,b.attack,b.hp,a.pick_no FROM pickcard A inner join card_list B on a.card_no = b.card_no ";
		sql += " where userid='"+userid+"' ";
		if (!srt_rarity.equals("none")) {
			sql += " and b.rarity = '"+srt_rarity+"' ";
		}
		
		if(!fl_rt.equals("none")&&!fl_pickno.equals("none")) {
			sql += " order by ";
			if (fl_rt.equals("up")) {
				sql += " decode(b.rarity,'L',1,'U',2,'R',3,'N',4) asc";
			}
			else if (fl_rt.equals("down")) {
				sql += " decode(b.rarity,'L',1,'U',2,'R',3,'N',4) desc";
			}
			
			if (fl_pickno.equals("up")) {
				sql += " ,pick_no asc";
			}
			else if (fl_pickno.equals("down")) {
				sql += " ,pick_no desc";
			}
			
		}
		else {
			if (fl_rt.equals("up")) {
				sql += "order by decode(b.rarity,'L',1,'U',2,'R',3,'N',4) asc";
			}
			else if (fl_rt.equals("down")) {
				sql += "order by decode(b.rarity,'L',1,'U',2,'R',3,'N',4) desc";
			}
			
			if (fl_pickno.equals("up")) {
				sql += "order by pick_no asc";
			}
			else if (fl_pickno.equals("down")) {
				sql += "order by pick_no desc";
			}
		}
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();		
		
		try (conn; pstmt; rs) {
			while (rs.next()) {
				CardList cl = new CardList();
				cl.setCard_no(rs.getInt(1));
				cl.setCard_name(rs.getString(2));
				cl.setRarity(rs.getString(3));
				cl.setAttack(rs.getInt(4));
				cl.setHp(rs.getInt(5));
				cl.setPick_no(rs.getInt(6));
				CardList.add(cl);
			}
			return CardList;
		}
	}
	
	public CardView ViewMyCard(int pick_no) throws Exception {
		CardView cv = new CardView();
		Connection conn = open();
		
		String sql = "select b.card_no,b.card_name,b.rarity,b.attack,b.hp,b.description,a.pick_no from pickcard A inner join card_list B on a.card_no = b.card_no where a.pick_no = "+pick_no;
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();		
		
		try (conn; pstmt; rs) {
			if (rs.next()) {				
				cv.setCard_no(rs.getInt(1));
				cv.setCard_name(rs.getString(2));
				cv.setRarity(rs.getString(3));
				cv.setAttack(rs.getInt(4));
				cv.setHp(rs.getInt(5));
				cv.setDescription(rs.getString(6));
				cv.setPick_no(rs.getInt(7));
			}
			return cv;
		}
	}
	
	public int LoginResult(Login login) throws Exception {
		
		Connection conn = open();
		String sql = "select userid from user_info where userid = ? and password = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		System.out.println(login.getUserid());
		try (conn; pstmt;) {
			pstmt.setString(1, login.getUserid());
			pstmt.setString(2, login.getPassword());
			ResultSet rs = pstmt.executeQuery();
			
			if (rs.next()) {	
				return 1;
			}
		}
		return 0;
	}
	
	public int joinResult(Join join) throws Exception {
		Connection conn = open();
		int dup = 0; // 1 = 중복있음, 0 = 중복 없음
		int result = 0; // 1 = 가입 성공 / 0 = 가입 실패 / 3 = 아이디 중복
		String sql1 = "select userid from user_info where userid = '"+join.getUser_id()+"'";
		PreparedStatement pstmt1 = conn.prepareStatement(sql1);
		if (rs.next()) {
			dup = 1;
			result = 3;
		}
		
		if (dup == 0) {
			String sql2 = "insert into user_info (userid,uname,password,reg_date) values(?, ?, ?, sysdate)";
			PreparedStatement pstmt2 = conn.prepareStatement(sql2);
			try (conn; pstmt2) {
				pstmt2.setString(1, join.getUser_id());
				pstmt2.setString(2, join.getUName());
				pstmt2.setString(3, join.getUPassword());
				result = pstmt2.executeUpdate();
			}
		}
		return result;
	} 
	
	public UserInfo ViewUserInfo(String userid) throws Exception {
		UserInfo ui = new UserInfo();
		Connection conn = open();
		
		String sql = "select uname,password,to_char(reg_date,'yyyy/mm/dd') from user_info where userid = '"+userid+"'";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();		
		
		try (conn; pstmt; rs) {
			if (rs.next()) {				
				ui.setUsername(rs.getString(1));
				ui.setPassword(rs.getString(2));
				ui.setRegdate(rs.getString(3));
			}
			return ui;
		}
	}
	
	public int ModifyUserInfo(Join join) throws Exception {
		Connection conn = open();
		int result = 0;
		String sql = "update user_info set uname = ?, password = ? where userid = ? ";
		
		System.out.println(join.getUName());
		System.out.println(join.getUPassword());
		System.out.println(join.getUser_id());
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		try (conn; pstmt) {
			pstmt.setString(1, join.getUName());
			pstmt.setString(2, join.getUPassword());
			pstmt.setString(3, join.getUser_id());
			result = pstmt.executeUpdate();	
		}	
		return result;
	}
}
