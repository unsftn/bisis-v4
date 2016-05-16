package com.gint.app.bisis4.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.WhitespaceAnalyzer;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Filter;
import org.apache.lucene.search.PhraseQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.QueryWrapperFilter;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.WildcardQuery;

import com.gint.util.string.StringUtils;


public class QueryUtils {

	public static Query makeLuceneAPIQuery(String pref1, String oper1,
			String text1, String pref2, String oper2, String text2,
			String pref3, String oper3, String text3, String pref4,
			String oper4, String text4, String pref5, String text5) {

		text1 = text1.toLowerCase();
		if (pref1.compareToIgnoreCase("SG") == 0) {
			text1 = toLuceneSignature(text1);
		}

		text2 = text2.toLowerCase();
		if (pref2.compareToIgnoreCase("SG") == 0) {
			text2 = toLuceneSignature(text2);
		}

		text3 = text3.toLowerCase();
		if (pref3.compareToIgnoreCase("SG") == 0) {
			text3 = toLuceneSignature(text3);
		}

		text4 = text4.toLowerCase();
		if (pref4.compareToIgnoreCase("SG") == 0) {
			text4 = toLuceneSignature(text4);
		}

		text5 = text5.toLowerCase();
		if (pref5.compareToIgnoreCase("SG") == 0) {
			text5 = toLuceneSignature(text5);
		}

		String lastOperator = "";
		Query q = null;
		if (!"".equals(text1)) {
			q = makeQueryTerm(pref1, text1, lastOperator, q);
			lastOperator = oper1;
		}
		if (!"".equals(text2)) {
			q = makeQueryTerm(pref2, text2, lastOperator, q);
			lastOperator = oper2;
		}
		if (!"".equals(text3)) {
			q = makeQueryTerm(pref3, text3, lastOperator, q);
			lastOperator = oper3;
		}
		if (!"".equals(text4)) {
			q = makeQueryTerm(pref4, text4, lastOperator, q);
			lastOperator = oper4;
		}
		if (!"".equals(text5)) {
			q = makeQueryTerm(pref5, text5, lastOperator, q);
		}
		return q;
	}

	
	public static Query makeQueryTerm(String pref, String text, String lastOperator, Query operand) {
		text = LatCyrUtils.toLatin(text);
		text = LatCyrUtils.removeAccents(text);
		 if(!nontokenized.contains(pref)){
			 text=StringUtils.clearDelimiters(text, delims);// da bi izbacio sve znakove interpukcije osim za UDK,ISBN, ISSN
	      }
		  if (isbnList.contains(pref)){
			  text=text.replace(" ", "");
		  }
		if(usersprefix==null){
			init();
		}
		if(text.contains("~")&&(text.contains("*"))){
			text=text.replace("~", "");
		}
		if (text.startsWith("~")){
			text=text.replaceFirst("~", "0start0 ");
		}
		if(text.endsWith("~")){
			text=text.replace("~", " 0end0");
			
		}
		String[] parts = text.split(" ");
			
		Query query = null;
		Query polje7024=null;
		Query polje9024=null;
		BooleanQuery codesQuery=new BooleanQuery();
		BooleanQuery bquery = new BooleanQuery();
		BooleanQuery booleantemp = new BooleanQuery();
		boolean ok=false;
		String novi=pref;
		if(usersprefix.contains(pref)){
			pref="VA";
		}
		if (parts.length > 1) {
				Query temp = null;
				
				if (text.contains("*") || (text.contains("?"))) {
					for (int i = 0; i < parts.length; i++) {
						if (!parts[i].equals("")) {
							if ((parts[i].contains("*"))|| (parts[i].contains("?"))) {
								temp = new WildcardQuery(new Term(pref, parts[i]));
							} else {
								temp = new TermQuery(new Term(pref, parts[i]));
							}
							booleantemp.add(temp, BooleanClause.Occur.MUST);
						}
					}
					if(usersprefix.contains(novi)){
						String[] cod=returnAuthorityCode(novi);
						for (int i=0;i<cod.length;i++){
							if(cod[i]!=null){
								  polje7024=new TermQuery(new Term("7024",cod[i]));
								  polje9024=new TermQuery(new Term("9024",cod[i]));
								  codesQuery.add(polje7024, BooleanClause.Occur.SHOULD);
								  codesQuery.add(polje9024, BooleanClause.Occur.SHOULD);
							}
							
						}
						booleantemp.add(codesQuery,BooleanClause.Occur.MUST);
						
					}
					if(operand==null){
					return booleantemp;
					}
					else{
						return makeBooleanQuery(operand,booleantemp, lastOperator);
					}
				}else { // imamo vise reci bez joker znakova, to je onda fraza
					PhraseQuery pquery = new PhraseQuery();
					for (int i = 0; i < parts.length; i++) {
						if (!parts[i].equals("")) {
							pquery.add(new Term(pref, parts[i]));
						}
					}
					if(usersprefix.contains(novi)){
						ok=true;
						String[] cod=returnAuthorityCode(novi);
						for (int i=0;i<cod.length;i++){
							if(cod[i]!=null){
								  polje7024=new TermQuery(new Term("7024",cod[i]));
								  polje9024=new TermQuery(new Term("9024",cod[i]));
								  codesQuery.add(polje7024, BooleanClause.Occur.SHOULD);
								  codesQuery.add(polje9024, BooleanClause.Occur.SHOULD);
							}
							
						}
						booleantemp.add(pquery,BooleanClause.Occur.MUST);
						booleantemp.add(codesQuery,BooleanClause.Occur.MUST);
						
					}
					if((operand==null)&&(!ok)){
						return pquery;
					}else if((operand==null)&&(ok)){
						return booleantemp;
					}else if((operand!=null)&&(!ok)){
						 return makeBooleanQuery(operand,pquery, lastOperator);
					}else{
						 return makeBooleanQuery(operand,booleantemp, lastOperator);
					}
					
				}
		} else { // samo jedna rec u polju
				if (!parts[0].equals("")) {
				      if ((parts[0].contains("*")) || (parts[0].contains("?"))) {
						query = new WildcardQuery(new Term(pref, parts[0]));
					   }else {
						   
						query = new TermQuery(new Term(pref, parts[0]));
					   }
					   if(usersprefix.contains(novi)){
							ok=true;
							String[] cod=returnAuthorityCode(novi);
							for (int i=0;i<cod.length;i++){
								if(cod[i]!=null){
								  polje7024=new TermQuery(new Term("7024",cod[i]));
								  polje9024=new TermQuery(new Term("9024",cod[i]));
								  codesQuery.add(polje7024, BooleanClause.Occur.SHOULD);
								  codesQuery.add(polje9024, BooleanClause.Occur.SHOULD);
								}
								
							}
							booleantemp.add(query,BooleanClause.Occur.MUST);
							booleantemp.add(codesQuery,BooleanClause.Occur.MUST);
							
						}
					  
						if((operand==null)&&(!ok)){
							return query;
						}else if((operand==null)&&(ok)){
							return booleantemp;
						}else if((operand!=null)&&(!ok)){
							 return makeBooleanQuery(operand,query, lastOperator);
						}else{
							 return makeBooleanQuery(operand,booleantemp, lastOperator);
						}
					  				     
					}
				}
		 return null;
		}

