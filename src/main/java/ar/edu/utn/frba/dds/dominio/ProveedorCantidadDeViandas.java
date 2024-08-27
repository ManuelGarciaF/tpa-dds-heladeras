package ar.edu.utn.frba.dds.dominio;

public interface ProveedorCantidadDeViandas {
  Integer getCantidadDeViandas();

  void setNuevaMedicionHandler(Runnable nuevaMedicionHandler);
}
