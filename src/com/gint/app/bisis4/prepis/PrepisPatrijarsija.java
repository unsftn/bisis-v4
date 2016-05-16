package com.gint.app.bisis4.prepis;

import java.util.ArrayList;
import java.util.List;

import com.gint.app.bisis4.records.Godina;
import com.gint.app.bisis4.records.Primerak;
import com.gint.app.bisis4.records.Record;

public class PrepisPatrijarsija {
	
	public static Record prepisiZapis(Record rec){
		List<Primerak> primerci = new ArrayList<Primerak>();
		for(Primerak p:rec.getPrimerci()){
			primerci.add(p.copy());
		}
		
		List<Godina> godine = new ArrayList<Godina>();
		for(Godina g:rec.getGodine()){
			godine.add(g.copy());
		}
		
		rec.getPrimerci().clear();
		rec.getGodine().clear();
		
		for(Primerak p:primerci){
			if(p.getInvBroj()!=null && !p.getInvBroj().equals("")){
				rec.getPrimerci().add(p);
				if(p.getInvBroj().length()==9)
					p.setInvBroj("00"+p.getInvBroj());
			}
		}
		
		for(Godina g:godine){
			if(g.getInvBroj()!=null && !g.getInvBroj().equals("")){
				rec.getGodine().add(g);
				if(g.getInvBroj().length()==9)
					g.setInvBroj("00"+g.getInvBroj());
			}
		}		
		return rec;		
	}
	
	public static Record prepisiZapis1(Record rec){
	
		
		for(Primerak p:rec.getPrimerci()){
			if(p.getInvBroj()!=null && !p.getInvBroj().equals("")){				
				if(p.getInvBroj().length()==9)
					p.setInvBroj("00"+p.getInvBroj());
			}
		}
		
		for(Godina g:rec.getGodine()){
			if(g.getInvBroj()!=null && !g.getInvBroj().equals("")){			
				if(g.getInvBroj().length()==9)
					g.setInvBroj("00"+g.getInvBroj());
			}
		}		
		return rec;		
	}
	

}
