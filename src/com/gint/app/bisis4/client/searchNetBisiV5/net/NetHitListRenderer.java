package com.gint.app.bisis4.client.searchNetBisiV5.net;


import com.gint.app.bisis4.client.hitlist.formatters.RecordFormatter;
import com.gint.app.bisis4.client.hitlist.formatters.RecordFormatterFactory;
import com.gint.app.bisis4.client.searchNetBisiV5.model.BriefInfoModelV5;
import com.gint.app.bisis4.records.Record;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedHashMap;

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
    if (value instanceof BriefInfoModelV5) {
      BriefInfoModelV5 recBrief=(BriefInfoModelV5)value;
      setText(recBrief.getBriefInfo().toString()+"<br/>\n&nbsp;&nbsp;<b>["+recBrief.getLibFullName()+"]</b>");
      if (isSelected) {
    	String thisLib=recBrief.getLibFullName();
		LinkedHashMap hitsToThisAddress=(LinkedHashMap)selectedBriefs.get(thisLib);
		if(hitsToThisAddress==null){
			hitsToThisAddress=new LinkedHashMap();
		}
		String recId=recBrief.getBriefInfo().get_id();
		hitsToThisAddress.put(recId,recId);
		selectedBriefs.put(thisLib,hitsToThisAddress);
        setForeground(list.getSelectionForeground());
        setBackground(list.getSelectionBackground());
      } else {
    	String thisLib=recBrief.getLibFullName();
		LinkedHashMap hitsToThisAddress=(LinkedHashMap)selectedBriefs.get(thisLib);
		if(hitsToThisAddress!=null){
			hitsToThisAddress.remove(recBrief.getBriefInfo().get_id());
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
