package ar.edu.utn.frba.dds.dominio.incidentes;


import ar.edu.utn.frba.dds.dominio.Heladera;
import java.time.LocalDate;

public class AlertaTemperatura implements Incidente {
  private LocalDate fecha;
  private Heladera heladera;


  //CONSTRUCTOR
  public AlertaTemperatura(Heladera heladera) {
    this.heladera = heladera;
    this.fecha = LocalDate.now();
  }

  @Override
  public LocalDate getFecha() {
    return fecha;
  }

  @Override
  public Heladera getHeladera() {
    return heladera;
  }
}
