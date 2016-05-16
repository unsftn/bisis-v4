package com.gint.app.bisis4.prepis;

import com.gint.app.bisis4.records.Field;
import com.gint.app.bisis4.records.Godina;
import com.gint.app.bisis4.records.Primerak;
import com.gint.app.bisis4.records.Record;
import com.gint.app.bisis4.records.RecordFactory;
import com.gint.app.bisis4.records.Subfield;
import com.gint.app.bisis4.records.Subsubfield;
import com.gint.app.bisis4.records.Sveska;
import com.gint.app.bisis4.utils.LatCyrUtils;


public class PrepisPA {
	
	public static Record srediPolja996(Record rec){
		for(Field f: rec.getFields("996")){
			
			if(f.getSubfieldCount()==1 && f.getSubfield(0)!=null 
					&& (f.getSubfield(0).getContent()== null || f.getSubfield(0).getContent().equals(""))){
				rec.remove(f);
				continue;
			}
			
			if(f.getSubfield('f')==null || f.getSubfield('f').getContent()==null || f.getSubfield('f').getContent().equals("")){
				rec.remove(f);
				continue;
			}
				
			
			if(f.getSubfield('8')!=null){
				String darodavac = "";
				Subfield sf9968 = f.getSubfield('8');
				if(sf9968.getSubsubfieldCount()>0)
					if(sf9968.getSubsubfield('3')!=null)
						darodavac = sf9968.getSubsubfield('3').getContent();
				else
					darodavac = sf9968.getContent();
				if(!darodavac.equals(""))
					if(f.getSubfield('2')!=null) f.getSubfield('2').setContent(darodavac);
					else
						f.add(new Subfield('2',darodavac));
			}
				
				if (f.getSubfield('d')!=null){					
					Subfield sfd = f.getSubfield('d');
					if(sfd.getSubsubfield('s')!=null){					
						Subsubfield ssfs = sfd.getSubsubfield('s');
						Subsubfield ssfd = new Subsubfield('d');
						sfd.add(ssfd);
						// pivremeno stavljam podatak iz s u dublet da bi se prepisao u novu verziju
						// posle se parsira
						ssfd.setContent(ssfs.getContent());
					}						
				}				
						
		}	
		return rec;
	}
	
	
	public static Record prepisiZapis(Record rec){		
		if(rec.getGodine()!=null && rec.getGodine().size()>0){			
			for (Godina g:rec.getGodine()){				
				PrepisGeneral.createSveske(g);				
			}
		}		
		String str001a = rec.getSubfieldContent("001a");		
		if(str001a!=null && !str001a.equals("n") && !str001a.equals("i")){
			Field f992 = rec.getField("992");
			if(f992==null){
				f992 = new Field("992");
				rec.add(f992);
			}
			f992.add(new Subfield('c',str001a));
			if(str001a.equals("rs"))
				rec.getSubfield("001a").setContent("i");
			else
				rec.getSubfield("001a").setContent("n");
		}
		
		srediSifre(rec);
		srediInventarniBrojSveske(rec);
		srediSignaturu(rec);
		obrisinepotrebno(rec);
		return rec;
	}
	
	public static void srediSignaturu(Record rec){
		for(Primerak p:rec.getPrimerci()){
			String sig = p.getSigDublet();
			String invBroj  = p.getInvBroj();
			if(sig!=null && invBroj!=null && (invBroj.startsWith("01") || invBroj.startsWith("03")))
				p.setSigUDK(sig);
			else if(sig!=null && invBroj!=null && invBroj.startsWith("02"))
				p.setSigNumerusCurens(sig);
			else if(sig!=null && invBroj!=null && (invBroj.startsWith("05") || invBroj.startsWith("06"))){
				String[] sigArray = sig.split(" ");
				if(sigArray.length==4){			
					p.setSigPodlokacija(LatCyrUtils.toCyrillic(sigArray[0]));
					p.setSigIntOznaka(sigArray[1]);
					if(p.getSigIntOznaka()!=null && p.getSigIntOznaka().equals("cx"))
						p.setSigIntOznaka("сх");
					if(p.getSigIntOznaka()!=null && p.getSigIntOznaka().equals("сх"))
						p.setSigIntOznaka("ср");
					if(p.getSigIntOznaka()!=null && p.getSigIntOznaka().equals("cp"))
						p.setSigIntOznaka("ср");
					if(p.getSigIntOznaka()!=null && (p.getSigIntOznaka().equals("e") || p.getSigIntOznaka().equals("е")))
							p.setSigIntOznaka("e");
					
					p.setSigFormat(getFormat(sigArray[2]));
					p.setSigNumerusCurens(sigArray[3]);
				}else if(sigArray.length < 4){
					p.setSigUDK(sig);
				}
			
			}
			p.setSigDublet("");
		}		
	}
	
	/*
	 * rimski broj formata pretvara u arapski
	 */
	public static String getFormat(String rimski){
		if(rimski.equals("I")) return "1";
		if(rimski.equals("II")) return "2";
		if(rimski.equals("III")) return "3";
		if(rimski.equals("IV")) return "4";
		return rimski;		
	}
	
	public static void srediSifre(Record rec){
		for(Primerak p:rec.getPrimerci()){
			if(p.getNacinNabavke()!=null){
				if(p.getNacinNabavke().equals("kupovina") || p.getNacinNabavke().equals(LatCyrUtils.toCyrillic("kupovina")) 
						|| p.getNacinNabavke().equals("k1") || p.getNacinNabavke().contains("otkup") || p.getNacinNabavke().toLowerCase().contains("račun"))
					p.setNacinNabavke("k");
				if(p.getNacinNabavke().contains("poklon") || p.getNacinNabavke().toLowerCase().contains(LatCyrUtils.toCyrillic("poklon")))
					p.setNacinNabavke("p");				
				if(p.getStatus()==null ||  p.getStatus().equals(""))
					p.setStatus("A");
			}
		}
		for(Godina g:rec.getGodine()){
			if(g.getNacinNabavke()!=null){
				if(g.getNacinNabavke().equals("kupovina") || g.getNacinNabavke().equals(LatCyrUtils.toCyrillic("kupovina")) || g.getNacinNabavke().equals("k1")
						|| g.getNacinNabavke().contains("otkup") || g.getNacinNabavke().equals("k."));
					g.setNacinNabavke("k");
				if(g.getNacinNabavke().contains("poklon")|| g.getNacinNabavke().toLowerCase().contains(LatCyrUtils.toCyrillic("poklon")))
					g.setNacinNabavke("p");
				if(g.getNacinNabavke().equals("u pretplati"))
					g.setNacinNabavke("k");
				
			}
		}	
	}
	
	public static void srediInventarniBrojSveske(Record rec){
		if(rec.getGodine()!=null && rec.getGodine().size()>0)	
			for(Godina g : rec.getGodine())
				for(Sveska s : g.getSveske()){
					String stariInvBr = s.getInvBroj();
					//if(g.getInvBroj()!=null)
						s.setInvBroj(g.getInvBroj().substring(0, 2)+stariInvBr);
					/*else
						s.setInvBroj("00"+stariInvBr);*/
						
				}
	}
	
	private static void obrisinepotrebno(Record rec){
		for(Primerak p:rec.getPrimerci()){
			p.setOdeljenje(null);
		}
		for(Godina g:rec.getGodine()){
			g.setOdeljenje(null);
		}
		
	}
	
	
	
	
}
