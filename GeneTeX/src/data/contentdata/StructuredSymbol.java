package data.contentdata;

import data.Symbol;

/**
 * This class defines the symbol component of the structure containing the tokens 
 * related to the image recognition step.
 * An instance of <code>StructuredSymbol</code> is contained in an instance of 
 * <code>StructuredLine</code>.
 * 
 * @see {@link Token}, {@link Symbol}
 * 
 * @author Marceau Thalgott, Theo Merle, Mehdi Bouksara
 */
public class StructuredSymbol extends Symbol {

	/* ************************************************************************
	 *                              ATTRIBUTES                                * 
	 ************************************************************************ */
	
	/** The token this symbol represents. */
	private Token t;
    
    /* ************************************************************************
     *                              CONSTRUCTORS                              * 
     ************************************************************************ */
	
	/**
	 * Creates a symbol representing the given token.
	 * @param t The token.
	 */
	public StructuredSymbol(Token t) {
		this.t = t;
	}
    
    /* ************************************************************************
     *                              ACCESSORS                                 * 
     ************************************************************************ */
	
	/**
	 * Sets the symbol token to the given one.
	 * 
	 * @param t The given token.
	 */
	public void setToken(Token t) {
		this.t = t;
	}
	
	/**
	 * Gets the token of the symbol.
	 * 
	 * @return The token this symbol represents.
	 */
	public Token getToken() {
		return this.t;
	}
	
}
