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

  public static class MultipleFileDatasetUploadEvent extends HistoryContentsActionManager.HistoryContentsActionEvent {
  }

  @Subscribe
  public void handleUploadEvent(final UploadEvent event) {
    displayUploadDialog(event.getModel(), false);
  }
  
  @Subscribe
  public void handleMultipleFileDatasetUploadEvent(final MultipleFileDatasetUploadEvent event) {
    displayUploadDialog(event.getModel(), true);
  }
  
  private void displayUploadDialog(final HistoryContentsSelectionModel model, final boolean multipleFileDataset) {
    final UploadDisplay display = new UploadDisplay(model);
    display.displayDatasetName(multipleFileDataset);
    display.showDialog();        
  }
  
}
