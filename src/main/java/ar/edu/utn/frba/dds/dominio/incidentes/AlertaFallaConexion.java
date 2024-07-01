package ar.edu.utn.frba.dds.dominio.incidentes;

import ar.edu.utn.frba.dds.dominio.Heladera;
import java.time.OffsetDateTime;

public class AlertaFallaConexion implements Incidente {
  private Heladera heladera;
  private OffsetDateTime fecha;
  private TipoDeFalla tipo;


  // CONSTRUCTOR
  public AlertaFallaConexion(Heladera heladera, TipoDeFalla tipo) {
    this.heladera = heladera;
    this.tipo = tipo;
    this.fecha = OffsetDateTime.now();
  }

  @Override
  public OffsetDateTime getFecha() {
    return this.fecha;
  }

  @Override
  public Heladera getHeladera() {
    return heladera;
  }
}
