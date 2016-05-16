package test.com.gint.app.bisis4.librarian;

import com.gint.app.bisis4.librarian.LibrarianContext;
import com.gint.app.bisis4.librarian.ProcessType;
import com.gint.app.bisis4.librarian.ProcessTypeCatalog;

public class TestLibrarianContext {

  /**
   * @param args
   */
  public static void main(String[] args) {
    String xml1 = "<?xml version='1.0'?><process-type name='Monografske - kompletna obrada' pubType='1' pref1='AU' pref2='TI' pref3='PU' pref4='PY' pref5='KW'><initial-subfield name='200a'/><initial-subfield name='700a'/><initial-subfield name='700b'/><mandatory-subfield name='200a'/></process-type>";
    String xml2 = "<?xml version='1.0'?><process-type name='Serijske - kompletna obrada' pubType='2' pref1='AU' pref2='TI' pref3='PU' pref4='PY' pref5='KW'><initial-subfield name='200a'/><initial-subfield name='700a'/><initial-subfield name='700b'/><mandatory-subfield name='200a'/></process-type>";
    ProcessType pt1 = ProcessType.getProcessType(xml1);
    ProcessType pt2 = ProcessType.getProcessType(xml2);
    ProcessTypeCatalog.register(pt1);
    ProcessTypeCatalog.register(pt2);
    
    String lc1 = "<?xml version='1.0'?><librarian-context><process-type name='Monografske - kompletna obrada'/><process-type name='Serijske - kompletna obrada'/><default-process-type name='Monografske - kompletna obrada'/></librarian-context>"; 
    LibrarianContext lib = LibrarianContext.getLibrarianContext(lc1);
    System.out.println(lib);
  }

}
