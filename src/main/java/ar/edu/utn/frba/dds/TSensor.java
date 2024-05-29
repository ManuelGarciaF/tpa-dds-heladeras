package ar.edu.utn.frba.dds;

public interface TSensor {
  void connect(String serialNumber);
  void onTemperatureChange(Action action);
}
