package com.gint.app.bisis4.prepis;

import java.util.ArrayList;
import java.util.List;

import com.gint.app.bisis4.client.editor.recordtree.RecordUtils;
import com.gint.app.bisis4.records.Field;
import com.gint.app.bisis4.records.Godina;
import com.gint.app.bisis4.records.Primerak;
import com.gint.app.bisis4.records.Record;
import com.gint.app.bisis4.records.RecordFactory;
import com.gint.app.bisis4.records.Subfield;

public class PrepisNS {
	
	// nije istestirano!!!!!!!!!!!!!!!
	
	
	public static Record prepisiZapis(Record rec){		
		List<Primerak> primerci = new ArrayList<Primerak>();
		for(Primerak p:rec.getPrimerci()){
			Primerak prim = p.copy();
			primerci.add(prim);
		}
		List<Godina> godine = new ArrayList<Godina>();
		for(Godina g:rec.getGodine()){
			Godina god = g.copy();
			godine.add(god);
		}
		rec.getPrimerci().clear();
		rec.getGodine().clear();
		for(Primerak p:primerci){			
				if(!((p.getSigFormat()==null || p.getSigFormat().equals(""))
					&& (p.getSigNumerusCurens()==null || p.getSigNumerusCurens().equals("")) 
					&& (p.getSigUDK()==null || p.getSigUDK().equals(""))
					&& (p.getSigIntOznaka()==null || p.getSigIntOznaka().equals(""))
					&& (p.getInvBroj()==null || p.getInvBroj().equals("")))){					
			  	srediSignaturu(p);
			  	srediStatus(p);
			  	srediFormat(p);
			  	rec.getPrimerci().add(p);
			  	
			  	
			  }				
			}	
		for(Godina g:godine){
			if(!((g.getSigFormat()==null || g.getSigFormat().equals(""))
					&&(g.getSigNumerusCurens()==null || g.getSigNumerusCurens().equals("")) 
					&&(g.getSigUDK()==null || g.getSigUDK().equals(""))
					&&(g.getSigIntOznaka()==null || g.getSigIntOznaka().equals(""))
					&&(g.getInvBroj()==null || g.getInvBroj().equals("")))){								
					srediSignaturu(g);
			  srediFormat(g);
					rec.getGodine().add(g);
					if(g.getNacinNabavke()!=null && g.getNacinNabavke().equals("vp")) g.setNacinNabavke("p");
			}
		}		
		if(rec.getSubfield("001c")!=null && rec.getSubfield("001c").getContent().equals("a")){
			// analitika			
			// godina u 100c			
			String godina100c = rec.getSubfieldContent("100c");
			if(godina100c!=null && !godina100c.equals("") && godina100c.contains(".")){
				rec.getSubfield("100c").setContent(godina100c.substring(godina100c.lastIndexOf('.')+1).trim());				
			}			
			for(Primerak p:rec.getPrimerci()){				
				Field f675 = rec.getField("675");
				if(f675==null){
					f675 = new Field("675");
					rec.add(f675);
				}
				if(p.getInvBroj()==null || p.getInvBroj().equals("")){					
					if((p.getSigFormat()!=null && !p.getSigFormat().equals("")) 
							|| (p.getSigUDK()!=null && !p.getSigUDK().equals(""))){						
						Subfield sfb = new Subfield('b');
						f675.add(sfb);
						if(p.getSigFormat()!=null && p.getSigUDK()!=null)
							sfb.setContent(p.getSigFormat()+" "+p.getSigUDK());
						else if(p.getSigFormat()!=null)
							sfb.setContent(p.getSigFormat());
						else if(p.getSigUDK()!=null)
							sfb.setContent(p.getSigUDK());						
					}
				}					
			}
			rec.getPrimerci().clear();			
		}
		
		return rec;		
	}
	
	/*
	 * signatura za zavicajnu zbirku
	 * ako pocinje sa 31
	 * numerus kurens poslednje cifre iz inventarnog broja
	 * format:
	 * inv knj
	 * 		  31 - I
	 * 		  32 - II
	 * 		  33 - III 
	 * 
	 */
	private static void srediSignaturu(Primerak p){
		if(p.getInvBroj()!=null && p.getInvBroj().startsWith("31")){
			if(p.getSigNumerusCurens()==null || p.getSigNumerusCurens().equals("")){
				p.setSigNumerusCurens(getBrojFromInv(p.getInvBroj()));
				String invKnj = p.getInvBroj().substring(2,4);
				if(invKnj.equals("31"))
					p.setSigFormat("I");
				else if(invKnj.equals("32"))
					p.setSigFormat("II");
				else if(invKnj.equals("33"))
					p.setSigFormat("III");
			}				
		}		
	}
	
	private static void srediSignaturu(Godina g){
		if(g.getInvBroj()!=null && g.getInvBroj().startsWith("31")){
			if(g.getSigNumerusCurens()==null || g.getSigNumerusCurens().equals("")){
				g.setSigNumerusCurens(getBrojFromInv(g.getInvBroj()));
				String invKnj = g.getInvBroj().substring(2,4);
				if(invKnj.equals("31"))
					g.setSigFormat("I");
				else if(invKnj.equals("32"))
					g.setSigFormat("II");
				else if(invKnj.equals("33"))
					g.setSigFormat("III");
			}				
		}		
	}
	
	
	/*
	 * ako se prve dve cifre u inventarnom broju
	 * razlikuju od odeljenja stavlja se status 5 - preusmereno 
	 */
	private static void srediStatus(Primerak p){
		if(p.getInvBroj()!=null && !p.getInvBroj().equals("") 
				&& p.getInvBroj().length()>1 && !p.getInvBroj().substring(0, 2).equals(p.getOdeljenje()))
			p.setStatus("5");
	}	
	
	/*
	 * vraca poslednje cifre u inventarnom broj
	 * broj bez ogranka i inventarne knjige 
	 */
	public static String getBrojFromInv(String ceoBroj){
  	String ret = ceoBroj.substring(4);
  	while(ret.startsWith("0"))
  		ret = ret.substring(1);
  	return ret;
  }
	
	private static void sredi23(Record rec){
		List<Primerak> primerci = rec.getPrimerci();
		for(Primerak p:primerci){
			if(p.getOdeljenje().equals("23"))
				p.setInvBroj("23000000000");
		}
		List<Godina> godine = rec.getGodine();
		for(Godina g:godine){
			if(g.getOdeljenje().equals("23"))
				g.setInvBroj("23000000000");
		}
	}
	
	private static void srediFormat(Primerak p){
		if(p!=null && p.getSigFormat()!=null)
			if(p.getSigFormat().startsWith("DM"))
				p.getSigFormat().replace("DM", "");
		
	}
	
	private static void srediFormat(Godina g){
		if(g!=null && g.getSigFormat()!=null){
			if(g.getSigFormat().startsWith("DM"))
				g.getSigFormat().replace("DM", "");
			if(g.getSigFormat().equals("s")){
				g.setSigPodlokacija("s");
				g.setSigFormat(null);
			}
			if(g.getSigFormat()!=null && g.getSigFormat().equals("sr")){
				g.setSigPodlokacija("sr");
				g.setSigFormat(null);
			}
				
		}
		
	}
  
	
	public static void main (String[]args){
	
	}
	


}
