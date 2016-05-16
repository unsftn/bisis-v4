package com.gint.app.bisis4.client.circ.manager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang.SerializationUtils;
import org.apache.lucene.search.CachingWrapperFilter;
import org.apache.lucene.search.MatchAllDocsQuery;
import org.apache.lucene.search.Query;


import com.gint.app.bisis4.client.BisisApp;
import com.gint.app.bisis4.client.circ.Cirkulacija;
import com.gint.app.bisis4.commandservice.Service;
import com.gint.app.bisis4.client.circ.commands.GetPrimerakCommand;
import com.gint.app.bisis4.client.circ.commands.GetStatusPrimerkaCommand;
import com.gint.app.bisis4.client.circ.commands.GetSveskaCommand;
import com.gint.app.bisis4.client.circ.model.Primerci;
import com.gint.app.bisis4.client.circ.model.StatusPrimerka;
import com.gint.app.bisis4.client.circ.model.Sveske;
import com.gint.app.bisis4.records.Record;
import com.gint.app.bisis4.textsrv.BisisFilter;
import com.gint.app.bisis4.textsrv.Result;
import com.gint.app.bisis4.utils.QueryUtils;


public class RecordsManager {
	
	private Primerci primerak;
	private Sveske sveska;
	private List<Object> list;
	private Service service;

	
	public RecordsManager(){	
    init();
	}

  private void init(){
    list = new ArrayList<Object>();
    service = Cirkulacija.getApp().getService();
  }
  
  public Record lendBook(String ctlgno){ 
   int zaduziv = 0;
   Record record = null;
   GetPrimerakCommand getPrimerak = new GetPrimerakCommand(ctlgno);
   getPrimerak = (GetPrimerakCommand)service.executeCommand(getPrimerak);
   primerak = getPrimerak.getPrimerak();
   if (primerak != null){
	   listContainsPrimerak(primerak);
	   if (primerak.getStatusPrimerka() == null){
		   zaduziv = 1;
	   } else {
		   zaduziv = primerak.getStatusPrimerka().getZaduziv();
	   }
	   if (zaduziv == 1){
    	 if (primerak.getStanje() == 0){
    		 primerak.setStanje(1);
    		 list.add(primerak);
    		 record = getRecord(ctlgno);
    		 //record = BisisApp.getRecordManager().getRecord(primerak.getRecords().getRecordId());
    	 }
     }
   } else {
  	 GetSveskaCommand getSveska = new GetSveskaCommand(ctlgno);
     getSveska = (GetSveskaCommand)service.executeCommand(getSveska);
     sveska = getSveska.getSveska();
     if (sveska != null){
    	 listContainsSveska(sveska);
    	 if (sveska.getStatusPrimerka() == null){
    		 zaduziv = 1;
    	 }else{
    		 zaduziv = sveska.getStatusPrimerka().getZaduziv();
    	 }
    	 if (zaduziv == 1){
    		 if (sveska.getStanje() != 1){
    			 sveska.setStanje(1);
    			 list.add(sveska);
    			 record = getRecord(ctlgno);
    			 //record = BisisApp.getRecordManager().getRecord(sveska.getGodine().getRecords().getRecordId());
    		 }
       }
     }
   }
   return record;
  }
  
  public void listContainsPrimerak(Primerci prim){
	  Iterator<Object> it = list.iterator();
	  while (it.hasNext()){
		  Object tmp = it.next();
		  if (tmp instanceof Primerci && ((Primerci)tmp).getPrimerakId() == prim.getPrimerakId()){
			  list.remove(tmp);
			  return;
		  }
	  }
  }
  
  public void listContainsSveska(Sveske sv){
	  Iterator<Object> it = list.iterator();
	  while (it.hasNext()){
		  Object tmp = it.next();
		  if (tmp instanceof Sveske && ((Sveske)tmp).getSveskaId() == sv.getSveskaId()){
			  list.remove(tmp);
			  return;
		  }
	  }
  }
  
  public void returnBook(String ctlgno){ 
  	GetPrimerakCommand getPrimerak = new GetPrimerakCommand(ctlgno);
    getPrimerak = (GetPrimerakCommand)service.executeCommand(getPrimerak);
    primerak = getPrimerak.getPrimerak();
    if (primerak != null){
		 listContainsPrimerak(primerak);
	     if (primerak.getStanje() == 1){
	    	 primerak.setStanje(0);
	    	 list.add(primerak);
	     }
 	} else {
		 GetSveskaCommand getSveska = new GetSveskaCommand(ctlgno);
	     getSveska = (GetSveskaCommand)service.executeCommand(getSveska);
	     sveska = getSveska.getSveska();
		   if (sveska != null){
			 listContainsSveska(sveska);
		     if (sveska.getStanje() == 1){
		       sveska.setStanje(0);
		       list.add(sveska);
		     }
		   }
     }
    }
  
