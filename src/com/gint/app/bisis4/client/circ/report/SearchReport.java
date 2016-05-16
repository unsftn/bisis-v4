package com.gint.app.bisis4.client.circ.report;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.gint.app.bisis4.client.circ.view.SearchUsersTableModel;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRTableModelDataSource;


public class SearchReport {
  
  private static Log log = LogFactory.getLog(SearchReport.class.getName());

	public static JasperPrint setPrint(SearchUsersTableModel table, String query){

		try {
  		Map<String, Object> params = new HashMap<String, Object>(1);
  		params.put("upit", query);
  		
  		JasperPrint jp = JasperFillManager.fillReport(SearchReport.class.getResource(
  					"/com/gint/app/bisis4/client/circ/jaspers/searchuser.jasper").openStream(), 
            params, new JRTableModelDataSource(table));
			return jp;
		} catch (Exception e) {
			log.error(e);
			return null;
		}

	}

}
