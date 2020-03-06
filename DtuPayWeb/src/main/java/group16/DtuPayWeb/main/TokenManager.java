	package group16.DtuPayWeb.main;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TokenManager {
	private InMemRep inMemRep;
	
	private final String failMessage01 = "Access denied";
	private final String failMessage02 = "Must have no more than one token to request more";
	
	public TokenManager(InMemRep userRepo) {
		this.inMemRep = userRepo;
	}

	public List<Token> request(String CPR, int requestedTokensNum) throws Exception {
		if (!inMemRep.isCustomer(CPR)) {
			throw new Exception(failMessage01);
		} else if (getUserTokens(CPR).size() > 1) {
			throw new Exception(failMessage02);
		}
		
		if (requestedTokensNum > 5) throw new IllegalArgumentException("Cannot request more than 5 tokens");
		
		List<Token> requestedTokens = new ArrayList<Token>();
		
		for (int i = 0; i < requestedTokensNum; i++) {
			Token t = generateToken();
			getUserTokens(CPR).add(t);
			requestedTokens.add(t);
		}
		
		return requestedTokens;
	}

	protected List<Token> getUserTokens(String userID) {
		return inMemRep.getCustomer(userID).getTokens();
	}
	
	protected Token generateToken() {
		return new Token(UUID.randomUUID().toString());
	}
}
