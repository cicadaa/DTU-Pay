package group16.DtuPayWeb.rest.dto;

import java.math.BigDecimal;

public class MerchantRecord {
	private final Token token;
	private final BigDecimal price;
	private final int day;
	private final int month;
	private final int year;
	
	public MerchantRecord(Token token, BigDecimal price, int day, int month, int year) {
		this.token=token;
		this.price=price;
		this.day=day;
		this.month=month;
		this.year=year;
	}
	
	public Token getToken() {
		return this.token;
	}
	
	public BigDecimal getPrice() {
		return this.price;
	}
	
	public String getDate() {
		return this.day+"/"+this.month+"/"+this.year;
	}
	
	@Override
	public String toString() {
		return getDate()+":\n\tToken used: \t"+this.token.getUID()+"\n\tAmount spent: \t"+this.price; 
	}
	
	public String toCSVitem() {
		return getDate()+","+this.token.getUID()+","+this.price.toString()+";";
	}

	public int getDay() {
		return this.day;
	}
	
	public int getMonth() {
		return this.month;
	}
	
	public int getYear() {
		return this.year;
	}
}
