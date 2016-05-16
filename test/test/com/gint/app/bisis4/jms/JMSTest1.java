package test.com.gint.app.bisis4.jms;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gint.app.bisis4.records.Record;
import com.gint.app.bisis4.remoting.RecordManager;

public class JMSTest1 {

  public static void main(String[] args) throws Exception {
    System.out.println("Creating Hessian proxy factory...");
    HessianProxyFactory proxyFactory = new HessianProxyFactory();
    System.out.println("Creating RecordManager proxy...");
    RecordManager recMgr = (RecordManager)proxyFactory.create(
        RecordManager.class, "http://localhost:8080/bisis/RecMgr");
    
    System.out.println("Retrieving record...");
    Record rec = recMgr.getRecord(1);
    System.out.println("Updating record...");
    recMgr.update(rec);
    System.out.println("Done.");
  }

}
