package com.gint.app.bisis4.client.backup;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.zip.ZipOutputStream;

import com.gint.app.bisis4.client.backup.dbmodel.Column;
import com.gint.app.bisis4.client.backup.dbmodel.Table;

public class BackupActions {
	
	private PrintWriter out;
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	
	public void saveTableData(Connection conn, Table t, ZipOutputStream zip) throws SQLException,
	IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		out = new PrintWriter(new OutputStreamWriter(baos, "UTF8"), true);
		
		Statement stmt = conn.createStatement(java.sql.ResultSet.TYPE_FORWARD_ONLY,
		              java.sql.ResultSet.CONCUR_READ_ONLY);
		stmt.setFetchSize(Integer.MIN_VALUE);
		
		ResultSet rset = stmt.executeQuery(getSelect(t));
		while (rset.next()) {
			int index = 1;
			for (Column c : t.getColumns()) {
				switch (c.getType()) {
				case Types.INTEGER:
					write(out, rset, rset.getInt(index++));
					break;
				case Types.CHAR:
					write(out, rset, rset.getString(index++));
					break;
				case Types.VARCHAR:
					write(out, rset, rset.getString(index++));
					break;
				case Types.DECIMAL:
					write(out, rset, rset.getBigDecimal(index++));
					break;
				case Types.FLOAT:
					write(out, rset, rset.getFloat(index++));
					break;
				case Types.DOUBLE:
					write(out, rset, rset.getDouble(index++));
					break;
				case Types.DATE:
					write(out, rset, rset.getDate(index++));
					break;
				case Types.TIME:
					write(out, rset, rset.getTime(index++));
					break;
				case Types.TIMESTAMP:
					write(out, rset, rset.getTimestamp(index++));
					break;
				case Types.BOOLEAN:
					write(out, rset, rset.getBoolean(index++));
					break;
				case Types.LONGVARCHAR:
					write(out, rset, rset.getString(index++));
					break;
				default:
					break;
				}
			}
			out.println();
			baos.writeTo(zip);
			baos.reset();
		}
		
		rset.close();
		stmt.close();
		baos.close();
		}
		
		private String getSelect(Table t) {
		StringBuffer retVal = new StringBuffer();
		retVal.append("SELECT ");
		int index = 0;
		for (Column c : t.getColumns()) {
			if (index++ > 0)
				retVal.append(", ");
			retVal.append(c.getName());
		}
		retVal.append(" FROM ");
		retVal.append(t.getName());
		return retVal.toString();
		}
		
		private void write(PrintWriter out, ResultSet rset, String s)
			throws SQLException {
		if (rset.wasNull())
			out.print("<sep>");
		else
			out.print(s.trim().replace('\r', ' ').replace('\n', ' ') + "<sep>");
		}
		
		private void write(PrintWriter out, ResultSet rset, int i)
			throws SQLException {
		if (rset.wasNull())
			out.print("<sep>");
		else
			out.print(Integer.toString(i) + "<sep>");
		
		}
		
		private void write(PrintWriter out, ResultSet rset, BigDecimal bd)
			throws SQLException {
		if (rset.wasNull())
			out.print("<sep>");
		else
			out.print(bd.toString() + "<sep>");
		
		}
		
		private void write(PrintWriter out, ResultSet rset, float f)
			throws SQLException {
		if (rset.wasNull())
			out.print("<sep>");
		else
			out.print(Float.toString(f) + "<sep>");
		
		}
		
		private void write(PrintWriter out, ResultSet rset, double d)
			throws SQLException {
		if (rset.wasNull())
			out.print("<sep>");
		else
			out.print(Double.toString(d) + "<sep>");
		
		}
		
		private void write(PrintWriter out, ResultSet rset, Date d)
			throws SQLException {
		if (rset.wasNull())
			out.print("<sep>");
		else
			out.print(sdf.format(d) + "<sep>");
		
		}
		
		private void write(PrintWriter out, ResultSet rset, boolean b)
			throws SQLException {
		if (rset.wasNull())
			out.print("<sep>");
		else
			out.print(Boolean.toString(b) + "<sep>");
		
		}
	
	
}
