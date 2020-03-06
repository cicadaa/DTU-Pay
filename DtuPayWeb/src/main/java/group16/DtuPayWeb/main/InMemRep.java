package group16.DtuPayWeb.main;

import java.util.*;

public class InMemRep implements UserRepository{
	
	
	private static InMemRep instance;
	List<Customer> customers;
	List<Merchant> merchants;
	public InMemRep() {
		customers = new ArrayList<Customer>();
		merchants = new ArrayList<Merchant>();
	}
	

	
	public static InMemRep getInstance() {
		
		if(instance == null) {
			instance = new InMemRep();
		}
		
		return instance;
	}

	
	@Override
	public void createCustomer(String cpr) {
		customers.add(new Customer(cpr));
		
	}
	
	@Override
	public void createMerchant(String cpr) {
		merchants.add(new Merchant(cpr));
		
	}

	public Customer getCustomer(String cpr) {
		for (Customer c : customers) {
			if (c.getCPR()==cpr) return c;
		}
		return null;
	}
	
	public Merchant getMerchant(String cpr) {
		for (Merchant m : merchants) {
			if (m.getCPR()==cpr) return m;
		}
		return null;
	}
	

	@Override
	public Boolean isCustomer(String cpr) {
		for (Customer c : customers) {
			if (cpr==c.getCPR()) {
				return true;
			}
		}
		return false;
	}

	@Override
	public Boolean isMerchant(String cpr) {
		for (Merchant m : merchants) {
			if (cpr==m.getCPR()) {
				return true;
			}
		}
		return false;
	}

	public void addCustomer(Customer customer) {
		customers.add(customer);
	}

	public void addMerchant(Merchant merchant) {
		merchants.add(merchant);
		
	}

}
