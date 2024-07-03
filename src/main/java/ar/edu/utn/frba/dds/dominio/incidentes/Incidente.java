package ar.edu.utn.frba.dds.dominio.incidentes;

import ar.edu.utn.frba.dds.dominio.Heladera;
import java.time.LocalDateTime;

public interface Incidente {
  LocalDateTime getFecha();

  String getDescripcion();
}
