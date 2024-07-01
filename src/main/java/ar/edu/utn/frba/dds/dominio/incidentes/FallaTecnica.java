package ar.edu.utn.frba.dds.dominio.incidentes;

import ar.edu.utn.frba.dds.dominio.Heladera;
import java.time.OffsetDateTime;

public class FallaTecnica implements Incidente {
  private Heladera heladera;
  private OffsetDateTime fecha;


  // CONSTRUCTOR
  public FallaTecnica(Heladera heladera) {
    this.heladera = heladera;
    fecha = OffsetDateTime.now();
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
