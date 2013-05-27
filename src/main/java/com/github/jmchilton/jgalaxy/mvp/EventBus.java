package com.github.jmchilton.jgalaxy.mvp;

import com.google.common.eventbus.DeadEvent;
import com.google.common.eventbus.Subscribe;

/**
 *
 * @author John Chilton
 */
public class EventBus extends com.google.common.eventbus.EventBus {
  private DeadEventHandler handler;

  public EventBus() {
    super();
    handler = new DeadEventHandler();
    this.register(handler);
  }
  
  public static class DeadEventHandler {

    @Subscribe
    public void handleDeadEvent(final DeadEvent deadEvent) {
      System.out.println("DEAD EVENT!");
      System.out.println(deadEvent.getEvent());
    }

  }
  
}
