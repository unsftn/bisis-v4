package com.gint.app.bisis4.prepis;

import java.util.ArrayList;
import java.util.List;

import com.gint.app.bisis4.records.Primerak;
import com.gint.app.bisis4.records.Record;

public class PrepisBGB {
	
	public static Record prepisiZapis(Record rec){
		
		List<Primerak> forRemove = new ArrayList<Primerak>();
		
		for(Primerak p:rec.getPrimerci()){			
			if((p.getInvBroj()==null || p.getInvBroj().equals("")) 
					  && (p.getSigFormat()==null || p.getSigFormat().equals(""))
					  && (p.getSigIntOznaka()==null || p.getSigIntOznaka().equals(""))
					  && (p.getSigNumerusCurens()==null || p.getSigNumerusCurens().equals(""))){
				forRemove.add(p);
				
			}
		}
		
		for(Primerak p:forRemove){
			rec.getPrimerci().remove(p);
		}		
		
		for(Primerak p:rec.getPrimerci()){
		//	p.setSigIntOznaka(p.getSigPodlokacija());
		//	p.setSigPodlokacija(null);
			if(p.getSigFormat()!=null)
				p.setSigFormat(p.getSigFormat().toUpperCase());
			if(p.getStatus()!=null && p.getStatus().equals("Ps"))
				p.setStatus("P");
			if(p.getOdeljenje()!=null){
				if(p.getOdeljenje().equals("03"))
					p.setSigPodlokacija("002");
				if(p.getOdeljenje().equals("04"))
					p.setSigPodlokacija("003");
			}
			if(p.getInvBroj()!=null && p.getInvBroj().length()>2){
				p.setOdeljenje(p.getInvBroj().substring(0,2));			
			}
			
		}
		
		return rec;
		
		
	}

}
