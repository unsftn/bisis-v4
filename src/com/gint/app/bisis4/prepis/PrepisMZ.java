package com.gint.app.bisis4.prepis;

import java.util.Date;

import com.gint.app.bisis4.records.Author;
import com.gint.app.bisis4.records.Record;

public class PrepisMZ {
	
	public static Record prepisiZapis(Record rec){
		rec.setCreator(new Author("bisisImport","uns.ac.rs"));
		rec.setCreationDate(new Date());
		rec.setPubType(1);
		return rec;
	}

}
