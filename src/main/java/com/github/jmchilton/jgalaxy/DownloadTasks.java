package com.github.jmchilton.jgalaxy;

import com.github.jmchilton.blend4j.galaxy.HistoriesClient;
import com.github.jmchilton.blend4j.galaxy.beans.Dataset;
import com.github.jmchilton.blend4j.galaxy.beans.History;
import com.github.jmchilton.blend4j.galaxy.beans.HistoryContents;
import com.github.jmchilton.blend4j.galaxy.beans.HistoryContentsProvenance;
import com.google.common.io.Files;
import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Map;

class DownloadTasks extends javax.swing.SwingWorker {
  private static final int BUFFER_SIZE = 4096;
  private File destinationDirectory;
  private Map<HistoryContents, String> downloads;
  private History history;
  private HistoriesClient historiesClient;
  private IndexedProgressUpdater updater;
  private boolean downloadProvenance;

  DownloadTasks(final File destinationDirectory, 
                final Map<HistoryContents, String> downloads, 
                final History history, 
                final HistoriesClient historiesClient, 
                final IndexedProgressUpdater updater,
                final boolean downloadProvenance) {
    this.destinationDirectory = destinationDirectory;
    this.downloads = downloads;
    this.history = history;
    this.historiesClient = historiesClient;
    this.updater = updater;
    this.downloadProvenance = downloadProvenance;
  }
  
  private File getDestinationFile(final String name) {
    return new File(destinationDirectory, name);
  }
  
  private OutputStream getDestinationStream(final String name) throws IOException {
    final File destinationFile = getDestinationFile(name);
    final OutputStream outputStream = new FileOutputStream(destinationFile);    
    return outputStream;
  }
  
  private void closeQuietly(final Closeable closeable) {
    try {
      closeable.close();
    } catch(final IOException e) {
    }
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
      final OutputStream outputStream = getDestinationStream(entry.getValue());
      try {
        final byte[] buffer = new byte[BUFFER_SIZE];
        while (true) {
          int bytesRead = inputStream.read(buffer);
          if (bytesRead == -1) {
            break;
          }
          outputStream.write(buffer);
          copiedSize += bytesRead;
          final int percentInt = (int) ((copiedSize * 100) / totalFileSize);
          updater.setProgress(i, percentInt);
        }
      } finally {
        closeQuietly(inputStream);
        closeQuietly(outputStream);
      }
      if(downloadProvenance) {
        final HistoryContentsProvenance provenance = historiesClient.showProvenance(history.getId(), contents.getId());
        Files.write(serialize(provenance), getDestinationFile(entry.getValue() + ".galaxy_metadata"));
      }
      updater.setProgress(i++, 100);
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
  
  private byte[] serialize(final HistoryContentsProvenance provenance) {
    final String toolId = provenance.getToolId();
    final Map<String, Object> parameters = provenance.getParameters();
    final StringBuilder builder = new StringBuilder();
    // TODO: Use provenance to fillout builder.
    builder.append("Tool: " + toolId);
    return builder.toString().getBytes();
  }
  
}
