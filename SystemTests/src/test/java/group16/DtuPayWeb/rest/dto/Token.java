package group16.DtuPayWeb.rest.dto;

import java.util.Objects;

public class Token {
	private final String uid;
	
	public Token(String uid) {
		Objects.requireNonNull(uid, "UID required");
		this.uid=uid;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o)return true;
		if (o == null || this.getClass()!= o.getClass()) return false;
		Token token = (Token) o;
		return this.uid.equals(token.getUID());
	}
	
	public String getUID() {
		return uid;
	}
}
