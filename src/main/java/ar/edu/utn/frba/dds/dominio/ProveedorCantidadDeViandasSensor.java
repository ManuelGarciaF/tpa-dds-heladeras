package ar.edu.utn.frba.dds.dominio;

import ar.edu.utn.frba.dds.externo.LSensor;
import java.time.LocalDateTime;

public class ProveedorCantidadDeViandasSensor implements ProveedorCantidadDeViandas{
  private LocalDateTime ultimaLectura;
  private Integer cantidadDeViandas = 0;

  public ProveedorCantidadDeViandasSensor(LSensor sensor) {
    sensor.onLunchBoxChanged(this::interpretarLectura);
  }

  public LocalDateTime getUltimaLectura() {
    return ultimaLectura;
  }

  public Integer getCantidadDeViandas() {
    return cantidadDeViandas;
  }

  public void agregarViandas(Integer cantidadDeViandas) {
    this.cantidadDeViandas += cantidadDeViandas;
    this.interpretarLectura(this.cantidadDeViandas);
  }

  public void retirarViandas(Integer cantidadDeViandas) {
    this.cantidadDeViandas -= cantidadDeViandas;
    this.interpretarLectura(this.cantidadDeViandas);
  }

  @Override
  public void interpretarLectura(int nuevaCantidad) {
    ultimaLectura = LocalDateTime.now();

  }
}
