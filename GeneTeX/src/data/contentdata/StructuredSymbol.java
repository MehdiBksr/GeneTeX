package data.contentdata;

import data.Symbol;

public class StructuredSymbol extends Symbol {
	
	/** The token this symbol represents. */
	private Token t;
	
	public StructuredSymbol() {
		super();
	}
	
	public StructuredSymbol(Token t) {
		this.t 		= t;
	}
	
	public void setToken(Token t) {
		this.t = t;
	}
	
	public Token getToken() {
		return this.t;
	}
	
}
