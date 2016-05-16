package com.gint.app.bisis4.prepis;

import java.util.Date;

import com.gint.app.bisis4.records.Author;
import com.gint.app.bisis4.records.Field;
import com.gint.app.bisis4.records.Godina;
import com.gint.app.bisis4.records.Primerak;
import com.gint.app.bisis4.records.Record;

public class PrepisTSSU {
	
	public static Record prepisiZapis(Record rec){
		
		rec.setCreationDate(new Date());
		rec.setCreator(new Author("bisisimport","unknown"));
		if(rec.getSubfield("001c")!=null){
			String content = rec.getSubfieldContent("001c");
			if(content.equals("s"))
				rec.setPubType(2);
			else
				rec.setPubType(1);			
		}else
			rec.setPubType(1);	
		for(Primerak p:rec.getPrimerci()){
			if(p.getInvBroj()==null){
				p.setInvBroj("00000000000");
			}
			if(p.getNacinNabavke()!=null){
				p.setNacinNabavke(p.getNacinNabavke().toLowerCase());
			}
			if(p.getSigPodlokacija()!=null){
				p.setSigPodlokacija(p.getSigPodlokacija().replace("O", "0"));
			}
		}
		
		for(Godina g:rec.getGodine()){
			if(g.getInvBroj()==null){
				g.setInvBroj("00000000000");
			}
			if(g.getNacinNabavke()!=null){
				g.setNacinNabavke(g.getNacinNabavke().toLowerCase());
			}
			if(g.getSigPodlokacija()!=null){
				g.setSigPodlokacija(g.getSigPodlokacija().replace("O", "0"));
			}
		}	
		return rec;
	}

}
