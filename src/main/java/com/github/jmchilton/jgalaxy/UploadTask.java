package com.github.jmchilton.jgalaxy;

import com.github.jmchilton.blend4j.galaxy.ToolsClient;
import com.sun.jersey.api.client.ClientResponse;

import java.io.IOException;
import java.io.File;
import java.util.Map;

public class UploadTask extends javax.swing.SwingWorker {
  private static final int BUFFER_SIZE = 4096;
  private final Map<String, String> uploads;
  private final IndexedProgressUpdater progressUpdater;
  private final ToolsClient toolsClient;
  private final String historyId;
  private final String dbKey;
  private final String fileType;
            
  UploadTask(final Map<String, String> uploads,
             final ToolsClient toolsClient,
             final String historyId,
             final String fileType,
             final String dbKey,
             final IndexedProgressUpdater progressUpdater) {
    this.uploads = uploads;
    this.progressUpdater = progressUpdater;
    this.historyId = historyId;
    this.toolsClient = toolsClient;
    this.fileType = fileType;
    this.dbKey = dbKey;
  }
  
  private void uploadFiles() throws IOException {
    int i = 0;
    for (Map.Entry<String, String> entry : uploads.entrySet()) {
      final File filename = new File(entry.getKey());
      final String datasetName = entry.getValue();
      System.out.println("upload to history with id " + historyId);
      final ClientResponse response = toolsClient.fileUploadRequest(historyId, fileType, dbKey, filename);
      progressUpdater.setProgress(i++, 100);
    }
    
  }
  
  protected Object doInBackground() {
    try {
      uploadFiles();
    } catch (IOException ioException) {
      ioException.printStackTrace();
    }
    return null;
  }
  
}
