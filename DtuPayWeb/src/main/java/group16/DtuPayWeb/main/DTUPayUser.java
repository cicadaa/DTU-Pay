package group16.DtuPayWeb.main;

import java.util.*;

/*
 * Classes that keep a record of users in the application implements this Class
 * 
 * */

public abstract class DTUPayUser {
	
	private final String CPR;
	private List<Token> tokens;
	
	public DTUPayUser(String CPR){
		this.CPR = CPR;
		this.tokens = new ArrayList<Token>();
	}
	
	public String getCPR() {
		return CPR;
	}

	public List<Token> getTokens() {
		return tokens;
	}

	
}
