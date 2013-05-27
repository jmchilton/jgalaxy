package com.github.jmchilton.jgalaxy;

import com.google.common.base.Optional;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class JGalaxy {

  public static class InputState {
    private final Optional<Instance> instance;

    InputState(final Optional<Instance> instance) {
      this.instance = instance;
    }
    
    public Optional<Instance> getInstance() {
      return instance;
    }
    
  }
  
  public static void main(String args[]) {
      /* Set the Nimbus look and feel */
      /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
       * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
       */
    try {
      for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
        if ("Nimbus".equals(info.getName())) {
          javax.swing.UIManager.setLookAndFeel(info.getClassName());
          break;
        }
      }
    } catch (ClassNotFoundException ex) {
      java.util.logging.Logger.getLogger(JGalaxy.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (InstantiationException ex) {
      java.util.logging.Logger.getLogger(JGalaxy.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (IllegalAccessException ex) {
      java.util.logging.Logger.getLogger(JGalaxy.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    } catch (javax.swing.UnsupportedLookAndFeelException ex) {
      java.util.logging.Logger.getLogger(JGalaxy.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
    }

    final Optional<Instance> instance;
    if(args.length >= 2) {
      instance = Optional.of(new Instance());
      instance.get().setUrl(args[args.length - 2]);
      instance.get().setApiKey(args[args.length - 1]);
    } else {
      instance = Optional.absent();
    }

    for(final String arg : args) {
      if(arg.equals("--no-check-certificate")) {
        System.out.println("Disabling SSL certificate checks.");
        Ssl.disableCertificateCheck();
      }
    }
    
    final InputState inputState = new InputState(instance);

    /* Create and display the form */
    java.awt.EventQueue.invokeLater(new Runnable() {
      public void run() {
        final Injector injector = Guice.createInjector(new JGalaxyModule(inputState));
        final MainFrame frame = injector.getInstance(MainFrame.class);
        frame.setVisible(true);
    
        final Optional<Instance> instance = inputState.getInstance();
        if(instance.isPresent()) {
          injector.getInstance(InstanceManager.class).connectNewInstance(instance.get());
        }
        
      }
    });
  }
    
}
