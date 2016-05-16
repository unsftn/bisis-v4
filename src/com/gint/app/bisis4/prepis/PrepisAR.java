package com.gint.app.bisis4.prepis;

import java.util.Date;

import com.gint.app.bisis4.records.Author;
import com.gint.app.bisis4.records.Record;

public class PrepisAR {
	
	public static Record prepisiZapis(Record rec){
		rec.setCreationDate(new Date());
		rec.setCreator(new Author("bisisImport","pmf.uns.ac.rs"));
		rec.setPubType(1);
		return rec;
	}

}
