package com.github.jmchilton.jgalaxy;

import com.github.jmchilton.blend4j.galaxy.GalaxyInstance;
import com.github.jmchilton.blend4j.galaxy.beans.History;
import com.github.jmchilton.blend4j.galaxy.beans.HistoryContents;
import java.util.List;

public class HistoryContentsSelectionModel {
  private final GalaxyInstance galaxyInstance;
  private final History history;
  private final List<HistoryContents> historyContents;

  public List<HistoryContents> getHistoryContents() {
    return historyContents;
  }

  public GalaxyInstance getGalaxyInstance() {
    return galaxyInstance;
  }

  public History getHistory() {
    return history;
  }

  public HistoryContentsSelectionModel(final GalaxyInstance galaxyInstance, 
                      final History history,
                      final List<HistoryContents> historyContents) {
    this.galaxyInstance = galaxyInstance;
    this.history = history;
    this.historyContents = historyContents;
  }

}
