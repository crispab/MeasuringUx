package services;

import com.google.gdata.client.spreadsheet.FeedURLFactory;
import com.google.gdata.client.spreadsheet.ListQuery;
import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.data.spreadsheet.ListEntry;
import com.google.gdata.data.spreadsheet.ListFeed;
import com.google.gdata.data.spreadsheet.WorksheetEntry;
import com.google.gdata.data.spreadsheet.WorksheetFeed;
import com.google.gdata.util.ServiceException;
import models.Url;
import models.UxData;

import java.io.IOException;
import java.io.UncheckedIOException;
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
      SpreadsheetService service = new SpreadsheetService(APP_NAME);

      URL url = FeedURLFactory.getDefault().getWorksheetFeedUrl(extractKey(spreadsheetUrl), "public", "full");

      WorksheetFeed feed = service.getFeed(url, WorksheetFeed.class);
      List<WorksheetEntry> worksheetList = feed.getEntries();
      WorksheetEntry worksheetEntry = worksheetList.get(0);

      ListQuery listQuery = new ListQuery(worksheetEntry.getListFeedUrl());

      ListFeed listFeed = service.query(listQuery, ListFeed.class);
      list = listFeed.getEntries();

    } catch (ServiceException | IOException e) {
      throw new DataSourceException("Unable to read document: " + spreadsheetUrl, e);
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
    throw new DataSourceException("Not enough data in document: " + spreadsheetUrl);
  }

  static String extractKey(Url googleSpreadsheetUrl) {
    Pattern keyPattern = Pattern.compile("^https://docs.google.com/\\S*spreadsheets/\\w*/(\\S+)/.*");
    Matcher keyMatcher = keyPattern.matcher(googleSpreadsheetUrl.getUrl());
    if (keyMatcher.matches()) {
      return keyMatcher.group(1);
    } else {
      throw new IllegalArgumentException("Not a Google Spreadsheet URL: " + googleSpreadsheetUrl);
    }
  }
}
