package com.gint.app.bisis4.client.editor.invholes;

import org.apache.commons.lang.SerializationUtils;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.WildcardQuery;

import com.gint.app.bisis4.client.BisisApp;

public class InvNumberHolesFinder {
	
	public static String getInvHolesfromIndex(String odeljenje, String invKnjiga, int odInt, int doInt){
//	 pogresna vrednost parametara
		if(odeljenje.equals("")) odeljenje = "*";
		if(invKnjiga.equals("")) invKnjiga = "*";
		if(odeljenje.equals("*") && invKnjiga.equals("*")) return null;
		if(!odeljenje.equals("*") && odeljenje.length()!=2) return null;
		if(!invKnjiga.equals("*") && invKnjiga.length()!=2) return null;
		
		// parametri su ok
		StringBuffer retVal = new StringBuffer();
 		//	
		int[] hits = {};  					
		if(!odeljenje.equals("*") && invKnjiga.equals("*")){				
			for (int brojac = odInt;brojac<=doInt;brojac++){
				String brojStr = String.valueOf(brojac);
				brojStr = "00000000000".substring(0, 7-brojStr.length())+brojStr;				
				String invBroj = odeljenje+"??"+brojStr;				
				hits = selectInvBroj(invBroj);			
				if(hits.length==0)
					 retVal.append(brojStr+"\n");				
			}
		}
		if(!invKnjiga.equals("*") && odeljenje.equals("*")){
			for (int brojac = odInt;brojac<=doInt;brojac++){
				String brojStr = String.valueOf(brojac);
				brojStr = "00000000000".substring(0, 7-brojStr.length())+brojStr;				
				String invBroj = "??"+invKnjiga+brojStr;				
				hits = selectInvBroj(invBroj);			
				if(hits.length==0)
					 retVal.append(brojStr+"\n");
			}
		}
		if(!invKnjiga.equals("*") && !odeljenje.equals("*")){
			for (int brojac = odInt;brojac<=doInt;brojac++){
				String brojStr = String.valueOf(brojac);
				brojStr = "00000000000".substring(0, 7-brojStr.length())+brojStr;				
				String invBroj = odeljenje+invKnjiga+brojStr;				
				hits = selectInvBroj(invBroj);			
				if(hits.length==0)
					 retVal.append(brojStr+"\n");
			}
		}
	 return retVal.toString();		
	}
	
	private static int[] selectInvBroj(String invBroj){
		Term t = new Term("IN",invBroj);
		WildcardQuery tq = new WildcardQuery(t);
		if(BisisApp.isStandalone())
			return BisisApp.getRecordManager().select2(tq, null);
		else
			return BisisApp.getRecordManager().select2x(SerializationUtils.serialize(tq), null);
	}

}
