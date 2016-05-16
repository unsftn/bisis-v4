package com.gint.app.bisis4.prepis;

import com.gint.app.bisis4.records.Godina;
import com.gint.app.bisis4.records.Primerak;
import com.gint.app.bisis4.records.Record;
import com.gint.app.bisis4.records.Sveska;

public class PrepisIM {
	
	static int brojacSveske;
	static String prefix = "XX";
	public static int brojKracihInvBrojeva = 0; 
	
	static{
		brojacSveske = 1;
	}
	
	public static Record prepisiZapis(Record rec){		
		if(rec.getGodine()!=null && rec.getGodine().size()>0){			
			for (Godina g:rec.getGodine()){				
				PrepisGeneral.createSveske(g);				
				if(g.getInvBroj()==null){
					g.setInvBroj(findInvBroj(g, rec));
				}else if(g.getInvBroj().contains(".")){
					g.setInvBroj(g.getInvBroj()
							.substring(0, g.getInvBroj().indexOf(".")));					
			}			
		}
		}
		srediInventarneBrojeveNa11(rec);		
		return rec;
	}
	
	
	
	private static String findInvBroj(Godina godina, Record rec){
		if(godina!=null && godina.getGodina()!=null)
			for(Godina g:rec.getGodine()){
				if (g!=null && g.getGodina()!=null && g.getGodina().equals(godina.getGodina()))
					return g.getInvBroj();
			}
		return null;
		
	}
	
	
	
	private static void srediInventarneBrojeveNa11(Record rec){
		if(rec.getPrimerci()!=null)
			for(Primerak p:rec.getPrimerci()){
				String oldInvBroj = p.getInvBroj();
				if(oldInvBroj!=null && oldInvBroj.length()<9){
					brojKracihInvBrojeva++;
					oldInvBroj = oldInvBroj.substring(0, 3)+"0000000".substring(0,9-oldInvBroj.length())+oldInvBroj.substring(3);
				}
				if(oldInvBroj!=null){
				if( oldInvBroj.startsWith("02") ||
						oldInvBroj.startsWith("04")||
						oldInvBroj.startsWith("06")||
						oldInvBroj.startsWith("11")||
						oldInvBroj.startsWith("13")||
						oldInvBroj.startsWith("16")){
					p.setInvBroj("01"+oldInvBroj);
					p.setOdeljenje("01");
				}
				else{
					p.setInvBroj("00"+oldInvBroj);
					p.setOdeljenje("00");
				}
				}	
		}
		if(rec.getGodine()!=null)
			for(Godina g:rec.getGodine()){
				String oldInvBroj = g.getInvBroj();
				if(oldInvBroj!=null && oldInvBroj.length()<9){
					brojKracihInvBrojeva++;
					oldInvBroj = oldInvBroj.substring(0, 3)+"0000000".substring(0,9-oldInvBroj.length())+oldInvBroj.substring(3);
				}
				if(oldInvBroj!=null){
				if(oldInvBroj.startsWith("11")){
					g.setInvBroj("01"+oldInvBroj);
					g.setOdeljenje("01");
				}
				else{
					g.setInvBroj("00"+oldInvBroj);
					g.setOdeljenje("00");
				}				
				for(Sveska s:g.getSveske()){
					String oldSveskaInvBroj = s.getInvBroj();
					if(oldInvBroj.startsWith("11"))
						s.setInvBroj("01"+oldSveskaInvBroj);					
				else
						s.setInvBroj("00"+oldSveskaInvBroj);
				}
				}
			}
	}
	
	
	

}
