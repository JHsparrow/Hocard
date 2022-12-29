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
	
	public String PickCard() throws Exception {
		
		int percentage = (int)(Math.random()*100);
		String sql;
		Connection conn = open();
		
		if (percentage <= 7) {
			sql = "select board_no, title, user_id, to_char(reg_date, 'yyyy.mm.dd') reg_date, views from board order by board_no";
		}
		else if(percentage <= 13) {
			sql = "select board_no, title, user_id, to_char(reg_date, 'yyyy.mm.dd') reg_date, views from board order by board_no";
		}
		else if(percentage <= 35) {
			sql = "select board_no, title, user_id, to_char(reg_date, 'yyyy.mm.dd') reg_date, views from board order by board_no";
		}
		else{
			sql = "select board_no, title, user_id, to_char(reg_date, 'yyyy.mm.dd') reg_date, views from board order by board_no";
		}
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();	
		
		
		return "view.jsp";
	}
	
	public void insertCard(AddCard ac) throws Exception {
		Connection conn = open();

		String sql = "insert into card_list (card_no, card_name, rarity, attact,defense, reg_date) values(CARDLIST_SEQ.nextval, ?, ?, ?, ?, sysdate)";
		PreparedStatement pstmt = conn.prepareStatement(sql);

		try (conn; pstmt) {
			pstmt.setString(1, ac.getCard_name());
			pstmt.setString(2, ac.getRarity());
			pstmt.setInt(3, ac.getAttack());
			pstmt.setInt(4, ac.getDefense());
			pstmt.executeUpdate();
		}
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
	
	public ArrayList<CardList> getCardList() throws Exception {
		Connection conn = open();
		ArrayList<CardList> CardList = new ArrayList<>();
		
		String sql = "select card_no,card_name,rarity,attact,defense from card_list";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();		
		
		try (conn; pstmt; rs) {
			while (rs.next()) {
				CardList cl = new CardList();
				cl.setCard_no(rs.getInt(1));
				cl.setCard_name(rs.getString(2));
				cl.setRarity(rs.getString(3));
				cl.setAttack(rs.getInt(4));
				cl.setDefense(rs.getInt(5));
				CardList.add(cl);
			}
			return CardList;
		}
		
	}
	
	public CardList ViewCard(int card_no) throws Exception {
		CardList cl = new CardList();
		Connection conn = open();
		
		String sql = "select card_no,card_name,rarity,attact,defense from card_list where card_no = "+card_no;
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		try (conn; pstmt; rs) {
			if (rs.next()) {				
				cl.setCard_no(rs.getInt(1));
				cl.setCard_name(rs.getString(2));
				cl.setRarity(rs.getString(3));
				cl.setAttack(rs.getInt(4));
				cl.setDefense(rs.getInt(5));
			}
			return cl;
		}
	}
	
	public CardView ViewforModi(int card_no) throws Exception {
		CardView cl = new CardView();
		Connection conn = open();
		
		String sql = "select card_no,card_name,rarity,attact,defense from card_list where card_no = "+card_no;
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();		
		
		try (conn; pstmt; rs) {
			if (rs.next()) {				
				cl.setCard_no(rs.getInt(1));
				cl.setCard_name(rs.getString(2));
				cl.setRarity(rs.getString(3));
				cl.setAttack(rs.getInt(4));
				cl.setDefense(rs.getInt(5));
			}
			return cl;
		}
	}
	
	public void ModifyCard(ModifyCard mc) throws Exception {
		Connection conn = open();
		
		String sql = "update card_list set card_name = ?, rarity = ?, attact = ? , defense = ? where card_no = ? ";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		try (conn; pstmt) {
			pstmt.setString(1, mc.getCard_name());
			pstmt.setString(2, mc.getRarity());
			pstmt.setInt(3, mc.getAttack());
			pstmt.setInt(4, mc.getHp());
			pstmt.setInt(5, mc.getCard_no());
			pstmt.executeUpdate();	
		}	
	}
	public void DeleteCard(int card_no) throws Exception {
		Connection conn = open();

		String sql = "Delete from card_list where card_no = "+card_no ;
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		try (conn; pstmt) {
			pstmt.executeUpdate();
		}
	}	
	
	public void insertDelCard(AddCard ac, int card_no) throws Exception {
		Connection conn = open();
		String card_name = null;
		String rarity = null;
		int attact = 0;
		int hp = 0;
		String sql = "select card_name, rarity, attact,defense from card_list where card_no = "+card_no;
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		try (pstmt; rs) {
			if (rs.next()) {				
				card_name = rs.getString(1);
				rarity = rs.getString(2);
				attact = rs.getInt(3);
				hp = rs.getInt(4);
			}
		}

		String sql2 = "insert into card_del_list (card_no, card_name, rarity, attack, hp, reg_date) values(?, ?, ?, ?, ?, sysdate)";
		PreparedStatement ps = conn.prepareStatement(sql2);

		try (conn; ps) {
			ps.setInt(1, card_no);
			ps.setString(2, card_name);
			ps.setString(3, rarity);
			ps.setInt(4, attact);
			ps.setInt(5, hp);
			ps.executeUpdate();
		}
	}
	
	
	public ArrayList<CardLog> getCardLog() throws Exception {
		Connection conn = open();
		ArrayList<CardLog> CardList = new ArrayList<>();
//		String sql = "select log_no,card_no,decode(gubun,'C','추가','U','수정','D','삭제'),to_char(reg_date,'yyyy.mm.dd') from card_log";
		String sql = "select A.log_no,A.card_no, "
				+ "    case when b.card_name is null then (select card_name from card_del_list where card_no = a.card_no) else b.card_name end "
				+ "    ,decode(A.gubun,'C','추가','U','수정','D','삭제'),to_char(A.reg_date,'yyyy.mm.dd') from card_log A "
				+ "    left outer join card_list B on a.card_no = b.card_no order by a.log_no";
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
}
