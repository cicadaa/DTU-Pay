package group16.DtuPayWeb.main;

import java.util.ArrayList;
import java.util.List;

import javax.xml.datatype.XMLGregorianCalendar;

import dtu.ws.fastmoney.Account;
import dtu.ws.fastmoney.BankService;
import dtu.ws.fastmoney.Transaction;
import dtu.ws.fastmoney.User;

public class ReportManager {
	private BankService bank;
	private InMemRep rep;

	public ReportManager(BankService bank, InMemRep rep) {
		this.bank = bank;
		this.rep = rep;
	}

	public List<CustomerRecord> requestCustomerReport(String cCPR, int day1, int month1, int year1, int day2,
			int month2, int year2) throws Exception {
		if (!rep.isCustomer(cCPR)) throw  new Exception("User does not exist");
		Customer customer = rep.getCustomer(cCPR);
		List<CustomerRecord> history = customer.getHistory();
		List<CustomerRecord> report = new ArrayList<CustomerRecord>();
		
		for (CustomerRecord r : history) {
			if (year1 <= r.getYear() && r.getYear() <= year2) {
				if (month1 <= r.getMonth() && r.getMonth() <= month2) {
					if (day1 <= r.getDay() && r.getDay() <= day2) {
						report.add(r);
					}
				}
			}
		}
		return report;
	}

	public List<MerchantRecord> requestMerchantReport(String mCPR, int day1, int month1, int year1, int day2,
			int month2, int year2) throws Exception {
		if (!rep.isMerchant(mCPR)) throw  new Exception("User does not exist");
		Merchant merchant = rep.getMerchant(mCPR);
		List<MerchantRecord> history = merchant.getHistory();
		List<MerchantRecord> report = new ArrayList<MerchantRecord>();
		
		for (MerchantRecord r : history) {
			if (year1 <= r.getYear() && r.getYear() <= year2) {
				if (month1 <= r.getMonth() && r.getMonth() <= month2) {
					if (day1 <= r.getDay() && r.getDay() <= day2) {
						report.add(r);
					}
				}
			}
		}
		return report;
	}
	
}
