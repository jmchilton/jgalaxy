package com.github.jmchilton.jgalaxy;

import com.github.jmchilton.blend4j.galaxy.beans.HistoryContents;
import java.awt.Color;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

public class HistoryContentsListCellRenderer extends JLabel implements ListCellRenderer {

  public Component getListCellRendererComponent(
          final JList jList, 
          final Object historyContentsValue, 
          final int index, 
          final boolean isSelected, 
          final boolean cellHasFocus) {
    final HistoryContents history = (HistoryContents) historyContentsValue;
    setText(history.getName());
    if (isSelected) {
      setBackground(Color.white);
      setForeground(Color.gray);
    } else {
      setBackground(Color.white);
      setForeground(Color.black);
    }
    return this;
  }
  
}
