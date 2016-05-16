package com.gint.app.bisis4.client.editor.groupinv;

import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.search.CachingWrapperFilter;
import org.apache.lucene.search.MatchAllDocsQuery;

import com.gint.app.bisis4.client.BisisApp;
import com.gint.app.bisis4.records.Godina;
import com.gint.app.bisis4.records.Primerak;
import com.gint.app.bisis4.records.Record;
import com.gint.app.bisis4.records.Sveska;
import com.gint.app.bisis4.textsrv.BisisFilter;

public class GroupInvRecordUtils {
	
	//private static List<Record> updatedRecords = new ArrayList<Record>(); 
	private static List<String> titles = new ArrayList<String>();
	
	public static Record getRecordForInv(String invBroj){
		List<String> invList = new ArrayList<String>();
		invList.add(invBroj);
		CachingWrapperFilter filter = new CachingWrapperFilter(new BisisFilter(invList));
    int[] recordIds = BisisApp.getRecordManager().select3(new MatchAllDocsQuery(),filter, null);    
    if(recordIds.length==0) return null;
    return BisisApp.getRecordManager().getRecord(recordIds[0]);		
	}
	
	public static List<Record> getRecordsForInvs(List<String> invList){
		CachingWrapperFilter filter = new CachingWrapperFilter(new BisisFilter(invList));
    int[] recordIds = BisisApp.getRecordManager().select3(new MatchAllDocsQuery(),filter, null);
    List<Record> recList = new ArrayList<Record>(); 
    for(Record rec: BisisApp.getRecordManager().getRecords(recordIds))
    	recList.add(rec);    
    return recList;		
	}
	

	
	/*
	 * pronalazi zapis sa inventarnim brojem invBroj
	 * stavlja ga u listu zapisa za azuriranje - updatedRecords
	 * a vraca konkretan primerak, godinu ili svesku
	 * na koji se odnosi prosledjeni inventarni broj 
	 */
	public static Object loadItem(String invBroj){
		Record rec = getRecordForInv(invBroj);
		if(rec==null) return null;
		if(rec.getSubfieldContent("200a")!=null)
			titles.add(rec.getSubfieldContent("200a"));
		else
			titles.add("");
		if(rec!=null){		
				//updatedRecords.add(rec);
			for(Primerak p:rec.getPrimerci())
				if(p.getInvBroj().equals(invBroj))				
					return p;
			for(Godina g:rec.getGodine()){
				if(g.getInvBroj().equals(invBroj))
					return g;
				else
					for(Sveska s:g.getSveske())
						if(s.getInvBroj().equals(invBroj))
							return s;				
			}
		}
		return null;			
	}
	
/*
	public static Record getRecord(int index){
		return updatedRecords.get(index);
	}
*/	
	
	public static String getTitle(int index){
		return titles.get(index);
	}
	
	public static void clearTitles(){
		titles.clear();
	}
	
	
	
	
	
	

}
