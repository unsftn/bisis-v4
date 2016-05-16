package com.gint.app.bisis4.prepis;

import com.gint.app.bisis4.records.Godina;
import com.gint.app.bisis4.records.Primerak;
import com.gint.app.bisis4.records.Record;
import com.gint.app.bisis4.utils.LatCyrUtils;

public class PrepisAPV {
	
	public static Record prepisiZapis(Record rec){
		for(Primerak p: rec.getPrimerci()){
			String intOznaka = p.getSigIntOznaka();
			if(intOznaka!=null){
				if(intOznaka.contains("-")){
					p.setSigIntOznaka(intOznaka.substring(0,intOznaka.indexOf('-')).replace('O', '0'));
					p.setSigNumerusCurens(intOznaka.substring(intOznaka.indexOf('-')+1));
					
				}else if(intOznaka.length()>2){
					p.setSigIntOznaka(intOznaka.substring(0,2));
					String numCur = intOznaka.substring(2);				
					while(numCur.startsWith("0"))
						numCur = numCur.substring(1);
					p.setSigNumerusCurens(numCur);
				}
			}
			if(p.getInvBroj()==null || p.getInvBroj().equals(""))
				p.setInvBroj("00000000000");
			if(p.getNapomene()!= null && p.getNapomene().contains("/") && p.getNapomene().indexOf("/")==3){
				String str = p.getNapomene().substring(0,p.getNapomene().indexOf("/")).trim();				
				String podlokacija = str.substring(0,1);
				String internaOznaka = str.substring(1,2);
				if(podlokacija!=null){
					if(podlokacija.equals("S"))
						podlokacija = "SS";
					if(podlokacija.equals("Z"))
						podlokacija = "ZZ";
					if(podlokacija.equals("C"))
						podlokacija = "CC";
				}
				podlokacija = LatCyrUtils.toLatin(podlokacija).toUpperCase();
				if(!podlokacija.equals("?"))
					p.setSigPodlokacija(podlokacija);
				
				if("1234567890".contains(internaOznaka))
					p.setSigIntOznaka(internaOznaka);				
			}			
		}
		
		for(Godina g:rec.getGodine()){
			if(g.getSigPodlokacija()==null || !g.getSigPodlokacija().equals("SL")){
				PrepisGeneral.createSveske(g);
			}
			if(g.getSigPodlokacija()!=null){
				if(g.getSigPodlokacija().equals("SL"))
					g.setPovez("t");
				if(g.getSigPodlokacija().equals("C"))
					g.setPovez("m");
			}
		}
		
		return rec;
	}

}
