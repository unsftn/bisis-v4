package com.gint.app.bisis4.prepis;


import com.gint.app.bisis4.records.Primerak;
import com.gint.app.bisis4.records.Record;

public class PrepisSZPB {
	
	public static Record prepisiZapis(Record rec){
		for(Primerak p:rec.getPrimerci()){
			if(p.getInvBroj()==null || p.getInvBroj().equals(""))
				p.setInvBroj("00000000000");			
		}
		return rec;
	}

}
