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

		String sql = "insert into card_list (card_no, card_name, rarity, attact,defense) values(CARDLIST_SEQ.nextval, ?, ?, ?, ?)";
		PreparedStatement pstmt = conn.prepareStatement(sql);

		try (conn; pstmt) {
			pstmt.setString(1, ac.getCard_name());
			pstmt.setString(2, ac.getRarity());
			pstmt.setInt(3, ac.getAttack());
			pstmt.setInt(4, ac.getDefense());
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
	
}
