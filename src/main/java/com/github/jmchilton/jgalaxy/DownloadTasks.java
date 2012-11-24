/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.jmchilton.jgalaxy;

import com.github.jmchilton.blend4j.galaxy.HistoriesClient;
import com.github.jmchilton.blend4j.galaxy.beans.Dataset;
import com.github.jmchilton.blend4j.galaxy.beans.History;
import com.github.jmchilton.blend4j.galaxy.beans.HistoryContents;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Map;

/**
 *
 * @author john
 */
class DownloadTasks extends javax.swing.SwingWorker {
  private static final int BUFFER_SIZE = 4096;
  private File destinationDirectory;
  private Map<HistoryContents, String> downloads;
  private History history;
  private HistoriesClient historiesClient;
  private DownloadUpdater updater;

  interface DownloadUpdater {

    void setProgress(int index, int percentComplete);
  }

  DownloadTasks(final File destinationDirectory, final Map<HistoryContents, String> downloads, final History history, final HistoriesClient historiesClient, final DownloadUpdater updater) {
    this.destinationDirectory = destinationDirectory;
    this.downloads = downloads;
    this.history = history;
    this.historiesClient = historiesClient;
    this.updater = updater;
  }

  private void copyFiles() throws IOException {
    int i = 0;
    for (Map.Entry<HistoryContents, String> entry : downloads.entrySet()) {
      final HistoryContents contents = entry.getKey();
      final Dataset dataset = historiesClient.showDataset(history.getId(), contents.getId());
      final long totalFileSize = dataset.getFileSize();
      long copiedSize = 0L;
      final String downloadUrl = dataset.getFullDownloadUrl();
      final URL u = new URL(downloadUrl);
      final InputStream inputStream = u.openStream();
      final File destinationFile = new File(destinationDirectory, entry.getValue());
      final OutputStream outputStream = new FileOutputStream(destinationFile);
      final byte[] buffer = new byte[BUFFER_SIZE];
      while (true) {
        int bytesRead = inputStream.read(buffer);
        if (bytesRead == -1) {
          break;
        }
        outputStream.write(buffer);
        copiedSize += bytesRead;
        final int percentInt = (int) ((copiedSize * 100) / totalFileSize);
        updater.setProgress(i++, percentInt);
      }
    }
  }

  protected Object doInBackground() {
    try {
      copyFiles();
    } catch (IOException ioException) {
      ioException.printStackTrace();
    }
    return null;
  }
  
}
