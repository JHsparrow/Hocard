package DTO;

public class CardLog {
	int log_no;
	int card_no;
	String card_name;
	public String getCard_name() {
		return card_name;
	}
	public void setCard_name(String card_name) {
		this.card_name = card_name;
	}
	String gubun;
	String reg_date;
	public int getLog_no() {
		return log_no;
	}
	public void setLog_no(int log_no) {
		this.log_no = log_no;
	}
	public int getCard_no() {
		return card_no;
	}
	public void setCard_no(int card_no) {
		this.card_no = card_no;
	}
	public String getGubun() {
		return gubun;
	}
	public void setGubun(String gubun) {
		this.gubun = gubun;
	}
	public String getReg_date() {
		return reg_date;
	}
	public void setReg_date(String reg_date) {
		this.reg_date = reg_date;
	}
	
	
}
