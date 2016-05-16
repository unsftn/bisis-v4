package com.gint.app.bisis4.client.hitlist;

import java.awt.Component;
import java.util.LinkedHashMap;

import javax.swing.JEditorPane;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import com.gint.app.bisis4.client.hitlist.formatters.RecordFormatter;
import com.gint.app.bisis4.client.hitlist.formatters.RecordFormatterFactory;
import com.gint.app.bisis4.records.Record;
import com.gint.app.bisis4.xmlmessaging.BriefInfoModel;
import com.gint.app.bisis4.xmlmessaging.communication.LibraryServerDesc;


public class NetHitListRenderer extends JEditorPane implements ListCellRenderer {

  public NetHitListRenderer(LinkedHashMap selectedBriefs) {
    super("text/html", "");
    setOpaque(true);
    this.selectedBriefs=selectedBriefs;
    
  }
  
  public void setFormatter(int type) {
    formatter = RecordFormatterFactory.getFormatter(type);
    if (formatter == null)
      formatter = RecordFormatterFactory.getFormatter(
          RecordFormatterFactory.FORMAT_BRIEF);
  }
  
  public Component getListCellRendererComponent(JList list, Object value, 
      int index, boolean isSelected, boolean cellHasFocus) {
    if (value instanceof BriefInfoModel) {
      BriefInfoModel recBrief=(BriefInfoModel)value;
      setText(recBrief.getBriefInfo().toString()+"<br/>\n&nbsp;&nbsp;<b>["+recBrief.getLibServ().getLibName()+"]</b>");
      if (isSelected) {
    	LibraryServerDesc thisLib=recBrief.getLibServ();
		LinkedHashMap hitsToThisAddress=(LinkedHashMap)selectedBriefs.get(thisLib);
		if(hitsToThisAddress==null){
			hitsToThisAddress=new LinkedHashMap();
		}
		int recId=recBrief.getBriefInfo().getDocId();
		hitsToThisAddress.put(""+recId,""+recId);
		selectedBriefs.put(thisLib,hitsToThisAddress);
        setForeground(list.getSelectionForeground());
        setBackground(list.getSelectionBackground());
      } else {
    	LibraryServerDesc thisLib=recBrief.getLibServ();
		LinkedHashMap hitsToThisAddress=(LinkedHashMap)selectedBriefs.get(thisLib);
		if(hitsToThisAddress!=null){
			hitsToThisAddress.remove(""+recBrief.getBriefInfo().getDocId());
			selectedBriefs.put(thisLib,hitsToThisAddress);
		}
        setForeground(list.getForeground());
        setBackground(list.getBackground());
      }
    }else if(value instanceof Record) {
    	Record rec = (Record)value;
        setText(formatter.toHTML(rec, "sr"));
        if (isSelected) {
          setForeground(list.getSelectionForeground());
          setBackground(list.getSelectionBackground());
        } else {
          setForeground(list.getForeground());
          setBackground(list.getBackground());
        }
      }
    return this;
  }
  
  private RecordFormatter formatter;
  private LinkedHashMap selectedBriefs; 
}
