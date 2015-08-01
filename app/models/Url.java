package models;

import java.net.MalformedURLException;
import java.net.URL;

public class Url {
  private String url;

  public Url(String url) {
    this.url = url;
  }

  public String getUrl() {
    return url;
  }

  public URL toURL() throws MalformedURLException {
    return new URL(this.url);
  }

  public String getValidationError() {
    try {
      toURL();
      return null;
    } catch (MalformedURLException e) {
      return "Ogiltig URL: " + e.getMessage();
    }
  }

  public boolean isValid() {
    return getValidationError() == null;
  }
}
