package analysis;

import data.contentdata.StructuredPage;

public interface Analyzer {
	
	public StructuredPage analyse(String fileName) throws Exception;

}
