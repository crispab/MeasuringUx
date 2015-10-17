package controllers;

import models.Url;
import models.UxData;
import play.mvc.Controller;
import play.mvc.Result;
import scala.Option;
import services.GoogleSpreadsheetDataSource;
import services.UxDataSource;
import views.html.blank;
import views.html.graph;


public class Application extends Controller {

  UxDataSource spreadsheetService = new GoogleSpreadsheetDataSource();

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
    try {
      Url url = new Url(spreadsheetUrl);
      if (url.isValid()) {
        UxData data = spreadsheetService.readUxData(url);
        return ok(graph.render(url, data));
      }
      return badRequest(blank.render(spreadsheetUrl, Option.apply(url.getValidationError())));
    } catch(RuntimeException e) {
      return badRequest(blank.render(spreadsheetUrl, Option.apply(e.getLocalizedMessage())));
    }
  }

  private Result showBlank() {
    return ok(blank.render("", Option.<String>empty()));
  }
}
