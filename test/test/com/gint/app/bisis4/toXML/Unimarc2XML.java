package test.com.gint.app.bisis4.toXML;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import com.caucho.burlap.client.BurlapProxyFactory;
import com.gint.app.bisis4.records.RecordFactory;
import com.gint.app.bisis4.records.Record;
import com.gint.app.bisis4.remoting.RecordManager;

public class Unimarc2XML {
	
	private static String outputFile = "XMLrecords.xml";

	public static void main(String[] args) throws Exception {
	    BurlapProxyFactory proxyFactory = new BurlapProxyFactory();
	    RecordManager recMgr = (RecordManager)proxyFactory.create(
	        RecordManager.class, "http://biblioteka.uns.ac.rs/bisis/RecMgr");
	    PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFile), "UTF8")));
	    
	    int[] hits = recMgr.select1("DT:s + LA:eng",null);
	    for (int i = 0; i<20 ; i++){
		    Record rec = recMgr.getRecord(hits[i]);
		    out.println(RecordFactory.toLooseXML(rec));
	    }
	    out.close();
	    
	  }
}
