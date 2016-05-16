package com.gint.app.bisis4.prepis;


import com.gint.app.bisis4.records.Primerak;
import com.gint.app.bisis4.records.Record;

public class PrepisIB {
	
	public static Record prepisiZapis(Record rec){
		for(Primerak p:rec.getPrimerci()){
			if(p.getInvBroj()==null || p.getInvBroj().equals(""))
				p.setInvBroj("00009999999");			
		 else
		 	p.setInvBroj("00"+p.getInvBroj());			
			p.setOdeljenje(null);
		}
		return rec;
	}

}
