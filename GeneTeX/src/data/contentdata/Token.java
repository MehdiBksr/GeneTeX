package data.contentdata;

public enum Token {

    /* ************************************************************************
     *                              FIGURES                                   * 
     ************************************************************************ */
	
	ZERO("0"),
	ONE("1"),
	TWO("2"),
	THREE("3"),
	FOUR("4"),
	FIVE("5"),
	SIX("6"),
	SEVEN("7"),
	EIGHT("8"),
	NINE("9"),

    /* ************************************************************************
     *                      LOWER CASE LATIN LETTERS                          * 
     ************************************************************************ */
	
	LOWER_CASE_A("a"),
	LOWER_CASE_B("b"),
	LOWER_CASE_C("c"),
	LOWER_CASE_D("d"),
	LOWER_CASE_E("e"),
	LOWER_CASE_F("f"),
	LOWER_CASE_G("g"),
	LOWER_CASE_H("h"),
	LOWER_CASE_I("i"),
	LOWER_CASE_J("j"),
	LOWER_CASE_K("k"),
	LOWER_CASE_L("l"),
	LOWER_CASE_M("m"),
	LOWER_CASE_N("n"),
	LOWER_CASE_O("o"),
	LOWER_CASE_P("p"),
	LOWER_CASE_Q("q"),
	LOWER_CASE_R("r"),
	LOWER_CASE_S("s"),
	LOWER_CASE_T("t"),
	LOWER_CASE_U("u"),
	LOWER_CASE_V("v"),
	LOWER_CASE_W("w"),
	LOWER_CASE_X("x"),
	LOWER_CASE_Y("y"),
	LOWER_CASE_Z("z"),

    /* ************************************************************************
     *                      UPPER CASE LATIN LETTERS                          * 
     ************************************************************************ */

	UPPER_CASE_A("A"),
	UPPER_CASE_B("B"),
	UPPER_CASE_C("C"),
	UPPER_CASE_D("D"),
	UPPER_CASE_E("E"),
	UPPER_CASE_F("F"),
	UPPER_CASE_G("G"),
	UPPER_CASE_H("H"),
	UPPER_CASE_I("I"),
	UPPER_CASE_J("J"),
	UPPER_CASE_K("K"),
	UPPER_CASE_L("L"),
	UPPER_CASE_M("M"),
	UPPER_CASE_N("N"),
	UPPER_CASE_O("O"),
	UPPER_CASE_P("P"),
	UPPER_CASE_Q("Q"),
	UPPER_CASE_R("R"),
	UPPER_CASE_S("S"),
	UPPER_CASE_T("T"),
	UPPER_CASE_U("U"),
	UPPER_CASE_V("V"),
	UPPER_CASE_W("W"),
	UPPER_CASE_X("X"),
	UPPER_CASE_Y("Y"),
	UPPER_CASE_Z("Z"),

    /* ************************************************************************
     *                      LOWER CASE GREEK LETTERS                          * 
     ************************************************************************ */
	
	LOWER_CASE_ALPHA("\\alpha"),
	LOWER_CASE_BETA("\\beta"),
	LOWER_CASE_GAMMA("\\gamma"),
	LOWER_CASE_DELTA("\\delta"),
	LOWER_CASE_EPSILON("\\epsilon"),
	LOWER_CASE_ZETA("\\zeta"),
	LOWER_CASE_ETA("\\eta"),
	LOWER_CASE_THETA("\\theta"),
	LOWER_CASE_IOTA("\\iota"),
	LOWER_CASE_KAPPA("\\kappa"),
	LOWER_CASE_LAMBDA("\\lambda"),
	LOWER_CASE_MU("\\mu"),
	LOWER_CASE_NU("\\nu"),
	LOWER_CASE_XI("\\xi"),
	LOWER_CASE_PI("\\pi"),
	LOWER_CASE_RHO("\\rho"),
	LOWER_CASE_SIGMA("\\sigma"),
	LOWER_CASE_("\\tau"),
	LOWER_CASE_UPSILON("\\upsilon"),
	LOWER_CASE_PHI("\\phi"),
	LOWER_CASE_CHI("\\chi"),
	LOWER_CASE_PSI("\\psi"),
	LOWER_CASE_OMEGA("\\omega"),
//	LOWER_CASE_OMICRON("\\omicron"),
//	LOWER_CASE_SIGMAF("\\sigmaf"),

