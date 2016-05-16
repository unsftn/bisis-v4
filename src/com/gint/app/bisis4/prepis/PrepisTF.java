package com.gint.app.bisis4.prepis;

import com.gint.app.bisis4.records.Godina;
import com.gint.app.bisis4.records.Record;

public class PrepisTF {
	
	
	
	public static Record prepisiZapis(Record rec){
		rec.poljaUGodine();
		for(Godina g:rec.getGodine()){
			PrepisGeneral.createSveske(g);		 
		}
		
		if(rec.getSubfieldContent("001c")!=null && rec.getSubfieldContent("001c").equals("s"))
				rec.setPubType(2);
		else
			rec.setPubType(1);
		
		return rec;
		
	}
}
