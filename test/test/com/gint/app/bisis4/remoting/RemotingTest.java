package test.com.gint.app.bisis4.remoting;

import com.caucho.hessian.client.HessianProxyFactory;
import com.gint.app.bisis4.records.Record;
import com.gint.app.bisis4.remoting.RecordManager;

public class RemotingTest {

  public static void main(String[] args) throws Exception {
    HessianProxyFactory proxyFactory = new HessianProxyFactory();
    RecordManager recMgr = (RecordManager)proxyFactory.create(
        RecordManager.class, "http://localhost:8080/bisis/RecMgr");
    
    Record rec = recMgr.getRecord(1);
    System.out.println(rec);
    String user = recMgr.lock(1, "branko");
    System.out.println("record locked by: " + user);
    recMgr.unlock(1);
    System.out.println("unlocked");
    recMgr.update(rec);
    System.out.println("record updated");
    
  }
}
