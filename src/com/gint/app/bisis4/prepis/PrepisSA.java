package com.gint.app.bisis4.prepis;

import com.gint.app.bisis4.records.Godina;
import com.gint.app.bisis4.records.Primerak;
import com.gint.app.bisis4.records.Record;

public class PrepisSA {
	
	public static Record prepisiZapis(Record rec){
		for(Primerak p : rec.getPrimerci())
			if(p.getSigFormat()!=null && p.getSigFormat().equals("F."))
				p.setSigFormat("FOL");
		
		for(Godina g:rec.getGodine())
			if(g.getSigFormat()!=null && g.getSigFormat().equals("F."))
				g.setSigFormat("FOL");
		
		return rec;
	}
}
