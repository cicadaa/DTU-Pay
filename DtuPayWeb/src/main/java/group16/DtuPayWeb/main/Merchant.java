package group16.DtuPayWeb.main;

import java.util.ArrayList;
import java.util.List;

public class Merchant extends DTUPayUser{
	private final String CPR;
	private List<MerchantRecord> history;
	
	public Merchant(String CPR) {
		super(CPR);
		this.CPR = CPR;
		history = new ArrayList<MerchantRecord>();
	}
	
	public String getCPR() {
		return CPR;
	}
	
	public void addRecord(MerchantRecord record) {
		this.history.add(record);
	}
	
	public List<MerchantRecord> getHistory() {
		return this.history;
	}
}
