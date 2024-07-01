package ar.edu.utn.frba.dds.dominio.incidentes;

import ar.edu.utn.frba.dds.dominio.Heladera;
import java.time.OffsetDateTime;

public interface Incidente {

  public OffsetDateTime getFecha();

  public Heladera getHeladera();
}
