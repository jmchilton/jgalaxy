package com.github.jmchilton.jgalaxy;

import com.github.jmchilton.jgalaxy.HistoryContentsActionManager.HistoryContentsActionEvent;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class BulkDownloadPresenter {
  
  @Inject
  public BulkDownloadPresenter(final EventBus eventBus) {
    eventBus.register(this);
  }
  
  public static class BulkDownloadEvent extends HistoryContentsActionEvent {    
  }
  
  @Subscribe
  public void handleBulkDownloadEvent(final BulkDownloadEvent event) {
    final BulkDownloadDisplay display = new BulkDownloadDisplay(event.getModel());
    display.display();
    display.showDialog();
  }

}
