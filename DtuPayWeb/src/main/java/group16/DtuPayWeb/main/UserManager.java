package group16.DtuPayWeb.main;

import dtu.ws.fastmoney.Account;
import dtu.ws.fastmoney.BankService;
import dtu.ws.fastmoney.BankServiceException_Exception;

public class UserManager {
	private BankService bank;
	private InMemRep rep;
	private final String failMessage = "Must have a bank account to join";
	
	public UserManager(BankService bank, InMemRep rep) {
		this.bank=bank;
		this.rep = rep;
	}
	
	public void createCustomer(String CPR) throws Exception{
		Account account = null;
		try {
			account = bank.getAccountByCprNumber(CPR);
		} catch (BankServiceException_Exception e) {
			throw new Exception(failMessage);
		}

		rep.addCustomer(new Customer(CPR));			
	}

	public void createMerchant(String CPR) throws Exception {
		Account account = null;
		try {
			account = bank.getAccountByCprNumber(CPR);
		} catch (BankServiceException_Exception e) {
			
		}
		
		
		if (account == null) throw new Exception(failMessage);
		
		else {
			rep.addMerchant(new Merchant(CPR));
		}	
	}

}
