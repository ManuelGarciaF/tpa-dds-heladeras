package ar.edu.utn.frba.dds.dominio.sensoresheladera;

public interface ProveedorCantidadDeViandas {
  Integer getCantidadDeViandas();

  void setNuevaMedicionHandler(Runnable nuevaMedicionHandler);
}
