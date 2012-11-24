package com.github.jmchilton.jgalaxy;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Instance {
  private String apiKey;
  private String url;
  
  public String getApiKey() {
    return apiKey;
  }
  
  public String getUrl() {
    return url;
  }
  
  public void setApiKey(final String apiKey) {
    this.apiKey = apiKey;
  }
  
  public void setUrl(final String url) {
    this.url = url;
  }
  
  @Override
  public int hashCode() {
    int hash = 7;
    hash = 37 * hash + (this.apiKey != null ? this.apiKey.hashCode() : 0);
    hash = 37 * hash + (this.url != null ? this.url.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final Instance other = (Instance) obj;
    if ((this.apiKey == null) ? (other.apiKey != null) : !this.apiKey.equals(other.apiKey)) {
      return false;
    }
    if ((this.url == null) ? (other.url != null) : !this.url.equals(other.url)) {
      return false;
    }
    return true;
  }

}
