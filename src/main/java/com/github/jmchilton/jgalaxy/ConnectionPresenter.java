package com.github.jmchilton.jgalaxy;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ConnectionPresenter {
  private final EventBus eventBus;
  private final InstanceManager instanceManager;
  private final ConnectionDisplay connectionDisplay;
  
  @Inject
  public ConnectionPresenter(final EventBus eventBus,
                             final InstanceManager instanceManager) {
    this.eventBus = eventBus;
    this.instanceManager = instanceManager;
    this.connectionDisplay = new ConnectionDisplay(this, instanceManager);
    eventBus.register(this);
  }
  
  public static class ManageConnectionEvent {
    
  }
  
  public static class EstablishConnectionEvent {
    private final String url;
    private final String apiKey;
    
    public EstablishConnectionEvent(final String url, final String apiKey) {
      this.url = url;
      this.apiKey = apiKey;
    }

    public String getUrl() {
      return url;
    }

    public String getApiKey() {
      return apiKey;
    }
    
  }
  
  @Subscribe
  public void onManageConnectionEvent(final ManageConnectionEvent event) {
    connectionDisplay.showDialog();
  }

  @Subscribe
  public void onEstablishConnectionEvent(final EstablishConnectionEvent event) {
    connectionDisplay.setVisible(false);
  }

  public void connect(final String url, final String apiKey) {
    eventBus.post(new EstablishConnectionEvent(url, apiKey));
  }
  
}
