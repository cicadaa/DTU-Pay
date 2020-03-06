package group16.DtuPayWeb.steps;

import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ClientConfig;

import dtu.ws.fastmoney.BankService;
import group16.DtuPayWeb.rest.dto.CustomerRecord;
import group16.DtuPayWeb.rest.dto.MerchantRecord;
import group16.DtuPayWeb.rest.dto.ReportManagerRepresentation;
import group16.DtuPayWeb.rest.dto.UserManagerRepresentation;


//@author Chanyu Yang(s192239)
public class ReportManagerClient {
	
	WebTarget rootURL;
	BankService bank;

	public ReportManagerClient() {
		Client client = ClientBuilder.newClient();
		rootURL = client.target("http://localhost:8080/reportmanager");	
	}
//
	public List<String> requestMerchantReport(ReportManagerRepresentation m) {
	
		List<String> merchantrecord = rootURL
                .path("merchantreport")
                .request()
                .put(Entity.entity(m, "application/json"))
                .readEntity(new GenericType<List<String>>() {});
		return merchantrecord;
	}

	public List<String> requestCustomerReport(ReportManagerRepresentation c) {
		List<String> customerrecord = rootURL
                .path("customerreport")
                .request()
                .put(Entity.entity(c, "application/json"))
                .readEntity(new GenericType<List<String>>() {});	
		return customerrecord;
	}
	
}
