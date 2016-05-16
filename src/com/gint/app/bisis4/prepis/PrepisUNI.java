package com.gint.app.bisis4.prepis;


import com.gint.app.bisis4.records.Godina;
import com.gint.app.bisis4.records.Primerak;
import com.gint.app.bisis4.records.Record;

public class PrepisUNI {

	public static Record prepisiZapis(Record rec){		
		for(Primerak p:rec.getPrimerci()){
			if(p.getInvBroj()!=null && !p.getInvBroj().equals("")){
				p.setInvBroj("00"+p.getInvBroj());
				p.setOdeljenje("00");
			}
		}
		for(Godina g:rec.getGodine()){
			if(g.getInvBroj()!=null && !g.getInvBroj().equals("")){
				g.setInvBroj("00"+g.getInvBroj());
				g.setOdeljenje("00");
			}
		}		
		return rec;
		
	}
}
