package controllers;

import play.libs.F.Option;
import play.mvc.QueryStringBindable;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class UrlParameter implements QueryStringBindable<UrlParameter>{

  private URL value;

  @Override
  public Option<UrlParameter> bind(String key, Map<String, String[]> parameters) {
    if(parameters.containsKey(key)){
      String urlParameter = parameters.get(key)[0];
      try {
        this.value = new URL(urlParameter);
      } catch (MalformedURLException e) {
        throw new IllegalArgumentException(e);
      }
      return Option.Some(this);
    }
    return Option.None();
  }

  @Override
  public String unbind(String key) {
    try {
      return "key="+ URLEncoder.encode(value.toString(), StandardCharsets.ISO_8859_1.toString());
    } catch (UnsupportedEncodingException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public String javascriptUnbind() {
    throw new RuntimeException("Javascript not supported for this parameter type");
  }

  URL getValue() {
    return value;
  }
}
