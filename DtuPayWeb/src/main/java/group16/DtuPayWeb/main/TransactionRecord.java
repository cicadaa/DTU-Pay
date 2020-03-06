package group16.DtuPayWeb.main;

import java.math.BigDecimal;
import java.util.UUID;

import javax.xml.datatype.XMLGregorianCalendar;

import dtu.ws.fastmoney.Transaction;

public class TransactionRecord {
	private final Token token;
	private final Transaction transaction;
	
	public TransactionRecord(Token token, Transaction transaction) {
		this.token = token;
		this.transaction = transaction;
	}

	public Token getToken() {
		return this.token;
	}
	
	public XMLGregorianCalendar getTime() {
		return this.transaction.getTime();
	}
	
	public String getCreditor() {
		return this.transaction.getCreditor();
	}
	
	public BigDecimal getPrice() {
		return this.transaction.getAmount();
	}
	
}
