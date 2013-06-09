package data.contentdata;

import java.io.Serializable;

public enum Token implements Serializable{

    /* ************************************************************************
     *                              FIGURES                                   * 
     ************************************************************************ */
	
	ZERO(false, "0"),
	ONE(false, "1"),
	TWO(false, "2"),
	THREE(false, "3"),
	FOUR(false, "4"),
	FIVE(false, "5"),
	SIX(false, "6"),
	SEVEN(false, "7"),
	EIGHT(false, "8"),
	NINE(false, "9"),

    /* ************************************************************************
     *                      LOWER CASE LATIN LETTERS                          * 
     ************************************************************************ */
	
	LOWER_CASE_A(false, "a"),
	LOWER_CASE_B(false, "b"),
	LOWER_CASE_C(false, "c"),
	LOWER_CASE_D(false, "d"),
	LOWER_CASE_E(false, "e"),
	LOWER_CASE_F(false, "f"),
	LOWER_CASE_G(false, "g"),
	LOWER_CASE_H(false, "h"),
	LOWER_CASE_I(false, "i"),
	LOWER_CASE_J(false, "j"),
	LOWER_CASE_K(false, "k"),
	LOWER_CASE_L(false, "l"),
	LOWER_CASE_M(false, "m"),
	LOWER_CASE_N(false, "n"),
	LOWER_CASE_O(false, "o"),
	LOWER_CASE_P(false, "p"),
	LOWER_CASE_Q(false, "q"),
	LOWER_CASE_R(false, "r"),
	LOWER_CASE_S(false, "s"),
	LOWER_CASE_T(false, "t"),
	LOWER_CASE_U(false, "u"),
	LOWER_CASE_V(false, "v"),
	LOWER_CASE_W(false, "w"),
	LOWER_CASE_X(false, "x"),
	LOWER_CASE_Y(false, "y"),
	LOWER_CASE_Z(false, "z"),

    /* ************************************************************************
     *                      UPPER CASE LATIN LETTERS                          * 
     ************************************************************************ */

	UPPER_CASE_A(false, "A"),
	UPPER_CASE_B(false, "B"),
	UPPER_CASE_C(false, "C"),
	UPPER_CASE_D(false, "D"),
	UPPER_CASE_E(false, "E"),
	UPPER_CASE_F(false, "F"),
	UPPER_CASE_G(false, "G"),
	UPPER_CASE_H(false, "H"),
	UPPER_CASE_I(false, "I"),
	UPPER_CASE_J(false, "J"),
	UPPER_CASE_K(false, "K"),
	UPPER_CASE_L(false, "L"),
	UPPER_CASE_M(false, "M"),
	UPPER_CASE_N(false, "N"),
	UPPER_CASE_O(false, "O"),
	UPPER_CASE_P(false, "P"),
	UPPER_CASE_Q(false, "Q"),
	UPPER_CASE_R(false, "R"),
	UPPER_CASE_S(false, "S"),
	UPPER_CASE_T(false, "T"),
	UPPER_CASE_U(false, "U"),
	UPPER_CASE_V(false, "V"),
	UPPER_CASE_W(false, "W"),
	UPPER_CASE_X(false, "X"),
	UPPER_CASE_Y(false, "Y"),
	UPPER_CASE_Z(false, "Z"),

    /* ************************************************************************
     *                      LOWER CASE GREEK LETTERS                          * 
     ************************************************************************ */
	
	LOWER_CASE_ALPHA(false, "\\alpha"),
	LOWER_CASE_BETA(false, "\\beta"),
	LOWER_CASE_GAMMA(false, "\\gamma"),
	LOWER_CASE_DELTA(false, "\\delta"),
	LOWER_CASE_EPSILON(false, "\\epsilon"),
	LOWER_CASE_ZETA(false, "\\zeta"),
	LOWER_CASE_ETA(false, "\\eta"),
	LOWER_CASE_THETA(false, "\\theta"),
	LOWER_CASE_IOTA(false, "\\iota"),
	LOWER_CASE_KAPPA(false, "\\kappa"),
	LOWER_CASE_LAMBDA(false, "\\lambda"),
	LOWER_CASE_MU(false, "\\mu"),
	LOWER_CASE_NU(false, "\\nu"),
	LOWER_CASE_XI(false, "\\xi"),
	LOWER_CASE_PI(false, "\\pi"),
	LOWER_CASE_RHO(false, "\\rho"),
	LOWER_CASE_SIGMA(false, "\\sigma"),
	LOWER_CASE_(false, "\\tau"),
	LOWER_CASE_UPSILON(false, "\\upsilon"),
	LOWER_CASE_PHI(false, "\\phi"),
	LOWER_CASE_CHI(false, "\\chi"),
	LOWER_CASE_PSI(false, "\\psi"),
	LOWER_CASE_OMEGA(false, "\\omega"),
//	LOWER_CASE_OMICRON(false, "\\omicron"),
//	LOWER_CASE_SIGMAF(false, "\\sigmaf"),

    /* ************************************************************************
     *                      UPPER CASE GREEK LETTERS                          * 
     ************************************************************************ */
	
