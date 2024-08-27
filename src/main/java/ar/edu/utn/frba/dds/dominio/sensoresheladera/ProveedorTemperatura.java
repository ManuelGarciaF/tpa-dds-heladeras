package ar.edu.utn.frba.dds.dominio.sensoresheladera;

import java.util.List;

public interface ProveedorTemperatura {
  List<Double> ultimasTresTemperaturas();

  void setCheckeoDeTemperaturaHandler(Runnable checkeoDeTemperaturaHandler);

  boolean hayFalloConexion();
}
