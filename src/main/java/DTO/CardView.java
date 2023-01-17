package DTO;

public class CardView {
	int card_no;
	String card_name;
	String rarity;
	int attack;
	int hp;
	String description;
	int pick_no;
	
	public int getPick_no() {
		return pick_no;
	}
	public void setPick_no(int pick_no) {
		this.pick_no = pick_no;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getCard_no() {
		return card_no;
	}
	public void setCard_no(int card_no) {
		this.card_no = card_no;
	}
	public String getCard_name() {
		return card_name;
	}
	public void setCard_name(String card_name) {
		this.card_name = card_name;
	}
	public String getRarity() {
		return rarity;
	}
	public void setRarity(String rarity) {
		this.rarity = rarity;
	}
	public int getAttack() {
		return attack;
	}
	public void setAttack(int attack) {
		this.attack = attack;
	}
	public int getHp() {
		return hp;
	}
	public void setHp(int hp) {
		this.hp = hp;
	}
	
	
	
}
