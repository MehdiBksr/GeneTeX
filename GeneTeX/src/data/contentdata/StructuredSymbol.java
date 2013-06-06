package data.contentdata;

import data.Symbol;

public class StructuredSymbol extends Symbol {
	
	/** The token this symbol represents. */
	private Token t;
	/** Whether this symbol is a mathematical symbol. */
	private boolean math;
	
	public StructuredSymbol() {
		super();
	}
	
	public StructuredSymbol(Token t, boolean math) {
		this.t 		= t;
		this.math 	= math;
	}
	
	public void setToken(Token t) {
		this.t = t;
	}
	
	public Token getToken() {
		return this.t;
	}
	
	public void setMath(boolean math) {
		this.math = math;
	}
	
	public boolean getMath() {
		return this.math;
	}

}
