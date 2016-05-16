package com.gint.app.bisismobile;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.lucene.search.Query;

import com.gint.app.bisis4.records.Field;
import com.gint.app.bisis4.records.Primerak;
import com.gint.app.bisis4.records.Record;
import com.gint.app.bisis4.records.serializers.LooseXMLSerializer;
import com.gint.app.bisis4.records.serializers.PrimerakSerializer;
import com.gint.app.bisis4.utils.QueryUtils;


@SuppressWarnings("serial")
public class MobileQueryServlet extends HttpServlet {
	 
	 public MobileQueryServlet() {
		    super();
		  }

		  public void init(ServletConfig config) throws ServletException {
		    super.init(config);
		  }
		  
		  public void doGet(HttpServletRequest request, HttpServletResponse response)
				  throws ServletException, IOException {
			  doPost(request, response);
		  }
		  
		  public void doPost(HttpServletRequest request, HttpServletResponse response)
		    throws ServletException, IOException {
			response.setContentType("text/html;charset=UTF-8"); 
		    PrintWriter out = response.getWriter();
		    response.setContentType("text/html; charset=utf-8");
		    request.setCharacterEncoding(response.getCharacterEncoding());	  
		    
		    String action = request.getParameter("action");
		    UserBean user = new UserBean();
		    
		    if (action.equals("login")){
		    	user.setUser(request.getParameter("user"));
			    user.setPass(request.getParameter("pass"));
		    	boolean login = DBManager.login(user);
		    	if (login){
		    		out.println("OK");
		    	}else{
		    		out.println(user.getMsg());
		    	}
		    } else if (action.equals("query")){
		    	String bookid = request.getParameter("bookid");
	            Query q = QueryUtils.makeQueryTerm("IN", bookid, "", null);
	            if (q == null) {
	            	out.println("Query is null!");
	            }else{
		            int[] hits = DBManager.getDBManager().getRecMgr().select2(q, null);
				    String xmlrecord = "";
				    if (hits != null){
			    		Record [] records = DBManager.getDBManager().getRecMgr().getRecords(hits);
			    		Record record = records[0];
			    		Primerak p = record.getPrimerak(bookid);
			    		Field f = PrimerakSerializer.getField(p);
			    		record.add(f);
			    		xmlrecord = LooseXMLSerializer.toLooseXML(record);
					    out.println(xmlrecord);
				    }else{
				    	out.println("Record not found!");
				    }
	            }
		    } else if (action.equals("save")){
		    	String xmlrecord = request.getParameter("record");
		    	System.out.println("Received: ");
		    	System.out.println(xmlrecord);
		    	Record recordsent = LooseXMLSerializer.fromLooseXML(xmlrecord);
		    	Field f = recordsent.getField("996");
		    	Primerak p = PrimerakSerializer.getPrimerak(f);
		    	
	    		Record record = DBManager.getDBManager().getRecMgr().getRecord(recordsent.getRecordID());
	    		if (record != null){
		    		List<Primerak> list= record.getPrimerci();
		    		for (int i=0; i<list.size(); i++){
		    			if (list.get(i).getInvBroj().equals(p.getInvBroj())){
		    				Primerak p2= list.get(i);
		    				p2.setStatus(p.getStatus().equals("null") ? null : p.getStatus());
		    				p2.setDatumStatusa(p.getStatus().equals("null") ? null : p.getDatumStatusa());
		    				p2.setSigDublet(p.getSigDublet());
		    				p2.setSigFormat(p.getSigFormat());
		    				p2.setSigIntOznaka(p.getSigIntOznaka());
		    				p2.setSigNumerusCurens(p.getSigNumerusCurens());
		    				p2.setSigPodlokacija(p.getSigPodlokacija());
		    				p2.setSigUDK(p.getSigUDK());
		    			}
		    		}
		    		record = DBManager.getDBManager().getRecMgr().update(record);
		    		System.out.println("After update: ");
		    		System.out.println(record);
		    		if (record != null){
		    			out.println("OK");
		    		}else{
		    			out.println("Record not saved!");
		    		}
		    		 
            	}else{
            		out.println("Record not found!");
            	}	    	
		    } else if (action.equals("coder")) {
		    	out.println(DBManager.getStatusCoder());
		    }
	    	out.close();  
		  }
}
