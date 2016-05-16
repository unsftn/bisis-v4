package com.gint.app.bisis4.client.circ.warnings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.gint.app.bisis4.client.circ.model.WarnCounters;
import com.gint.app.bisis4.client.circ.model.WarningTypes;

public class Counters {
	
	private HashMap counters = null;
  private WarningTypes wt = null;
	
	public Counters(List list, WarningTypes wt){
		this.wt = wt;
		counters = new HashMap();
		Iterator it = list.iterator();
		WarnCounters wc = null;
		while (it.hasNext()){
			wc = (WarnCounters)it.next();
			counters.put(wc.getWarnYear(), wc);
    }
	}
	
	public int getNext(int year){
	  	Integer key = new Integer(year);
	  	int value = 0;
	  	if (counters.containsKey(key)){
         WarnCounters wc = (WarnCounters)counters.get(key);
  	     value = wc.getLastNo();
  	     value++;
         wc.setLastNo(value);
	  	}else{
         WarnCounters wc = new WarnCounters();
         wc.setWarningTypes(wt);
         wc.setWarnYear(year);
         wc.setLastNo(1);
	  		 counters.put(key, wc);
	  		 value = 1;
	  	}
	  	return value;
	}
	
	public void turnBack(int year){
		Integer key = new Integer(year);
		int value;
		if (counters.containsKey(key)){
       WarnCounters wc = (WarnCounters)counters.get(key);
       value = wc.getLastNo();
       value--;
       wc.setLastNo(value);
		}
	}
	
	public List<WarnCounters> getList(){
      List<WarnCounters> list = new ArrayList<WarnCounters>();
			Set set = counters.keySet();
			Iterator it = set.iterator();
			while (it.hasNext()){
				Integer key = (Integer)it.next();
				list.add((WarnCounters)counters.get(key));
      }
      return list;
  }

}
