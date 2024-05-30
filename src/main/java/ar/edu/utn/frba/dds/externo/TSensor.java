package ar.edu.utn.frba.dds.externo;

public interface TSensor {
  void connect(String serialNumber);

  void onTemperatureChange(Action action);
}