	UPPER_CASE_GAMMA(false, "\\Gamma"),
	UPPER_CASE_DELTA(false, "\\Delta"),
	UPPER_CASE_THETA(false, "\\Theta"),
	UPPER_CASE_LAMBDA(false, "\\Lambda"),
	UPPER_CASE_XI(false, "\\Xi"),
	UPPER_CASE_PI(false, "\\Pi"),
	UPPER_CASE_SIGMA(false, "\\Sigma"),
	UPPER_CASE_UPSILON(false, "\\Upsilon"),
	UPPER_CASE_PHI(false, "\\Phi"),
	UPPER_CASE_PSI(false, "\\Psi"),
	UPPER_CASE_OMEGA(false, "\\Omega"),
//	UPPER_CASE_ALPHA(false, "\\Alpha"),
//	UPPER_CASE_BETA(false, "\\Beta"),
//	UPPER_CASE_EPSILON(false, "\\Epsilon"),
//	UPPER_CASE_ZETA(false, "\\Zeta"),
//	UPPER_CASE_ETA(false, "\\Eta"),
//	UPPER_CASE_IOTA(false, "\\Iota"),
//	UPPER_CASE_KAPPA(false, "\\Kappa"),
//	UPPER_CASE_MU(false, "\\Mu"),
//	UPPER_CASE_NU(false, "\\Nu"),
//	UPPER_CASE_OMICRON(false, "\\Omicron"),
//	UPPER_CASE_RHO(false, "\\Rho"),
//	UPPER_CASE_TAU(false, "\\Tau"),
//	UPPER_CASE_CHI(false, "\\Chi"),

    /* ************************************************************************
     *                         PUNCTUATION MARK                               * 
     ************************************************************************ */
	
	SPACE(false, " "),
	ASTERISK(false, "*"),
	COLON(false, ":"),
	COMMA(false, ","),
	DOT(false, "."),
	EXCLAMATION_MARK(false, "!"),
	HYPHEN(false, "-"),
	INTERROGATION_MARK(false, "?"),
	QUOTE(false, "'"),
	SEMICOLON(false, ";"),
	SLASH(false, "/"),
	UNDERSCORE(false, "\\_"),

    /* ************************************************************************
     *                             BRACKETS                                   * 
     ************************************************************************ */

	OPENING_PARENTHESES(false, "("),
	CLOSING_PARENTHESES(false, ")"),
	OPENING_SQUARE_BRACKET(false, "["),
	CLOSING_SQUARE_BRACKET(false, "]"),
	OPENING_BRACE(false, "{"),
	CLOSING_BRACE(false, "}"),
	

    /* ************************************************************************
     *                        MATHEMATICAL SYMBOLS                            * 
     ************************************************************************ */
	
	PLUS(false, "+"),
	MINUS(false, "-"),
	DIVIDE(false, "/"),
	MULTIPLY(false, "*"),
	FOR_ALL(false, "\\forall"),
	PART(false, "\\part"),
	EXISTS(false, "\\exists"),
	EMPTY_SET(false, "\\emptyset"),
	CONTAINED_IN(false, "\\in"),
	NOT_CONTAINED_IN(false, "\\notin"),
	CONTAINS(false, "\\ni"),
	PRODUCT(false, "\\prod"),
	SUM(false, "\\sum"),
	SQUARE_ROOT(false, "\\sqrt"),
	INFINITY(false, "\\infty"),
	AND(false, "\\wedge"),
	OR(false, "\\vee"),
	INTERSECTION(false, "\\cap"),
	UNION(false, "\\cup"),
	INTEGRAL(false, "\\int"),
	SIMILAR_TO(false, "\\sim"),
	EQUAL(false, "="),
	NOT_EQUAL(false, "\\ne"),
	LOWER(false, "<"),
	GREATER(false, ">"),
	LOWER_OR_EQUAL(false, "\\le"),
	GREATER_OR_EQUAL(false, "\\ge"),

    /* ************************************************************************
     *                             ARROWS                                     * 
     ************************************************************************ */
	
	LEFT_ARROW(false, "\\leftarrow"),
	RIGHT_ARROW(false, "\\rightarrow"),
	UP_ARROW(false, "\\uparrow"),
	DOWN_ARROW(false, "\\downarrow");
	
	/* ************************************************************************
     *                              ATTRIBUTES                                * 
     ************************************************************************ */
 	
	/** The latex string corresponding to the token. */
 	private final String latex;
 
	/** Whether this symbol is a mathematical symbol. */
 	private final boolean math;
 	
 	
    /* ************************************************************************
     *                              CONSTRUCTORS                              * 
     ************************************************************************ */
	
	private Token(boolean math, String latexString) {
		this.latex = latexString;
		this.math = math;
	}
    
    /* ************************************************************************
     *                              METHODS                                   * 
     ************************************************************************ */
	
	public String toLatex() {
		return this.latex;
	}

    /* ************************************************************************
     *                              ACCESSORS                                 * 
     ************************************************************************ */
	
	public boolean isMath() {
		return math;
	}
	
}
