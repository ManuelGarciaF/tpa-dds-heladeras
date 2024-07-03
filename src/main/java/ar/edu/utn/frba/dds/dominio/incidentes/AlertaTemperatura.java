package ar.edu.utn.frba.dds.dominio.incidentes;


import ar.edu.utn.frba.dds.dominio.Heladera;
import java.time.LocalDateTime;

public class AlertaTemperatura implements Incidente {
  private LocalDateTime fecha;

  //CONSTRUCTOR
  public AlertaTemperatura(LocalDateTime fecha) {
    this.fecha = fecha;
  }

  @Override
  public LocalDateTime getFecha() {
    return fecha;
  }

  @Override
  public String getDescripcion() {
    return "La temperatura de la heladera salio afuera de los limites permitidos.";
  }
}
