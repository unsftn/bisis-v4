package com.gint.app.bisis4.prepis;

import java.util.ArrayList;
import java.util.List;

import com.gint.app.bisis4.records.Field;
import com.gint.app.bisis4.records.Godina;
import com.gint.app.bisis4.records.Primerak;
import com.gint.app.bisis4.records.Record;
import com.gint.app.bisis4.records.Subfield;

public class PrepisEKSU {
	
	
	public static Record sredi996(Record rec){
		for(Field f:rec.getFields("996")){
			if(f.getSubfield('2')!=null){
				String invBroj = f.getSubfieldContent('2');
				if(f.getSubfield('f')==null){
					Subfield sff= new Subfield('f');
					sff.setContent(invBroj);
					f.add(sff);					
				}
				f.remove(f.getSubfield('2'));
			}
		}
		
		
		return rec;
	}
	
	
		
	public static Record prepisiZapis(Record rec){		
		List<Primerak> primerci = new ArrayList<Primerak>();
		for(Primerak p:rec.getPrimerci()){
			primerci.add(p.copy());
		}		
		rec.setPrimerci(new ArrayList());
		for(Primerak p:primerci){
			if((p.getInvBroj()!=null && !p.getInvBroj().equals(""))
					|| (p.getSigNumerusCurens()!=null && !p.getSigNumerusCurens().equals(""))
					|| (p.getSigPodlokacija()!=null && !p.getSigPodlokacija().equals("")))
				rec.getPrimerci().add(p);					
		}
		
		for(Primerak p:rec.getPrimerci()){
			p.setInvBroj("00"+p.getInvBroj());
			if(p.getSigPodlokacija()!=null && p.getSigPodlokacija().equals("P\u0160"))
				p.setSigPodlokacija("P\u0160O");
		}
		for(Godina g:rec.getGodine()){
			g.setInvBroj("00"+g.getInvBroj());
			if(g.getSigPodlokacija()!=null && g.getSigPodlokacija().equals("P\u0160"))
				g.setSigPodlokacija("P\u0160O");
		}
		
		return rec;
		
	}

}