    /* ************************************************************************
     *                      UPPER CASE GREEK LETTERS                          * 
     ************************************************************************ */
	
	UPPER_CASE_GAMMA("\\Gamma"),
	UPPER_CASE_DELTA("\\Delta"),
	UPPER_CASE_THETA("\\Theta"),
	UPPER_CASE_LAMBDA("\\Lambda"),
	UPPER_CASE_XI("\\Xi"),
	UPPER_CASE_PI("\\Pi"),
	UPPER_CASE_SIGMA("\\Sigma"),
	UPPER_CASE_UPSILON("\\Upsilon"),
	UPPER_CASE_PHI("\\Phi"),
	UPPER_CASE_PSI("\\Psi"),
	UPPER_CASE_OMEGA("\\Omega"),
//	UPPER_CASE_ALPHA("\\Alpha"),
//	UPPER_CASE_BETA("\\Beta"),
//	UPPER_CASE_EPSILON("\\Epsilon"),
//	UPPER_CASE_ZETA("\\Zeta"),
//	UPPER_CASE_ETA("\\Eta"),
//	UPPER_CASE_IOTA("\\Iota"),
//	UPPER_CASE_KAPPA("\\Kappa"),
//	UPPER_CASE_MU("\\Mu"),
//	UPPER_CASE_NU("\\Nu"),
//	UPPER_CASE_OMICRON("\\Omicron"),
//	UPPER_CASE_RHO("\\Rho"),
//	UPPER_CASE_TAU("\\Tau"),
//	UPPER_CASE_CHI("\\Chi"),

    /* ************************************************************************
     *                         PUNCTUATION MARK                               * 
     ************************************************************************ */
	
	ASTERISK("*"),
	COLON(":"),
	COMMA(","),
	DOT("."),
	EXCLAMATION_MARK("!"),
	HYPHEN("-"),
	INTERROGATION_MARK("?"),
	QUOTE("'"),
	SEMICOLON(";"),
	SLASH("/"),
	UNDERSCORE("_"),

    /* ************************************************************************
     *                             BRACKETS                                   * 
     ************************************************************************ */

	OPENING_PARENTHESES("("),
	CLOSING_PARENTHESES(")"),
	OPENING_SQUARE_BRACKET("["),
	CLOSING_SQUARE_BRACKET("]"),
	OPENING_BRACE("{"),
	CLOSING_BRACE("}"),
	

    /* ************************************************************************
     *                        MATHEMATICAL SYMBOLS                            * 
     ************************************************************************ */
	
	PLUS("+"),
	MINUS("-"),
	DIVIDE("/"),
	MULTIPLY("*"),
	FOR_ALL("\\forall"),
	PART("\\part"),
	EXISTS("\\exists"),
	EMPTY_SET("\\emptyset"),
	CONTAINED_IN("\\in"),
	NOT_CONTAINED_IN("\\notin"),
	CONTAINS("\\ni"),
	PRODUCT("\\prod"),
	SUM("\\sum"),
	SQUARE_ROOT("\\sqrt"),
	INFINITY("\\infty"),
	AND("\\wedge"),
	OR("\\vee"),
	INTERSECTION("\\cap"),
	UNION("\\cup"),
	INTEGRAL("\\int"),
	SIMILAR_TO("\\sim"),
	EQUAL("="),
	NOT_EQUAL("\\ne"),
	LOWER("<"),
	GREATER(">"),
	LOWER_OR_EQUAL("\\le"),
	GREATER_OR_EQUAL("\\ge"),

    /* ************************************************************************
     *                             ARROWS                                     * 
     ************************************************************************ */
	
	LEFT_ARROW("\\leftarrow"),
	RIGHT_ARROW("\\rightarrow"),
	UP_ARROW("\\uparrow"),
	DOWN_ARROW("\\downarrow");
	
	/* ************************************************************************
     *                              ATTRIBUTES                                * 
     ************************************************************************ */
 	
 	private final String latex;
    
    /* ************************************************************************
     *                              CONSTRUCTORS                              * 
     ************************************************************************ */
	
	private Token(String latexString) {
		this.latex = latexString;
	}
    
    /* ************************************************************************
     *                              METHODS                                   * 
     ************************************************************************ */
	
	public String toLatex() {
		return this.latex;
	}
	
}
