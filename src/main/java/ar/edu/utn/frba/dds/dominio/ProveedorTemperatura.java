package ar.edu.utn.frba.dds.dominio;

import java.util.List;

public interface ProveedorTemperatura {
  List<Double> ultimasTresTemperaturas();

  void setCheckeoDeTemperaturaHandler(Runnable checkeoDeTemperaturaHandler);

  boolean hayFalloConexion();
}
