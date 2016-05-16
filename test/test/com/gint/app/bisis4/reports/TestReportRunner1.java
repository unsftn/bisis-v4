package test.com.gint.app.bisis4.reports;

import com.gint.app.bisis4.reports.ReportCollection;
import com.gint.app.bisis4.reports.ReportRunner;

public class TestReportRunner1 {

  public static void main(String[] args) {
    ReportCollection collection = new ReportCollection("/test/com/gint/app/bisis4/reports/reports1.ini");
    System.out.println(collection);
    ReportRunner runner = new ReportRunner(collection);
    runner.runFromFile("/home/branko/test/records.dat");
  }
}
