package com.gint.app.bisis4.client.search;

import org.apache.lucene.search.Query;


public class QueryPart {
  public QueryPart(Query query, String operator) {
    this.query = query;
    this.operator = operator;
  }
  public Query query;
  public String operator;
}

