package com.gint.app.bisis4.client.circ.report;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Document;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

import com.gint.app.bisis4.client.circ.Cirkulacija;
import com.gint.app.bisis4.client.circ.commands.reports.CategoriaReportCommand;
import com.gint.app.bisis4.client.circ.commands.reports.FreeSigningReportCommand;
import com.gint.app.bisis4.client.circ.commands.reports.GenderReportCommand;
import com.gint.app.bisis4.client.circ.commands.reports.MemberTypeReportCommand;
import com.gint.app.bisis4.client.circ.commands.reports.UsersNumberReportCommand;
import com.gint.app.bisis4.client.circ.model.Location;
import com.gint.app.bisis4.client.circ.common.Utils;

public class Structure {


public static JasperPrint setPrint(Date start, Date end, Object branch)
			throws IOException {

	try {
			if (start == null) {
				end = Utils.setMaxDate(end);
				start = Utils.setMinDate(end);
			} else if (end == null) {
				end = Utils.setMaxDate(start);
				start = Utils.setMinDate(start);
			} else {
				end = Utils.setMaxDate(end);
				start = Utils.setMinDate(start);
				
			}
					
			CategoriaReportCommand categoria = new CategoriaReportCommand(start, end, branch);
			categoria = (CategoriaReportCommand)Cirkulacija.getApp().getService().executeCommand(categoria);
			List l1= categoria.getList();	
			MemberTypeReportCommand memberType = new MemberTypeReportCommand(start, end, branch);
			memberType = (MemberTypeReportCommand)Cirkulacija.getApp().getService().executeCommand(memberType);
			List l2= memberType.getList();	
			FreeSigningReportCommand freeSigning = new FreeSigningReportCommand(start, end, branch);
			freeSigning = (FreeSigningReportCommand)Cirkulacija.getApp().getService().executeCommand(freeSigning);
			int numFree = freeSigning.getNum();
			UsersNumberReportCommand usersNumber = new UsersNumberReportCommand(Utils.setFirstDate(end), end, branch);
			usersNumber = (UsersNumberReportCommand)Cirkulacija.getApp().getService().executeCommand(usersNumber);
			int numUsers = usersNumber.getNum();
			GenderReportCommand gender = new GenderReportCommand(start, end, branch);
			gender = (GenderReportCommand)Cirkulacija.getApp().getService().executeCommand(gender);
			List l5= gender.getList();
          
			Document dom1 = CategoriSigning.setXML(l1);
			Document dom2 = MmbrTypeSigning.setXML(l2);
			Document dom3 = FreeMmbrShip.setXML(numFree);
			Document dom4 = UsersNumber.setXML(numUsers);
			Document dom5 = Gender.setXML(l5);
			

		
			Map<String, Object> params = new HashMap<String, Object>(11);
			if (branch instanceof Location) {
				params.put("nazivogr", "odeljenje: "+((Location) branch).getName());
			} else {
				params.put("nazivogr", "");
			}
			
			
			JasperReport brojbespl = (JasperReport) JRLoader
			.loadObject(Structure.class
					.getResource(
							"/com/gint/app/bisis4/client/circ/jaspers/brojbespl.jasper")
					.openStream());
			
			JasperReport clanovi = (JasperReport) JRLoader
					.loadObject(Structure.class
							.getResource(
									"/com/gint/app/bisis4/client/circ/jaspers/clanovi.jasper")
							.openStream());
			JasperReport placanje = (JasperReport) JRLoader
					.loadObject(Structure.class
							.getResource(
									"/com/gint/app/bisis4/client/circ/jaspers/placanje.jasper")
							.openStream());
	
			JasperReport broj = (JasperReport) JRLoader
					.loadObject(Structure.class
							.getResource(
									"/com/gint/app/bisis4/client/circ/jaspers/broj.jasper")
							.openStream());
			JasperReport pol = (JasperReport) JRLoader
					.loadObject(Structure.class
							.getResource(
									"/com/gint/app/bisis4/client/circ/jaspers/pol.jasper")
							.openStream());

			params.put("begdate", Utils.toLocaleDate(start));
			params.put("enddate", Utils.toLocaleDate(end));
			params.put("clanovi", clanovi);
			params.put("dom1", dom1);
			params.put("placanje", placanje);
			params.put("dom2", dom2);
			params.put("dom3", dom3);
			params.put("brojbespl", brojbespl);
			params.put("broj", broj);
			params.put("dom4", dom4);
			params.put("pol", pol);
			params.put("dom5", dom5);

			JasperPrint jp = JasperFillManager
					.fillReport(
							Structure.class
									.getResource(
											"/com/gint/app/bisis4/client/circ/jaspers/struktura.jasper")
									.openStream(), params,
							new JREmptyDataSource());

			
			return jp;
		} catch (JRException e) {
			e.printStackTrace();
			return null;
		}

	}

}
