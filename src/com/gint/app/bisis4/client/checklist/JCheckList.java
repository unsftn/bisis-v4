package com.gint.app.bisis4.client.checklist;

import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;

public class JCheckList extends JList {

  public JCheckList() {
    super();
    init();
  }

  public JCheckList(CheckableItem[] items) {
    super(items);
    init();
  }

  private void init() {
    setCellRenderer(new JCheckListRenderer());
    setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    setBorder(new EmptyBorder(0,4,0,0));
    addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
        int index = locationToIndex(e.getPoint());
        CheckableItem item = (CheckableItem)getModel().getElementAt(index);
        item.setSelected(! item.isSelected());
        Rectangle rect = getCellBounds(index, index);
        repaint(rect);
      }
    });
  }
}
