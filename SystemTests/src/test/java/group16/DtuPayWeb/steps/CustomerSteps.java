package group16.DtuPayWeb.steps;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

//import dtu.ws.fastmoney.Account;
//import dtu.ws.fastmoney.BankServiceException_Exception;
//import io.cucumber.java.After;
import dtu.ws.fastmoney.BankServiceService;
import dtu.ws.fastmoney.User;
import dtu.ws.fastmoney.Account;
import dtu.ws.fastmoney.BankService;
import dtu.ws.fastmoney.BankServiceException_Exception;
import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;

public class CustomerSteps {
	
	List<String> accounts = new ArrayList<String>();
	BankService bank = new BankServiceService().getBankServicePort();
	private String customerCprNumber;
	private BigDecimal customerBalance;
	private String customerAccount;
	
	@After
	public void cleanupUsedAccounts() throws BankServiceException_Exception {
		for (String a : accounts) {
			bank.retireAccount(a);
		}
	}
	
	@Given("a user with a bank account")
	public void aUserWithABankAccount() {
		// Create customer with a bank account
		User u = new User();
		customerCprNumber = "050880-2222";
		u.setCprNumber(customerCprNumber);
		u.setFirstName("Pelle");
		u.setLastName("Pelle");
		customerBalance = new BigDecimal(1000);
		try {
			customerAccount = bank.createAccountWithBalance(u, customerBalance);
		} catch (BankServiceException_Exception e) {
			System.out.println(e);
		}
		try {
			accounts.add(bank.getAccountByCprNumber(customerCprNumber).getId());
		} catch (BankServiceException_Exception e) {
			System.out.println(e.getMessage());
		} // Remember created accounts for cleanup
		
		// Create user with a bank account with DTUPay
	    UserManagerClient userManager = new UserManagerClient();
		userManager.createCustomer(customerCprNumber);
	}

	@When("user requests to join DTU Pay as a customer")
	public void userRequestsToJoinDTUPayAsACustomer() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@Then("user has been registered by DTU Pay as a customer")
	public void userHasBeenRegisteredByDTUPayAsACustomer() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

}
