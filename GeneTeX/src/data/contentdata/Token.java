package data.contentdata;

import java.io.Serializable;

/**
 * This enumeration contains the different symbols that can be recognised and
 * associates them with strings, each string corresponding to the text which is
 * to be written in the output for a specific file type (Latex, word, etc.).
 * 
 * @author Marceau Thalgott, Theo Merle, Mehdi Bouksara
 */
public enum Token implements Serializable {

    /* ************************************************************************
     *                              FIGURES                                   * 
     ************************************************************************ */
	
/*	ZERO(false, "0", "0"),
	ONE(false, "1", "1"),
	TWO(false, "2", "2"),
	THREE(false, "3", "3"),
	FOUR(false, "4", "4"),
	FIVE(false, "5", "5"),
	SIX(false, "6", "6"),
	SEVEN(false, "7", "7"),
	EIGHT(false, "8", "8"),
	NINE(false, "9", "9"),*/

    /* ************************************************************************
     *                      LOWER CASE LATIN LETTERS                          * 
     ************************************************************************ */
	
	LOWER_CASE_A(false, "a", "a"),
	LOWER_CASE_B(false, "b", "b"),
	LOWER_CASE_C(false, "c", "c"),
	LOWER_CASE_D(false, "d", "d"),
	LOWER_CASE_E(false, "e", "e"),
	LOWER_CASE_F(false, "f", "f"),
	LOWER_CASE_G(false, "g", "g"),
	LOWER_CASE_H(false, "h", "h"),
	LOWER_CASE_I(false, "i", "i"),
	LOWER_CASE_J(false, "j", "j"),
	LOWER_CASE_K(false, "k", "k"),
	LOWER_CASE_L(false, "l", "l"),
	LOWER_CASE_M(false, "m", "m"),
	LOWER_CASE_N(false, "n", "n"),
	LOWER_CASE_O(false, "o", "o"),
	LOWER_CASE_P(false, "p", "p"),
	LOWER_CASE_Q(false, "q", "q"),
	LOWER_CASE_R(false, "r", "r"),
	LOWER_CASE_S(false, "s", "s"),
	LOWER_CASE_T(false, "t", "t"),
	LOWER_CASE_U(false, "u", "u"),
	LOWER_CASE_V(false, "v", "v"),
	LOWER_CASE_W(false, "w", "w"),
	LOWER_CASE_X(false, "x", "x"),
	LOWER_CASE_Y(false, "y", "y"),
	LOWER_CASE_Z(false, "z", "z"),

    /* ************************************************************************
     *                      UPPER CASE LATIN LETTERS                          * 
     ************************************************************************ */

	UPPER_CASE_A(false, "A", "Amaj"),
	UPPER_CASE_B(false, "B", "Bmaj"),
	UPPER_CASE_C(false, "C", "Cmaj"),
	UPPER_CASE_D(false, "D", "Dmaj"),
	UPPER_CASE_E(false, "E", "Emaj"),
	UPPER_CASE_F(false, "F", "Fmaj"),
	UPPER_CASE_G(false, "G", "Gmaj"),
	UPPER_CASE_H(false, "H", "Hmaj"),
	UPPER_CASE_I(false, "I", "Imaj"),
	UPPER_CASE_J(false, "J", "Jmaj"),
	UPPER_CASE_K(false, "K", "Kmaj"),
	UPPER_CASE_L(false, "L", "Lmaj"),
	UPPER_CASE_M(false, "M", "Mmaj"),
	UPPER_CASE_N(false, "N", "Nmaj"),
	UPPER_CASE_O(false, "O", "Omaj"),
	UPPER_CASE_P(false, "P", "Pmaj"),
	UPPER_CASE_Q(false, "Q", "Qmaj"),
	UPPER_CASE_R(false, "R", "Rmaj"),
	UPPER_CASE_S(false, "S", "Smaj"),
	UPPER_CASE_T(false, "T", "Tmaj"),
	UPPER_CASE_U(false, "U", "Umaj"),
	UPPER_CASE_V(false, "V", "Vmaj"),
	UPPER_CASE_W(false, "W", "Wmaj"),
	UPPER_CASE_X(false, "X", "Xmaj"),
	UPPER_CASE_Y(false, "Y", "Ymaj"),
	UPPER_CASE_Z(false, "Z", "Zmaj"),

