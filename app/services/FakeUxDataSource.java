package services;

import models.Url;
import models.UxData;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class FakeUxDataSource implements UxDataSource {
  @Override
  public UxData readUxData(Url url) {
    List<String> labels = Arrays.asList(
        "Användbarhet",
        "Användarvänlighet",
        "Yttre effektivitet",
        "Inre effektivitet",
        "Tillfredsställande",
        "Mognadsgrad",
        "Felhantering",
        "Gemytlighet",
        "Följdriktighet",
        "Enkelhet",
        "Lärbarhet",
        "Tillförsikt");

    Map<String, List<Double>> dataRows = new LinkedHashMap<String, List<Double>>(){
      {
        put("Experter", Arrays.asList(6.0, 6.0, 7.0, 7.0, 5.0, 5.0, 4.0, 6.0, 7.0, 7.0, 4.0, 3.0));
        put("Nybörjare", Arrays.asList(2.0, 4.0, 4.0, 2.0, 5.0, 3.0, 7.0, 4.0, 1.0, 7.0, 3.0, 5.0));
      }
    };

    return new UxData(labels, dataRows);
  }
}
