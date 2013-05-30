package com.github.jmchilton.jgalaxy;

import com.github.jmchilton.blend4j.galaxy.GalaxyInstance;
import com.github.jmchilton.blend4j.galaxy.GalaxyInstanceFactory;
import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

@Singleton
public class InstanceManager {
  public static final Map<String, String> GALAXY_INSTANCES = new HashMap<String, String>();
  static {
    GALAXY_INSTANCES.put("https://main.g2.bx.psu.edu/", "Main Galaxy (https://main.g2.bx.psu.edu/)");
    GALAXY_INSTANCES.put("https://test.g2.bx.psu.edu/", "Test Galaxy (https://test.g2.bx.psu.edu/)");
    GALAXY_INSTANCES.put("http://cistrome.org/ap/", "Cistrome Galaxy");
    GALAXY_INSTANCES.put("http://galaxy.nbic.nl/", "NBIC Galaxy");
    GALAXY_INSTANCES.put("http://hyperbrowser.uio.no/hb/", "The Genomic HyperBrowser");
    GALAXY_INSTANCES.put("https://galaxyp.msi.umn.edu/", "MSI Galaxy-P");
  }
  private GalaxyInstance currentInstance;
  private EventBus eventBus;
  private List<Instance> instances;
  
  public static class InstanceUpdateEvent {
  }
  
  public Map<String, String> getStockGalaxyInstances() {
    return GALAXY_INSTANCES;
  }
  
  @Inject
  public InstanceManager(EventBus eventBus) {
    this.eventBus = eventBus;
    eventBus.register(this);
    initInstances();    
  }
  
  @Subscribe
  public void onEstablishConnectionEvent(final ConnectionPresenter.EstablishConnectionEvent event) {
    this.connectNewInstance(event.getUrl(), event.getApiKey());
  }
  
  public List<Instance> getInstances() {
    return instances;
  }
  
  private void initInstances() {
    final Optional<File> instancesFile = Config.getInstancesFile();
    List<Instance> instances = Lists.newArrayList();
    if(instancesFile.isPresent()) {
      if(instancesFile.get().exists()) {
        try {
          instances = new ObjectMapper().readValue(instancesFile.get(), new TypeReference<List<Instance>>() {});
        } catch(IOException ioException) {
          ioException.printStackTrace();
          // pass
        }
      }
    }
    this.instances = instances;
  }
  
  private void saveInstances() {
    final Optional<File> instancesFile = Config.getInstancesFile();
    if(instancesFile.isPresent()) {
      try {
        new ObjectMapper().writeValue(instancesFile.get(), instances);
      } catch(IOException ioException) {
        ioException.printStackTrace();
      }
    }
  }
  
  public void connectNewInstance(final Instance instance) {
    currentInstance = GalaxyInstanceFactory.get(instance.getUrl(), instance.getApiKey());
    if(!instances.contains(instance)) {
      instances.add(instance);
      saveInstances();
    }
    this.eventBus.post(new InstanceUpdateEvent());
  }
  
  public void connectNewInstance(final String url, final String apiKey) {
    final Instance instance = new Instance();
    instance.setUrl(url);
    instance.setApiKey(apiKey);
    this.connectNewInstance(instance);
  }
  
  public GalaxyInstance getCurrentInstance() {
    return currentInstance;
  }
  
}