    /* ************************************************************************
     *                      LOWER CASE GREEK LETTERS                          * 
     ************************************************************************ */
/*// the following commented lines do not have learning data.	
	LOWER_CASE_ALPHA(false, "\\alpha", "alpha"),
	LOWER_CASE_BETA(false, "\\beta", "beta"),
	LOWER_CASE_GAMMA(false, "\\gamma", "gamma"),
	LOWER_CASE_DELTA(false, "\\delta", "delta"),
	LOWER_CASE_EPSILON(false, "\\epsilon", "epsilon"),
	LOWER_CASE_ZETA(false, "\\zeta", "zeta"),
	LOWER_CASE_ETA(false, "\\eta", "eta"),
	LOWER_CASE_THETA(false, "\\theta", "theta"),
//	LOWER_CASE_IOTA(false, "\\iota", "iota"),
//	LOWER_CASE_KAPPA(false, "\\kappa", "kappa"),
//	LOWER_CASE_LAMBDA(false, "\\lambda", "lambda"),
	LOWER_CASE_MU(false, "\\mu", "mu"),
	LOWER_CASE_NU(false, "\\nu", "nu"),
//	LOWER_CASE_XI(false, "\\xi", "xi"),
//	LOWER_CASE_PI(false, "\\pi", "pi"),
	LOWER_CASE_RHO(false, "\\rho", "rho"),
	LOWER_CASE_SIGMA(false, "\\sigma", "sigma"),
//	LOWER_CASE_TAU(false, "\\tau", "tau"),
//	LOWER_CASE_UPSILON(false, "\\upsilon", "upsilon"),
	LOWER_CASE_PHI(false, "\\phi", "phi"),
//	LOWER_CASE_CHI(false, "\\chi", "chi"),
	LOWER_CASE_PSI(false, "\\psi", "psi"),
	LOWER_CASE_OMEGA(false, "\\omega", "omega"),
//	LOWER_CASE_OMICRON(false, "\\omicron", "omicron"),
//	LOWER_CASE_SIGMAF(false, "\\sigmaf", "sigmaf"),
*/
    /* ************************************************************************
     *                      UPPER CASE GREEK LETTERS                          * 
     ************************************************************************ */
	
//	UPPER_CASE_GAMMA(false, "\\Gamma", "Gammamaj"),
//	UPPER_CASE_DELTA(false, "\\Delta", "Deltamaj"),
//	UPPER_CASE_THETA(false, "\\Theta", "Thetamaj"),
//	UPPER_CASE_LAMBDA(false, "\\Lambda", "LambdaMaj"),
//	UPPER_CASE_XI(false, "\\Xi", "Ximaj"),
//	UPPER_CASE_PI(false, "\\Pi", "Pimaj"),
//	UPPER_CASE_SIGMA(false, "\\Sigma", "Sigmamaj"),
//	UPPER_CASE_UPSILON(false, "\\Upsilon", "Upsilonmaj"),
//	UPPER_CASE_PHI(false, "\\Phi", "Phimaj"),
//	UPPER_CASE_PSI(false, "\\Psi", "Psimaj"),
//	UPPER_CASE_OMEGA(false, "\\Omega", "Omegamaj"),
//	UPPER_CASE_ALPHA(false, "\\Alpha", "Alphamaj"),
//	UPPER_CASE_BETA(false, "\\Beta", "Betamaj"),
//	UPPER_CASE_EPSILON(false, "\\Epsilon", "Epsilonmaj"),
//	UPPER_CASE_ZETA(false, "\\Zeta", "Zetamaj"),
//	UPPER_CASE_ETA(false, "\\Eta", "Etamaj"),
//	UPPER_CASE_IOTA(false, "\\Iota", "Iotamaj"),
//	UPPER_CASE_KAPPA(false, "\\Kappa", "Kappamaj"),
//	UPPER_CASE_MU(false, "\\Mu", "Mumaj"),
//	UPPER_CASE_NU(false, "\\Nu", "Numaj"),
//	UPPER_CASE_OMICRON(false, "\\Omicron", "Omicronmaj"),
//	UPPER_CASE_RHO(false, "\\Rho", "Rhomaj"),
//	UPPER_CASE_TAU(false, "\\Tau", "Taumaj"),
//	UPPER_CASE_CHI(false, "\\Chi", "Chimaj"),

    /* ************************************************************************
     *                         PUNCTUATION MARK                               * 
     ************************************************************************ */
	
