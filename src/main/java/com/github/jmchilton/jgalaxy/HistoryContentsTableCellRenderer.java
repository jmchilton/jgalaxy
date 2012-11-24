package com.github.jmchilton.jgalaxy;

import com.github.jmchilton.blend4j.galaxy.beans.HistoryContents;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class HistoryContentsTableCellRenderer extends DefaultTableCellRenderer {

  public Component getTableCellRendererComponent(
          JTable table, 
          Object value,
          boolean isSelected, 
          boolean hasFocus, 
          int row, 
          int column) {
    final HistoryContents historyContents = (HistoryContents) value;
    return super.getTableCellRendererComponent(table, historyContents.getName(), isSelected, hasFocus, row, column);
  }  
  
  
}
