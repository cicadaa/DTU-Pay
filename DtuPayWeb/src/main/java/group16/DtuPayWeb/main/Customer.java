package group16.DtuPayWeb.main;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Customer extends DTUPayUser {
	private final String CPR;
	private List<Token> tokens;
	private List<CustomerRecord> history;
	
	public Customer(String CPR) {
		super(CPR);
		this.CPR = CPR;		
		this.tokens = new ArrayList<Token>();
		history = new ArrayList<CustomerRecord>();
	}

	public String getCPR() {
		return CPR;
	}
	
	public List<Token> getTokens() {
		return tokens;
	}

	public void addRecord(CustomerRecord record) {
		this.history.add(record);
	}
	
	public List<CustomerRecord> getHistory() {
		return this.history;
	}
	
}
