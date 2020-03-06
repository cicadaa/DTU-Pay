package group16.DtuPayWeb.main;

import java.math.BigDecimal;

public class CustomerRecord extends MerchantRecord {
	private final String merchant;

	public CustomerRecord(Token token, BigDecimal price, int day, int month, int year, String creditor) {
		super(token, price, day, month, year);
		this.merchant=creditor;
	}
	
	@Override
	public String toString() {
		return getDate()+":\n\tToken used: \t"+getToken().getUID()+"\n\tAmount spent: \t"+getPrice()+"\n\tMerchant: \t"+merchant; 
	}

	public String getCreditor() {
		return this.merchant;
	}
	
}
