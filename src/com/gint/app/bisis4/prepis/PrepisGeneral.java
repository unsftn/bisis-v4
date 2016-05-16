package com.gint.app.bisis4.prepis;

import com.gint.app.bisis4.records.Godina;
import com.gint.app.bisis4.records.Sveska;
import com.gint.app.bisis4.utils.Signature;

public class PrepisGeneral {
	
	static int brojacSveske;	
	
	static{
		brojacSveske = 1;
	}
	
	
	public static void createSveske(Godina g){
		if(g!=null && g.getBroj()!=null){		
		String[] brojevi = g.getBroj().split("[,+]");	
		for(int i=0;i<brojevi.length;i++){	
			try{
			if(brojevi[i].contains("-")){				
				int drugiBroj = Integer.valueOf(brojevi[i]
				               .substring(brojevi[i].indexOf("-")+1)).intValue();
				int prviBroj = Integer.valueOf(brojevi[i]
				               .substring(0,brojevi[i].indexOf("-"))).intValue();
				
				int brojSvesaka = drugiBroj-prviBroj+1;			
				for(int j=0;j<brojSvesaka;j++){					
					createSveska(g,String.valueOf(prviBroj+j));
				}				
			}else{
				createSveska(g,brojevi[i]);
			}
			}catch(Exception e){
				
			}
		}
		}	
	}
	
	private static void createSveska(Godina g, String brojSveske){
		Sveska s = new Sveska();		
		s.setKnjiga(g.getGodiste());
		s.setSignatura(Signature.format(g));		
		s.setInvBroj(generateInvBroj());		
		s.setBrojSveske(brojSveske);
		//s.setStatus("");
		g.add(s);
	}
	
	private static String generateInvBroj(){		
		StringBuffer buff = new StringBuffer();
		buff.append("00990000000");
		String broj = String.valueOf(brojacSveske++);		
		return buff.replace(11-broj.length(),11,broj).toString();
	}
	
	
	
	public static void main(String[] args){
		String str = "12,13,14+3/4";
		String[] splited = str.split("[,+]");
		for(String s:splited){
			System.out.println(s);
		}
	}

}
