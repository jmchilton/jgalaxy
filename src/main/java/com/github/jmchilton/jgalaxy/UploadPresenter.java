package com.github.jmchilton.jgalaxy;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 *
 * @author John Chilton
 */
@Singleton
public class UploadPresenter {

  @Inject
  public UploadPresenter(final EventBus eventBus) {
    eventBus.register(this);
  }

  public static class UploadEvent extends HistoryContentsActionManager.HistoryContentsActionEvent {
  }

  @Subscribe
  public void handleUploadEvent(final UploadEvent event) {
    final UploadDisplay display = new UploadDisplay(event.getModel());
    display.showDialog();    
  }

}