	SPACE(false, " ", "space");
	/*ASTERISK(false, "*", "asterisk"),
	COLON(false, ":", "colon"),
	COMMA(false, ",", "comma"),
	DOT(false, ".", "dot"),
	EXCLAMATION_MARK(false, "!", "exclmark"),
//	HYPHEN(false, "-", "hyphen,"),
	INTERROGATION_MARK(false, "?", "interrmark"),
	QUOTE(false, "'", "quote"),
	SEMICOLON(false, ";", "semicolon"),
	SLASH(false, "/", "slash"),
	UNDERSCORE(false, "\\_", "underscore"),*/

    /* ************************************************************************
     *                             BRACKETS                                   * 
     ************************************************************************ */

/*	OPENING_PARENTHESES(false, "(", "openpar"),
	CLOSING_PARENTHESES(false, ")", "closepar"),
//	OPENING_SQUARE_BRACKET(false, "[", "opensqbracket"),
//	CLOSING_SQUARE_BRACKET(false, "]", "closesqbracket"),
//	OPENING_BRACE(false, "{", "openbrace"),
//	CLOSING_BRACE(false, "}", "closebrace"),
*/	

    /* ************************************************************************
     *                        MATHEMATICAL SYMBOLS                            * 
     ************************************************************************ */
	
/*	PLUS(false, "+", "plus"),
	MINUS(false, "-", "hyphen"),
	DIVIDE(false, "/", "slash"),
	MULTIPLY(false, "*", "asterisk"),
	FOR_ALL(false, "\\forall", "forall"),
//	PART(false, "\\part", "part"),
	EXISTS(false, "\\exists", "exists"),
//	EMPTY_SET(false, "\\emptyset", "emptyset"),
	CONTAINED_IN(false, "\\in", "in");
//	NOT_CONTAINED_IN(false, "\\notin", "notin"),
//	CONTAINS(false, "\\ni", "ni"),
//	PRODUCT(false, "\\prod", "product"),
//	SUM(false, "\\sum", "sum"),
//	SQUARE_ROOT(false, "\\sqrt", "sqrt"),
//	INFINITY(false, "\\infty", "infinity"),
//	AND(false, "\\wedge", "and"),
//	OR(false, "\\vee", "or"),
//	INTERSECTION(false, "\\cap", "intersection"),
//	UNION(false, "\\cup", "union"),
//	INTEGRAL(false, "\\int", "integral"),
//	SIMILAR_TO(false, "\\sim", "similar"),
//	EQUAL(false, "=", "equals"),
//	NOT_EQUAL(false, "\\ne", "notequals"),
//	LOWER(false, "<", "lower"),
//	GREATER(false, ">", "greater"),
//	LOWER_OR_EQUAL(false, "\\le", "lowerequal"),
//	GREATER_OR_EQUAL(false, "\\ge", "greaterequal"),
*/
    /* ************************************************************************
     *                             ARROWS                                     * 
     ************************************************************************ */
	
//	LEFT_ARROW(false, "\\leftarrow", "leftarrow"),
//	RIGHT_ARROW(false, "\\rightarrow", "rightarrow"),
//	UP_ARROW(false, "\\uparrow", "uparrow"),
//	DOWN_ARROW(false, "\\downarrow", "downarrow");
	
	/* ************************************************************************
     *                              ATTRIBUTES                                * 
     ************************************************************************ */
 	
	/** The latex string corresponding to the token. */
 	private final String latex;
 	
 	/** The name of the directory containing the samples for this token */
 	private final String sampleDirectory;
 
	/** Whether this symbol is a mathematical symbol. */
 	private final boolean math;
 	
 	
    /* ************************************************************************
     *                              CONSTRUCTORS                              * 
     ************************************************************************ */
	
 	/**
 	 * Creates a token.
 	 * 
 	 * @param math Whether the token is only writable in math mode.
 	 * @param latexString The string to be written in a latex document.
 	 * @param sampleDirectory
 	 */
	private Token(boolean math, String latexString, String sampleDirectory) {
		this.latex = latexString;
		this.sampleDirectory = sampleDirectory;
		this.math = math;
	}
    
    /* ************************************************************************
     *                              METHODS                                   * 
     ************************************************************************ */
	
	/**
	 * Gets the string representation of this token for a latex document.
	 * 
	 * @return The string to be written in a latex document.
	 */
	public String toLatex() {
		return this.latex;
	}

    /* ************************************************************************
     *                              ACCESSORS                                 * 
     ************************************************************************ */
	
	public boolean isMath() {
		return math;
	}
	
	public String getSampleDirectory() {
		return sampleDirectory;
	}
	
}
