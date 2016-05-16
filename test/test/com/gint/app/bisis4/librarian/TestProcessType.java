package test.com.gint.app.bisis4.librarian;

import com.gint.app.bisis4.librarian.ProcessType;

public class TestProcessType {

  public static void main(String[] args) {
    String xml1 = "<?xml version='1.0'?><process-type name='Monografske - kompletna obrada' pubType='1' pref1='AU' pref2='TI' pref3='PU' pref4='PY' pref5='KW'><initial-subfield name='200a'/><initial-subfield name='700a'/><initial-subfield name='700b'/><mandatory-subfield name='200a'/></process-type>";
    ProcessType pt1 = ProcessType.getProcessType(xml1);
    System.out.println(pt1);

  }

}
