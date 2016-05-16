package com.gint.app.bisis4.prepis;

import com.gint.app.bisis4.records.Primerak;
import com.gint.app.bisis4.records.Record;

public class PrepisPFSO {
	
	public static Record prepisiZapis(Record rec){
		for (Primerak p:rec.getPrimerci()){
			p.setInvBroj("00"+p.getInvBroj());
		}
		return rec;
	}

}
