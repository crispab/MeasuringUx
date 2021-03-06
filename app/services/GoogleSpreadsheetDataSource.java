package services;

import com.google.gdata.client.spreadsheet.FeedURLFactory;
import com.google.gdata.client.spreadsheet.ListQuery;
import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.data.spreadsheet.ListEntry;
import com.google.gdata.data.spreadsheet.ListFeed;
import com.google.gdata.data.spreadsheet.WorksheetEntry;
import com.google.gdata.data.spreadsheet.WorksheetFeed;
import com.google.gdata.util.RedirectRequiredException;
import com.google.gdata.util.ServiceException;
import models.Url;
import models.UxData;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class GoogleSpreadsheetDataSource implements UxDataSource {

  private static final String APP_NAME = "MeasuringUx";

  @Override
  public UxData readUxData(Url spreadsheetUrl) {
    List<ListEntry> list;
    try {
      URL url = FeedURLFactory.getDefault().getWorksheetFeedUrl(extractKey(spreadsheetUrl), "public", "full");
      SpreadsheetService service = new SpreadsheetService(APP_NAME);
      WorksheetFeed feed = service.getFeed(url, WorksheetFeed.class);
      List<WorksheetEntry> worksheetList = feed.getEntries();
      WorksheetEntry worksheetEntry = worksheetList.get(0);
      URL listFeedUrl = worksheetEntry.getListFeedUrl();
      ListFeed listFeed = service.getFeed(listFeedUrl, ListFeed.class);
      list = listFeed.getEntries();
    } catch (RedirectRequiredException r) {
      throw new DataSourceException("Kan inte läsa dokumentet: inloggning krävs", r);
    } catch (ServiceException | IOException e) {
      throw new DataSourceException("Kan inte läsa dokumentet: " + e.getLocalizedMessage(), e);
    }

    if (list.size() > 0) {
      List<String> labels = new ArrayList<>(list.get(0).getCustomElements().getTags());
      labels.remove(0);
      Map<String, List<Double>> rows = new HashMap<>();
      for (ListEntry row : list) {
        String category = row.getTitle().getPlainText();
        List<Double> scores = new ArrayList<>();
        for (String label : labels) {
          scores.add(Double.parseDouble(row.getCustomElements().getValue(label).replace(',', '.')));
        }
        rows.put(category, scores);
      }
      return new UxData(labels, rows);
    }
    throw new DataSourceException("Dokumentet saknar data");
  }

  static String extractKey(Url googleSpreadsheetUrl) {
    Pattern keyPattern = Pattern.compile("^https://docs.google.com/\\S*spreadsheets/\\w*/(\\S+)/.*");
    Matcher keyMatcher = keyPattern.matcher(googleSpreadsheetUrl.getUrl());
    if (keyMatcher.matches()) {
      return keyMatcher.group(1);
    } else {
      throw new IllegalArgumentException("Länken anger inte ett Google Kalkylark");
    }
  }
}
