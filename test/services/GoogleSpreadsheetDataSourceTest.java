package services;

import models.Url;
import org.junit.Test;

import static org.junit.Assert.*;

public class GoogleSpreadsheetDataSourceTest {

  @Test
  public void testExtractKey1() throws Exception {
    assertEquals("10D2OrjQoVq7R56UIQGzTRaR9ACl3n8K2IBSDmXwLkZY", GoogleSpreadsheetDataSource.extractKey(new Url("https://docs.google.com/a/crisp.se/spreadsheets/d/10D2OrjQoVq7R56UIQGzTRaR9ACl3n8K2IBSDmXwLkZY/edit?usp=sharing")));
  }

  @Test
  public void testExtractKey2() throws Exception {
    assertEquals("10D2OrjQoVq7R56UIQGzTRaR9ACl3n8K2IBSDmXwLkZY", GoogleSpreadsheetDataSource.extractKey(new Url("https://docs.google.com/spreadsheets/d/10D2OrjQoVq7R56UIQGzTRaR9ACl3n8K2IBSDmXwLkZY/edit#gid=0")));
  }
}