package models;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

public class UxData {
  private final List<String> labels;
  private final Map<String, List<Double>> dataRows;

  public UxData(@NotNull List<String> labels, @NotNull Map<String, List<Double>> dataRows) {
    dataRows.values().stream().forEach(doubles -> {assert doubles.size() == labels.size();});

    this.labels = labels;
    this.dataRows = dataRows;
  }

  public List<String> getLabels() {
    return labels;
  }

  public Map<String, List<Double>> getDataRows() {
    return dataRows;
  }

  @Override
  public String toString() {
    return "UxData{" +
        "labels=" + labels +
        ", dataRows=" + dataRows +
        '}';
  }
}
