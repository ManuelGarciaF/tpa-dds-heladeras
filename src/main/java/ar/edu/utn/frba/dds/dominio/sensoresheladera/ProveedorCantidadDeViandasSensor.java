package ar.edu.utn.frba.dds.dominio.sensoresheladera;

import ar.edu.utn.frba.dds.externo.LSensor;

public class ProveedorCantidadDeViandasSensor implements ProveedorCantidadDeViandas {
  private Integer cantidadDeViandas;

  private Runnable nuevaMedicionHandler;

  public ProveedorCantidadDeViandasSensor(LSensor sensor) {
    sensor.onLunchBoxChanged(this::interpretarLectura);
  }

  @Override
  public Integer getCantidadDeViandas() {
    return cantidadDeViandas;
  }

  public void interpretarLectura(int nuevaCantidad) {
    cantidadDeViandas = nuevaCantidad;

    if (nuevaMedicionHandler != null) {
      nuevaMedicionHandler.run();
    }
  }

  @Override
  public void setNuevaMedicionHandler(Runnable nuevaMedicionHandler) {
    this.nuevaMedicionHandler = nuevaMedicionHandler;
  }
}
