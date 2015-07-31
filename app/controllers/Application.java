package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.empty;
import views.html.graph;

public class Application extends Controller {

  public Result show(UrlParameter spreadsheetUrl) {
    if (spreadsheetUrl != null)
      return ok(graph.render(spreadsheetUrl.getValue().toString()));
    else
      return ok(empty.render());
  }

  public Result submit() {
      return redirect(routes.Application.show(null));
  }
}
