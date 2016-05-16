package com.gint.app.bisis4.prepis;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.gint.app.bisis4.client.editor.inventar.InventarConstraints;
import com.gint.app.bisis4.format.validators.YearValidator;
import com.gint.app.bisis4.records.Field;
import com.gint.app.bisis4.records.Godina;
import com.gint.app.bisis4.records.Primerak;
import com.gint.app.bisis4.records.Record;

public class PrepisKI {
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	private static YearValidator yv = new YearValidator();
	
	
	public static Record prepisiZapis(Record rec){
		
		
		
		/*
		 * ako primerak ili godina nema datum inventarisanja
		 * upisuje se XXXX0101 gde je XXXX sadrzaj potpolja 210d
		 * ili 100c ako u 210 godina nije dobrog formata
		 *  
		 */
		for(Primerak p:rec.getPrimerci()){
			if(p.getDatumInventarisanja()== null ||p.getDatumInventarisanja().equals(""))
				if(getGodinaIzdanja(rec)!=null)
					try {
						p.setDatumInventarisanja(sdf.parse(getGodinaIzdanja(rec)+"0101"));
					} catch (ParseException e) {						
			}
		}
		
		for(Godina p:rec.getGodine()){
			if(p.getDatumInventarisanja()== null ||p.getDatumInventarisanja().equals(""))
				if(getGodinaIzdanja(rec)!=null)
					try {
						p.setDatumInventarisanja(sdf.parse(getGodinaIzdanja(rec)+"0101"));
					} catch (ParseException e) {						
			}
		}		
		return rec;		
	}
	
	
	private static String getGodinaIzdanja(Record rec){
		
		String str210d="";
		for(Field f:rec.getFields("210")){
			if(f.getSubfieldContent('d')!=null)
				str210d = f.getSubfieldContent('d');
		}		
		if(!str210d.equals("") && 
				yv.isValid(str210d).equals("")){			
			if(!str210d.contains("?"))
				return str210d;			
		}else{
			if(rec.getSubfieldContent("100c")!=null && 
					yv.isValid(rec.getSubfieldContent("100c")).equals(""))
				if(!rec.getSubfieldContent("100c").contains("?"))
					return rec.getSubfieldContent("100c");	
		}
		return null;		
	}
	
}
