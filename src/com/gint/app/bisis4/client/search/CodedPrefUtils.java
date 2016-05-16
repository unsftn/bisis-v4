/**
 * 
 */
package com.gint.app.bisis4.client.search;

import java.util.List;

import com.gint.app.bisis4.format.HoldingsDataCoders;
import com.gint.app.bisis4.format.PubTypes;
import com.gint.app.bisis4.format.UItem;
import com.gint.app.bisis4.format.USubfield;
import com.gint.app.bisis4.prefixes.PrefixConfigFactory;
import com.gint.app.bisis4.prefixes.def.DefaultPrefixMap;

/**
 * @author dimicb
 *
 */
public class CodedPrefUtils {
	
	public static boolean isPrefCoded(String pref){
		return getCodesForPrefix(pref)!=null;		
	}
	
	public static List<UItem> getCodesForPrefix(String pref){		
		List<UItem> items = null; 
		if(pref.length()>1 && isNumber(pref.substring(0, 2))){  //prefiks je ustvari potpolje			
			USubfield usf =PubTypes.getFormat().getSubfield(pref);			
			if(usf!=null && usf.getCoder()!=null)
				return usf.getCoder().getItems();		
		}
		if(pref.equals("AM") || pref.equals("ST") || pref.equals("LI")){
			// prefiksi za lokacijske podatke
			if(pref.equals("AM"))
			  return	HoldingsDataCoders.getCoder(HoldingsDataCoders.NACINNABAVKE_CODER);
			if(pref.equals("ST"))
				return HoldingsDataCoders.getCoder(HoldingsDataCoders.STATUS_CODER);
			if(pref.equals("LI"))
				return HoldingsDataCoders.getCoder(HoldingsDataCoders.DOSTUPNOST_CODER);
			
		}
		
		// pravi prefiks koji sadrzi vise potpolja
		DefaultPrefixMap prefMap = 
			(DefaultPrefixMap)PrefixConfigFactory.getPrefixConfig().getPrefixMap();
		List<String> sfList = prefMap.getSubfields(pref);
		for(String sfName : sfList){			
			USubfield usf = PubTypes.getFormat().getSubfield(sfName);			
			if(usf!=null && usf.getCoder()!=null)
				if(items==null)
					items = usf.getCoder().getItems();
				else{
					for(UItem it : usf.getCoder().getItems())
						if(!items.contains(it))
							items.add(it);
				}
		}	
		
		return items;		
	}
	
	private static boolean isNumber(String str){
		try{
			Integer.valueOf(str);
			return true;
		}catch(NumberFormatException e){
			return false;
		}
	}

}
