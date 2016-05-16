package com.gint.app.bisis4.prepis;

import java.util.Date;

import com.gint.app.bisis4.records.Author;
import com.gint.app.bisis4.records.Godina;
import com.gint.app.bisis4.records.Primerak;
import com.gint.app.bisis4.records.Record;

public class PrepisIH {
	
	public static Record prepisiZapis(Record rec){
	//	rec.setCreationDate(new Date());		
		//rec.setCreator(new Author("prepisBISIS4","ih.uns.ac.rs"));
		for(Primerak p:rec.getPrimerci()){
			p.setInvBroj("00"+p.getInvBroj());
			p.setOdeljenje("00");
		}
		for(Godina g:rec.getGodine()){
			g.setInvBroj("00"+g.getInvBroj());
			g.setOdeljenje("00");
			PrepisGeneral.createSveske(g);
		}		
		return rec;
		
	}

}
