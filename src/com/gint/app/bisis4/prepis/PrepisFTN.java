package com.gint.app.bisis4.prepis;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.gint.app.bisis4.records.Author;
import com.gint.app.bisis4.records.Field;
import com.gint.app.bisis4.records.Godina;
import com.gint.app.bisis4.records.Primerak;
import com.gint.app.bisis4.records.Record;
import com.gint.app.bisis4.records.Subfield;
import com.gint.app.bisis4.records.Sveska;


public class PrepisFTN {
	
	public static int redniBroj=0;	
	public static int redniBrojBezInv = 0;
	
	public static Record prepisiZapis(Record rec){
		redniBroj++;
		
		
		
		// 610 ->606
		
		if(rec.getField("610")!=null){
			for(Field f610:rec.getFields("610")){
				if(f610.getSubfield('a')!=null){
					String content610a = f610.getSubfieldContent('a');
					f610.getSubfield('a').setContent("");
					Field f606 = new Field("606");
					f606.add(new Subfield('a',content610a));
					rec.add(f606);
				}
			}					
		}
		
		while(rec.getField("610")!=null){
			rec.remove(rec.getField("610"));
		}
		
		//330 ->610
		
		
		if(rec.getField("330")!=null){
			for(Field f330:rec.getFields("330")){
				if(f330.getSubfield('a')!=null){
					String content330a = f330.getSubfieldContent('a');
					f330.getSubfield('a').setContent("");
					Field f610 = new Field("610");
					f610.add(new Subfield('a',content330a));
					rec.add(f610);
				}
			}					
		}
		
		while(rec.getField("330")!=null){
			rec.remove(rec.getField("330"));
		}
		
		
		//110b->326a
		
		
		if(rec.getField("110")!=null && rec.getField("110").getSubfield('b')!=null){
			String content = rec.getSubfieldContent("110b");
			Field f326 = rec.getField("326");
					if(f326==null){
						f326 = new Field("326");
						rec.add(f326);
				}
				 Subfield sfa = f326.getSubfield('a');
					if(sfa==null){
						sfa= new Subfield('a');						
						f326.add(sfa);
					}
					sfa.setContent(content);					
					rec.remove(rec.getField("110"));
			}
		
		
		
		// 210 100c
		
		
		if(rec.getFields("210")!=null){
			String godina = null;
			for(Field f:rec.getFields("210")){
				if(f.getSubfield('d')!=null){		
					godina = f.getSubfieldContent('d');
					continue;
				}
			}
		
			if(godina!=null){				
				if(godina.length()==4){
					try{
						Integer.valueOf(godina);
						Field f100 = rec.getField("100");
						if(f100==null){
							f100 = new Field("100");
							rec.add(f100);
						}
						Subfield sfc = f100.getSubfield('c');
						if(sfc==null){
							sfc= new Subfield('c');						
							f100.add(sfc);
						}
						sfc.setContent(godina);				
					}catch(NumberFormatException e){					
					}
				}
		}	
		}	
		
		rec.sortFields();
		
		
	
		
	
		//rec.setCreationDate(new Date());
		//rec.setCreator(new Author("unknown","unknown"));
		
		
		for(Primerak p:rec.getPrimerci()){
			//podlokacija
			if(p.getSigPodlokacija()!=null && (p.getSigPodlokacija().equals("C") || p.getSigPodlokacija().equals("\u0106") || p.getSigPodlokacija().startsWith("\u010C")))
				p.setSigPodlokacija("\u010C");
			
			if(p.getSigPodlokacija()!=null && p.getSigPodlokacija().startsWith("Z"))
				p.setSigPodlokacija("Z");
			
		
			
			if(p.getInvBroj()==null || p.getInvBroj().equals("")){
				redniBrojBezInv++;
				String broj = String.valueOf(redniBrojBezInv);
				p.setInvBroj("90000000000".substring(0,11-broj.length())+broj);
			}
			
			if(p.getOdeljenje()!=null && !p.getOdeljenje().equals(""))
				p.setOdeljenje(null);
			
			String lastSigPodlokacija = null;
			
			if(p.getSigPodlokacija()!=null && !p.getSigPodlokacija().equals("")){
				lastSigPodlokacija = p.getSigPodlokacija();				
			}
			
			String invBroj = p.getInvBroj();
			if(invBroj!=null){
				if(invBroj.startsWith("00"))
					invBroj = vratiBezNula(invBroj);				
				if(invBroj.length()==9)
					p.setInvBroj("00"+invBroj);
				else if (invBroj.length()<7){
					if(p.getSigPodlokacija()!=null){
						lastSigPodlokacija = p.getSigPodlokacija();
						if(p.getSigPodlokacija().equals("D")
							|| p.getSigPodlokacija().equals("D/I")
							|| p.getSigPodlokacija().equals("D/B")
							|| p.getSigPodlokacija().equals("D/M"))
						p.setInvBroj("0001"+"00000000000".substring(0, 11-4-invBroj.length())+invBroj);
						else if(p.getSigPodlokacija().equals("M")
							|| p.getSigPodlokacija().equals("U"))
						p.setInvBroj("0002"+"00000000000".substring(0, 11-4-invBroj.length())+invBroj);					
						else if(p.getSigPodlokacija().equals("SR")
							|| p.getSigPodlokacija().equals("MR"))						
							p.setInvBroj("0003"+"00000000000".substring(0, 11-4-invBroj.length())+invBroj); 
						else if(p.getSigPodlokacija().equals("\u010C")
							|| p.getSigPodlokacija().equals("Z"))
						p.setInvBroj("0004"+"00000000000".substring(0, 11-4-invBroj.length())+invBroj);
						else if(p.getSigPodlokacija().equals("LR"))
						p.setInvBroj("0005"+"00000000000".substring(0, 11-4-invBroj.length())+invBroj);
						else if  (p.getSigPodlokacija().equals("DR"))
							p.setInvBroj("0006"+"00000000000".substring(0, 11-4-invBroj.length())+invBroj);
						else 							
						p.setInvBroj("0000"+"00000000000".substring(0, 11-4-invBroj.length())+invBroj);
					}
					
					
					else if(lastSigPodlokacija!=null && !lastSigPodlokacija.equals("")){
						if(lastSigPodlokacija.equals("D")
								|| lastSigPodlokacija.equals("D/I")
								|| lastSigPodlokacija.equals("D/B")
								|| lastSigPodlokacija.equals("D/M"))
							p.setInvBroj("0001"+"00000000000".substring(0, 11-4-invBroj.length())+invBroj);
							else if(lastSigPodlokacija.equals("M")
								|| lastSigPodlokacija.equals("U"))
							p.setInvBroj("0002"+"00000000000".substring(0, 11-4-invBroj.length())+invBroj);					
							else if(lastSigPodlokacija.equals("SR")
								|| lastSigPodlokacija.equals("MR"))						
								p.setInvBroj("0003"+"00000000000".substring(0, 11-4-invBroj.length())+invBroj); 
							else if(lastSigPodlokacija.equals("\u010C")
								|| lastSigPodlokacija.equals("Z"))
							p.setInvBroj("0004"+"00000000000".substring(0, 11-4-invBroj.length())+invBroj);
							else if(lastSigPodlokacija.equals("LR"))
							p.setInvBroj("0005"+"00000000000".substring(0, 11-4-invBroj.length())+invBroj);
							else if  (lastSigPodlokacija.equals("DR"))
								p.setInvBroj("0006"+"00000000000".substring(0, 11-4-invBroj.length())+invBroj);
							else 							
								p.setInvBroj("0000"+"00000000000".substring(0, 11-4-invBroj.length())+invBroj);
						
					}else{
						p.setInvBroj("0002"+"00000000000".substring(0, 11-4-invBroj.length())+invBroj);
					}						
					}						
			}
				try{
				if(p.getInvBroj().length()<11){
					p.setInvBroj("0000"+"00000000000".substring(0, 11-4-invBroj.length())+invBroj);
				}
				}catch(StringIndexOutOfBoundsException e){					
				}			
				
				if(p.getStatus()!=null) p.setStatus(p.getStatus().toLowerCase());
				
				if(p.getPovez()!=null && (p.getPovez().equals("tp") || p.getPovez().equals("tk")))
					p.setPovez("t");
				if(p.getPovez()!=null && (p.getPovez().equals("mp") || p.getPovez().equals("mk")))
					p.setPovez("m");
				if(p.getNacinNabavke()!=null){
					if(p.getNacinNabavke().toLowerCase().equals("kupovina"))
						p.setNacinNabavke("k");
					if(p.getNacinNabavke().toLowerCase().equals("poklon"))
						p.setNacinNabavke("p");
					if(p.getNacinNabavke().equals("koordi"))
						p.setNacinNabavke("m");
				}
		
		}


		
		
		if(rec.getCreator()==null)
			rec.setCreator(new Author("unknown","ftn"));
		
		
	for(Primerak p:rec.getPrimerci()){
			if(p.getInvBroj()!=null){
			try {
				JedinstvenostInventarnogBroja.dodajInventarniBroj(p);
			} catch (IOException e) {
				
				//e.printStackTrace();
			}
			}
		}
		
		
		
		// generisanje svesaka
		
		List<Godina> newGodine = new ArrayList<Godina>();
		
		String godina = "";
		String previousGodina = "";
		
		Godina mainGodina = null;
		
		for(Godina g:rec.getGodine()){
			godina = g.getGodina();
			if(godina!=null){
				if(!godina.equals(previousGodina)){
					previousGodina = godina;
					mainGodina = g.copy();
					newGodine.add(mainGodina);					
					PrepisGeneral.createSveske(mainGodina);
					for(Sveska s:mainGodina.getSveske()){
						s.setKnjiga(mainGodina.getGodiste());
					}
				}else{
					PrepisGeneral.createSveske(g);
					List<Sveska> sveske = g.getSveske();
					for(Sveska s:sveske){
						s.setKnjiga(g.getGodiste());
						mainGodina.add(s);
					}									
				}				
			}			
		}
		
		rec.getGodine().clear();	
		rec.setGodine(newGodine);
		
		
		
		// godine
		for(Godina g:rec.getGodine()){
			// podlokacija
			
			if(g.getSigPodlokacija()!=null && (g.getSigPodlokacija().equals("C") || g.getSigPodlokacija().equals("\u0106") || g.getSigPodlokacija().startsWith("\u010C")))
				g.setSigPodlokacija("\u010C");
			
			if(g.getSigPodlokacija()!=null && g.getSigPodlokacija().startsWith("Z"))
				g.setSigPodlokacija("Z");
			
			if(g.getPovez()!=null && (g.getPovez().equals("tp") || g.getPovez().equals("tk")))
				g.setPovez("t");
			if(g.getPovez()!=null && (g.getPovez().equals("mp") || g.getPovez().equals("mk")))
				g.setPovez("m");
			
			if(g.getNacinNabavke()!=null){
				if(g.getNacinNabavke().toLowerCase().equals("kupovina"))
					g.setNacinNabavke("k");
				if(g.getNacinNabavke().toLowerCase().equals("poklon"))
					g.setNacinNabavke("p");
				if(g.getNacinNabavke().equals("koordi"))
					g.setNacinNabavke("m");
			}			
			
			if(g.getOdeljenje()!=null && !g.getOdeljenje().equals(""))
				g.setOdeljenje(null);
			
			
			String lastSigPodlokacija = null;
			
			if(g.getSigPodlokacija()!=null && !g.getSigPodlokacija().equals("")){
				lastSigPodlokacija = g.getSigPodlokacija();				
			}
			
			
			
			
			String invBroj = g.getInvBroj();
			if(invBroj!=null){
				if(invBroj.startsWith("00"))
					invBroj = vratiBezNula(invBroj);
				
				if(invBroj.length()==9)
					g.setInvBroj("00"+invBroj);
				else if (invBroj.length()<7){
					if(g.getSigPodlokacija()!=null){
						lastSigPodlokacija = g.getSigPodlokacija();
						if(g.getSigPodlokacija().equals("D")
							|| g.getSigPodlokacija().equals("D/I")
							|| g.getSigPodlokacija().equals("D/B")
							|| g.getSigPodlokacija().equals("D/M"))
						g.setInvBroj("0001"+"00000000000".substring(0, 11-4-invBroj.length())+invBroj);
						else if(g.getSigPodlokacija().equals("M")
							|| g.getSigPodlokacija().equals("U"))
						g.setInvBroj("0002"+"00000000000".substring(0, 11-4-invBroj.length())+invBroj);					
						else if(g.getSigPodlokacija().equals("SR")
							|| g.getSigPodlokacija().equals("MR"))
							g.setInvBroj("0003"+"00000000000".substring(0, 11-4-invBroj.length())+invBroj); 
						else if(g.getSigPodlokacija().equals("\u010C")
							|| g.getSigPodlokacija().equals("Z"))
						g.setInvBroj("0004"+"00000000000".substring(0, 11-4-invBroj.length())+invBroj);
						else if(g.getSigPodlokacija().equals("LR"))
						g.setInvBroj("0005"+"00000000000".substring(0, 11-4-invBroj.length())+invBroj);			
						else if(g.getSigPodlokacija().equals("DR"))
							g.setInvBroj("0006"+"00000000000".substring(0, 11-4-invBroj.length())+invBroj);
						else 
						g.setInvBroj("0000"+"00000000000".substring(0, 11-4-invBroj.length())+invBroj);
					}else if(lastSigPodlokacija!=null && !lastSigPodlokacija.equals("")){
						if(lastSigPodlokacija.equals("D")
								|| lastSigPodlokacija.equals("D/I")
								|| lastSigPodlokacija.equals("D/B")
								|| lastSigPodlokacija.equals("D/M"))
							g.setInvBroj("0001"+"00000000000".substring(0, 11-4-invBroj.length())+invBroj);
							else if(lastSigPodlokacija.equals("M")
								|| lastSigPodlokacija.equals("U"))
							g.setInvBroj("0002"+"00000000000".substring(0, 11-4-invBroj.length())+invBroj);					
							else if(lastSigPodlokacija.equals("SR")
								|| lastSigPodlokacija.equals("MR"))						
								g.setInvBroj("0003"+"00000000000".substring(0, 11-4-invBroj.length())+invBroj); 
							else if(lastSigPodlokacija.equals("\u010C")
								|| lastSigPodlokacija.equals("Z"))
							g.setInvBroj("0004"+"00000000000".substring(0, 11-4-invBroj.length())+invBroj);
							else if(lastSigPodlokacija.equals("LR"))
							g.setInvBroj("0005"+"00000000000".substring(0, 11-4-invBroj.length())+invBroj);
							else if  (lastSigPodlokacija.equals("DR"))
								g.setInvBroj("0006"+"00000000000".substring(0, 11-4-invBroj.length())+invBroj);
							else 							
								g.setInvBroj("0000"+"00000000000".substring(0, 11-4-invBroj.length())+invBroj);
						
					}else{
						g.setInvBroj("0004"+"00000000000".substring(0, 11-4-invBroj.length())+invBroj);
					}
						
			}
				try{
				if(g.getInvBroj().length()<11){
					g.setInvBroj("0000"+"00000000000".substring(0, 11-4-invBroj.length())+invBroj);
				}
				}catch(StringIndexOutOfBoundsException e){					
				}			
				
					
			
			if(g.getInvBroj()!=null){
				try {
					JedinstvenostInventarnogBroja.dodajInventarniBroj(g);
				} catch (IOException e) {
					
					//e.printStackTrace();
				}
		}		
	 }
	}		
	return rec;		
	}
	
	public static Record prepisiZapisIzBisisa(Record rec){
		for(Primerak p:rec.getPrimerci()){
			if(p.getInvBroj()!=null){
			try {
				JedinstvenostInventarnogBroja.dodajInventarniBroj(p);
			} catch (IOException e) {
				
				//e.printStackTrace();
			}
			}
		}		
		return rec;
	}

 private static String vratiBezNula(String str){
 		while(str.startsWith("0"))
 			str = str.substring(1);
 		return str;
 }
 
 
}
