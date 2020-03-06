package group16.DtuPayWeb.main;

import java.util.List;

public interface UserRepository {
	public void createCustomer(String ID);
	public Boolean isCustomer(String ID);
	public void createMerchant(String ID);
	public Boolean isMerchant(String ID);
}
