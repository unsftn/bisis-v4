package com.gint.app.bisis4.remoting;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServlet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class InitializerServlet extends HttpServlet {

  public void init(ServletConfig cfg) {
    String factoryName = cfg.getInitParameter("jndiFactory");
    String queueName = cfg.getInitParameter("jndiQueue");
    luceneIndex = cfg.getInitParameter("luceneIndex");
    try {
      Context ctx = new InitialContext();
      ConnectionFactory cf = (ConnectionFactory)ctx.lookup(
          "java:comp/env/" + factoryName);
      Queue queue = (Queue)ctx.lookup("java:comp/env/" + queueName);
      conn = cf.createConnection();
      Session session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
      MessageConsumer consumer = session.createConsumer(queue);
      consumer.setMessageListener(new RecordConsumer(luceneIndex));
      conn.start();
    } catch (NamingException ex) {
      log.fatal(ex);
    } catch (JMSException e) {
      log.fatal(e);
    }
  }
  
  public void destroy() {
    try {
      conn.close();
    } catch (JMSException e) {
      log.fatal(e);
    }
  }
  
  public static String getIndexPath() {
    return luceneIndex;
  }

  private Connection conn;
  private static String luceneIndex;
  private static Log log = LogFactory.getLog(InitializerServlet.class);
}
