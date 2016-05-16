package com.gint.app.bisis4.reports;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.gint.app.bisis4.client.BisisApp;
import com.gint.app.bisis4.utils.INIFile;

public class ReportCollection {

 
  public ReportCollection(String url) {
    INIFile iniFile = new INIFile(
        ReportRunner.class.getResource(url));
    List<String> reportNames = iniFile.getCategories();
    if (reportNames.size() < 2)
      return;
    reports = new ArrayList<Report>(reportNames.size() - 1);
    for (String rptName : reportNames) {
      if ("global".equals(rptName))
        continue;
      ReportSettings rptSettings = new ReportSettings(rptName);
      List<String> paramNames = iniFile.getParams(rptName);
      for (String paramName : paramNames) {
        rptSettings.getParams().add(
            new ReportParam(paramName, iniFile.getString(rptName, paramName)));
      }
      String className = rptSettings.getParam("class");
      String menuItem = rptSettings.getParam("menuitem");
      String reportType = rptSettings.getParam("type");
      String fileName = rptSettings.getParam("file");
      String name = rptSettings.getParam("name");
      String jasper = rptSettings.getParam("jasper");
      if (className != null && menuItem != null && reportType != null
          && fileName != null && name != null && jasper != null) {
        try {
          Report report = (Report) Class.forName(className).newInstance();
          report.setReportSettings(rptSettings);
          reports.add(report);
        } catch (Exception e) {
          log.fatal(e);
        }
      }
    }
    destination = iniFile.getString("global", "destination");
    driver = iniFile.getString("global", "driver");
    jdbcUrl = iniFile.getString("global", "url");
    username = iniFile.getString("global", "username");
    password = iniFile.getString("global", "password");
    
    // svi parametri iz global sekcije se dodaju svim postojecim izvestajima
    List<String> globalParams = iniFile.getParams("global");
    for (String gp : globalParams) {
      String value = iniFile.getString("global", gp);
      ReportParam rp = new ReportParam(gp, value);
      for (Report rpt : reports) {
        rpt.getReportSettings().getParams().add(rp);
      }
    }
  }
  
  public List<Report> getReports() {
    return reports;
  }
  
  public String toString() {
    StringBuffer retVal = new StringBuffer();
    for (Report rpt : reports) {
      retVal.append(rpt.getReportSettings());
    }
    return retVal.toString();
  }
  
  public String getDestination() {
    return destination;
  }

  public String getDriver() {
    return driver;
  }

  public String getJdbcUrl() {
    return jdbcUrl;
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

  private List<Report> reports;
  private String destination;
  private String driver;
  private String jdbcUrl;
  private String username;
  private String password;
  private Log log = LogFactory.getLog(ReportCollection.class);
}