	private static String[] returnAuthorityCode(String prefix){
		String [] codes=new String[3];
		if(prefix.equals("PG")){
			codes[0]="912";
		}else if (prefix.equals("PO")){
			codes[0]="911";
		}else if (prefix.equals("PV")){
			codes[0]="730";
			codes[1]="903";
		}else if (prefix.equals("PD")){
			codes[0]="902";
		}else if (prefix.equals("RD")){
			codes[0]="915";
		}else if (prefix.equals("PE")){
			codes[0]="010";
		}else if (prefix.equals("AI")){
			codes[0]="904";
		}else if (prefix.equals("KM")){
			codes[0]="210";
		}else if (prefix.equals("IL")){
			codes[0]="440";
			codes[1]="919";
			codes[2]="920";
		}else if (prefix.equals("FO")){
			codes[0]="600";
		}else if (prefix.equals("UR")){
			codes[0]="340";
		}
		return codes;
	}
	private static String toLuceneSignature(String signature) {
		String newsig = "";
	
		while (!signature.equals("")) {
			if(signature.startsWith("*")){
				newsig=newsig+"*";
				signature=signature.substring(1);
			}
			newsig = newsig + "<" + signature.substring(0, 1) + ">";
			signature = signature.substring(1);
			int kosa = signature.indexOf("\\");
			if (kosa != -1) {
				newsig = newsig + signature.substring(0, kosa);
				signature = signature.substring(kosa + 1);
			} else {
				newsig = newsig + signature;
				signature = "";
			}
		}
		return newsig;
		
	}
	
	public static Query makeBooleanQuery(Query oper1,Query oper2, String operator) {
		BooleanQuery bquery=new BooleanQuery();
		if (operator.equalsIgnoreCase("AND")) {
			bquery.add(oper1, BooleanClause.Occur.MUST);
			bquery.add(oper2, BooleanClause.Occur.MUST);
		} else if (operator.equalsIgnoreCase("OR")) {
			bquery.add(oper1, BooleanClause.Occur.SHOULD);
			bquery.add(oper2, BooleanClause.Occur.SHOULD);
		} else if (operator.equalsIgnoreCase("NOT")) {
			bquery.add(oper1, BooleanClause.Occur.MUST);
			bquery.add(oper2, BooleanClause.Occur.MUST_NOT);
		}
      
			return bquery;
		
	}
	
	public static Query addToLuceneQuery(String pref,String oper, String text,Query query) {
		return makeQueryTerm(pref,text,oper,query);
	}
	
	public static Filter getQueryFilter(String query){
		try{
			WhitespaceAnalyzer sa= new WhitespaceAnalyzer();
			QueryParser p = new QueryParser("contents",sa);
			Query q = p.parse(query);
			Filter filter = new QueryWrapperFilter(q);
			return filter;
		}catch (Exception e){
			return null;
		}
	}
	
	private static void init(){
		usersprefix=new ArrayList<String>();
		usersprefix.add("PG");
		usersprefix.add("PO");
		usersprefix.add("PV");
		usersprefix.add("PD");
		usersprefix.add("RD");
		usersprefix.add("PE");
		usersprefix.add("AI");
		usersprefix.add("KM");
		usersprefix.add("IL");
		usersprefix.add("FO");
		usersprefix.add("UR");
		
	}
	private static List <String>usersprefix=null;
	 private static List nontokenized=new ArrayList();
	 private static List isbnList=new ArrayList();
	  static{
		  nontokenized.add("DC");
		  nontokenized.add("675a");
	      nontokenized.add("IN");
	  }
	  static{

		  isbnList.add("SN"); //za isbn i issn izbacujemo crtice
		  isbnList.add("SP");
		  isbnList.add("SC");
		  isbnList.add("BN");;
		  isbnList.add("010a");
		  isbnList.add("011a");	
	  }
	  private static String delims = ",;:\"()[]{}+/.!-" ;
	  
	  public static	void main(String [] args){
		  System.out.println(makeLuceneAPIQuery("SG", null, "fii\\ix\\n92724", "", null, "", "", null, "", "", null, "", "", "")
				); }

}
