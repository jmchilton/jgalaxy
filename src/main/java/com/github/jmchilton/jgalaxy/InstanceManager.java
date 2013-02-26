package com.github.jmchilton.jgalaxy;

import com.github.jmchilton.blend4j.galaxy.GalaxyInstance;
import com.github.jmchilton.blend4j.galaxy.GalaxyInstanceFactory;
import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

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
  private InstanceUpdateListener instanceUpdateListener;
  private List<Instance> instances;
  
  public Map<String, String> getStockGalaxyInstances() {
    return GALAXY_INSTANCES;
  }
  
  public InstanceManager(InstanceUpdateListener instanceUpdateListener) {
    this.instanceUpdateListener = instanceUpdateListener;
    initInstances();
  }
  
  public interface InstanceUpdateListener {
    public void update();
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
    instanceUpdateListener.update();    
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
