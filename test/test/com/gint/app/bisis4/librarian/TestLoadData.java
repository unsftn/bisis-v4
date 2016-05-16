package test.com.gint.app.bisis4.librarian;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

import com.gint.app.bisis4.format.PubTypes;
import com.gint.app.bisis4.librarian.Librarian;
import com.gint.app.bisis4.librarian.LibrarianContext;
import com.gint.app.bisis4.librarian.ProcessType;
import com.gint.app.bisis4.librarian.ProcessTypeCatalog;

public class TestLoadData {

  public static void main(String[] args) throws Exception {
    Class.forName("com.mysql.jdbc.Driver");
    Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/bisis35?characterEncoding=UTF-8", 
        "bisis35", "bisis35");
    conn.setAutoCommit(false);
    Statement stmt = conn.createStatement();
    stmt.executeUpdate("DELETE FROM tipovi_obrade");
    stmt.executeUpdate("DELETE FROM bibliotekari");
    stmt.close();

    ProcessType pt1 = new ProcessType();
    pt1.setName("Monografske - kompletna obrada");
    pt1.setPubType(PubTypes.getPubType(1));    
    pt1.getInitialSubfields().add(PubTypes.getFormat().getSubfield("001e"));
    pt1.getInitialSubfields().add(PubTypes.getFormat().getSubfield("200a"));
    pt1.getInitialSubfields().add(PubTypes.getFormat().getSubfield("700a"));
    pt1.getInitialSubfields().add(PubTypes.getFormat().getSubfield("700b"));
    pt1.getMandatorySubfields().add(PubTypes.getFormat().getSubfield("001e"));
    pt1.getMandatorySubfields().add(PubTypes.getFormat().getSubfield("200a"));
    ProcessTypeCatalog.register(pt1);
    ProcessType pt2 = new ProcessType();
    pt2.setName("Serijske - kompletna obrada");
    pt2.setPubType(PubTypes.getPubType(2));    
    pt2.getInitialSubfields().add(PubTypes.getFormat().getSubfield("001e"));
    pt2.getInitialSubfields().add(PubTypes.getFormat().getSubfield("200a"));
    pt2.getMandatorySubfields().add(PubTypes.getFormat().getSubfield("001e"));
    pt2.getMandatorySubfields().add(PubTypes.getFormat().getSubfield("200a"));
    ProcessTypeCatalog.register(pt2);
    
    LibrarianContext lc = new LibrarianContext();
    lc.setDefaultProcessType(pt1);
    lc.getProcessTypes().add(pt1);
    lc.getProcessTypes().add(pt2);
    
    PreparedStatement pstmt1 = conn.prepareStatement(
        "INSERT INTO tipovi_obrade (tipobr_id, tipobr_spec) VALUES (?, ?)");
    pstmt1.setInt(1, 1);
    pstmt1.setString(2, pt1.toXML());
    pstmt1.executeUpdate();
    pstmt1.setInt(1, 2);
    pstmt1.setString(2, pt2.toXML());
    pstmt1.executeUpdate();
    pstmt1.close();

    PreparedStatement pstmt2 = conn.prepareStatement(
        "INSERT INTO bibliotekari (ime, lozinka, obrada, cirkulacija, administracija, context) VALUES (?, ?, ?, ?, ?, ?)");
    pstmt2.setString(1, "admin");
    pstmt2.setString(2, "admin");
    pstmt2.setInt(3, 1);
    pstmt2.setInt(4, 1);
    pstmt2.setInt(5, 1);
    pstmt2.setString(6, lc.toXML());
    pstmt2.executeUpdate();
    conn.commit();
    
    ProcessTypeCatalog.init(conn);
    Librarian admin = new Librarian("admin", "admin");
    boolean success = admin.login(conn);
    if (success)
      System.out.println(admin);
    else
      System.out.println("Invalid username/password");
    conn.close();
  }
}
