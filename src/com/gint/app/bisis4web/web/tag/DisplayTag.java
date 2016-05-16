package com.gint.app.bisis4web.web.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.gint.app.bisis4.format.HoldingsDataCodersJdbc;
import com.gint.app.bisis4.records.Record;
import com.gint.app.bisis4web.formatters.RecordFormatter;
import com.gint.app.bisis4web.formatters.RecordFormatterFactory;
import com.gint.app.bisis4web.web.Settings;
import com.gint.app.bisis4web.web.beans.WebUser;

/**
 * JSP Tag Handler class
 *
 * @jsp.tag 
 *   name = "display"
 *   description = "Generates the retrived records paged table"
 *   body-content = "empty"
 */
public class DisplayTag extends TagSupport {

  public DisplayTag() {
    super();
  }
  
  public int doStartTag() throws JspException {
    if (user == null)
      throw new JspException("DisplayTag: missing attribute user");
    if (tableClass == null)
      throw new JspException("DisplayTag: missing attribute tableClass");
    if (cellClass == null)
      throw new JspException("DisplayTag: missing attribute cellClass");    
    try {
    	
    	
    	
      int startIndex = user.getStartIndex();
      int endIndex = startIndex + user.getPageSize();
      if (endIndex > user.getHitCount())
        endIndex = user.getHitCount();
      RecordFormatter rf = RecordFormatterFactory.getFormatter(
          user.getDisplayMode());
      ctx.getOut().println("<table class=\""+tableClass+"\">");
      int [] recIDs = new int[endIndex - startIndex];
      for (int i = 0; i < endIndex - startIndex; i++) {
      	recIDs[i] = user.getRecordID(startIndex + i).getLocalID();
      }
      Record[] records = Settings.getSettings().getRecMgr().getRecords(recIDs);
      
      for (int i = 0; i<records.length; i++){
      	Record rec;
        if (records[i] != null){
        	rec = records[i];
        	
        	if (Settings.getSettings().getShowRashod().equals("no")){
        		String rashodCode = HoldingsDataCodersJdbc.getRashodCode();
        		for (int j = rec.getPrimerci().size()-1; j>= 0; j--){
        			if (rec.getPrimerci().get(j).getStatus() != null && rec.getPrimerci().get(j).getStatus().equalsIgnoreCase(rashodCode)){
        				rec.getPrimerci().remove(j);
        			}
        		}
        	}
           	String s = rf.toHTML(rec, user.getLocale());
          ctx.getOut().print("<tr><td class=\""+cellClass+"\">");
          ctx.getOut().print(s);
          ctx.getOut().println("</td></tr>");
        }
      }
      ctx.getOut().print("</table>");
    } catch (Exception ex) {
      log.fatal(ex);
      ex.printStackTrace();
    }
    return SKIP_BODY;
  }
  
  public int doEndTag() throws JspException {
    return EVAL_PAGE;
  }
  
  /**
   * @jsp.attribute
   *   required = "true"
   *   rtexprvalue = "true"
   *   type = "com.gint.app.bisis4web.web.beans.WebUser"
   * @return Returns the user.
   */
  public WebUser getUser() {
    return user;
  }
  /**
   * @param user The user to set.
   */
  public void setUser(WebUser user) {
    this.user = user;
  }
  /**
   * @jsp.attribute
   *   required = "true"
   *   rtexprvalue = "false"
   *   type = "java.lang.String"
   * @return Returns the cellClass.
   */
  public String getCellClass() {
    return cellClass;
  }
  /**
   * @param cellClass The cellClass to set.
   */
  public void setCellClass(String cellClass) {
    this.cellClass = cellClass;
  }
  /**
   * @jsp.attribute
   *   required = "true"
   *   rtexprvalue = "false"
   *   type = "java.lang.String"
   * @return Returns the tableClass.
   */
  public String getTableClass() {
    return tableClass;
  }
  /**
   * @param tableClass The tableClass to set.
   */
  public void setTableClass(String tableClass) {
    this.tableClass = tableClass;
  }
  public void setPageContext(PageContext ctx) {
    this.ctx = ctx;
  }

//  /** Home for creating record retrievers. */
//  private RecordRetrieverLocalHome rrh;
  /** JSP page context. */
  private PageContext ctx;
  /** The WebUser object instance used in the current HTTP session. */
  private WebUser user;
  /** Indicates whether the owner library should be displayed. */
  private boolean showLibraries;
  /** CSS table class. */
  private String tableClass;
  /** CSS cell class. */ 
  private String cellClass;
  
  private static Log log = LogFactory.getLog(
      "com.gint.app.bisis4web.web.tag.DisplayTag");
}