package com.github.jmchilton.jgalaxy.mvp;

import javax.swing.JDialog;

/**
 *
 * @author John Chilton
 */
public class DialogDisplay extends JDialog implements Display {

  public void showDialog() {
    super.pack();
    super.setVisible(true);    
  }
  
}
