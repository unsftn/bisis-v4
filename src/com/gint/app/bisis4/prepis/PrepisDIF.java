package com.gint.app.bisis4.prepis;

import java.util.Date;

import com.gint.app.bisis4.records.Author;
import com.gint.app.bisis4.records.Field;
import com.gint.app.bisis4.records.Primerak;
import com.gint.app.bisis4.records.Record;
import com.gint.app.bisis4.records.Subfield;

public class PrepisDIF {
	
	public static Record prepisiZapis(Record rec){
		rec.setCreationDate(new Date());
		rec.setCreator(new Author("unknown","dif.uns.rs"));
		rec.setPubType(1);
		Field f001 = rec.getField("001");
		if(f001==null){
			f001 = new Field("001");
			rec.add(f001);
			f001.add(new Subfield('c',"m"));			
		}
		Subfield sfb = f001.getSubfield('b');
		if(sfb==null){
			sfb = new Subfield('b',"a");
			f001.add(sfb);
		}
		Subfield sfd = f001.getSubfield('d');
		if(sfd==null){
			sfd = new Subfield('d',"0");
			f001.add(sfd);
		}
		
		rec.sortFields();
		return rec;
	}
	
	

}
