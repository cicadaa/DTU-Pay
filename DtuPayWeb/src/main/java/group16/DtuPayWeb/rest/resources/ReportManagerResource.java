package group16.DtuPayWeb.rest.resources;

import group16.DtuPayWeb.main.Customer;
import group16.DtuPayWeb.main.CustomerRecord;
import group16.DtuPayWeb.main.InMemRep;
import group16.DtuPayWeb.main.Merchant;
import group16.DtuPayWeb.main.MerchantRecord;
import group16.DtuPayWeb.main.ReportManager;
import group16.DtuPayWeb.main.UserManager;
import group16.DtuPayWeb.rest.dto.ReportManagerRepresentation;

import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.core.Response;

import dtu.ws.fastmoney.BankService;
import dtu.ws.fastmoney.BankServiceService;

//@author Chanyu Yang(s192239)
@Path("/reportmanager")
@Consumes("application/json")
@Produces("application/json")
public class ReportManagerResource {
	InMemRep rep = InMemRep.getInstance();
	
	BankService bank = new BankServiceService().getBankServicePort();
	ReportManager reportManager = new ReportManager(bank, rep);
	@PUT
	@Path("customerreport")	
	public Response requestCustomerReport(ReportManagerRepresentation c) throws Exception {
		
		TestPreparationOfReportManager testprepare = new TestPreparationOfReportManager();	
		testprepare.customerHavePayments(c, 1);		
		
		List<CustomerRecord> customerReportOrigin = reportManager.requestCustomerReport(c.getCpr(),
				c.getDay1(), c.getMonth1(), c.getYear1(), 
				c.getDay2(), c.getMonth2(), c.getYear2());
		List<String> customerReport = new ArrayList<String>();
		
		for (CustomerRecord r : customerReportOrigin) {
			customerReport.add(r.toString());
		}

		return Response
				.status(Response.Status.OK)
				.entity(customerReport)
				.build();
	}
	
	@PUT
	@Path("merchantreport")
	public Response requestMerchantReport(ReportManagerRepresentation m) throws Exception {
		InMemRep rep = InMemRep.getInstance();
		
		TestPreparationOfReportManager testprepare = new TestPreparationOfReportManager();	
		testprepare.merchantHavePayments(m, 1);	
		
		if (!rep.isMerchant(m.getCpr())) {
			return Response
					.status(Response.Status.NOT_FOUND)
					.entity(null)
					.build();
		}
		
		List<MerchantRecord> merchantReportOrigin = reportManager.requestMerchantReport(m.getCpr(),
				m.getDay1(), m.getMonth1(), m.getYear1(), 
				m.getDay2(), m.getMonth2(), m.getYear2());
		List<String> merchantReport = new ArrayList<String>();
		
		for (MerchantRecord r : merchantReportOrigin) {
			merchantReport.add(r.toString());
		}
		
		return Response
				.status(Response.Status.OK)
				.entity(merchantReport)
				.build();

	}

	
}
