package ar.edu.utn.frba.dds.dominio;

import ar.edu.utn.frba.dds.dominio.notificacionesHeladera.NotificacionHeladeraHandler;
import ar.edu.utn.frba.dds.externo.LSensor;

public class ProveedorCantidadDeViandasSensor implements ProveedorCantidadDeViandas {
  private Integer cantidadDeViandas;

  public ProveedorCantidadDeViandasSensor(LSensor sensor) {
    sensor.onLunchBoxChanged(this::interpretarLectura);
  }

  public Integer getCantidadDeViandas() {
    return cantidadDeViandas;
  }

  private void interpretarLectura(int nuevaCantidad) {
    cantidadDeViandas = nuevaCantidad;
  }
}
