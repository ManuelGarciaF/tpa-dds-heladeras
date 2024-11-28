package ar.edu.utn.frba.dds.model.sensoresheladera;

import ar.edu.utn.frba.dds.externo.LSensor;
import ar.edu.utn.frba.dds.model.ServiceLocator;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PostLoad;
import javax.persistence.Transient;

@Entity
@DiscriminatorValue("SENSOR")
public class ProveedorCantidadDeViandasSensor extends ProveedorCantidadDeViandas {
  private Integer cantidadDeViandas = 0;

  @Transient
  private Runnable nuevaMedicionHandler;

  public ProveedorCantidadDeViandasSensor(LSensor sensor) {
    sensor.onLunchBoxChanged(this::interpretarLectura);
  }

  public ProveedorCantidadDeViandasSensor() {
  }

  @PostLoad
  public void postLoad() {
    LSensor sensor = ServiceLocator.getLSensor();
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
