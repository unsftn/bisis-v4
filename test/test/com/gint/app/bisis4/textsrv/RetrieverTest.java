package test.com.gint.app.bisis4.textsrv;

import org.apache.commons.dbcp.DriverManagerConnectionFactory;
import org.apache.commons.dbcp.PoolableConnectionFactory;
import org.apache.commons.dbcp.PoolingDataSource;
import org.apache.commons.pool.impl.GenericObjectPool;

import com.gint.app.bisis4.records.Record;
import com.gint.app.bisis4.remoting.RecordManagerImpl;

public class RetrieverTest {
  public static void main(String[] args) throws Exception {
    Class.forName("com.mysql.jdbc.Driver");
    GenericObjectPool connectionPool = new GenericObjectPool(null);
    DriverManagerConnectionFactory connectionFactory = 
      new DriverManagerConnectionFactory("jdbc:mysql://localhost/bisis35?characterEncoding=UTF-8", 
          "bisis35", "bisis35");
    PoolableConnectionFactory poolableConnectionFactory =
      new PoolableConnectionFactory(connectionFactory, connectionPool, null, 
          null, false, true);
    PoolingDataSource poolingDataSource = new PoolingDataSource(connectionPool);
    
    RecordManagerImpl recMgr = new RecordManagerImpl();
    recMgr.setDataSource(poolingDataSource);
    
    Record rec1 = recMgr.getRecord(1);
    
    System.out.println(rec1);
  }
}
