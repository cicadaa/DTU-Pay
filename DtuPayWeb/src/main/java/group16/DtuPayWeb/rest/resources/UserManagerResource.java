package group16.DtuPayWeb.rest.resources;

import group16.DtuPayWeb.main.InMemRep;
import group16.DtuPayWeb.main.UserManager;
import group16.DtuPayWeb.rest.dto.UserManagerRepresentation;
import dtu.ws.fastmoney.BankServiceService;

import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.POST;
import javax.ws.rs.Consumes;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

@Path("/usermanager")
@Consumes("application/json")
@Produces("application/json")
public class UserManagerResource {
	
	static InMemRep rep = InMemRep.getInstance(); 
	private static UserManager userManager = new UserManager(new BankServiceService().getBankServicePort(), rep);
	
	@Path("customer")
	@POST
	public Response createCustomer(UserManagerRepresentation c) {
		
			try {
				System.out.println("c.getCPR="+c.getCPR());
				userManager.createCustomer(c.getCPR());
			} catch(Exception e) {
				throw new WebApplicationException(Response.Status.NOT_FOUND);
			}	
			
		return Response
				.status(Response.Status.CREATED)
				.entity(c.getCPR())
				.build();
	}
	
//	@POST
//	@Path("merchant")
//	public Response createMerchant(UserManagerRepresentation c) {
//		try {
//			userManager.createMerchant(c.getCPR());
//		} catch(Exception e) {
//			
//			// to do: best practice for exception handling
//			throw new WebApplicationException(Response.Status.NOT_FOUND);
//		}
//
//		return Response
//				.status(Response.Status.CREATED)
//				.entity(c.getCPR())
//				.build();
//	}
	
}
