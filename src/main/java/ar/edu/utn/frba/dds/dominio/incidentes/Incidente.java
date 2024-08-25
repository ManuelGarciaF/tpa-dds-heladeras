package ar.edu.utn.frba.dds.dominio.incidentes;

import java.time.LocalDateTime;

public abstract class Incidente {
  private final LocalDateTime fecha;

  public Incidente(LocalDateTime fecha) {
    this.fecha = fecha;
  }

  public LocalDateTime getFecha() {
    return fecha;
  }

  abstract String getDescripcionDelError();
}
