package group16.DtuPayWeb.steps;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import dtu.ws.fastmoney.Account;
import dtu.ws.fastmoney.BankService;
import dtu.ws.fastmoney.BankServiceException_Exception;
import dtu.ws.fastmoney.BankServiceService;
import dtu.ws.fastmoney.Transaction;
import dtu.ws.fastmoney.User;
import group16.DtuPayWeb.rest.dto.CustomerRecord;
import group16.DtuPayWeb.rest.dto.MerchantRecord;
import group16.DtuPayWeb.rest.dto.ReportManagerRepresentation;
import group16.DtuPayWeb.rest.dto.Token;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
//@author Chanyu Yang(s192239)
public class ReportSteps {
	private ReportManagerClient reportManagerClient = new ReportManagerClient();

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy MMM dd");
     
	List<String> accounts = new ArrayList<String>();
	BankService bank = new BankServiceService().getBankServicePort();
	

	private List<String> customerRecord;
	private List<String> merchantRecord;
	
	ReportManagerRepresentation c = new ReportManagerRepresentation();
	ReportManagerRepresentation m = new ReportManagerRepresentation();
	
	@After
	public void cleanupUsedAccounts() throws BankServiceException_Exception {
		//bank.retireAccount(bank.getAccountByCprNumber("060606-9999").getId());
		for(String a : accounts) {
			bank.retireAccount(a);
			System.out.println(a);
		}
		accounts=new ArrayList<String>();		
	}

	
	@When("the customer requests report of purchases made in current time period")
	public void theCustomerRequestsReportOfPurchasesMadeInCurrentTimePeriod() {
		
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		c.setDay1(cal.get(Calendar.DAY_OF_MONTH));
		c.setDay2(cal.get(Calendar.DAY_OF_MONTH));
		c.setMonth1(cal.get(Calendar.MONTH)+1);
		c.setMonth2(cal.get(Calendar.MONTH)+1);
		c.setYear1(cal.get(Calendar.YEAR));
		c.setYear2(cal.get(Calendar.YEAR));
		c.setCpr("060606-0606");
		
		customerRecord = reportManagerClient.requestCustomerReport(c);
		for(String r: customerRecord) {
			System.out.println(r);
		}

		try {
			accounts.add(bank.getAccountByCprNumber(c.getCpr()).getId());
			accounts.add(bank.getAccountByCprNumber("060606-9999").getId());
			
		} catch (BankServiceException_Exception e) {
			System.out.println("fail to add account");
			e.printStackTrace();
		}
	}

	
	@Then("the customer receives a history-record containing {int} item\\(s)")
	public void theCustomerReceivesAHistoryRecordContainingItemS(Integer int1) {
		//System.out.println("then excuted");
		assertTrue(customerRecord.size()==int1);
	}
	
	
	
	@When("the merchant requests report of purchases made in current time period")
	public void theMerchantRequestsReportOfPurchasesMadeInCurrentTimePeriod() {
		Calendar cal = Calendar.getInstance();
		
		m.setDay1(cal.get(Calendar.DAY_OF_MONTH));
		m.setDay2(cal.get(Calendar.DAY_OF_MONTH));
		m.setMonth1(cal.get(Calendar.MONTH)+1);
		m.setMonth2(cal.get(Calendar.MONTH)+1);
		m.setYear1(cal.get(Calendar.YEAR));
		m.setYear2(cal.get(Calendar.YEAR));
		m.setCpr("010101-0606");
		
		merchantRecord = reportManagerClient.requestMerchantReport(m);
		try {
			accounts.add(bank.getAccountByCprNumber(m.getCpr()).getId());
			accounts.add(bank.getAccountByCprNumber("010101-9999").getId());//add dummy customer account
			
		} catch (BankServiceException_Exception e) {
			System.out.println("fail to add account");
			e.printStackTrace();
		}
	
	}

	@Then("the merchant receives a history-record containing {int} item\\(s)")
	public void theMerchantReceivesAHistoryRecordContainingItemS(Integer int1) {
		assertTrue(merchantRecord.size()==int1);
	}
}
