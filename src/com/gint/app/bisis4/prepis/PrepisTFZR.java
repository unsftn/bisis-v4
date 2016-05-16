package com.gint.app.bisis4.prepis;

import com.gint.app.bisis4.records.Primerak;
import com.gint.app.bisis4.records.Record;

public class PrepisTFZR {
	
	public static Record prepisiZapis(Record rec){
		
		for(Primerak p:rec.getPrimerci()){
			if(p.getSigPodlokacija()!=null && p.getSigPodlokacija().equals("Me"))
				p.setSigPodlokacija("Men");
			
		}
		return rec;
		
		
	}

}
