package test.com.gint.app.bisis4.dbmodel;

import java.sql.Connection;
import java.sql.DriverManager;

import com.gint.app.bisis4.client.backup.dbmodel.Model;
import com.gint.app.bisis4.client.backup.dbmodel.ModelFactory;

public class TestModel {

  public static void main(String[] args) throws Exception {
    Class.forName("com.mysql.jdbc.Driver");
    Connection conn = DriverManager.getConnection(
        "jdbc:mysql://localhost/bisis35?characterEncoding=UTF-8", "bisis35", "bisis35");
    conn.setAutoCommit(false);
    
    Model model = ModelFactory.createModel(conn);
    System.out.println(model);
    conn.close();
  }
}