  public Object changeStanje(String ctlgno){ 
  	GetPrimerakCommand getPrimerak = new GetPrimerakCommand(ctlgno);
    getPrimerak = (GetPrimerakCommand)service.executeCommand(getPrimerak);
    Primerci primerak = getPrimerak.getPrimerak();
    if (primerak != null){
      if (primerak.getStanje() == 1){
        primerak.setStanje(0);
      }
      return primerak;
    } else {
    	GetSveskaCommand getSveska = new GetSveskaCommand(ctlgno);
	    getSveska = (GetSveskaCommand)service.executeCommand(getSveska);
	    Sveske sveska = getSveska.getSveska();
      if (sveska != null){
        if (sveska.getStanje() == 1){
          sveska.setStanje(0);
        }
      }
      return sveska;
    }
   }
  
  public String getErrorMessage(){
    String message = "";
    if (primerak == null && sveska == null){
      message = "Nepostojeci inventarni broj!";
    } else if (primerak != null){
      message = "Status primerka: ";
      if (primerak.getStatusPrimerka() != null){
      	message = message + primerak.getStatusPrimerka().getStatusOpis() + ", ";
      }
      if (primerak.getStanje() == 1){
      	message = message + "Zauzet";
      } else {
      	message = message + "Slobodan";
      }
      primerak = null;
    } else if (sveska != null){
      message = "Status primerka: ";
      if (sveska.getStatusPrimerka() != null){
      	message = message + sveska.getStatusPrimerka().getStatusOpis() + ", ";
      }
      if (sveska.getStanje() == 1){
      	message = message + "Zauzet";
      } else {
      	message = message + "Slobodan";
      }
      sveska = null;
    }
    return message;
  }
  
  public boolean existBook(){
    if (primerak == null && sveska == null){
      return false;
    }else{
      return true;
    }
  }
  
  public boolean chargedBook(){
    if (primerak != null ){
    	if (primerak.getStatusPrimerka() == null || primerak.getStatusPrimerka().getZaduziv() == 1){
    		return primerak.getStanje() == 1;
    	}
      return false;
    }else if (sveska != null){
    	if (sveska.getStatusPrimerka() == null || sveska.getStatusPrimerka().getZaduziv() == 1){
    		return sveska.getStanje() == 1;
    	}
      return false;
    }else{
      return false;
    }
  }
  
  public List getList(){
    return list;
  }
  
  public void releaseList(){
    list.clear();
  }

  public Record getRecord(String ctlgno){
    Record record = null;
    int[] hits = BisisApp.getRecordManager().select2x(SerializationUtils.serialize(QueryUtils.makeQueryTerm("IN", ctlgno, "", null)), null);
    if (hits!=null && hits.length != 0){
      record = BisisApp.getRecordManager().getRecord(hits[0]);
    }
    return record;
  }
  
  public int getRecordId(String ctlgno){
	    int[] hits = BisisApp.getRecordManager().select2x(SerializationUtils.serialize(QueryUtils.makeQueryTerm("IN", ctlgno, "", null)), null);
	    if (hits!=null && hits.length != 0){
	    	return hits[0];
	    }
	    return 0; //u slucaju greske
	  }
  
  public int getRecords(Query q, List list){
    Result res = null;
    List resultList = null;
    
    if (q != null){
      if (list != null){       
        CachingWrapperFilter filter = new CachingWrapperFilter(new BisisFilter(list));
        res = BisisApp.getRecordManager().selectAll3x(SerializationUtils.serialize(q),SerializationUtils.serialize(filter), "TI_sort");
        resultList = ListUtils.intersection(res.getInvs(),list);
      }else{
        res = BisisApp.getRecordManager().selectAll2x(SerializationUtils.serialize(q), "TI_sort");
        resultList = res.getInvs();
      }
    } else {
      CachingWrapperFilter filter = new CachingWrapperFilter(new BisisFilter(list));
      res = BisisApp.getRecordManager().selectAll3x(SerializationUtils.serialize(new MatchAllDocsQuery()),SerializationUtils.serialize(filter), "TI_sort");
      resultList = list;
    }
    if (res == null){
      return 0;
    }else if(res.getRecords().length == 0){
      return 1;
    }else{
      Cirkulacija.getApp().getMainFrame().getSearchBooksResults().setHits(res.getRecords());
      if (resultList != null)
        Cirkulacija.getApp().getMainFrame().getSearchBooksResults().setCtlgnoNum(resultList.size());
      Cirkulacija.getApp().getMainFrame().showPanel("searchBooksResultsPanel");
      return 2;
    }
  }
  
  public StatusPrimerka getStatus(String id){
  	if (id != null && !id.equals("")){
  		GetStatusPrimerkaCommand getStatus = new GetStatusPrimerkaCommand(id);
  		getStatus = (GetStatusPrimerkaCommand)service.executeCommand(getStatus);
  		return getStatus.getStatus();
  	} else {
  		return null;
  	}
  }

 
}
