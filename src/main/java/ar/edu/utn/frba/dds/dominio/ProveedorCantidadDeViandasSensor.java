package ar.edu.utn.frba.dds.dominio;

import ar.edu.utn.frba.dds.externo.LSensor;
import java.time.LocalDateTime;

public class ProveedorCantidadDeViandasSensor implements ProveedorCantidadDeViandas{
  private Integer cantidadDeViandasParaNotificar;
  private LocalDateTime ultimaLectura;

  public ProveedorCantidadDeViandasSensor(LSensor sensor) {
    sensor.onLunchBoxChanged(this::interpretarLectura);
  }

  @Override
  public void interpretarLectura(int nuevaCantidad) {
    ultimaLectura = LocalDateTime.now();
    if (nuevaCantidad <= cantidadDeViandasParaNotificar) {
      //NOTIFICAR A LAS PERSONAS
    }
  }
}
