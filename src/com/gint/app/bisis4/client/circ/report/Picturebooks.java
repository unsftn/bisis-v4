package com.gint.app.bisis4.client.circ.report;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import com.gint.app.bisis4.client.circ.Cirkulacija;
import com.gint.app.bisis4.client.circ.commands.reports.PictureBooksReportCommand;
import com.gint.app.bisis4.client.circ.common.Utils;

public class Picturebooks {

	public static JasperPrint setPrint(Date start, Date end)
			throws IOException {
		
		Map<String, Object> params = new HashMap<String, Object>(5);
		
		if (start == null) {
			end = Utils.setMaxDate(end);
			start = Utils.setMinDate(end);
		} else if (end == null) {
			end = Utils.setMaxDate(start);
			start = Utils.setMinDate(start);
		} else {
			start = Utils.setMinDate(start);
			end = Utils.setMaxDate(end);
		}
		
		params.put("begdate", Utils.toLocaleDate(start));
		params.put("enddate", Utils.toLocaleDate(end));
		
		PictureBooksReportCommand pictureBooks = new PictureBooksReportCommand(start, end);
		pictureBooks = (PictureBooksReportCommand)Cirkulacija.getApp().getService().executeCommand(pictureBooks);
		List l= pictureBooks.getList();
		Object[] row = (Object[])l.get(0);
		params.put("users", ((Long)row[0]).toString());
		params.put("lend", ((Long)row[1]).toString());
		params.put("return", ((Long)row[2]).toString());
		
		try {

			JasperPrint jp = JasperFillManager.fillReport(Picturebooks.class.getResource(
											"/com/gint/app/bisis4/client/circ/jaspers/picturebooks.jasper")
									.openStream(), params, new JREmptyDataSource());
			return jp;
		} catch (JRException e) {
			e.printStackTrace();
			return null;
		}
	}
}
