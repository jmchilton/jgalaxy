package com.github.jmchilton.jgalaxy;

import com.github.jmchilton.blend4j.galaxy.ToolsClient;
import com.sun.jersey.api.client.ClientResponse;
import java.io.IOException;
import java.util.List;

public class UploadTask extends javax.swing.SwingWorker {
  private final IndexedProgressUpdater progressUpdater;
  private final ToolsClient toolsClient;
  private final List<ToolsClient.FileUploadRequest> uploadRequests;
        
  UploadTask(final List<ToolsClient.FileUploadRequest> uploadRequests,
             final ToolsClient toolsClient,
             final IndexedProgressUpdater progressUpdater) {
    this.uploadRequests = uploadRequests;
    this.progressUpdater = progressUpdater;
    this.toolsClient = toolsClient;
  }
  
  private void uploadFiles() throws IOException {
    int i = 0;
    for (ToolsClient.FileUploadRequest request : uploadRequests) {
      final ClientResponse response = toolsClient.uploadRequest(request);
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
