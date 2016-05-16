package com.gint.app.bisis4.textsrv;

import java.io.Reader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.LowerCaseFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.WhitespaceTokenizer;

public class BisisAnalyzer extends Analyzer {
  public TokenStream tokenStream(String fieldname, Reader reader) {
    return new LatCyrFilter(
        new LowerCaseFilter(
            new WhitespaceTokenizer(reader)));
            //new StandardTokenizer(reader)));
  }
}
