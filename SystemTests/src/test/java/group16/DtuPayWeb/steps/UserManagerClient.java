package group16.DtuPayWeb.steps;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;

import dtu.ws.fastmoney.BankService;
import group16.DtuPayWeb.rest.dto.UserManagerRepresentation;

public class UserManagerClient {
	
	WebTarget rootURL;
	BankService bank;

	public UserManagerClient() {
		Client client = ClientBuilder.newClient();
		rootURL = client.target("http://localhost:8080/usermanager");	
	}
//
	public void createCustomer(String cpr) {
		
		UserManagerRepresentation customer = new UserManagerRepresentation();
		
		customer.setCPR(cpr);
		rootURL.path("customer")
		.request()
		.post(Entity.entity(customer, "application/json"));
	}
	
}
