package ar.edu.utn.frba.dds.dominio.incidentes;


import ar.edu.utn.frba.dds.dominio.Heladera;
import java.time.OffsetDateTime;

public class AlertaTemperatura implements Incidente {
  private OffsetDateTime fecha;
  private Heladera heladera;


  //CONSTRUCTOR
  public AlertaTemperatura(Heladera heladera) {
    this.heladera = heladera;
    this.fecha = OffsetDateTime.now();
  }

  @Override
  public OffsetDateTime getFecha() {
    return fecha;
  }

  @Override
  public Heladera getHeladera() {
    return heladera;
  }
}
