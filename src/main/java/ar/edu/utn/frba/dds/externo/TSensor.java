package ar.edu.utn.frba.dds.externo;

@SuppressWarnings({"checkstyle:AbbreviationAsWordInName", "checkstyle:MissingJavadocType"})
public interface TSensor {
  void connect(String serialNumber);

  void onTemperatureChange(Action action);
}
