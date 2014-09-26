/**
 * 
 */
package org.ansj.solr.analysis;

import java.io.IOException;
import java.io.Reader;

import org.ansj.domain.Term;
import org.ansj.splitWord.Analysis;
import org.ansj.splitWord.analysis.*;
import org.ansj.util.AnsjReader;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
//import org.apache.lucene.analysis.tokenattributes.PositionIncrementAttribute;

/**
 * @author shiki
 *
 */
public class AnsjTokenizer extends Tokenizer {
	// 当前词
	private CharTermAttribute termAtt;
	// 偏移量
	private OffsetAttribute offsetAtt;
	// 距离
	//private PositionIncrementAttribute positionAttr;
	//
	protected Analysis ta = null;

	/**
	 * @param input
	 * @param mode
	 */
	public AnsjTokenizer(Reader input, AnsjMode mode) {
		super(input);
		switch (mode) {
		case BASE:
			ta = new BaseAnalysis(input);
			break;
		case TO:
			ta = new ToAnalysis(input);
			break;
		case NLP:
			ta = new NlpAnalysis(input);
			break;
		case INDEX:
			ta = new IndexAnalysis(input);
			break;
		default:
			break;
		}
		termAtt = addAttribute(CharTermAttribute.class);
		offsetAtt = addAttribute(OffsetAttribute.class);
		//positionAttr = addAttribute(PositionIncrementAttribute.class);
	}
	
	/* (non-Javadoc)
	 * @see org.apache.lucene.analysis.TokenStream#incrementToken()
	 */
	@Override
	public boolean incrementToken() throws IOException {
		clearAttributes();
		Term term = ta.next();
		if (term != null) {
			termAtt.append(term.getName());
			termAtt.setLength(term.getName().length());
			offsetAtt.setOffset(term.getOffe(), term.getOffe() + term.getName().length());
			return true;
		} else {
			return false;
		}
	}
	
	/* (non-Javadoc)
	 * @see org.apache.lucene.analysis.Tokenizer#reset()
	 */
	@Override
	public void reset() throws IOException {
		super.reset();
		ta.resetContent(new AnsjReader(this.input));
	}
}
