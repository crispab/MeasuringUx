package services;

import com.google.gdata.client.spreadsheet.FeedURLFactory;
import com.google.gdata.client.spreadsheet.ListQuery;
import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.data.spreadsheet.CustomElementCollection;
import com.google.gdata.data.spreadsheet.ListEntry;
import com.google.gdata.data.spreadsheet.ListFeed;
import com.google.gdata.data.spreadsheet.WorksheetEntry;
import com.google.gdata.data.spreadsheet.WorksheetFeed;
import com.google.gdata.util.ServiceException;
import models.Url;
import models.UxData;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 https://docs.google.com/spreadsheets/d/17fnWPmxkjTaR06LqNMHc3Kes9uNfPYwmmVTUgeb8F2g/edit?usp=sharing
 */
public class GoogleUxDataSource implements UxDataSource {

  private static final String KEY = "17fnWPmxkjTaR06LqNMHc3Kes9uNfPYwmmVTUgeb8F2g";
  private static final String APP_NAME = "MeasuringUx";

  @Override
  public UxData readUxData(Url spreadsheetUrl) {
    try {
      SpreadsheetService service = new SpreadsheetService(APP_NAME);

      URL url = FeedURLFactory.getDefault().getWorksheetFeedUrl(KEY, "public", "basic");

      WorksheetFeed feed = service.getFeed(url, WorksheetFeed.class);
      List<WorksheetEntry> worksheetList = feed.getEntries();
      WorksheetEntry worksheetEntry = worksheetList.get(0);

      ListQuery listQuery = new ListQuery(worksheetEntry.getListFeedUrl());

      ListFeed listFeed = service.query(listQuery, ListFeed.class);
      List<ListEntry> list = listFeed.getEntries();
      for (ListEntry row : list) {
        System.out.println("content=[" + row.getPlainTextContent() + "]");
        System.out.println(row.getTitle().getPlainText() + "\t" + row.getPlainTextContent());
      }

    } catch (ServiceException e) {
      e.printStackTrace();
    } catch (MalformedURLException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }

    return new FakeUxDataSource().readUxData(spreadsheetUrl);
  }
}
