package group16.DtuPayWeb.rest.resources;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import dtu.ws.fastmoney.BankService;
import dtu.ws.fastmoney.BankServiceException_Exception;
import dtu.ws.fastmoney.BankServiceService;
import dtu.ws.fastmoney.User;
import group16.DtuPayWeb.main.DTUPayOperations;
import group16.DtuPayWeb.main.InMemRep;
import group16.DtuPayWeb.main.Token;
import group16.DtuPayWeb.main.TokenManager;
import group16.DtuPayWeb.main.UserManager;
import group16.DtuPayWeb.rest.dto.ReportManagerRepresentation;

//@author Chanyu Yang(s192239)
public class TestPreparationOfReportManager {
	//resource parameters
	static InMemRep rep = InMemRep.getInstance();
	private static UserManager userManager = new UserManager(new BankServiceService().getBankServicePort(), rep);
	
	//test parameters
	private DTUPayOperations payment ;
	private TokenManager tokenManager ;
	private List<Token> cToken;
	private BankService bank;
	private BigDecimal balance;
	
	
	
	//test clean
	public void resetReportEnv() throws BankServiceException_Exception {
		payment = new DTUPayOperations(rep);
		tokenManager = new TokenManager(rep);
		bank = new BankServiceService().getBankServicePort();	
		balance = new BigDecimal("2500.00");
		
	}
	
	public void retireCustomerAccount() {
		try {
			bank.retireAccount(bank.getAccountByCprNumber("010101-9999").getId());
		} catch (BankServiceException_Exception e) {
			 System.out.println("retire error customer");
		}
	}
	
	public void retireMerchantAccount() {
		try {
			bank.retireAccount(bank.getAccountByCprNumber("060606-9999").getId());
		} catch (BankServiceException_Exception e) {
            System.out.println("retire error merchant");
		}
	}
	
	//test part prepare merchant
	public void aMerchantByTheNameWithABankAccount(String cpr) throws Exception{
	    User merchantUsr = new User();
	    merchantUsr.setCprNumber(cpr);
	    merchantUsr.setFirstName("Monic");
	    merchantUsr.setLastName("Green");
	    
	    try {
			bank.createAccountWithBalance(merchantUsr, balance);
		} catch (BankServiceException_Exception e) {
			System.out.println("merchant already in bank");
		}
	    
	    try {
			userManager.createMerchant(cpr);
		} catch (Exception e) {
			System.out.println("create Merchant problem");
		}
	}
	
	//test prepare customer
	public void aCustomerWithABankAccount(String cpr) throws BankServiceException_Exception {
		//bank.retireAccount(bank.getAccountByCprNumber(cpr).getId());
		User customerUsr = new User();
	    customerUsr.setCprNumber(cpr);
	    customerUsr.setFirstName("Heinz");
	    customerUsr.setLastName("Ketchup");    
	    try {
			bank.createAccountWithBalance(customerUsr, balance);
			
		} catch (BankServiceException_Exception e) {
			System.out.println("customer already in bank");
		}
	    
	    try {
			userManager.createCustomer(cpr);
		} catch (Exception e) {
			System.out.println("customer already exists in InMem");
		}
	}
	
	public void theCustomerHasToken(String cpr) throws Exception {
	    cToken = tokenManager.request(cpr, 1);
	}
	
	public void theCustomerMakesPurchasesOfDKKDKKAndDKK(String cCPR, String mCPR, Integer PaymentNumber) {
		int basePrice = 40;
	    for (int i = 0; i < PaymentNumber; i++) {
	    	
		try {
			payment.transferMoney(cToken.get(i).getUID(),
					new BigDecimal(basePrice+i*5),
					rep.getCustomer(cCPR), 
					rep.getMerchant(mCPR), bank);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
	    }
	}
	
	public void customerHavePayments(ReportManagerRepresentation c, int PaymentNumber) throws Exception {
		this.resetReportEnv();
		//dummy merchant
		bank = new BankServiceService().getBankServicePort();	
		ReportManagerRepresentation m = new ReportManagerRepresentation();
		m.setCpr("060606-9999");
	
		this.aMerchantByTheNameWithABankAccount(m.getCpr());
		this.aCustomerWithABankAccount(c.getCpr());
		this.theCustomerHasToken(c.getCpr());
		this.theCustomerMakesPurchasesOfDKKDKKAndDKK(c.getCpr(), m.getCpr(), PaymentNumber);	
		//this.retireMerchantAccount();
	}
	
	public void merchantHavePayments(ReportManagerRepresentation m, int PaymentNumber) throws Exception {
		this.resetReportEnv();
		//dummy customer
		bank = new BankServiceService().getBankServicePort();	
		ReportManagerRepresentation c = new ReportManagerRepresentation();
		c.setCpr("010101-9999");
	
		this.aMerchantByTheNameWithABankAccount(m.getCpr());
		this.aCustomerWithABankAccount(c.getCpr());
		this.theCustomerHasToken(c.getCpr());
		this.theCustomerMakesPurchasesOfDKKDKKAndDKK(c.getCpr(), m.getCpr(), PaymentNumber);	
		//this.retireCustomerAccount();
		
	}
}
