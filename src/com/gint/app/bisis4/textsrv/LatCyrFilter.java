package com.gint.app.bisis4.textsrv;

import java.io.IOException;

import org.apache.lucene.analysis.Token;
import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;

import com.gint.app.bisis4.utils.LatCyrUtils;

public class LatCyrFilter extends TokenFilter {
  
  public LatCyrFilter(TokenStream input) {
    super(input);
  }
  
  public Token next() throws IOException {
    Token nextToken = input.next();
    if (nextToken == null)
      return null;
    String term = nextToken.termText();
    term =  LatCyrUtils.toLatinUnaccented(term);
    return new Token(term, 0, term.length());
  }
  
// VERZIJA ZA LUCENE 2.4+
//
//  public Token next(Token reusableToken) throws IOException {
//    assert reusableToken != null;
//    Token nextToken = input.next(reusableToken);
//    if (nextToken == null)
//      return null;
//    String term = nextToken.term();
//    nextToken.setTermBuffer(LatCyrUtils.toLatinUnaccented(term));
//    return nextToken;
//  }

  

}
