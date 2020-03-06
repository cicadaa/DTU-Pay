package group16.DtuPayWeb.main;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

import javax.xml.datatype.XMLGregorianCalendar;

import dtu.ws.fastmoney.Account;
import dtu.ws.fastmoney.BankServiceException_Exception;
import dtu.ws.fastmoney.Transaction;
import dtu.ws.fastmoney.BankService;


public class DTUPayOperations {

	private InMemRep inMemRep;
	private String failMessage;

	public DTUPayOperations(InMemRep inMemRep) {
		this.inMemRep = inMemRep;
		this.failMessage = new String("Invalid action");
	}

	public boolean transferMoney(String token, BigDecimal price, Customer customer, Merchant merchant, BankService bank) throws Throwable {

		BigDecimal amount = price;
		
		if (validateToken(token, customer)) {
			Account customerAccount;
			Account merchantAccount;
	            	
	            	customerAccount = bank.getAccountByCprNumber(customer.getCPR());
	                merchantAccount = bank.getAccountByCprNumber(merchant.getCPR());
	            
	                List<Token> customerTokens = customer.getTokens();
	                
	                Token presentedToken = null;
	                presentedToken = getPresentedToken(token, customerTokens, presentedToken);
	                
	                if (hasLowBalance(price, customer, customerAccount, presentedToken)) throw new Exception(failMessage);
	                
	                bank.transferMoneyFromTo(customerAccount.getId(), merchantAccount.getId(), amount, "description");
	                
	                Transaction custTransaction = getTransactions(customer.getCPR(), bank);
	                
	                Transaction merchTransaction = getTransactions(merchant.getCPR(), bank);
	                
	                
	                XMLGregorianCalendar cTime = custTransaction.getTime();
            		XMLGregorianCalendar mTime = merchTransaction.getTime();
            		customer.addRecord(new CustomerRecord(presentedToken, custTransaction.getAmount(), cTime.getDay(), cTime.getMonth(), cTime.getYear(), custTransaction.getCreditor()));
            		merchant.addRecord(new MerchantRecord(presentedToken, merchTransaction.getAmount(), mTime.getDay(), mTime.getMonth(), mTime.getYear()));
            		
	                return true;
	            
		} else {
			throw new IllegalArgumentException(failMessage);
		}
	}

	private Transaction getTransactions(String cpr, BankService bank)
			throws BankServiceException_Exception {
		List<Transaction> transactions = bank.getAccountByCprNumber(cpr).getTransactions();
		return transactions.get(transactions.size()-1);
	}

	private Boolean hasLowBalance(BigDecimal price, Customer customer, Account customerAccount,
			Token presentedToken) {
		if (customerAccount.getBalance().compareTo(price) < 0) {
			Calendar cal = Calendar.getInstance();
			customer.addRecord(new CustomerRecord(presentedToken, null, cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.MONTH), cal.get(Calendar.YEAR), null));
			return true;
		}
		return false;
	}

	private Token getPresentedToken(String token, List<Token> customerTokens, Token presentedToken) {
		for (int i = 0; i < customerTokens.size(); i++) {
			if (customerTokens.get(i).getUID().compareTo(token) == 0) {
				presentedToken = customerTokens.get(i);
				customerTokens.remove(i);
				return presentedToken;
			}
		}
		return presentedToken;
	}

	private boolean validateToken(String token, Customer customer) {
		List<Token> tokens = inMemRep.getCustomer(customer.getCPR()).getTokens();
		for (int i = 0; i < tokens.size(); i++) {
			if (tokens.get(i).getUID() == token)
				return true;
		}	return false;
	}

	//	public void HasBalance(Token testToken, Account acc, BigDecimal price) {
	//
	//		BigDecimal runningBalance;
	//		
	//		try {
	//			//= bank.getAccountByCprNumber(customer1CPR);	
	//			runningBalance = acc.getBalance();
	//			
	//			int res = runningBalance.compareTo(price);
	//			if(res >= 0) {
	//				running balance is greater/equal to
	//				
	//			}
	//			
	//		} catch(BankServiceException e) {
	//			System.out.print(e);
	//		}	
	//
	//		
	//	}
}
