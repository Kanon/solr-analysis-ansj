/**
 * 
 */
package org.ansj.solr.analysis;

//import java.io.IOException;
import java.io.Reader;
import java.util.Map;

import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.util.TokenizerFactory;
import org.apache.lucene.util.AttributeFactory;

/**
 * @author shiki
 *
 */
public class AnsjTokenizerFactory extends TokenizerFactory {

	private AnsjMode mode;
	
	/**
	 * set the mode arguments in the schema.xml 
	 * configuration file to change the segment mode 
	 * for ansj.
	 * 
	 * @see TokenizerFactory#TokenizerFactory(Map<String, String)
	 */
	public AnsjTokenizerFactory(Map<String, String> args) {
		super(args);
		
		String _mode = get(args, "mode");
		if (_mode == null) {
			mode = AnsjMode.BASE;
		} else {
			try {
				_mode = _mode.toUpperCase();
				mode = AnsjMode.valueOf(_mode);
			} catch (Exception e) {
				mode = AnsjMode.BASE;
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see org.apache.lucene.analysis.util.TokenizerFactory#create(org.apache.lucene.util.AttributeFactory, java.io.Reader)
	 */
	@Override
	public Tokenizer create(AttributeFactory factory, Reader input) {
		return new AnsjTokenizer(input, mode);
	}
}
