package controllers;

import models.Url;
import play.mvc.Controller;
import play.mvc.Result;
import scala.Option;
import views.html.blank;
import views.html.graph;

public class Application extends Controller {

  public Result show(String spreadsheetUrl) {
    if (isPresent(spreadsheetUrl)) {
      return showGraph(spreadsheetUrl);
    } else {
      return showBlank();
    }
  }

  private boolean isPresent(String parameter) {
    return parameter != null && parameter.trim().length() > 0;
  }

  private Result showGraph(String spreadsheetUrl) {
    Url url = new Url(spreadsheetUrl);
    if(url.isValid()) {
      return ok(graph.render(url));
    } else {
      return badRequest(blank.render(Option.apply(url.getValidationError())));
    }
  }

  private Result showBlank() {
    return ok(blank.render(Option.<String>empty()));
  }
}
