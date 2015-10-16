package services;

import models.Url;
import models.UxData;

public interface UxDataSource {
  UxData readUxData(Url url);
}
