package ar.edu.utn.frba.dds.dominio.incidentes;

import ar.edu.utn.frba.dds.dominio.Heladera;
import ar.edu.utn.frba.dds.dominio.Ubicacion;
import java.time.LocalDateTime;

// podria ser un abstract????
public interface Incidente {
  LocalDateTime getFecha();

  String getDescripcion();
  Ubicacion getUbicacionDelIncidente();
}
