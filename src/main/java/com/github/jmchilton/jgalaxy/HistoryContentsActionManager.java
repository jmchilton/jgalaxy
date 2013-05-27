package com.github.jmchilton.jgalaxy;

public interface HistoryContentsActionManager {

  public class HistoryContentsActionEvent {
    private HistoryContentsSelectionModel model;

    public void setModel(final HistoryContentsSelectionModel model) {
      this.model = model;
    }
    
    public HistoryContentsSelectionModel getModel() {
      return model;
    }
    
  }
  
  public class HistoryContentsActionDescription<T extends HistoryContentsActionEvent> {
    private final String name;    
    private final Class<T> clazz;

    public HistoryContentsActionDescription(final String name, final Class<T> clazz) {
      this.name = name;
      this.clazz = clazz;
    }

    public String getName() {
      return name;
    }

    public Class<T> getEventClass() {
      return clazz;
    }    
    
  }
  
  void addAction(final HistoryContentsActionDescription actionDescription);
  
}
