package com.gint.app.bisis4web.views;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.gint.app.bisis4.format.HoldingsDataCodersJdbc;
import com.gint.app.bisis4.records.Godina;
import com.gint.app.bisis4.records.Primerak;
import com.gint.app.bisis4.records.Record;
import com.gint.app.bisis4.utils.Signature;

public class Utils {
	Record rec;

	public Utils(Record rec) {
		this.rec = rec;
	}
	
	 public String getSignatura(){
	    if (rec == null)
	      return "";
	    String text = "";
	    try {
	      Iterator<Primerak> it = rec.getPrimerci().iterator();
	      while (it.hasNext()){
	        if (!text.equals(""))
	          text = text + ", ";
	        text = text + Signature.format(it.next());
	      }
	      //String empty = text.substring(0, 1);
	    } catch (Exception e1) {
	      Iterator<Godina> it = rec.getGodine().iterator();
	      while (it.hasNext()){
	        if (!text.equals(""))
	          text = text + ", ";
	        text = text + Signature.format(it.next());
	      }
	    }
	    return text;
	  }
	 
	 
	 public  List<List<String>> getRaspodela(){
	    if (rec == null)
	      return null;
	    HashMap<String,List<Integer>> map = new HashMap<String,List<Integer>>();
	    String key = null;
	    //int num;
	    //String text = "";
	    Primerak pr = null;
	    try {
	      List<Primerak> fields = rec.getPrimerci();
	      Iterator<Primerak> it = fields.iterator();
	      while (it.hasNext()){
	      	pr = it.next(); 
	      	int zaduzivost = 1;
	      	if (pr.getStatus() != null){
	      		zaduzivost = HoldingsDataCodersJdbc.getZaduzivostStatusa(pr.getStatus());
	      	}
	      	if (zaduzivost != 2){
	      		key = pr.getOdeljenje();
	      		if (key != null){
		      		if (map.containsKey(key)){
		      			map.get(key).set(0, map.get(key).get(0).intValue()+1);
		      			if (zaduzivost == 1 && pr.getStanje() == 0){
		      				map.get(key).set(1, map.get(key).get(1).intValue()+1);
		      			}
		      		}else{
		      			List<Integer> list = new ArrayList<Integer>();
		      			list.add(0, 1);
		      			if (zaduzivost == 1 && pr.getStanje() == 0){
		      				list.add(1, 1);
		      			}else{
		      				list.add(1, 0);
		      			}
		      			map.put(key, list);
		      		}
	      		}
	      	}
	      }
	      
	      
	      List<String> list = new ArrayList<String>();
	      list.addAll(map.keySet());
	      Collections.sort(list);
	      Iterator<String> it2 = list.iterator();
	      List<List<String>> res = new ArrayList<List<String>>();
	      List<String> item;
	      while (it2.hasNext()){
	        key = it2.next();
	        item = new ArrayList<String>();
	        String name = HoldingsDataCodersJdbc.getValue(HoldingsDataCodersJdbc.ODELJENJE_CODER, key);
	        if (name != null){
	        	item.add(name);
	        }else{
	        	item.add(key);
	        }
	        item.add(map.get(key).get(0).toString());
	        item.add(map.get(key).get(1).toString());
	        res.add(item);
	      }
	      return res;
	    } catch (Exception e1) {
	    	e1.printStackTrace();
	    	return null;
	    }
	    
	  }
	 
	 public int getSlobodnih(){
		 if (rec == null)
	      return 0;
		 Primerak pr = null;
		 int res = 0;
		 try {
	      Iterator<Primerak> it = rec.getPrimerci().iterator();
	      while (it.hasNext()){
	      	pr = it.next(); 
	      	if ((pr.getStatus() == null || 
	      			pr.getStatus().equals("") || 
	      			HoldingsDataCodersJdbc.getZaduzivostStatusa(pr.getStatus()) == 1) 
	      			&& pr.getStanje() == 0){
	      		res++;
	      	}
	      }
	      return res;
	    } catch (Exception e1) {
	    	e1.printStackTrace();
	    	return 0;
	    }
	 }
	 
	 public String getInvBrojevi(){
		 if (rec == null)
	      return null;
		 Primerak pr = null;
		 String res = "";
		 try {
	      Iterator<Primerak> it = rec.getPrimerci().iterator();
	      while (it.hasNext()){
	      	pr = it.next(); 
	      	res = res + pr.getInvBroj() + " ";
	      }
	      return res;
	    } catch (Exception e1) {
	    	e1.printStackTrace();
	    	return null;
	    }
	 }

}
